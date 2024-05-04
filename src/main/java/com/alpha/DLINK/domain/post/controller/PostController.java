package com.alpha.DLINK.domain.post.controller;

import com.alpha.DLINK.domain.file.domain.File;
import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.likeHistory.service.LikeHistoryService;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.service.MemberService;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.dto.FindPostDTO;
import com.alpha.DLINK.domain.post.dto.PostDetailDTO;
import com.alpha.DLINK.domain.post.service.PostService;
import com.alpha.DLINK.setting.S3.S3FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
@Slf4j
public class PostController {
    private final PostService postService;
    private final S3FileService s3FileService;
    private final MemberService memberService;
    private final LikeHistoryService likeHistoryService;

    @GetMapping
    @Operation(summary = "게시글 전체 조회", description = "최근 게시글 순으로 정렬")
    public List<FindPostDTO> findAll() {
        return postService.findAll();
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "게시글 정보 + 사용자가 좋아요를 눌렀는 지 알려주는 boolean 값")
    public PostDetailDTO findById(@AuthenticationPrincipal Member member,
                                  @PathVariable("postId") Long postId) throws Exception {
        Post post = postService.findById(postId);
        Optional<LikeHistory> history = likeHistoryService.findHistory(member.getId(), postId);
        if(history.isPresent()) {
            return new PostDetailDTO(post, true);
        }
        return new PostDetailDTO(post, false);
    }

    @PostMapping("/create")
    @Operation(summary = "게시글 생성", description = "formData에 제목, 내용, multipart file 데이터 담아서 전송하기.")
    public ResponseEntity<String> createPost(@AuthenticationPrincipal Member member,
                                             @RequestParam("files") List<MultipartFile> files,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content) {
        try {
            Member findMember = memberService.findByEmail(member.getEmail());
            postService.create(title, content, findMember, files);

            return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글 및 s3 버킷 사진 파일 삭제")
    public void deletePost(@PathVariable Long postId) {
        Post post = postService.findById(postId);
        List<File> files = post.getFiles();
        for (File file : files) {
            s3FileService.deletePostImageFile("post_image", file.getUrl());
        }
        postService.delete(post);
    }

    @PatchMapping("/update/{postId}")
    @Operation(summary = "게시글 수정", description = "제목 및 내용 수정하기")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody FindPostDTO findPostDto) {
        Post post = postService.findById(postId);
        postService.update(post, findPostDto.getTitle(), findPostDto.getContent());

        return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
    }
}
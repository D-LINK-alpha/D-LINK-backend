package com.alpha.DLINK.domain.likeHistory.service;


import com.alpha.DLINK.domain.likeHistory.domain.LikeHistory;
import com.alpha.DLINK.domain.likeHistory.repository.LikeHistoryRepository;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.domain.member.repository.MemberRepository;
import com.alpha.DLINK.domain.post.domain.Post;
import com.alpha.DLINK.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeHistoryService {

    private final LikeHistoryRepository likeHistoryRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Optional<LikeHistory> findHistory(Long memberId, Long postId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(Exception::new);
        Post post = postRepository.findById(postId).orElseThrow(Exception::new);

        return likeHistoryRepository.findByMemberAndPost(member, post);
    }

    public boolean setLike(Long memberId, Long postId) throws Exception {
        Member member = memberRepository.findById(memberId).orElseThrow(Exception::new);
        Post post = postRepository.findById(postId).orElseThrow(Exception::new);

        LikeHistory isPresent = likeHistoryRepository.findByMemberAndPost(member, post).orElse(null);

        if (isPresent == null) {
            LikeHistory likeHistory = LikeHistory.create(member, post);
            post.setLikes(post.getLikes() + 1);
            likeHistoryRepository.save(likeHistory);
        } else {
            Long likes = post.getLikes();
            if(likes >= 0) post.setLikes(likes - 1);
            likeHistoryRepository.delete(isPresent);
        }
        return isPresent == null;
    }
}
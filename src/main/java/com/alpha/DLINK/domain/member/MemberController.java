package com.alpha.DLINK.domain.member;

import com.alpha.DLINK.domain.member.dto.SignupDto;
import com.alpha.DLINK.domain.member.entity.Member;
import com.alpha.DLINK.setting.jwt.JwtProvider;
import com.alpha.DLINK.setting.oauth2.domain.CustomOauth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class MemberController {

    private final JwtProvider jwtProvider;

    @GetMapping("/join")
    public String test() {
        return "에러 난다고!!";
    }

    @PostMapping("/join")
    public ResponseEntity<Member> signUp(@RequestBody SignupDto signupDto,
                       @AuthenticationPrincipal CustomOauth2User customOauth2User) {

        Member member = customOauth2User.getMember();
        member.setNickname(signupDto.getNickname());

        String accessToken = jwtProvider.createAccessToken(member.getEmail());

        return ResponseEntity.ok()
                .header(AUTHORIZATION, "Bearer" + accessToken)
                .body(member);
    }
}
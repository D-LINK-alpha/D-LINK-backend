package com.alpha.DLINK.domain.recommendHistory.domain;

import com.alpha.DLINK.domain.beverage.domain.Beverage;
import com.alpha.DLINK.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RecommendHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "recommend_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    @Column(name = "is_like")
    private Boolean isLike;

    @Column(name = "is_recommended")
    private Boolean isRecommended;

    @Column(name = "similarity")
    private String similarity;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static RecommendHistory create(Member member, Beverage beverage) {
        RecommendHistory recommendHistory = new RecommendHistory();
        recommendHistory.setSimilarity("0");
        recommendHistory.setIsLike(false);
        recommendHistory.setIsRecommended(false);
        recommendHistory.setMember(member);
        recommendHistory.setBeverage(beverage);

        return recommendHistory;
    }
}
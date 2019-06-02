package com.haseong.demo.entity;

import com.haseong.demo.model.stereotype.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity {
    /**
     * 회원 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Integer memberId;
    /**
     * 인증 제공자
     */
    @Column(name = "provider_type")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
    /**
     * 인증 제공자에서 사용하는 아이디
     */
    @Column(name = "provider_user_id")
    private String providerUserId;
    /**
     * 별명
     */
    @Column(name = "nickname")
    private String nickname;
    /**
     * 생성 시간
     */
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    /**
     * 수정 시간
     */
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

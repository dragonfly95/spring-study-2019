package com.haseong.demo.dto;

import com.haseong.demo.entity.MemberEntity;
import com.haseong.demo.model.stereotype.ProviderType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class MemberResponse {
    /**
     * 회원 아이디
     */
    private Integer id;
    /**
     * 별명
     */
    private String nickname;
    /**
     * 인증 제공자
     */
    private ProviderType providerType;
    /**
     * 인증 제공자에서 사용하는 user_id
     */
    private String providerUserId;
    /**
     * 생성 시간
     */
    private LocalDateTime createdAt;
    /**
     * 수정 시간
     */
    private LocalDateTime updatedAt;

    public static MemberResponse from(MemberEntity memberEntity) {
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.id = memberEntity.getMemberId();
        memberResponse.nickname = memberEntity.getNickname();
        memberResponse.providerType = memberEntity.getProviderType();
        memberResponse.providerUserId = memberEntity.getProviderUserId();
        memberResponse.createdAt = memberEntity.getCreatedAt();
        memberResponse.updatedAt = memberEntity.getUpdatedAt();
        return memberResponse;
    }
}

package com.haseong.demo.dto;

import com.haseong.demo.model.stereotype.ProviderType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberRequest {
    private String nickname;
    private ProviderType providerType;
    private String providerUserId;
}

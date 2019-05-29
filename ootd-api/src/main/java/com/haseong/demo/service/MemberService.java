package com.haseong.demo.service;

import com.haseong.demo.entity.MemberEntity;
import com.haseong.demo.model.stereotype.ProviderType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    MemberEntity createMember(String nickname, ProviderType providerType, String providerUserId);

    MemberEntity getMember(Integer memberId);

    List<MemberEntity> getMembers(Pageable pageable);
}

package com.haseong.demo.service.impl;

import com.haseong.demo.entity.MemberEntity;
import com.haseong.demo.exception.ApiFailedException;
import com.haseong.demo.model.stereotype.ProviderType;
import com.haseong.demo.repository.MemberRepository;
import com.haseong.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberEntity createMember(String nickname, ProviderType providerType, String providerUserId) {
        if (memberRepository.findByProviderTypeAndProviderUserId(providerType, providerUserId).isPresent()) {
            throw ApiFailedException.of(HttpStatus.BAD_REQUEST, "이미 가입한 회원입니다. ");
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname(nickname);
        memberEntity.setProviderType(providerType);
        memberEntity.setProviderUserId(providerUserId);
        return memberRepository.save(memberEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberEntity getMember(Integer memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> ApiFailedException.of(HttpStatus.NOT_FOUND, "member not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberEntity> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable).getContent();
    }

}

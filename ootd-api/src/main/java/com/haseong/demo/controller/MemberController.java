package com.haseong.demo.controller;

import com.haseong.demo.dto.MemberRequest;
import com.haseong.demo.dto.MemberResponse;
import com.haseong.demo.entity.MemberEntity;
import com.haseong.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원등록
     * @param memberRequest
     * @return
     */
    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse createMember(@RequestBody MemberRequest memberRequest) {
        MemberEntity memberEntity = memberService.createMember(memberRequest.getNickname(),
                memberRequest.getProviderType(),
                memberRequest.getProviderUserId());
        return MemberResponse.from(memberEntity);
    }

    /**
     * 회원목록 조회
     * @param pageable
     * @return
     */
    @GetMapping("/members")
    public List<MemberResponse> getMembers(Pageable pageable) {
        return memberService.getMembers(pageable).stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}

package com.haseong.demo.dto;

import com.haseong.demo.entity.MemberEntity;
import com.haseong.demo.entity.PostEntity;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class PostResponse {
    /**
     * 글 id
     */
    private Integer id;
    /**
     * 글쓴사람
     */
    private MemberResponse member;
    /**
     * 이미지 주소
     */
    private String imageUrl;
    /**
     * 본문
     */
    private String description;
    /**
     * 좋아요 개수
     */
    private Integer count;
    /**
     * 생성 시간
     */
    private LocalDateTime createdAt;
    /**
     * 마지막 수정 시간
     */
    private LocalDateTime updatedAt;

    public static PostResponse of(PostEntity postEntity, MemberEntity memberEntity) {
        PostResponse postResponse = new PostResponse();
        postResponse.id = postEntity.getPostId();
        postResponse.imageUrl = postEntity.getImageUrl();
        postResponse.description = postEntity.getDescription();
        postResponse.count = postEntity.getCount();
        postResponse.createdAt = postEntity.getCreatedAt();
        postResponse.updatedAt = postEntity.getUpdatedAt();
        postResponse.member = MemberResponse.from(memberEntity);
        return postResponse;
    }
}

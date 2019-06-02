package com.haseong.demo.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequest {
    /**
     * 이미지 주소
     */
    private String imageUrl;
    /**
     * 글 본문
     */
    private String description;
}

package com.haseong.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@Data
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    /**
     * 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer postId;

    /**
     * 글쓴사람 아이디
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 본문
     */
    @Column
    private String description;

    /**
     * 이미지 주소
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 좋아요 개수
     */
    @Column(name = "count")
    private Integer count;

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

    public void addCount() {
        count += 1;
    }

    public void subCount() {
        count -= 1;
    }

}

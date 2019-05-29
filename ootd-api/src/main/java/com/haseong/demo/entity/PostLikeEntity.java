package com.haseong.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "PostLikeEntity")
@Data
public class PostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_like_id")
    private Integer postLikeId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "post_id")
    private Integer postId;
}

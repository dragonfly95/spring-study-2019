package com.haseong.demo.service;

import com.haseong.demo.entity.PostEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostEntity createPost(Integer memberId, String description, String imageUrl);

    List<PostEntity> getPosts(Pageable pageable);

    PostEntity likePost(Integer memberId, Integer postId);

    PostEntity unlikePost(Integer memberId, Integer postId);

    List<PostEntity> recommendPosts(Integer memberId);
}

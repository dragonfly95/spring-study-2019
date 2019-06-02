package com.haseong.demo.service.impl;

import com.haseong.demo.entity.MemberEntity;
import com.haseong.demo.entity.PostEntity;
import com.haseong.demo.entity.PostLikeEntity;
import com.haseong.demo.exception.ApiFailedException;
import com.haseong.demo.repository.MemberRepository;
import com.haseong.demo.repository.PostLikeRepository;
import com.haseong.demo.repository.PostRepository;
import com.haseong.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    @Transactional
    public PostEntity createPost(Integer memberId, String description, String imageUrl) {
        PostEntity postEntity = new PostEntity();
        postEntity.setMemberId(memberId);
        postEntity.setDescription(description);
        postEntity.setImageUrl(imageUrl);
        postEntity.setCount(0);
        return postRepository.save(postEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostEntity> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public PostEntity likePost(Integer memberId, Integer postId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> ApiFailedException.of(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> ApiFailedException.of(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        if (postLikeRepository.findByMemberIdAndPostId(memberId, postId).isPresent()) {
            return postEntity;
        }

        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.setMemberId(memberId);
        postLikeEntity.setPostId(postId);
        postLikeRepository.save(postLikeEntity);

        postEntity.addCount();
        return postEntity;
    }

    @Override
    @Transactional
    public PostEntity unlikePost(Integer memberId, Integer postId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> ApiFailedException.of(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> ApiFailedException.of(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."));

        PostLikeEntity postLikeEntity = postLikeRepository.findByMemberIdAndPostId(memberId, postId).orElse(null);
        if (postLikeEntity == null) {
            return postEntity;
        }

        postLikeRepository.delete(postLikeEntity);
        postEntity.subCount();

        return postEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostEntity> recommendPosts(Integer memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> ApiFailedException.of(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."));
        List<PostEntity> postEntities = postRepository.findAll();
        Collections.shuffle(postEntities);
        int index = Integer.min(postEntities.size(), 5);
        return postEntities.subList(0, index);
    }
}

package com.suzy.community_be.posts.repository;

import com.suzy.community_be.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
} 
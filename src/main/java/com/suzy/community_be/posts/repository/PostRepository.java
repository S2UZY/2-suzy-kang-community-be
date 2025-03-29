package com.suzy.community_be.posts.repository;

import com.suzy.community_be.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes LEFT JOIN FETCH p.comments")
    List<Post> findAllWithLikesAndComments();
} 
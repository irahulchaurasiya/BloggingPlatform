package com.blog.BloggingPlatform.repository;

import com.blog.BloggingPlatform.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepo extends JpaRepository<Post, Long> {
}

package com.blog.BloggingPlatform.repository;


import com.blog.BloggingPlatform.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICommentRepo extends JpaRepository<Comment, Long> {
}

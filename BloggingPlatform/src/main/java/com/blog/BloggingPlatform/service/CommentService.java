package com.blog.BloggingPlatform.service;

import com.blog.BloggingPlatform.models.Comment;
import com.blog.BloggingPlatform.models.User;
import com.blog.BloggingPlatform.repository.ICommentRepo;
import com.blog.BloggingPlatform.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class CommentService {

    @Autowired
    ICommentRepo commentRepo;

    @Autowired
    IPostRepo postRepo;

    public String addComment(Comment comment, User user) {
        comment.setCommenter(user);
        commentRepo.save(comment);
        return "Comment Done!!!";
    }

    public String removeBlogComment(Long commentId, User user) {
        Comment comment  = commentRepo.findById(commentId).orElse(null);
        if(comment != null && comment.getCommenter().equals(user))
        {
            commentRepo.deleteById(commentId);
            return "Comment Removed successfully";
        }
        else if (comment == null)
        {
            return "Comment to be deleted does not exist";
        }
        else{
            return "Un-Authorized delete detected....Not allowed";
        }
    }

}

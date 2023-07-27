package com.blog.BloggingPlatform.service;

import com.blog.BloggingPlatform.models.Post;
import com.blog.BloggingPlatform.models.User;
import com.blog.BloggingPlatform.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    IPostRepo postRepo;

    public String createBlogPost(Post post) {
        post.setCreateDate(LocalDateTime.now());
        postRepo.save(post);
        return "Blog posted Successfully!!!";
    }

    public String updateBlogPost(Post updatePost) {
        Post post = postRepo.getReferenceById(updatePost.getPostId());
        post.setPostData(updatePost.getPostData());
        postRepo.save(post);
        return "Blog Updated Successfully!!!";
    }

    public String removeBlogPost(Long postId, User user) {
        Post post  = postRepo.findById(postId).orElse(null);
        if(post != null && post.getBlogOwner().equals(user))
        {
            postRepo.deleteById(postId);
            return "Blog Removed successfully";
        }
        else if (post == null)
        {
            return "Blog to be deleted does not exist";
        }
        else{
            return "Un-Authorized delete detected....Not allowed";
        }
    }

    public Object getAllBlogs() {
        return postRepo.findAll();
    }

    public Object getBlogById(Long postId) {
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty())
        {
            return "Blog doesn't exist!!!";
        }
        else
        {
            return post;
        }
    }

}

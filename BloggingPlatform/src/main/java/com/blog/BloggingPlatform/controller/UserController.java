package com.blog.BloggingPlatform.controller;

import com.blog.BloggingPlatform.models.Comment;
import com.blog.BloggingPlatform.models.Follow;
import com.blog.BloggingPlatform.models.Post;
import com.blog.BloggingPlatform.models.User;
import com.blog.BloggingPlatform.models.dto.SignInInput;
import com.blog.BloggingPlatform.models.dto.SignUpOutput;
import com.blog.BloggingPlatform.service.AuthenticationService;
import com.blog.BloggingPlatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;


    //sign up, sign in , sign out a particular user
    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String signInUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutUser(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.signOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }

    //creation of post

    @PostMapping("post/postBlog")
    public String createBlogPost(@RequestBody @Valid Post post, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.createBlogPost(post,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @PutMapping("post/updateBlog")
    public String updateBlogPost(@RequestBody @Valid Post updatePost, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.updateBlogPost(updatePost,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("post/deleteBlog")
    public String removeBlogPost(@RequestParam Long postId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogPost(postId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    //commenting functionalities

    @PostMapping("blog/addComment")
    public String addComment(@RequestBody @Valid Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterToken)
    {
        if(authenticationService.authenticate(commenterEmail,commenterToken)) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("blog/deleteComment")
    public String removeBlogComment(@RequestParam Long commentId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeBlogComment(commentId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("/getAllBlogs")
    public Object getAll(@RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.getAllBlogs();
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("/getBlog/{postId}")
    public Object getBlogById(@RequestParam String email, @RequestParam String token , @PathVariable Long postId)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.getBlogById(postId);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }



    //follow functionality

    @PostMapping("follow/user")
    public String followUser(@RequestBody @Valid Follow follow, @RequestParam String followerEmail, @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.followUser(follow,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("unfollow/user/{followId}")
    public String unFollowUser(@PathVariable Long followId, @RequestParam String followerEmail, @RequestParam String followerToken)
    {
        if(authenticationService.authenticate(followerEmail,followerToken)) {
            return userService.unFollowUser(followId,followerEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
}

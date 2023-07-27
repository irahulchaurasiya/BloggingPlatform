package com.blog.BloggingPlatform.service;

import com.blog.BloggingPlatform.models.*;
import com.blog.BloggingPlatform.models.dto.SignInInput;
import com.blog.BloggingPlatform.models.dto.SignUpOutput;
import com.blog.BloggingPlatform.repository.IUserRepo;
import com.blog.BloggingPlatform.service.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(false,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(false,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //saveAppointment the user with the new encrypted password

            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(true, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(false,signUpStatusMessage);
        }
    }


    public String signInUser(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                return "your token is = " + authToken.getTokenValue();
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }


    public String signOutUser(String email) {

        User user = userRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";
    }

    public String createBlogPost(Post post, String email) {
        User postOwner = userRepo.findFirstByUserEmail(email);
        post.setBlogOwner(postOwner);
        return postService.createBlogPost(post);
    }

    public String updateBlogPost(Post updatePost, String email) {
        User postOwner = userRepo.findFirstByUserEmail(email);
        updatePost.setBlogOwner(postOwner);
        return postService.updateBlogPost(updatePost);
    }

    public String removeBlogPost(Long postId, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return postService.removeBlogPost(postId , user);
    }

    public String addComment(Comment comment, String commenterEmail) {
        User user = userRepo.findFirstByUserEmail(commenterEmail);
        return commentService.addComment(comment , user);
    }

    public String removeBlogComment(Long commentId, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return commentService.removeBlogComment(commentId , user);
    }

    public Object getAllBlogs() {
        return postService.getAllBlogs();
    }

    public Object getBlogById(Long postId) {
        return postService.getBlogById(postId);
    }

    public String followUser(Follow follow, String followerEmail) {


        User followTargetUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);

        User follower = userRepo.findFirstByUserEmail(followerEmail);

        if(followTargetUser!=null)
        {
            if(followService.isFollowAllowed(followTargetUser,follower))
            {
                followService.startFollowing(follow,follower);
                return follower.getUserName()  + " is now following " + followTargetUser.getUserName();
            }
            else {
                return follower.getUserName()  + " already follows " + followTargetUser.getUserName();
            }
        }
        else {
            return "User to be followed is Invalid!!!";
        }


    }

    private boolean authorizeUnfollow(String email, Follow follow) {

        String  targetEmail = follow.getCurrentUser().getUserEmail();
        String  followerEmail  = follow.getCurrentUserFollower().getUserEmail();

        return targetEmail.equals(email) || followerEmail.equals(email);
    }

    public String unFollowUser(Long followId, String followerEmail) {

        Follow follow  = followService.findFollow(followId);
        if(follow != null)
        {
            if(authorizeUnfollow(followerEmail,follow))
            {
                followService.unfollow(follow);
                return follow.getCurrentUser().getUserName() + " not followed by " + followerEmail;
            }
            else
            {
                return "Unauthorized unfollow detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid follow mapping";
        }
    }

}

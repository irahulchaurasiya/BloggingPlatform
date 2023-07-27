package com.blog.BloggingPlatform.repository;

import com.blog.BloggingPlatform.models.AuthenticationToken;
import com.blog.BloggingPlatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken, Long> {

    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
package com.blog.BloggingPlatform.repository;

import com.blog.BloggingPlatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User , Long> {
    User findFirstByUserEmail(String newEmail);
}

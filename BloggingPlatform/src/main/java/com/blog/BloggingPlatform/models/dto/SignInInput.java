package com.blog.BloggingPlatform.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInInput {

    private String email;
    private String password;
}

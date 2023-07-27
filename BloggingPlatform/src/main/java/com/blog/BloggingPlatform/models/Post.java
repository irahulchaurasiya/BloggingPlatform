package com.blog.BloggingPlatform.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String postData;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "Fk_User_ID")
    private User blogOwner;
}

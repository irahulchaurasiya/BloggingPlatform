package com.blog.BloggingPlatform.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PostComment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String commentBody;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private LocalDateTime commentCreationTimeStamp;

    @ManyToOne
    @JoinColumn(name = "Fk_Post_ID")
    private Post blog;

    @ManyToOne
    @JoinColumn(nullable = false,name = "Fk_User_ID")
    private User commenter;
}

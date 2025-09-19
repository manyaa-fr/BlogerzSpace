package com.ghanshyam.blogera.dto;

import com.ghanshyam.blogera.blog.Blog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorUsername;

    public PostResponse(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.createdAt = blog.getCreatedAt();
        this.authorUsername = blog.getAuthor().getUsername();
    }
    public PostResponse() {}

}
package com.ghanshyam.blogera.dto;

import com.ghanshyam.blogera.blog.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private UserDto author;

    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.createdAt = blog.getCreatedAt();
        this.author = new UserDto(blog.getAuthor());
    }



}
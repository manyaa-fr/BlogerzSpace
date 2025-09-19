package com.ghanshyam.blogera.blog;

import com.ghanshyam.blogera.dto.BlogDto;
import com.ghanshyam.blogera.dto.PostRequest;
import com.ghanshyam.blogera.dto.PostResponse;
import com.ghanshyam.blogera.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public ResponseEntity<List<PostResponse>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlog());  // already DTO
    }

    //    @GetMapping("/blogs/{id}")
//    public Optional<Blog> getBlog(@PathVariable long id) {
//        return blogService.getBlogById(id);
//    }
    @GetMapping("/blogs/{id}")
    public ResponseEntity<PostResponse> getBlogById(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return ResponseEntity.ok(new PostResponse(blog));
    }



    @PostMapping("/blogs/post")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createBlog(@RequestBody PostRequest postRequest) {
        blogService.createBlog(postRequest);
        return new ResponseEntity<>("Your blog has been successfully posted!", HttpStatus.OK);
    }

    @DeleteMapping("/blogs/delete/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable long id) {
        blogService.deleteBlog(id);
        return new ResponseEntity<>("Blog is deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/blogs/update/{id}")
    public ResponseEntity<String> updateBlog(@PathVariable long id, @RequestBody Blog blog) {
        blogService.update(id, blog);

        return new ResponseEntity<>("Blog is updated", HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostResponse>> getBlogsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(blogService.getBlogByusername(username));
    }
}

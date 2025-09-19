package com.ghanshyam.blogera;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    //test route: create a post
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createPost() {
        return ResponseEntity.ok("Post created successfully!");
    }

    //test route: see if route is up
    @GetMapping
    public ResponseEntity<String> getAllPosts() {
        return ResponseEntity.ok("Here are your posts");
    }
}

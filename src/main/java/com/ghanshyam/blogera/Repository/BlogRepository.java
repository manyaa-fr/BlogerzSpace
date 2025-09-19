package com.ghanshyam.blogera.Repository;

import com.ghanshyam.blogera.blog.Blog;
import com.ghanshyam.blogera.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByAuthor_Username(String username);

}

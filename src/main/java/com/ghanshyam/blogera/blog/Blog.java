package com.ghanshyam.blogera.blog;

import com.ghanshyam.blogera.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_seq")
    @SequenceGenerator(name = "blog_seq", sequenceName = "blog_sequence", allocationSize = 1)
    private long id;

    @ManyToOne
    private AppUser author;
    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public Blog(long id, AppUser author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Blog() {
    }
}

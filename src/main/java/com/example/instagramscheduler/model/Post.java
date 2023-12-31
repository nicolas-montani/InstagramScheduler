package com.example.instagramscheduler.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_posts")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "caption")
    private String caption;
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;
    // Other attributes as needed

    public Post() {
    }

    public Post(String imageUrl, String caption, LocalDateTime scheduledTime, User user) {
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.scheduledTime = scheduledTime;
    }
}

package com.example.instagramscheduler.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post extends AbstractEntity{

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "caption")
    private String caption;
    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;


    // Other attributes as needed
    /*
    public Post() {
    }

    public Post(String imageUrl, String caption, LocalDateTime scheduledTime, User user) {
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.scheduledTime = scheduledTime;
    }
     */
}

package com.example.instagramscheduler.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaign")
@Getter
@Setter
public class Campaign extends AbstractEntity {

    @Column(name= "campaign_name" , unique=true)
    private String campain_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name= "posts")
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    /*
    public Campaign() {
    }
    public Campaign(String campain_name) {
        this.campain_name = campain_name;
    }

     */
}

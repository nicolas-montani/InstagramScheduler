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
    private String campaign_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name= "posts")
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();


}

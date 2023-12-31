package com.example.instagramscheduler.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scheduled_posts")
@Getter
@Setter
public class Campain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campain_id")
    private Long id;
    @Column(name= "campain_name" , unique=true)
    private String campain_name;

    @Column(name= "post")
    @OneToMany(mappedBy = "campain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public Campain() {
    }
    public Campain(String campain_name) {
        this.campain_name = campain_name;
    }
}

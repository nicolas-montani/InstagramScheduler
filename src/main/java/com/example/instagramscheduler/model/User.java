
package com.example.instagramscheduler.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name= "username" , unique=true)
    private String username;
    @Column(name= "email" , unique=true)
    private String email;
    @Column(name= "password")
    private String password;

    @Column(name= "campains")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Campain> campains = new ArrayList<>();

    public User() {
    }
    public User(String username, String mail, String password) {
        this.username = username;
        this.email = mail;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
    }
}

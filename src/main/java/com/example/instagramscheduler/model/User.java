
package com.example.instagramscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "APPLICATION_USER")
@Getter
@Setter
public class User extends AbstractEntity {

    @Column(name= "username" , unique=true)
    private String username;

    @Column(name= "email" , unique=true)
    private String email;

    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;

    @Column(name= "campaigns")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Campaign> campaigns = new ArrayList<>();
}

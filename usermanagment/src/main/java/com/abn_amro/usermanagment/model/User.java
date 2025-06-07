package com.abn_amro.usermanagment.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@ToString
public class User extends BaseEntity {
    @Column(unique = true,name = "username")
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "activation_token", unique = true)
    private String activationToken;
    @Column(name = "last_name")
    private  String lastName;
    @Column(unique = true,name = "email")
    private String email;
    @Column(name = "password")
    private String password; // encrypted
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    public Set<Role> getRoles() {
        return this.roles;
    }
}




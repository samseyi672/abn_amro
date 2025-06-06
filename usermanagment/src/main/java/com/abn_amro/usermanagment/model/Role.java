package com.abn_amro.usermanagment.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Table(name = "roles")
@Entity
@Data
@ToString
public class Role extends BaseEntity{
    @Column(name="rolename",unique = true)
    private String name;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "role_permissions",
//            joinColumns = @JoinColumn(name = "role_id",nullable = true),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
//    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

}





































































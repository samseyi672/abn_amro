package com.abn_amro.usermanagment.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Table(name = "roles")
@Entity
@Data
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
public class Role extends BaseEntity{
    @Column(name="role")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

}





































































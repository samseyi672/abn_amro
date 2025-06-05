package com.abn_amro.usermanagment.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Table(name = "permissions")
@Entity
@Data
public class Permission {

    @Column(name="permission")
    private String permission;

    @ManyToMany(mappedBy = "permissions",cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();
}

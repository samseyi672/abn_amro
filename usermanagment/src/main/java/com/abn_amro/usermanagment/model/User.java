package com.abn_amro.usermanagment.model;


import jakarta.persistence.*;
import org.springframework.boot.actuate.audit.listener.AuditListener;

@Table(name = "users")
@Entity
@EntityListeners(AuditListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password; // encrypted
    private boolean enabled;
}
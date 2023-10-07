package com.example.csye6225assignment02.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;


    @CreationTimestamp
    @Column(name = "account_created", updatable = false, nullable = false)
    private String accountCreated;

    @UpdateTimestamp
    @Column(name = "account_updated", nullable = false)
    private String accountUpdated;



}

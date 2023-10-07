package com.example.csye6225assignment02.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "assignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("points")
    private Integer points;

    @JsonProperty("num_of_attemps")
    private Integer num_of_attemps;

    @JsonProperty("deadline")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @CreationTimestamp
    @Column(name = "deadline", updatable = false, nullable = false)
    private String deadline;

    @JsonProperty("assignment_created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @CreationTimestamp
    @Column(name = "assignment_created", updatable = false, nullable = false)
    private String assignment_created;

    @JsonProperty("assignment_updated")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "assignment_updated", nullable = false)
    @UpdateTimestamp
    private String assignment_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;



}

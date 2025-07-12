package com.cavcav.Eventify.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_follows")
public class UserFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who follows
    @ManyToOne(optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    // The user being followed
    @ManyToOne(optional = false)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

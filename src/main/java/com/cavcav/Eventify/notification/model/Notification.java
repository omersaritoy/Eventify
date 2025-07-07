package com.cavcav.Eventify.notification.model;

import com.cavcav.Eventify.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String type;
    private String title;
    private String message;
    private boolean isRead = false;
    private Long relatedId;
    @CreationTimestamp
    private LocalDateTime createdAt ;
}
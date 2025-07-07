package com.cavcav.Eventify.event.model;

import com.cavcav.Eventify.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    private int rating; // 1-5
    private String review;
    @CreationTimestamp
    private LocalDateTime createdAt ;

}
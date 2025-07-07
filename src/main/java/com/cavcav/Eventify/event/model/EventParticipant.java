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
public class EventParticipant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    private String status; // pending, approved, etc.
    private String attendanceStatus; // attended, not_attended, unknown
    @CreationTimestamp
    private LocalDateTime registrationDate;
    private String notes;
}
package com.cavcav.Eventify.event.model;

import com.cavcav.Eventify.event.model.enums.AttendanceStatus;
import com.cavcav.Eventify.event.model.enums.ParticipantStatus;
import com.cavcav.Eventify.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data

public class EventParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantStatus status = ParticipantStatus.PENDING;
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status")
    private AttendanceStatus attendanceStatus = AttendanceStatus.UNKNOWN;

    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

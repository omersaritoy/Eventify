package com.cavcav.Eventify.event.model;

import com.cavcav.Eventify.event.model.enums.EventStatus;
import com.cavcav.Eventify.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //it's our user id
    @Column(name = "organizer_id", insertable = false, updatable = false)
    private UUID organizer_id;
    private String title;
    private String description;
    private Long category_id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private String address;
    private Integer maxParticipants;
    private Integer currentParticipants=0;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isPublic;
    private EventStatus status;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;
}

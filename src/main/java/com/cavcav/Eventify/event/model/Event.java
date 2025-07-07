package com.cavcav.Eventify.event.model;

import com.cavcav.Eventify.category.model.Category;
import com.cavcav.Eventify.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer maxParticipants;
    private Integer currentParticipants = 0;
    private Double price;
    private String imageUrl;
    private Boolean isPublic = true;
    private Boolean requiresApproval = false;
    private String status; // draft, published, etc.

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventParticipant> participants;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventComment> comments;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventRating> ratings;
}
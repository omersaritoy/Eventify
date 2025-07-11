package com.cavcav.Eventify.event.dto;

import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.event.model.enums.EventStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventResponseDTO {
    private Long id;
    private UUID organizerId;
    private String title;
    private String description;
    private Long categoryId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private String address;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isPublic;
    private EventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventResponseDTO toDto(Event event) {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(event.getId());
        dto.setOrganizerId(event.getOrganizer().getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCategoryId(event.getCategory().getId());
        dto.setStart(event.getStart());
        dto.setEnd(event.getEnd());
        dto.setLocation(event.getLocation());
        dto.setAddress(event.getAddress());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setCurrentParticipants(event.getCurrentParticipants());
        dto.setPrice(event.getPrice());
        dto.setImageUrl(event.getImageUrl());
        dto.setIsPublic(event.getIsPublic());
        dto.setStatus(event.getStatus());
        dto.setCreatedAt(event.getCreated_at());
        dto.setUpdatedAt(event.getUpdated_at());
        return dto;
    }
    public static Event toEntity(EventResponseDTO dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStart(dto.getStart());
        event.setEnd(dto.getEnd());
        event.setLocation(dto.getLocation());
        event.setAddress(dto.getAddress());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setCurrentParticipants(dto.getCurrentParticipants());
        event.setPrice(dto.getPrice());
        event.setImageUrl(dto.getImageUrl());
        event.setIsPublic(dto.getIsPublic());
        event.setStatus(dto.getStatus());
        event.setCreated_at(dto.getCreatedAt());
        event.setUpdated_at(dto.getUpdatedAt());
        return event;
    }
}


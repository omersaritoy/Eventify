package com.cavcav.Eventify.event.dto;


import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.event.model.enums.EventStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventRegisterDTO {
    private UUID organizerId;
    private String title;
    private String description;
    private Long categoryId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private String address;
    private Integer maxParticipants;
    private BigDecimal price;
    private String imageUrl;
    private Boolean isPublic;
    private EventStatus status;

    public static EventRegisterDTO toDto(Event event) {
        EventRegisterDTO dto = new EventRegisterDTO();
        dto.setOrganizerId(event.getOrganizer().getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCategoryId(event.getCategory().getId());
        dto.setStart(event.getStart());
        dto.setEnd(event.getEnd());
        dto.setLocation(event.getLocation());
        dto.setAddress(event.getAddress());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setPrice(event.getPrice());
        dto.setImageUrl(event.getImageUrl());
        dto.setIsPublic(event.getIsPublic());
        dto.setStatus(event.getStatus());
        return dto;
    }
    public static Event toEntity(EventRegisterDTO dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStart(dto.getStart());
        event.setEnd(dto.getEnd());
        event.setLocation(dto.getLocation());
        event.setAddress(dto.getAddress());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setPrice(dto.getPrice());
        event.setImageUrl(dto.getImageUrl());
        event.setIsPublic(dto.getIsPublic());
        event.setStatus(dto.getStatus());
        event.setCurrentParticipants(0);
        return event;
    }
}

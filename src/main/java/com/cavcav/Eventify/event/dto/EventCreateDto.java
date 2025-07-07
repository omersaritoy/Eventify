package com.cavcav.Eventify.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateDto {
    private String title;
    private String description;
    private Long categoryId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer maxParticipants;
    private Double price;
    private Boolean isPublic;
    private Boolean requiresApproval;
    private String imageUrl;
}
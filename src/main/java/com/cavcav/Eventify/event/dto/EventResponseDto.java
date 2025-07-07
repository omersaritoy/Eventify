package com.cavcav.Eventify.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponseDto {
    private Long id;
    private String title;
    private String description;
    private String categoryName;
    private String organizerEmail;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private Integer currentParticipants;
    private Double price;
    private String status;
}
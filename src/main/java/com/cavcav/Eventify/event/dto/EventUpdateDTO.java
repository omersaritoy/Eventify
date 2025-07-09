package com.cavcav.Eventify.event.dto;

import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.event.model.enums.EventStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventUpdateDTO {
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


}

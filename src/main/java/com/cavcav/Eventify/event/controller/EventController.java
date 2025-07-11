package com.cavcav.Eventify.event.controller;

import com.cavcav.Eventify.event.dto.EventRegisterDTO;
import com.cavcav.Eventify.event.dto.EventResponseDTO;
import com.cavcav.Eventify.event.dto.EventUpdateDTO;
import com.cavcav.Eventify.event.service.EventService;
import com.cavcav.Eventify.user.dto.ApiResponse;
import com.cavcav.Eventify.user.dto.UserResponseDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventResponseDTO>> create(@RequestBody EventRegisterDTO event) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(event));
    }

    @GetMapping("/getByOrganizerId/{id}")
    public ResponseEntity<ApiResponse<List<EventResponseDTO>>> getByOrganizerId(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventsByOrganizer(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponseDTO>> updateEvent(
            @PathVariable Long id,
            @RequestBody @Valid EventUpdateDTO dto) {

        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEvent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.deleteEvent(id));
    }


    @GetMapping("/getAllEvents")
    public ResponseEntity<ApiResponse<List<EventResponseDTO>>> getAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents());
    }

    @GetMapping("/getEventById/{id}")
    public ResponseEntity<ApiResponse<EventResponseDTO>> getEventById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @PostMapping("/join/{eventId}")
    public ResponseEntity<ApiResponse<EventResponseDTO>> joinEvent(@PathVariable Long eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.joinEvent(eventId));
    }

    @DeleteMapping("/leave/{eventId}")
    public ResponseEntity<ApiResponse<?>> leaveEvent(@PathVariable Long eventId) {

        return ResponseEntity.status(HttpStatus.OK).body(eventService.leaveEvent(eventId));
    }
    @GetMapping("/user/events")
    public ResponseEntity<ApiResponse<List<EventResponseDTO>>> getMyJoinedEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getMyJoinedEvents());
    }
    @GetMapping("/events/{eventId}/participants")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getEventParticipants(@PathVariable Long eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getParticipantsByEvent(eventId));
    }

}

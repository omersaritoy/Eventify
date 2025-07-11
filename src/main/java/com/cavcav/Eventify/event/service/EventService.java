package com.cavcav.Eventify.event.service;

import com.cavcav.Eventify.category.dto.CategoryResponse;
import com.cavcav.Eventify.category.model.Category;
import com.cavcav.Eventify.category.repository.CategoryRepository;
import com.cavcav.Eventify.event.dto.EventRegisterDTO;
import com.cavcav.Eventify.event.dto.EventResponseDTO;
import com.cavcav.Eventify.event.dto.EventUpdateDTO;
import com.cavcav.Eventify.event.mapper.EventMapper;
import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.event.repository.EventRepository;
import com.cavcav.Eventify.user.dto.ApiResponse;
import com.cavcav.Eventify.user.model.User;
import com.cavcav.Eventify.user.repository.UserRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    public EventService(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;

        this.eventMapper = eventMapper;
    }
    public ApiResponse<EventResponseDTO> createEvent(EventRegisterDTO dto) {

        Optional<User> organizerOpt = userRepository.findById(dto.getOrganizerId());
        if (organizerOpt.isEmpty()) {
            return ApiResponse.error("User not found");
        }
        Category category=categoryRepository.findById(dto.getCategoryId()).orElse(null);
        Event event = EventRegisterDTO.toEntity(dto);
        event.setOrganizer(organizerOpt.get());
        event.setCategory(category);
        Event saved = eventRepository.save(event);
        EventResponseDTO response = EventResponseDTO.toDto(saved);
        return ApiResponse.<EventResponseDTO>builder()
                .success(true)
                .message("Event added successfully")
                .data(response)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<List<EventResponseDTO>> getEventsByOrganizer(UUID organizerId) {
        Optional<User> organizerOpt = userRepository.findById(organizerId);
        if (organizerOpt.isEmpty()) {
            return ApiResponse.error("User not found");
        }
        List<EventResponseDTO> eventResponseDTOS=eventRepository.findAllByOrganizerId(organizerId).stream().map(EventResponseDTO::toDto).toList();
        return ApiResponse.<List<EventResponseDTO>>builder()
                .success(true)
                .message("Find all events by organizer:"+organizerId)
                .data(eventResponseDTOS)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }


    public  ApiResponse<List<EventResponseDTO>> getAllEvents() {
        List<EventResponseDTO> responses=eventRepository.findAll().stream().map(EventResponseDTO::toDto).toList();

        return ApiResponse.<List<EventResponseDTO>>builder()
                .success(true)
                .message("Events fetched successfully")
                .data(responses)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }


    public ApiResponse<EventResponseDTO> updateEvent(Long id, EventUpdateDTO dto) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ApiResponse.<EventResponseDTO>builder()
                    .success(false)
                    .message("Event not found with ID: " + id)
                    .timestamp(LocalDateTime.now().toString())
                    .build();
        }

        Event event = optionalEvent.get();
        eventMapper.updateEventFromDto(dto, event);
        Event updatedEvent = eventRepository.save(event);

        EventResponseDTO responseDTO = EventResponseDTO.toDto(updatedEvent);
        return ApiResponse.<EventResponseDTO>builder()
                .success(true)
                .message("Event updated successfully")
                .data(responseDTO)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<EventResponseDTO> getEventById(Long id){
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) {
            return ApiResponse.error("Event not found with ID: " + id);
        }
        EventResponseDTO response = EventResponseDTO.toDto(eventOpt.get());
        return ApiResponse.<EventResponseDTO>builder()
                .success(true)
                .message("Event got successfully")
                .data(response)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<String> deleteEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ApiResponse.<String>builder()
                    .success(false)
                    .message("Event not found with ID: " + id)
                    .timestamp(LocalDateTime.now().toString())
                    .build();
        }

        eventRepository.deleteById(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Event deleted successfully.")
                .data("Deleted event ID: " + id)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}

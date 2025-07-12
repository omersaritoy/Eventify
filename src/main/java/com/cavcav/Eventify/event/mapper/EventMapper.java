package com.cavcav.Eventify.event.mapper;

import com.cavcav.Eventify.event.dto.EventRegisterDTO;
import com.cavcav.Eventify.event.dto.EventResponseDTO;
import com.cavcav.Eventify.event.dto.EventUpdateDTO;
import com.cavcav.Eventify.event.model.Event;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventUpdateDTO toUpdateDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventUpdateDTO dto, @MappingTarget Event event);



}
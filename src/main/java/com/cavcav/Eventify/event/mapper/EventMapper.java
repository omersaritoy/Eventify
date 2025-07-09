package com.cavcav.Eventify.event.mapper;

import com.cavcav.Eventify.event.dto.EventUpdateDTO;
import com.cavcav.Eventify.event.model.Event;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventUpdateDTO toUpdateDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventUpdateDTO dto, @MappingTarget Event event);
}
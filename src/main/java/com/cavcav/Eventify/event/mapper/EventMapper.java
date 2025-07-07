package com.cavcav.Eventify.event.mapper;

import com.cavcav.Eventify.category.mapper.CategoryMapper;
import com.cavcav.Eventify.event.dto.EventCreateDto;
import com.cavcav.Eventify.event.dto.EventResponseDto;
import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {
    @Mapping(source = "category.id", target = "categoryId")
    EventCreateDto toCreateDto(Event event);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "organizer.email", target = "organizerEmail")
    EventResponseDto toResponseDto(Event event);

    @Mapping(source = "categoryId", target = "category.id")
    Event toEntity(EventCreateDto dto);
}
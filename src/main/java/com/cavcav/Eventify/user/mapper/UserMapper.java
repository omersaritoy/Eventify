package com.cavcav.Eventify.user.mapper;


import com.cavcav.Eventify.user.dto.UserRegisterDto;
import com.cavcav.Eventify.user.dto.UserResponseDTO;

import com.cavcav.Eventify.user.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.*;


@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRegisterDto userRegisterDto);



    UserResponseDTO toUserResponseDTO(User user);
}

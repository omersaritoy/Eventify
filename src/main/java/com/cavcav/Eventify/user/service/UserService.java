package com.cavcav.Eventify.user.service;

import com.cavcav.Eventify.user.dto.ApiResponse;
import com.cavcav.Eventify.user.dto.UserRegisterDto;
import com.cavcav.Eventify.user.dto.UserResponseDTO;
import com.cavcav.Eventify.user.mapper.UserMapper;
import com.cavcav.Eventify.user.model.enums.Role;
import com.cavcav.Eventify.user.model.User;
import com.cavcav.Eventify.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ApiResponse<UserResponseDTO> registerUser(UserRegisterDto registerDto) {
        User user = userMapper.toUser(registerDto);
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        UserResponseDTO dto = userMapper.toUserResponseDTO(savedUser);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User registered successfully")
                .data(dto)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<UserResponseDTO> getUserById(UUID id) {
        User user=userRepository.getReferenceById(id);
        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User registered successfully")
                .data(userMapper.toUserResponseDTO(user))
                .timestamp(LocalDateTime.now().toString())
                .build();

    }

    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<UserResponseDTO>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(users)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<UserResponseDTO> deleteUser(UUID id) {
        if(!userRepository.existsById(id)) {
            return ApiResponse.error("User not found with id: " + id);
        }
        User user=userRepository.getReferenceById(id);
        userRepository.delete(user);
        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User deleted successfully")
                .data(userMapper.toUserResponseDTO(user))
                .timestamp(LocalDateTime.now().toString())
                .build();

    }
}

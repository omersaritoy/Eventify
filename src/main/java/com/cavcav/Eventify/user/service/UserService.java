package com.cavcav.Eventify.user.service;

import com.cavcav.Eventify.security.JwtUtilities;
import com.cavcav.Eventify.user.dto.*;
import com.cavcav.Eventify.user.mapper.UserMapper;
import com.cavcav.Eventify.user.model.enums.Role;
import com.cavcav.Eventify.user.model.User;
import com.cavcav.Eventify.user.repository.UserFollowRepository;
import com.cavcav.Eventify.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;

    public UserService(UserFollowRepository userFollowRepository, UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder, JwtUtilities jwtUtilities, AuthenticationManager authenticationManager) {
        this.userFollowRepository = userFollowRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilities = jwtUtilities;
        this.authenticationManager = authenticationManager;
    }

    public ApiResponse<UserResponseDTO> registerUser(UserRegisterDto registerDto) {
        User user = userMapper.toUser(registerDto);
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        UserResponseDTO dto = userMapper.toUserResponseDTO(savedUser);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User registered successfully")
                .data(dto)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<?> login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if(authentication.isAuthenticated()) {
            String token= jwtUtilities.generateToken(request.getEmail());
            System.out.println("Bearer "+token);
            return ApiResponse.builder()
                    .success(true)
                    .message("Login is successful")
                    .data("Bearer "+token)
                    .timestamp(LocalDateTime.now().toString())
                    .build();
        }
        return ApiResponse.error("email or password is incorrect");
    }


    public ApiResponse<UserResponseDTO> getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO = userMapper.toUserResponseDTO(user);

        long followers = userFollowRepository.countByFollowingId(id);
        long following = userFollowRepository.countByFollowerId(id);
        responseDTO.setFollowersCount(followers);
        responseDTO.setFollowingCount(following);
        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User registered successfully")
                .data(responseDTO)
                .timestamp(LocalDateTime.now().toString())
                .build();

    }

    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userRepository.findAll().stream()
                .map(user -> {
                    UserResponseDTO dto = userMapper.toUserResponseDTO(user);
                    dto.setFollowersCount(userFollowRepository.countByFollowingId(user.getId()));
                    dto.setFollowingCount(userFollowRepository.countByFollowerId(user.getId()));
                    return dto;
                })
                .collect(Collectors.toList());

        return ApiResponse.<List<UserResponseDTO>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(users)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<UserResponseDTO> updateUser(UUID userId, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUserFromDto(updateDTO, user);
         user=userRepository.save(user);
        UserResponseDTO responseDTO = userMapper.toUserResponseDTO(user);
        long followers = userFollowRepository.countByFollowingId(userId);
        long following = userFollowRepository.countByFollowerId(userId);
        responseDTO.setFollowersCount(followers);
        responseDTO.setFollowingCount(following);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User updated successfully")
                .data(responseDTO)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<UserResponseDTO> deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            return ApiResponse.error("User not found with id: " + id);
        }
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User deleted successfully")
                .data(userMapper.toUserResponseDTO(user))
                .timestamp(LocalDateTime.now().toString())
                .build();

    }
}

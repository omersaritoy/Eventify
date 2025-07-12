package com.cavcav.Eventify.user.service;

import com.cavcav.Eventify.security.JwtUtilities;
import com.cavcav.Eventify.user.dto.*;
import com.cavcav.Eventify.user.mapper.UserMapper;
import com.cavcav.Eventify.user.model.enums.Role;
import com.cavcav.Eventify.user.model.User;
import com.cavcav.Eventify.user.repository.UserFollowRepository;
import com.cavcav.Eventify.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserService(UserFollowRepository userFollowRepository, UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder, JwtUtilities jwtUtilities, AuthenticationManager authenticationManager, EmailService emailService, RedisTemplate<String, Object> redisTemplate) {
        this.userFollowRepository = userFollowRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilities = jwtUtilities;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;

        this.redisTemplate = redisTemplate;
    }

    public ApiResponse<UserResponseDTO> registerUser(UserRegisterDto registerDto) throws MessagingException {
        Optional<User> isExist = userRepository.findByEmail(registerDto.getEmail());
        if (isExist.isPresent()) {
            return ApiResponse.error("Email already in use");
        }
        User user = userMapper.toUser(registerDto);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setIsVerified(false);
        user.setIsActive(false);
        User savedUser = userRepository.save(user);

        UserResponseDTO dto = userMapper.toUserResponseDTO(savedUser);
        String verificationCode = jwtUtilities.generateToken(registerDto.getEmail());
        verificationCode = verificationCode.trim();
        System.out.println("token: " + verificationCode);
        redisTemplate.opsForValue().set("verificationCode:" + user.getId(), verificationCode, 10, TimeUnit.MINUTES);

        emailService.sendMail(registerDto.getEmail(), user.getFirstName(), verificationCode);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User registered successfully, please verify your email address to login.")
                .data(dto)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<?> verifyUser(UUID userId,String token) {
        String storedCode = (String) redisTemplate.opsForValue().get("verificationCode:" + userId);
        System.out.println("Redis Value: " + storedCode);
        if (storedCode == null) {
            return ApiResponse.error("Verification code not found or expired");
        }
        if (!storedCode.equals(token)) {
            return ApiResponse.error("Invalid verification token");
        }
        User user = userRepository.findByEmail(jwtUtilities.extractUsername(token))
                .orElseThrow(() -> new RuntimeException("User not found"));


        user.setIsVerified(true);
        user.setIsActive(true);
        userRepository.save(user);

        redisTemplate.delete("verificationCode:" + token);

        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User verified successfully. Your account is now active.")
                .data(null)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<?> login(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) ApiResponse.error("User not found");

        if (!user.get().getIsActive())
            return ApiResponse.error("User is not active");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtUtilities.generateToken(request.getEmail());
            System.out.println("Bearer " + token);
            return ApiResponse.builder()
                    .success(true)
                    .message("Login is successful")
                    .data("Bearer " + token)
                    .timestamp(LocalDateTime.now().toString())
                    .build();
        }
        return ApiResponse.error("email or password is incorrect");
    }


    public ApiResponse<UserResponseDTO> getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.getIsActive())
            return ApiResponse.error("User is not active");
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
        user = userRepository.save(user);
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
        user.setIsActive(false);
        return ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("User deleted successfully")
                .data(userMapper.toUserResponseDTO(user))
                .timestamp(LocalDateTime.now().toString())
                .build();

    }
}

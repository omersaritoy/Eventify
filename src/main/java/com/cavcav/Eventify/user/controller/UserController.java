package com.cavcav.Eventify.user.controller;

import com.cavcav.Eventify.event.dto.EventResponseDTO;
import com.cavcav.Eventify.user.dto.*;
import com.cavcav.Eventify.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@RequestBody UserRegisterDto registrationDTO) throws MessagingException {

        ApiResponse<UserResponseDTO> response = userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO updateDTO) {
        return ResponseEntity.ok(userService.updateUser(id, updateDTO));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> deleteUserById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }
    @PostMapping("/verify/{userId}")
    public ApiResponse<?> verifyEmail(@PathVariable UUID userId,@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyUser(userId,token)).getBody();
    }




}

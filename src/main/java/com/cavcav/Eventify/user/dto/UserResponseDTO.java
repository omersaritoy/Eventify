package com.cavcav.Eventify.user.dto;

import com.cavcav.Eventify.user.model.enums.Gender;
import com.cavcav.Eventify.user.model.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data

public class UserResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String phone;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String location;
    private String bio;
    private String profilePicture;
    private Boolean isVerified;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long followersCount;
    private Long followingCount;

}

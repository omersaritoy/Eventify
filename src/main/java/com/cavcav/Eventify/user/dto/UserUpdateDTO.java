package com.cavcav.Eventify.user.dto;

import com.cavcav.Eventify.user.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String bio;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String location;
}

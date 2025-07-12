package com.cavcav.Eventify.user.model;

import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.event.model.EventParticipant;
import com.cavcav.Eventify.user.model.enums.Gender;
import com.cavcav.Eventify.user.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Pattern(regexp = "^\\+?[0-9\\- ]{7,20}$", message = "Phone number format is invalid")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Size(max = 500, message = "Bio must be at most 500 characters")
    private String bio;

    private String profilePicture;

    private Boolean isVerified = false;
    private Boolean isActive = false;

    @Size(max = 100, message = "Location must be at most 100 characters")
    private String location;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> organizedEvents;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<UserFollow> following;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private List<UserFollow> followers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventParticipant> participations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}

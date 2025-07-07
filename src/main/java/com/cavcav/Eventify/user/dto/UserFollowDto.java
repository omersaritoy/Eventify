package com.cavcav.Eventify.user.dto;

import com.cavcav.Eventify.user.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserFollowDto {
    private Long id;
    private UUID followerId;
    private String followerEmail;
    private UUID followingId;
    private String followingEmail;
    private LocalDateTime createdAt;
}

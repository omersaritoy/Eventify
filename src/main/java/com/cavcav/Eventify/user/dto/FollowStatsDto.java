package com.cavcav.Eventify.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowStatsDto {
    private long followers;
    private long following;
}
package com.cavcav.Eventify.user.controller;

import com.cavcav.Eventify.user.dto.ApiResponse;
import com.cavcav.Eventify.user.dto.FollowStatsDto;
import com.cavcav.Eventify.user.dto.UserFollowDto;
import com.cavcav.Eventify.user.service.FollowService;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final FollowService followService;
    public FollowController(FollowService followService ) {
        this.followService = followService;
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<ApiResponse<UserFollowDto>> follow(@PathVariable UUID followerId, @PathVariable UUID followingId) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.followUser(followerId, followingId));
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<ApiResponse<UserFollowDto>> unfollow(@PathVariable UUID followerId, @PathVariable UUID followingId) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.unfollowUser(followerId, followingId));
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<ApiResponse<List<UserFollowDto>>> getFollowers(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponse<List<UserFollowDto>>> getFollowing(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowing(userId));
    }

    @GetMapping("/{userId}/stats")
    public ResponseEntity<ApiResponse<FollowStatsDto>> getFollowStats(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowStats(userId));
    }
}

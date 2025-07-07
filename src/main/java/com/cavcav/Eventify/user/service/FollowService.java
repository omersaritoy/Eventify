package com.cavcav.Eventify.user.service;


import com.cavcav.Eventify.user.dto.ApiResponse;
import com.cavcav.Eventify.user.dto.FollowStatsDto;
import com.cavcav.Eventify.user.dto.UserFollowDto;
import com.cavcav.Eventify.user.dto.UserResponseDTO;
import com.cavcav.Eventify.user.mapper.UserFollowMapper;
import com.cavcav.Eventify.user.mapper.UserMapper;
import com.cavcav.Eventify.user.model.User;
import com.cavcav.Eventify.user.model.UserFollow;
import com.cavcav.Eventify.user.repository.UserFollowRepository;
import com.cavcav.Eventify.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserFollowMapper userMapper;

    public FollowService(UserRepository userRepository, UserFollowRepository userFollowRepository, UserFollowMapper userMapper) {
        this.userRepository = userRepository;
        this.userFollowRepository = userFollowRepository;
        this.userMapper = userMapper;
    }

    public ApiResponse<UserFollowDto> followUser(UUID followerId, UUID followingId) {
        if (followerId.equals(followingId)) {
            return ApiResponse.error("You can't follow yourself");
        }
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        userFollowRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(f -> {
                    throw new RuntimeException("Already following");
                });
        UserFollow follow = new UserFollow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow = userFollowRepository.save(follow);
        return ApiResponse.<UserFollowDto>builder()
                .success(true)
                .message("Followed successfully")
                .data(userMapper.toDto(follow))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<UserFollowDto> unfollowUser(UUID followerId, UUID followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        UserFollow follow = userFollowRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Not following this user"));
        userFollowRepository.delete(follow);
        return ApiResponse.<UserFollowDto>builder()
                .success(true)
                .message("Unfollowed Successfully")
                .data(userMapper.toDto(follow))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<List<UserFollowDto>> getFollowers(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserFollowDto> followDtos = userFollowRepository.findAllByFollowing(user).stream().map(userMapper::toDto).toList();
        return ApiResponse.<List<UserFollowDto>>builder()
                .success(true)
                .message("Get all followers successfull")
                .data(followDtos)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<List<UserFollowDto>> getFollowing(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserFollowDto> followDtos = userFollowRepository.findAllByFollower(user).stream().map(userMapper::toDto).toList();
        return ApiResponse.<List<UserFollowDto>>builder()
                .success(true)
                .message("Get all following successfull")
                .data(followDtos)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<FollowStatsDto> getFollowStats(UUID userId) {
        if (!userRepository.existsById(userId)) {
            return ApiResponse.error("User not found");
        }

        long followers = userFollowRepository.countByFollowingId(userId);  // Onu takip edenler
        long following = userFollowRepository.countByFollowerId(userId);  // Onun takip ettikleri

        FollowStatsDto stats = new FollowStatsDto(followers, following);

        return ApiResponse.<FollowStatsDto>builder()
                .success(true)
                .message("Get follow stats successful")
                .data(stats)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }


}

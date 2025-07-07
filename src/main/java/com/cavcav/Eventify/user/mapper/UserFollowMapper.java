package com.cavcav.Eventify.user.mapper;

import com.cavcav.Eventify.user.dto.UserFollowDto;
import com.cavcav.Eventify.user.model.UserFollow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserFollowMapper {

    @Mapping(source = "follower.id", target = "followerId")
    @Mapping(source = "follower.email", target = "followerEmail")
    @Mapping(source = "following.id", target = "followingId")
    @Mapping(source = "following.email", target = "followingEmail")
    UserFollowDto toDto(UserFollow follow);
}
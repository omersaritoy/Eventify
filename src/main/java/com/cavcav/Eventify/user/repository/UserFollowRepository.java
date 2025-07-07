package com.cavcav.Eventify.user.repository;

import com.cavcav.Eventify.user.model.User;
import com.cavcav.Eventify.user.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    Optional<UserFollow> findByFollowerAndFollowing(User follower, User following);

    List<UserFollow> findAllByFollower(User follower);

    List<UserFollow> findAllByFollowing(User following);

    long countByFollowerId(UUID followerId);   // → kaç kişiyi takip ediyor

    long countByFollowingId(UUID followingId);
}

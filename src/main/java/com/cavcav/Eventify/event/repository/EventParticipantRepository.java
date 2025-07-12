package com.cavcav.Eventify.event.repository;

import com.cavcav.Eventify.event.model.Event;
import com.cavcav.Eventify.event.model.EventParticipant;
import com.cavcav.Eventify.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository 
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    boolean existsByEventAndUser(Event event, User user);

    Optional<EventParticipant> findByEventAndUser(Event event, User user);


    List<EventParticipant> findByUser(User user);

    List<EventParticipant> findByEvent(Event event);
}

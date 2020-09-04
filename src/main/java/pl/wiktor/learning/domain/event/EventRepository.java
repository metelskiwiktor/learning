package pl.wiktor.learning.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findAllByPlanningAtBeforeAndPlanningAtAfterAndTo_IdEquals(Instant start, Instant end, String toId);
}

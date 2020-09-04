package pl.wiktor.learning.domain.event;

import org.springframework.stereotype.Service;
import pl.wiktor.learning.api.exception.DomainException;
import pl.wiktor.learning.api.exception.ExceptionCode;
import pl.wiktor.learning.domain.document.Document;
import pl.wiktor.learning.domain.document.DocumentRepository;
import pl.wiktor.learning.domain.document.DocumentService;
import pl.wiktor.learning.domain.user.User;
import pl.wiktor.learning.domain.user.UserService;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final DocumentRepository documentRepository;

    public EventService(EventRepository eventRepository, UserService userService, DocumentRepository documentRepository) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.documentRepository = documentRepository;
    }

    public void createEvent(String from, String to, Instant planningAt, Type type, List<String> documentsId) {
        User administrator = userService.getUserById(from);
        User regularUser = userService.getUserById(to);

        Event event = new Event(
                administrator,
                regularUser,
                planningAt,
                type,
                documentsId
        );

        eventRepository.save(event);
    }

    public List<Event> getEventsTo(String userId, Instant from, Instant to) {
        return eventRepository.findAllByPlanningAtBeforeAndPlanningAtAfterAndTo_IdEquals(from, to, userId);
    }

    private Document getDocumentById(String id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DomainException(ExceptionCode.INVALID_FIELD));
    }
}

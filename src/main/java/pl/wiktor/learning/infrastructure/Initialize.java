package pl.wiktor.learning.infrastructure;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.domain.document.Document;
import pl.wiktor.learning.domain.document.DocumentRepository;
import pl.wiktor.learning.domain.document.DocumentService;
import pl.wiktor.learning.domain.document.DocumentStatus;
import pl.wiktor.learning.domain.event.EventService;
import pl.wiktor.learning.domain.event.Type;
import pl.wiktor.learning.domain.user.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;

@Component
public class Initialize implements ApplicationRunner {
    private UserRepository userRepository;
    private DocumentService documentService;
    private EventService eventService;
    private DocumentRepository documentRepository;

    public Initialize(UserRepository userRepository, DocumentService documentService, EventService eventService, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentService = documentService;
        this.eventService = eventService;
        this.documentRepository = documentRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        User adminMock = new User(
                "admin123",
                "admin@admin.pl",
                "Administrator Mock",
                Role.ADMINISTRATOR,
                Status.ENABLED
        );

        User regularUserMock = new User(
                "regular123",
                "regular@user.pl",
                "Regular User Mock",
                Role.REGULAR,
                Status.ENABLED
        );

//        boolean admin = userRepository.findAll().stream()
//                .anyMatch(a -> a.getName().equals(adminMock.getName()));
//
//        if (admin) return;

        userRepository.save(adminMock);
        userRepository.save(regularUserMock);

        System.out.println("*********************");
        System.out.println("RegularUserMock id: " + regularUserMock.getId());
        System.out.println("*********************");

        Document document1 = documentRepository.save(makeDocument(1, Month.JULY, Month.AUGUST, 30, 3, adminMock, regularUserMock));
        Document document2 = documentRepository.save(makeDocument(2, Month.AUGUST, Month.AUGUST, 4, 7, adminMock, regularUserMock));
        Document document3 = documentRepository.save(makeDocument(3, Month.AUGUST, Month.AUGUST, 8, 10, adminMock, regularUserMock));
        Document document4 = documentRepository.save(makeDocument(4, Month.AUGUST, Month.AUGUST, 11, 14, adminMock, regularUserMock));
        Document document5 = documentRepository.save(makeDocument(5, Month.AUGUST, Month.AUGUST, 15, 18, adminMock, regularUserMock));
        Document document6 = documentRepository.save(makeDocument(6, Month.AUGUST, Month.AUGUST, 19, 20, adminMock, regularUserMock));
        Document document7 = documentRepository.save(makeDocument(7, Month.AUGUST, Month.AUGUST, 22, 26, adminMock, regularUserMock));
        Document document8 = documentRepository.save(makeDocument(8, Month.AUGUST, Month.AUGUST, 27, 29, adminMock, regularUserMock));
        Document document9 = documentRepository.save(makeDocument(9, Month.AUGUST, Month.SEPTEMBER, 30, 3, adminMock, regularUserMock));

        String adminId = adminMock.getId();
        String regularId = regularUserMock.getId();

        eventService.createEvent(adminId, regularId, LocalDate.of(2020, Month.AUGUST, 3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), Type.MEETING, null);
        eventService.createEvent(adminId, regularId, LocalDate.of(2020, Month.AUGUST, 9).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), Type.INTERVIEW, Arrays.asList(document1.getId(), document2.getId()));
        eventService.createEvent(adminId, regularId, LocalDate.of(2020, Month.AUGUST, 10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), Type.MEETING, Collections.singletonList(document4.getId()));
        eventService.createEvent(adminId, regularId, LocalDate.of(2020, Month.AUGUST, 15).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), Type.INTERVIEW, Arrays.asList(document3.getId(), document4.getId()));
        eventService.createEvent(adminId, regularId, LocalDate.of(2020, Month.AUGUST, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), Type.MEETING, Collections.singletonList(document5.getId()));

    }

    private Document makeDocument(int index, Month start, Month end, int dayStart, int dayEnd, User adminMock, User regularUserMock){
        return new Document(
                "Pytania rekrutacyjne " + index,
                null,
                adminMock,
                regularUserMock,
                DocumentStatus.IN_PROGRESS,
                LocalDate.of(2020, start, dayStart),
                LocalDate.of(2020, end, dayEnd)
        );
    }
}

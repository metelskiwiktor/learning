package pl.wiktor.learning.domain.document;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import pl.wiktor.learning.api.dto.response.document.*;
import pl.wiktor.learning.api.dto.response.event.EventView;
import pl.wiktor.learning.api.exception.DomainException;
import pl.wiktor.learning.api.exception.ExceptionCode;
import pl.wiktor.learning.domain.event.Event;
import pl.wiktor.learning.domain.event.EventService;
import pl.wiktor.learning.domain.file.File;
import pl.wiktor.learning.domain.file.FileService;
import pl.wiktor.learning.domain.user.User;
import pl.wiktor.learning.domain.user.UserService;
//import pl.wiktor.learning.infrastructure.security.SecurityContextUtils;
import pl.wiktor.learning.lib.Assertion;

import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final AnswerRepository answerRepository;
    private final DocumentRepository documentRepository;
    private final ReportRepository reportRepository;
    private final FileService fileService;
    private final UserService userService;
    private final Clock clock;
    private final ConversionService conversionService;
    private final EventService eventService;

    public DocumentService(AnswerRepository answerRepository, DocumentRepository documentRepository, ReportRepository reportRepository, FileService fileService, UserService userService, Clock clock, ConversionService conversionService, EventService eventService) {
        this.answerRepository = answerRepository;
        this.documentRepository = documentRepository;
        this.reportRepository = reportRepository;
        this.fileService = fileService;
        this.userService = userService;
        this.clock = clock;
        this.conversionService = conversionService;
        this.eventService = eventService;
    }

    public DocumentView createDocument(String title, String fileId, String fromUserId, String toUserId, LocalDate startAt, LocalDate endAt) {
        Validator.validate(title);

        File questionsFile = fileService.getFileById(fileId);
        User from = userService.getUserById(fromUserId);
        User to = userService.getUserById(toUserId);

        Document document = new Document(
                title,
                questionsFile,
                from,
                to,
                DocumentStatus.TO_DO,
                startAt,
                endAt
        );

        documentRepository.save(document);

        return conversionService.convert(document, DocumentView.class);
    }

    public void uploadAnswer(String documentId, String fileId) {
        Document document = getDocumentById(documentId);
        validateOwnObject(document.getTo().getEmail());
        File file = fileService.getFileById(fileId);

        if (!containsOnlyRejectedAnswers(document.getAnswers())) {
            throw new DomainException(ExceptionCode.ANSWER_ALREADY_ACCEPTED, document.getId());
        }

        Answer answer = new Answer(
                file,
                clock.instant(),
                AnswerStatus.PENDING
        );

        document.addAnswer(answer);

        answerRepository.save(answer);
        documentRepository.save(document);
    }


    public void reviewAnswer(String answerId, String administratorNote, AnswerStatus answerStatus) {
        Answer answer = getAnswerById(answerId);

        answer.updateReview(
                administratorNote,
                answerStatus
        );

        answerRepository.save(answer);
    }

    public List<LightDocumentView> getLightDocuments(String fromUser, String toUser, LocalDate fromDate, LocalDate toDate, DocumentStatus documentStatus) {
        validateOwnObject(toUser);

        return getDocuments(fromUser, toUser, fromDate, toDate, documentStatus).stream()
                .map(document -> conversionService.convert(document, LightDocumentView.class))
                .collect(Collectors.toList());
    }

    public List<DocumentView> getAllDocuments(String fromUser, String toUser, LocalDate fromDate, LocalDate toDate, DocumentStatus documentStatus) {
        validateOwnObject(toUser);

        return getDocuments(fromUser, toUser, fromDate, toDate, documentStatus).stream()
                .map(document -> conversionService.convert(document, DocumentView.class))
                .collect(Collectors.toList());
    }

    public DocumentView getDocument(String documentId) {
        Document document = getDocumentById(documentId);

        return conversionService.convert(document, DocumentView.class);
    }

    public ReportView uploadReport(String documentId, String content, String title) {
        Document document = getDocumentById(documentId);

        Report report = new Report(
                title,
                content,
                clock.instant()
        );

        document.addReport(report);

        reportRepository.save(report);
        documentRepository.save(document);

        return conversionService.convert(report, ReportView.class);
    }

    public List<ReportView> getReports(String documentId) {
        Document document = getDocumentById(documentId);

        return document.getReports().stream()
                .map(report -> conversionService.convert(report, ReportView.class))
                .collect(Collectors.toList());
    }

    public TimetableView getTimetable(String userId, Month month, int year) {
        User user = userService.getUserById(userId);

        validateOwnObject(user.getName());

        Map<Integer, LocalDate> mappedDaysPerMonth = getMappedDaysPerMonth(month, year);

        return getTimetableViewByDaysPerMonth(mappedDaysPerMonth, userId);
    }

    private TimetableView getTimetableViewByDaysPerMonth(Map<Integer, LocalDate> mappedDaysPerMonth, String userId) {
        List<LightDocumentView> overdueDocuments = documentRepository
                .getAllByStartAtBeforeAndEndAtBeforeAndTo_IdEqualsAndDocumentStatusEquals(
                        LocalDate.now(clock), LocalDate.now(clock).minusDays(1), userId, DocumentStatus.IN_PROGRESS).stream()
                .map(document -> conversionService.convert(document, LightDocumentView.class))
                .collect(Collectors.toList());

        TimetableView timetableView = new TimetableView(new ArrayList<>(), overdueDocuments);
        for (int i = 1; i <= 42; i++) {
            LocalDate date = mappedDaysPerMonth.get(i);
            LocalDate from = date.plusDays(1);
            LocalDate to = date.minusDays(1);
            Document document = getDocumentByDay(from, to, userId);
            LightDocumentView lightDocumentView = conversionService.convert(document, LightDocumentView.class);

            List<EventView> events = eventService.getEventsTo(userId, from.minusDays(1).atStartOfDay().toInstant(ZoneOffset.MIN),
                    to.atStartOfDay().toInstant(ZoneOffset.MIN)).stream()
                    .map(event -> conversionService.convert(event, EventView.class))
                    .collect(Collectors.toList());


            ThingsPerDay thingsPerDay = new ThingsPerDay(
                    lightDocumentView,
                    events,
                    date
            );
            timetableView.addThingPerDays(thingsPerDay);
        }

        return timetableView;
    }

    private Map<Integer, LocalDate> getMappedDaysPerMonth(Month month, int year) {
        LocalDate thisMonth = LocalDate.of(year, month, 1);

        int firstDay = 1;
        int lastDay = thisMonth.lengthOfMonth();
        int firstDayOfMonth = LocalDate.of(year, month, firstDay).getDayOfWeek().getValue();
        int sumOfDays = firstDayOfMonth + lastDay - 1;

        Map<Integer, LocalDate> map = new HashMap<>();
        LocalDate date;
        Month previousMonth = thisMonth.minusMonths(1).getMonth();

        int lastDayPreviousMonth = LocalDate.of(year, previousMonth, 1).lengthOfMonth();
        int index = 1;
        for (int i = firstDayOfMonth - 2; i >= 0; i--) {
            date = LocalDate.of(year, previousMonth, lastDayPreviousMonth - i);
            map.put(index++, date);
        }

        for (int i = firstDay; i <= lastDay; i++) {
            date = LocalDate.of(year, month, i);
            map.put(index++, date);
        }

        int maxDaysInCalendarPerMonth = 42;
        int daysInNextMonth = maxDaysInCalendarPerMonth - sumOfDays;
        Month nextMonth = thisMonth.plusMonths(1).getMonth();

        for (int i = 1; i <= daysInNextMonth; i++) {
            date = LocalDate.of(year, nextMonth, i);
            map.put(index++, date);
        }

        return map;
    }

    private Document getDocumentByDay(LocalDate start, LocalDate end, String userId) {
        return documentRepository.getByStartAtBeforeAndEndAtAfterAndTo_IdEquals(
                start,
                end,
                userId
        );
    }

    private void validateOwnObject(String owner) {
//        String sender = SecurityContextUtils.getUserName();
//        if(!SecurityContextUtils.getUserRoles().contains(Role.ADMINISTRATOR.toString())){
//            if(!owner.equals(sender)) throw new DomainException(ExceptionCode.UNAUTHORIZED_PERMISSIONS, sender);
//        }
    }

    private boolean containsOnlyRejectedAnswers(List<Answer> answers) {
        return answers.stream()
                .allMatch(answer -> answer.getAnswerStatus() == AnswerStatus.REJECTED);
    }

    private List<Document> getDocuments(String fromUser, String toUser, LocalDate fromDate, LocalDate toDate, DocumentStatus documentStatus) {
        Predicate<Document> fromUserPredicate = document -> true;
        Predicate<Document> toUserPredicate = document -> true;
        Predicate<Document> documentStatusPredicate = document -> true;

        if (!Objects.isNull(fromUser)) fromUserPredicate = document -> document.getFrom().getId().equals(fromUser);
        if (!Objects.isNull(toUser)) toUserPredicate = document -> document.getTo().getId().equals(toUser);
        if(!Objects.isNull(documentStatus)) documentStatusPredicate = document -> document.getDocumentStatus() == documentStatus;

        return documentRepository.findAll().stream()
                .filter(fromUserPredicate)
                .filter(toUserPredicate)
                .filter(documentStatusPredicate)
                .filter(document -> document.getStartAt().isAfter(fromDate.minusDays(1)) && document.getStartAt().isBefore(toDate.plusDays(1)))
                .collect(Collectors.toList());
    }

    public Document getDocumentById(String documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new DomainException(ExceptionCode.RESOURCE_NOT_FOUND, ExceptionCode.Resource.DOCUMENT.getPolish(), documentId));
    }

    private Answer getAnswerById(String answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new DomainException(ExceptionCode.RESOURCE_NOT_FOUND, ExceptionCode.Resource.ANSWER.getPolish(), answerId));
    }

    private static class Validator {
        static void validate(String title) {
            Assertion.notEmpty(title, () -> new DomainException(ExceptionCode.INVALID_FIELD, ExceptionCode.Resource.TITLE));
        }
    }

}

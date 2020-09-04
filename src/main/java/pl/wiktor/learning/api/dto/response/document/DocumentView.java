package pl.wiktor.learning.api.dto.response.document;

import org.springframework.format.annotation.DateTimeFormat;
import pl.wiktor.learning.api.dto.response.file.FileView;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class DocumentView {
    private String id;
    private String title;
    private FileView questionsFile;
    private List<AnswerView> answers;
    private UserView userToView;
    private UserView userFromView;
    private String documentStatus;
    private LocalDate startAt;
    private LocalDate endAt;

    public DocumentView(String id, String title, FileView questionsFile, List<AnswerView> answers, UserView userToView,
                        UserView userFromView, String documentStatus, LocalDate startAt, LocalDate endAt) {
        this.id = id;
        this.title = title;
        this.questionsFile = questionsFile;
        this.answers = answers;
        this.userToView = userToView;
        this.userFromView = userFromView;
        this.documentStatus = documentStatus;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public DocumentView() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public FileView getQuestionsFile() {
        return questionsFile;
    }

    public List<AnswerView> getAnswers() {
        return answers;
    }

    public UserView getUserToView() {
        return userToView;
    }

    public UserView getUserFromView() {
        return userFromView;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }
}
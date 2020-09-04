package pl.wiktor.learning.domain.document;

import org.hibernate.annotations.GenericGenerator;
import pl.wiktor.learning.domain.file.File;
import pl.wiktor.learning.domain.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Document {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String title;
    @OneToOne(fetch = FetchType.LAZY)
    private File questions;
    @OneToMany
    private List<Answer> answers;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
    @OneToMany
    private List<Report> reports;
    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;
    private LocalDate startAt;
    private LocalDate endAt;

    public Document(String title, File questions, User from, User to, DocumentStatus documentStatus, LocalDate startAt, LocalDate endAt) {
        this.title = title;
        this.questions = questions;
        this.from = from;
        this.to = to;
        this.documentStatus = documentStatus;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Document() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public File getQuestions() {
        return questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public List<Report> getReports() {
        return reports;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public void addAnswer(Answer answer){
        if(Objects.isNull(answers)) answers = new ArrayList<>();

        answers.add(answer);
    }

    public void addReport(Report report){
        if(Objects.isNull(reports)) reports = new ArrayList<>();

        reports.add(report);
    }
}

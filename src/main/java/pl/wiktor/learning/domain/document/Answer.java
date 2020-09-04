package pl.wiktor.learning.domain.document;

import org.hibernate.annotations.GenericGenerator;
import pl.wiktor.learning.domain.file.File;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Answer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    @OneToOne(fetch = FetchType.LAZY)
    private File answers;
    private Instant addedAt;
    private String administratorNote;
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    public Answer(File answers, Instant addedAt, AnswerStatus answerStatus) {
        this.answers = answers;
        this.addedAt = addedAt;
        this.answerStatus = answerStatus;
    }

    public Answer() {
    }

    public String getId() {
        return id;
    }

    public File getAnswers() {
        return answers;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public String getAdministratorNote() {
        return administratorNote;
    }

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", addedAt=" + addedAt +
                ", administratorNote='" + administratorNote + '\'' +
                ", answerStatus=" + answerStatus +
                '}';
    }

    public void updateReview(String administratorNote, AnswerStatus answerStatus){
        this.administratorNote = administratorNote;
        this.answerStatus = answerStatus;
    }
}

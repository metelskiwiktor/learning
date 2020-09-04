package pl.wiktor.learning.api.dto.response.document;

import pl.wiktor.learning.api.dto.response.file.FileView;

import java.time.Instant;

public class AnswerView {
    private String id;
    private FileView answerFile;
    private Instant addedAt;
    private String answerStatus;
    private String administrationNote;

    public AnswerView(String id, FileView answerFile, Instant addedAt, String answerStatus, String administrationNote) {
        this.id = id;
        this.answerFile = answerFile;
        this.addedAt = addedAt;
        this.answerStatus = answerStatus;
        this.administrationNote = administrationNote;
    }

    public AnswerView() {
    }

    public String getId() {
        return id;
    }

    public FileView getAnswerFile() {
        return answerFile;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    public String getAnswerStatus() {
        return answerStatus;
    }

    public String getAdministrationNote() {
        return administrationNote;
    }
}

package pl.wiktor.learning.api.dto.request.document;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;

public class CreateDocument {
    private String fileId;
    private String title;
    private String fromUserId;
    private String toUserId;
    private LocalDate startAt;
    private LocalDate endAt;

    public String getFileId() {
        return fileId;
    }

    public String getTitle() {
        return title;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }
}

package pl.wiktor.learning.api.dto.response.document;

import java.time.Instant;
import java.time.LocalDate;

public class LightDocumentView {
    private String id;
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private String documentStatus;

    public LightDocumentView(String id, String title, LocalDate startAt, LocalDate endAt, String documentStatus) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.documentStatus = documentStatus;
    }

    public LightDocumentView() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }
}

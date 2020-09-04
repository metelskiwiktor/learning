package pl.wiktor.learning.api.dto.response.document;

import pl.wiktor.learning.domain.document.DocumentStatus;

import java.time.LocalDate;

public class LightDocumentView {
    private String id;
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private DocumentStatus documentStatus;

    public LightDocumentView(String id, String title, LocalDate startAt, LocalDate endAt, DocumentStatus documentStatus) {
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

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }
}

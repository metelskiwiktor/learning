package pl.wiktor.learning.api.dto.response.document;

import java.time.Instant;

public class ReportView {
    private String id;
    private String title;
    private String content;
    private Instant createdAt;

    public ReportView(String id, String title, String content, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public ReportView() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

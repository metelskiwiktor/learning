package pl.wiktor.learning.api.dto.response.event;

import pl.wiktor.learning.api.dto.response.document.LightDocumentView;
import pl.wiktor.learning.domain.event.Type;

import java.time.LocalDate;
import java.util.List;

public class EventView {
    private String id;
    private LocalDate plannedAt;
    private List<LightDocumentView> documents;
    private Type type;
    private String toUserId;

    public EventView(String id, LocalDate plannedAt, List<LightDocumentView> documents, Type type, String toUserId) {
        this.id = id;
        this.plannedAt = plannedAt;
        this.documents = documents;
        this.type = type;
        this.toUserId = toUserId;
    }

    public String getId() {
        return id;
    }

    public LocalDate getPlannedAt() {
        return plannedAt;
    }

    public List<LightDocumentView> getDocuments() {
        return documents;
    }

    public Type getType() {
        return type;
    }

    public String getToUserId() {
        return toUserId;
    }
}

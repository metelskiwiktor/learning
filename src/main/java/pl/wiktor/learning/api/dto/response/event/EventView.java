package pl.wiktor.learning.api.dto.response.event;

import pl.wiktor.learning.api.dto.response.document.LightDocumentView;
import pl.wiktor.learning.domain.event.Type;

import java.time.LocalDateTime;
import java.util.List;

public class EventView {
    private String id;
    private LocalDateTime plannedAt;
    private List<LightDocumentView> documents;
    private Type type;

    public EventView(String id, LocalDateTime plannedAt, List<LightDocumentView> documents, Type type) {
        this.id = id;
        this.plannedAt = plannedAt;
        this.documents = documents;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getPlannedAt() {
        return plannedAt;
    }

    public List<LightDocumentView> getDocuments() {
        return documents;
    }

    public Type getType() {
        return type;
    }
}

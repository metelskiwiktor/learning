package pl.wiktor.learning.domain.event;


import org.hibernate.annotations.GenericGenerator;
import pl.wiktor.learning.domain.document.Document;
import pl.wiktor.learning.domain.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Event {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
    private Instant planningAt;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ElementCollection
    private List<String> documentIds;

    public Event(User from, User to, Instant planningAt, Type type, List<String> documentIds) {
        this.from = from;
        this.to = to;
        this.planningAt = planningAt;
        this.type = type;
        this.documentIds = documentIds;
    }

    public Event() {
    }

    public String getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public Instant getPlanningAt() {
        return planningAt;
    }

    public Type getType() {
        return type;
    }

    public List<String> getDocumentIds() {
        if(Objects.isNull(documentIds)) documentIds = new ArrayList<>();
        return documentIds;
    }
}

package pl.wiktor.learning.api.dto.response.document;

import pl.wiktor.learning.api.dto.response.event.EventView;
import pl.wiktor.learning.domain.event.Event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class ThingsPerDay {
    private final LocalDate day;
    private final LightDocumentView lightDocumentView;
    private final List<EventView> events;

    public ThingsPerDay(LightDocumentView lightDocumentView, List<EventView> events, LocalDate day) {
        this.lightDocumentView = lightDocumentView;
        this.events = events;
        this.day = day;
    }

    public LightDocumentView getLightDocumentView() {
        return lightDocumentView;
    }

    public List<EventView> getEvents() {
        return events;
    }

    public LocalDate getDay() {
        return day;
    }
}

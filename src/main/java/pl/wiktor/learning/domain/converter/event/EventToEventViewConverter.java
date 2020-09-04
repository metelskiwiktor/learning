package pl.wiktor.learning.domain.converter.event;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.document.LightDocumentView;
import pl.wiktor.learning.api.dto.response.event.EventView;
import pl.wiktor.learning.domain.document.DocumentService;
import pl.wiktor.learning.domain.event.Event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventToEventViewConverter implements Converter<Event, EventView> {
    private final ConversionService conversionService;
    private final DocumentService documentService;

    @Lazy
    public EventToEventViewConverter(ConversionService conversionService, DocumentService documentService) {
        this.conversionService = conversionService;
        this.documentService = documentService;
    }

    @Override
    public EventView convert(Event event) {

        List<LightDocumentView> documents = event.getDocumentIds().stream()
                .map(documentService::getDocumentById)
                .map(document -> conversionService.convert(document, LightDocumentView.class))
                .collect(Collectors.toList());

        return new EventView(
                event.getId(),
                LocalDateTime.ofInstant(event.getPlanningAt(), ZoneId.systemDefault()).toLocalDate(),
                documents,
                event.getType(),
                event.getTo().getId()
        );
    }
}

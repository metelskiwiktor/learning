package pl.wiktor.learning.domain.converter.document;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.document.LightDocumentView;
import pl.wiktor.learning.domain.document.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DocumentToLightDocumentViewConverter implements Converter<Document, LightDocumentView> {
    @Override
    public LightDocumentView convert(Document document) {
        return new LightDocumentView(
                document.getId(),
                document.getTitle(),
                document.getStartAt(),
                document.getEndAt(),
                document.getDocumentStatus().getPolishTranslate()
        );
    }
}

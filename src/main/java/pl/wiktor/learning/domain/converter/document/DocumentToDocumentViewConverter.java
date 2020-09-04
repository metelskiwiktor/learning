package pl.wiktor.learning.domain.converter.document;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.document.AnswerView;
import pl.wiktor.learning.api.dto.response.document.DocumentView;
import pl.wiktor.learning.api.dto.response.file.FileView;
import pl.wiktor.learning.api.dto.response.document.UserView;
import pl.wiktor.learning.domain.document.Document;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DocumentToDocumentViewConverter implements Converter<Document, DocumentView> {
    private final ConversionService conversionService;

    @Lazy
    public DocumentToDocumentViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public DocumentView convert(Document document) {
        List<AnswerView> answerViews = null;

        if(!Objects.isNull(document.getAnswers())){
            answerViews = document.getAnswers().stream()
                    .map(answer -> conversionService.convert(answer, AnswerView.class))
                    .collect(Collectors.toList());
        }

        FileView questions = conversionService.convert(document.getQuestions(), FileView.class);

        UserView to = conversionService.convert(document.getTo(), UserView.class);
        UserView from = conversionService.convert(document.getFrom(), UserView.class);

        return new DocumentView(
                document.getId(),
                document.getTitle(),
                questions,
                answerViews,
                to,
                from,
                document.getDocumentStatus(),
                document.getStartAt(),
                document.getEndAt()
        );
    }
}

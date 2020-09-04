package pl.wiktor.learning.domain.converter.document;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.wiktor.learning.api.dto.response.document.AnswerView;
import pl.wiktor.learning.api.dto.response.file.FileView;
import pl.wiktor.learning.domain.document.Answer;

@Component
public class AnswerToAnswerViewConverter implements Converter<Answer, AnswerView> {
    private final ConversionService conversionService;

    @Lazy
    public AnswerToAnswerViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public AnswerView convert(Answer answer) {
        FileView fileView = conversionService.convert(answer.getAnswers(), FileView.class);

        return new AnswerView(
                answer.getId(),
                fileView,
                answer.getAddedAt(),
                answer.getAnswerStatus().getPolishTranslate(),
                answer.getAdministratorNote()
        );
    }
}

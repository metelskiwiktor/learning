package pl.wiktor.learning.api.dto.request.document;

import pl.wiktor.learning.domain.document.AnswerStatus;

public class ReviewAnswer {
    private String administrationNote;
    private AnswerStatus answerStatus;

    public String getAdministrationNote() {
        return administrationNote;
    }

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }
}

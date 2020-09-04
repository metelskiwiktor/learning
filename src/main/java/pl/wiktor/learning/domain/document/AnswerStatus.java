package pl.wiktor.learning.domain.document;

public enum AnswerStatus {
    ACCEPTED("Zaakceptowano"), REJECTED("Wymaga poprawy"), PENDING("Do zatwierdzenia");

    private final String polishTranslate;

    AnswerStatus(String polishTranslate) {
        this.polishTranslate = polishTranslate;
    }

    public String getPolishTranslate() {
        return polishTranslate;
    }
}

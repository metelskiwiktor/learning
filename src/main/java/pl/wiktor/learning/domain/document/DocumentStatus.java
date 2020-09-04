package pl.wiktor.learning.domain.document;

public enum DocumentStatus {
    IN_PROGRESS("W trakcie"), DONE("Zrobione"), TO_DO("Do zrobienia");

    private final String polishTranslate;

    DocumentStatus(String polishTranslate) {
        this.polishTranslate = polishTranslate;
    }

    public String getPolishTranslate() {
        return polishTranslate;
    }
}

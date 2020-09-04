package pl.wiktor.learning.api.exception;

public enum ExceptionCode {
    FILE_PROCESS_FAILED("C_001", "Plik %s nie przetworzył się.", 400),
    RESOURCE_NOT_FOUND("C_002", "Zasób '%s' o id '%s' nie został znaleziony.", 400),
    INVALID_FIELD("C_003", "Pole %s jest nieprawidłowe.", 400),
    ANSWER_ALREADY_ACCEPTED("C_004", "Odpowiedź na dokument %s została już poprawnie udzielona.", 400),
    UNAUTHORIZED_PERMISSIONS("C_005", "Brak odpowiednich uprawnień użytkownika %s do wykonania akcji.", 403);

    private final String code;
    private final String message;
    private final int status;

    ExceptionCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public enum Resource{
        DOCUMENT("dokument"), FILE("plik"), USER("użytkownik"), TITLE("tytuł"),
        ANSWER("odpowiedź dokumentu"), PASSWORD("hasło"), NAME("nazwa"),EMAIL("email");

        private final String polish;

        Resource(String polish) {
            this.polish = polish;
        }

        public String getPolish() {
            return polish;
        }
    }
}

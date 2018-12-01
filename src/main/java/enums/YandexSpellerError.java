package enums;

public enum YandexSpellerError {

    ERROR_UNKNOWN_WORD(1),
    ERROR_REPEAT_WORD(2),
    ERROR_CAPITALIZATION(3),
    ERROR_TOO_MANY_ERRORS(4);

    private int code;

    public int getCode() {
        return code;
    }

    YandexSpellerError(int code) {
        this.code = code;
    }
}

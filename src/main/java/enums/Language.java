package enums;

public enum Language {

    RU("ru"),
    UK("uk"),
    EN("en");

    private String languageCode;

    public String langCode() {
        return languageCode;
    }

    Language(String lang) {
        this.languageCode = lang;
    }
}

package enums;

public enum SimpleWord {

    EN_MOSCOW("Moscow", "moscow"),
    EN_YORK("York", "york"),
    EN_VLADIVOSTOK("Vladivostok", "VladiVostok"),
    RU_MOSCOW("Москва", "москва"),
    RU_YORK("Нью-Йорк", "Нью-йорк"),
    RU_VLADIVOSTOK("Владивосток", "ВладиВосток"),
    EN_WORD_WITH_DIGITS("Age 23", "Age23"),
    RU_WORD_WITH_DIGITS("Возраст 23", "Возраст23"),
    UKR_WORD_WITH_DIGITS("вік 23", "вік23"),
    RU_USING("использованием", "исползованием"),
    RU_EQUATION("уравнение", "уравниние"),
    EN_TEST_STEPS("Test Steps", "TestSteps"),
    EN_TEST_CASE("Test Case", "TestCase"),
    UKR_IF("колі", "коль"),
    UKR_CAPITAL("столиця", "столица"),
    UKR_YOUNG("наймолодших", "найсолодших");

    private String corrVer;
    private String wrongVer;

    public String corrVer() {
        return corrVer;
    }

    public String wrongVer() {
        return wrongVer;
    }

    SimpleWord(String corrVer, String wrongVer) {
        this.corrVer = corrVer;
        this.wrongVer = wrongVer;

    }
}

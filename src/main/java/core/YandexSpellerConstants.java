package core;

/**
 * Created by yulia_atlasova@epam.com.
 * Constants of YandexSpeller
 */
public class YandexSpellerConstants {

    //useful constants for API under test
    public static final String YANDEX_SPELLER_CHECK_TEXT_URI = "https://speller.yandex.net/services/spellservice.json/checkText";
    public static final String YANDEX_SPELLER_CHECK_TEXTS_URI = "https://speller.yandex.net/services/spellservice.json/checkTexts";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String EN_REPEATED_WORD = "how";
    public static final String RU_REPEATED_WORD = "записано";
    public static final String UKR_REPEATED_WORD = "лише";
    public static final String QUOTES = "\"";

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
        UKR_YOUNG("наймолодших", "найсолодших");

        private String corrVer;
        private String wrongVer;

        public String corrVer() {
            return corrVer;
        }

        public String wrongVer() {
            return wrongVer;
        }

        private SimpleWord(String corrVer, String wrongVer) {
            this.corrVer = corrVer;
            this.wrongVer = wrongVer;

        }
    }

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

    public enum SoapAction {
        CHECK_TEXT("checkText", "CheckTextRequest"),
        CHECK_TEXTS("checkTexts", "CheckTextsRequest");

        String method;
        String reqName;

        SoapAction(String action, String reqName) {
            this.method = action;
            this.reqName = reqName;
        }
    }

    public enum Options {
        IGNORE_DIGITS("2"),
        IGNORE_URLS("4"),
        FIND_REPEAT_WORDS("8"),
        IGNORE_CAPITALIZATION("512");

        String code;

        Options(String code) {
            this.code = code;
        }
    }
}

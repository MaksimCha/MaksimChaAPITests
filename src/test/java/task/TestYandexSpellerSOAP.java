package task;

import core.YandexSpellerSOAP;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static core.YandexSpellerConstants.*;
import static core.YandexSpellerConstants.Language.*;
import static core.YandexSpellerConstants.Options.FIND_REPEAT_WORDS;
import static core.YandexSpellerConstants.SimpleWord.*;
import static core.YandexSpellerConstants.SimpleWord.EN_WORD_WITH_DIGITS;
import static core.YandexSpellerConstants.SoapAction.CHECK_TEXT;

/**
 * Created by yulia_atlasova@epam.com.
 * try to test SOAP via RestAssured
 */
public class TestYandexSpellerSOAP {

    @Test
    public void simpleCall() {
        YandexSpellerSOAP
                .with().text(EN_WORD_WITH_DIGITS.wrongVer())
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void useRequestBuilderToChangeParams() {
        YandexSpellerSOAP.with()
                .language(EN)
                .texts(EN_WORD_WITH_DIGITS.wrongVer(), EN_MOSCOW.wrongVer())
                .options(FIND_REPEAT_WORDS)
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }
}

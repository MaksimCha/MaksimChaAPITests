package task;

import core.YandexSpellerSOAP;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static core.YandexSpellerConstants.*;

/**
 * Created by yulia_atlasova@epam.com.
 * try to test SOAP via RestAssured
 */
public class TestYandexSpellerSOAP {

    @Test
    public void simpleCall() {
        YandexSpellerSOAP
                .with().text(SimpleWord.EN_WORD_WITH_DIGITS.wrongVer())
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(SimpleWord.EN_WORD_WITH_DIGITS.wrongVer(), SimpleWord.EN_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void useRequestBuilderToChangeParams() {
        YandexSpellerSOAP.with()
                .language(Language.EN)
                .text(SimpleWord.EN_WORD_WITH_DIGITS.wrongVer())
                .options("6")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(SimpleWord.EN_WORD_WITH_DIGITS.wrongVer(), SimpleWord.EN_WORD_WITH_DIGITS.corrVer())));
    }
}

package task;

import core.YandexSpellerSOAP;
import enums.Actions;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static enums.Language.EN;
import static enums.Options.FIND_REPEAT_WORDS;
import static enums.SimpleWord.EN_MOSCOW;
import static enums.SimpleWord.EN_WORD_WITH_DIGITS;

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
                .action(Actions.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }
}

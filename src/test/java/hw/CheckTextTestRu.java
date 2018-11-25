package hw;

import base.TestBase;
import core.YandexSpellerSOAP;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static core.YandexSpellerConstants.Language.RU;
import static core.YandexSpellerConstants.RU_REPEATED_WORD;
import static core.YandexSpellerConstants.SimpleWord.*;
import static core.YandexSpellerConstants.SoapAction;

public class CheckTextTestRu extends TestBase {

    @Test
    public void checkTextTest() {
        YandexSpellerSOAP.with()
                .language(RU)
                .text(TEXT_RU)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                RU_USING.wrongVer(), RU_USING.corrVer(),
                                RU_EQUATION.wrongVer(), RU_EQUATION.corrVer(),
                                RU_YORK.wrongVer(), RU_YORK.corrVer(),
                                RU_MOSCOW.wrongVer(), RU_MOSCOW.corrVer(),
                                RU_VLADIVOSTOK.wrongVer(), RU_VLADIVOSTOK.corrVer(),
                                RU_WORD_WITH_DIGITS.wrongVer(), RU_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkRepeatedWordsTest() {
        YandexSpellerSOAP.with()
                .language(RU)
                .text(TEXT_RU)
                .options("8")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                RU_USING.wrongVer(), RU_USING.corrVer(),
                                RU_REPEATED_WORD,
                                RU_EQUATION.wrongVer(), RU_EQUATION.corrVer(),
                                RU_YORK.wrongVer(), RU_YORK.corrVer(),
                                RU_MOSCOW.wrongVer(), RU_MOSCOW.corrVer(),
                                RU_VLADIVOSTOK.wrongVer(), RU_VLADIVOSTOK.corrVer(),
                                RU_WORD_WITH_DIGITS.wrongVer(), RU_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkIgnoreCapitalization() {
        YandexSpellerSOAP.with()
                .language(RU)
                .text(TEXT_RU)
                .options("512")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                RU_USING.wrongVer(), RU_USING.corrVer(),
                                RU_EQUATION.wrongVer(), RU_EQUATION.corrVer(),
                                RU_WORD_WITH_DIGITS.wrongVer(), RU_WORD_WITH_DIGITS.corrVer())));
    }
}

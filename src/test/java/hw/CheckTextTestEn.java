package hw;

import base.TestBase;
import core.YandexSpellerSOAP;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static core.YandexSpellerConstants.*;
import static core.YandexSpellerConstants.SimpleWord.*;

public class CheckTextTestEn extends TestBase {

    @Test
    public void checkTextTest() {
        YandexSpellerSOAP.with()
                .text(TEXT_EN)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                EN_TEST_STEPS.wrongVer(), EN_TEST_STEPS.corrVer(),
                                EN_TEST_CASE.wrongVer(), EN_TEST_CASE.corrVer(),
                                EN_YORK.wrongVer(), EN_YORK.corrVer(),
                                EN_MOSCOW.wrongVer(), EN_MOSCOW.corrVer(),
                                EN_VLADIVOSTOK.wrongVer(), EN_VLADIVOSTOK.corrVer(),
                                EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkRepeatedWordsTest() {
        YandexSpellerSOAP.with()
                .text(TEXT_EN)
                .options("8")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                EN_REPEATED_WORD,
                                EN_TEST_STEPS.wrongVer(), EN_TEST_STEPS.corrVer(),
                                EN_TEST_CASE.wrongVer(), EN_TEST_CASE.corrVer(),
                                EN_YORK.wrongVer(), EN_YORK.corrVer(),
                                EN_MOSCOW.wrongVer(), EN_MOSCOW.corrVer(),
                                EN_VLADIVOSTOK.wrongVer(), EN_VLADIVOSTOK.corrVer(),
                                EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkIgnoreCapitalization() {
        YandexSpellerSOAP.with()
                .text(TEXT_EN)
                .options("512")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                EN_TEST_STEPS.wrongVer(), EN_TEST_STEPS.corrVer(),
                                EN_TEST_CASE.wrongVer(), EN_TEST_CASE.corrVer(),
                                EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }
}

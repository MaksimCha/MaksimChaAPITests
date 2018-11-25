package hw;

import base.TestBase;
import core.YandexSpellerSOAP;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static core.YandexSpellerConstants.Language.UK;
import static core.YandexSpellerConstants.SimpleWord.*;
import static core.YandexSpellerConstants.SoapAction;
import static core.YandexSpellerConstants.UKR_REPEATED_WORD;

public class CheckTextTestUkr extends TestBase {

    @Test
    public void checkTextTest() {
        YandexSpellerSOAP.with()
                .language(UK)
                .text(TEXT_UKR)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                UKR_IF.wrongVer(), UKR_IF.corrVer(),
                                UKR_YOUNG.wrongVer(), UKR_YOUNG.corrVer(),
                                RU_YORK.wrongVer(), RU_YORK.corrVer(),
                                RU_MOSCOW.wrongVer(), RU_MOSCOW.corrVer(),
                                UKR_WORD_WITH_DIGITS.wrongVer(), UKR_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkRepeatedWordsTest() {
        YandexSpellerSOAP.with()
                .language(UK)
                .text(TEXT_UKR)
                .options("8")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                UKR_REPEATED_WORD,
                                UKR_IF.wrongVer(), UKR_IF.corrVer(),
                                UKR_YOUNG.wrongVer(), UKR_YOUNG.corrVer(),
                                RU_YORK.wrongVer(), RU_YORK.corrVer(),
                                RU_MOSCOW.wrongVer(), RU_MOSCOW.corrVer(),
                                UKR_WORD_WITH_DIGITS.wrongVer(), UKR_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkIgnoreCapitalization() {
        YandexSpellerSOAP.with()
                .language(UK)
                .text(TEXT_UKR)
                .options("512")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                UKR_YOUNG.wrongVer(), UKR_YOUNG.corrVer(),
                                UKR_WORD_WITH_DIGITS.wrongVer(), UKR_WORD_WITH_DIGITS.corrVer())));
    }
}

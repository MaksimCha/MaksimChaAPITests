package hw;

import base.TestBase;
import core.YandexSpellerSOAP;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static core.YandexSpellerConstants.*;
import static core.YandexSpellerConstants.Options.IGNORE_CAPITALIZATION;
import static core.YandexSpellerConstants.Options.IGNORE_DIGITS;
import static core.YandexSpellerConstants.SimpleWord.*;
import static core.YandexSpellerConstants.SoapAction.CHECK_TEXT;
import static org.apache.http.HttpStatus.*;

public class YandexSpellerRestTest extends TestBase {

    @Test
    public void statusTest() {
        RestAssured
                .given()
                .log().everything()
                .when()
                .get(YANDEX_SPELLER_CHECK_TEXT_URI)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }
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
                .options(IGNORE_DIGITS)
                .action(CHECK_TEXT)
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
                .options(IGNORE_CAPITALIZATION)
                .action(CHECK_TEXT)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(
                                EN_TEST_STEPS.wrongVer(), EN_TEST_STEPS.corrVer(),
                                EN_TEST_CASE.wrongVer(), EN_TEST_CASE.corrVer(),
                                EN_WORD_WITH_DIGITS.wrongVer(), EN_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void checkTextsTest() {
        YandexSpellerSOAP.with()
                .text(TEXT_EN)
                .options(IGNORE_DIGITS)
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
}

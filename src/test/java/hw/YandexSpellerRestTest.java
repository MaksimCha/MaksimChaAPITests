package hw;

import base.TestBase;
import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import core.YandexSpellerSOAP;
import dataProviders.RestDataProvider;
import enums.Language;
import enums.SimpleWord;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static core.YandexSpellerConstants.YANDEX_SPELLER_CHECK_TEXT_URI;
import static enums.Actions.CHECK_TEXT;
import static enums.Actions.CHECK_TEXTS;
import static enums.Options.*;
import static enums.YandexSpellerError.ERROR_REPEAT_WORD;
import static enums.YandexSpellerError.ERROR_UNKNOWN_WORD;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

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

    @Test(dataProvider = "simpleProvider", dataProviderClass = RestDataProvider.class)
    public void simpleTest(String text, Language lang, SimpleWord word) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi
                        .with()
                        .language(lang)
                        .text(text)
                        .callApi(CHECK_TEXT));
        assertThat(answers.get(0).code, equalTo(ERROR_UNKNOWN_WORD.getCode()));
        assertThat(answers.get(0).word, equalTo(word.wrongVer()));
        assertThat(answers.get(0).s.get(0), equalTo(word.corrVer()));
    }

    @Test(dataProvider = "ignoreCapitalProvider", dataProviderClass = RestDataProvider.class)
    public void ignoreCapitaizationTest(String text, Language lang) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi
                        .with()
                        .options(IGNORE_CAPITALIZATION)
                        .language(lang)
                        .text(text)
                        .callApi(CHECK_TEXT));
        assertThat(answers.size(), equalTo(0));
    }

    @Test(dataProvider = "capitalProvider", dataProviderClass = RestDataProvider.class)
    public void capitaizationTest(String text, Language lang, SimpleWord word) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi
                        .with()
                        .language(lang)
                        .text(text)
                        .callApi(CHECK_TEXT));
        assertThat(answers.get(0).word, equalTo(word.wrongVer()));
        assertThat(answers.get(0).s.get(0), equalTo(word.corrVer()));
    }

    @Test(dataProvider = "repeatWordProvider", dataProviderClass = RestDataProvider.class)
    public void repeatWordsTest(String text, Language lang, String word) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi
                        .with()
                        .options(FIND_REPEAT_WORDS)
                        .language(lang)
                        .text(text)
                        .callApi(CHECK_TEXT));
        assertThat(answers.get(0).code, equalTo(ERROR_REPEAT_WORD.getCode()));
        assertThat(answers.get(0).s.get(0), equalTo(word));
    }

    @Test(dataProvider = "digitWordProvider", dataProviderClass = RestDataProvider.class)
    public void ignoreDigitsTest(String text, Language lang, SimpleWord word) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi
                        .with()
                        .options(IGNORE_DIGITS)
                        .language(lang)
                        .text(text)
                        .callApi(CHECK_TEXT));
        assertThat(answers.size(), equalTo(0));
    }

    @Test(dataProvider = "digitWordProvider", dataProviderClass = RestDataProvider.class)
    public void checkDigitsTest(String text, Language lang, SimpleWord word) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(YandexSpellerApi
                        .with()
                        .language(lang)
                        .text(text)
                        .callApi(CHECK_TEXT));
        assertThat(answers.get(0).word, equalTo(word.wrongVer()));
        assertThat(answers.get(0).s.get(0), equalTo(word.corrVer()));
    }

    @Test(dataProvider = "textsProvider", dataProviderClass = RestDataProvider.class)
    public void checkTextsTest(List<String> texts, Language lang, List<SimpleWord> words) {
        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersTexts(YandexSpellerApi
                        .with()
                        .language(lang)
                        .texts(texts.toArray(new String[0]))
                        .callApi(CHECK_TEXTS));
        Iterator<List<YandexSpellerAnswer>> iter = answers.iterator();
        for (SimpleWord word : words) {
            List<YandexSpellerAnswer> answer = iter.next();
            assertThat(answer.get(0).word, equalTo(word.wrongVer()));
            assertThat(answer.get(0).s.get(0), equalTo(word.corrVer()));
        }
    }

    @Test(dataProvider = "textsProvider", dataProviderClass = RestDataProvider.class)
    public void ignoreDigitsCheckTextsTest(List<String> texts, Language lang, List<SimpleWord> words) {
        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersTexts(YandexSpellerApi
                        .with()
                        .language(lang)
                        .options(IGNORE_DIGITS)
                        .texts(texts.toArray(new String[0]))
                        .callApi(CHECK_TEXTS));
        Iterator<List<YandexSpellerAnswer>> iter = answers.iterator();
        List<YandexSpellerAnswer> answer;
        iter.next();
        for (SimpleWord word : words.subList(1, words.size())) {
            answer = iter.next();
            assertThat(answer.get(0).word, equalTo(word.wrongVer()));
            assertThat(answer.get(0).s.get(0), equalTo(word.corrVer()));
        }
    }

    @Test(dataProvider = "textsProvider", dataProviderClass = RestDataProvider.class)
    public void soapCheckTextsTest(List<String> texts, Language lang, List<SimpleWord> words) {
        ValidatableResponse answer = YandexSpellerSOAP
                .with()
                .language(lang)
                .action(CHECK_TEXTS)
                .texts(texts.toArray(new String[0]))
                .callSOAP()
                .then();

        for (SimpleWord word : words) {
            answer.body(containsString(word.wrongVer()), containsString(word.corrVer()));
        }
    }
}

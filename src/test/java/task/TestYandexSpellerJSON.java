package task;

import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static core.YandexSpellerConstants.*;
import static core.YandexSpellerConstants.SimpleWord.EN_MOSCOW;
import static core.YandexSpellerConstants.SimpleWord.UKR_WORD_WITH_DIGITS;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;


/**
 * Created by yulia-atlasova@epam.com.
 */
public class TestYandexSpellerJSON {

    // simple usage of RestAssured library: direct request call and response validations in test.
    @Test
    public void simpleSpellerApiCall() {
        RestAssured
                .given()
                .queryParam("text", "requisitee")
                .params("lang", "en", "CustomParameter", "valueOfParam")
                .accept(ContentType.JSON)
                .auth().basic("abcName", "abcPassword")
                .header("custom header1", "header1.value")
                .and()
                .body("some body payroll")
                .log().everything()
                .when()
                .get(YANDEX_SPELLER_API_URI)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(Matchers.allOf(
                        Matchers.stringContainsInOrder(Arrays.asList("requisitee", "requisite")),
                        Matchers.containsString("\"code\":1")))
                .contentType(ContentType.JSON)
                .time(lessThan(20000L)); // Milliseconds
    }

    // different http methods calls
    @Test
    public void spellerApiCallsWithDifferentMethods() {
        //GET
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log().everything()
                .get(YANDEX_SPELLER_API_URI)
                .prettyPeek();
        System.out.println(repeat("=", 100));

        //POST
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log().everything()
                .post(YANDEX_SPELLER_API_URI)
                .prettyPeek();
        System.out.println(repeat("=", 100));

        //HEAD
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log().everything()
                .head(YANDEX_SPELLER_API_URI)
                .prettyPeek();
        System.out.println(repeat("=", 100));

        //OPTIONS
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log().everything()
                .options(YANDEX_SPELLER_API_URI)
                .prettyPeek();
        System.out.println(repeat("=", 100));

        //PUT
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log().everything()
                .put(YANDEX_SPELLER_API_URI)
                .prettyPeek();
        System.out.println(repeat("=", 100));

        //PATCH
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log()
                .everything()
                .patch(YANDEX_SPELLER_API_URI)
                .prettyPeek();
        System.out.println(repeat("=", 100));

        //DELETE
        RestAssured
                .given()
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .log()
                .everything()
                .delete(YANDEX_SPELLER_API_URI).prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .statusLine("HTTP/1.1 405 Method not allowed");
    }


    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecifications() {
        RestAssured
                .given(YandexSpellerApi.baseRequestConfiguration())
                .param(PARAM_TEXT, SimpleWord.EN_MOSCOW.wrongVer())
                .get().prettyPeek()
                .then().specification(YandexSpellerApi.successResponse());
    }

    @Test
    public void reachBuilderUsage() {
        YandexSpellerApi.with()
                .language(Language.UK)
                .options("5")
                .text(UKR_REPEATED_WORD)
                .callApi()
                .then().specification(YandexSpellerApi.successResponse());
    }


    //validate an object we've got in API response
    @Test
    public void validateSpellerAnswerAsAnObject() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().text("motherr fatherr," + SimpleWord.RU_MOSCOW.wrongVer()).callApi());
        assertThat("expected number of answers is wrong.", answers.size(), equalTo(3));
        assertThat(answers.get(0).word, equalTo("motherr"));
        assertThat(answers.get(1).word, equalTo("fatherr"));
        assertThat(answers.get(0).s.get(0), equalTo("mother"));
        assertThat(answers.get(1).s.get(0), equalTo("father"));
        assertThat(answers.get(2).s.get(0), equalTo(SimpleWord.RU_MOSCOW.wrongVer()));
    }

    @Test
    public void optionsValueIgnoreDigits() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().
                                text(UKR_WORD_WITH_DIGITS.wrongVer())
                                .options("2")
                                .callApi());
        assertThat("expected number of answers is wrong.", answers.size(), equalTo(0));
    }

    @Test
    public void optionsIgnoreWrongCapital() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerApi.getYandexSpellerAnswers(
                        YandexSpellerApi.with().
                                text(EN_MOSCOW.wrongVer())
                                .options("512")
                                .callApi());
        assertThat("expected number of answers is wrong.", answers.size(), equalTo(0));
    }
}

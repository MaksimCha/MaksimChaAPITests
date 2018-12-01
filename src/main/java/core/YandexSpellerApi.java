package core;

import beans.YandexSpellerAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Actions;
import enums.Language;
import enums.Options;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.*;

import static core.YandexSpellerConstants.*;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by yulia_atlasova@epam.com
 * Describes Yandex Speller REST API
 */
public class YandexSpellerApi {

    //builder pattern
    private YandexSpellerApi() {
    }

    private HashMap<String, String> params = new HashMap<>();
    private List<String> texts = new ArrayList<>();

    public static class ApiBuilder {
        YandexSpellerApi spellerApi;

        private ApiBuilder(YandexSpellerApi gcApi) {
            spellerApi = gcApi;
        }

        public ApiBuilder text(String text) {
            spellerApi.texts.add(text);
            return this;
        }

        public ApiBuilder texts(String... texts) {
            spellerApi.texts.addAll(Arrays.asList(texts));
            return this;
        }

        public ApiBuilder options(Options... options) {
            int sum = 0;
            for (Options option : options) {
                sum += option.getCode();
            }
            spellerApi.params.put(PARAM_OPTIONS, String.valueOf(sum));
            return this;
        }

        public ApiBuilder language(Language language) {
            spellerApi.params.put(PARAM_LANG, language.langCode());
            return this;
        }

        public Response callApi(Actions action) {
            return RestAssured.with()
                    .params(spellerApi.params)
                    .queryParams(PARAM_TEXT, spellerApi.texts)
                    .log().all()
                    .get(action.getURI()).prettyPeek();
        }
    }

    public static ApiBuilder with() {
        YandexSpellerApi api = new YandexSpellerApi();
        return new ApiBuilder(api);
    }


    //get ready Speller answers list form api response
    public static List<YandexSpellerAnswer> getYandexSpellerAnswers(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<YandexSpellerAnswer>>() {
        }.getType());
    }

    //get ready Speller answers list form api response
    public static List<List<YandexSpellerAnswer>> getYandexSpellerAnswersTexts(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<List<YandexSpellerAnswer>>>() {
        }.getType());
    }


    //set base request and response specifications tu use in tests
    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .setRelaxedHTTPSValidation()
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(YANDEX_SPELLER_CHECK_TEXT_URI)
                .build();
    }
}

package core;

import enums.Language;
import enums.Options;
import enums.Actions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static core.YandexSpellerConstants.*;

/**
 * Created by yulia_atlasova@epam.com.
 * Describes Yandex Speller SOAP request.
 */
public class YandexSpellerSOAP {

    static RequestSpecification spellerSOAPreqSpec = new RequestSpecBuilder()
            .addHeader("Accept-Encoding", "gzip,deflate")
            .setContentType("text/xml;charset=UTF-8")
            .addHeader("Host", "speller.yandex.net")
            .setBaseUri("http://speller.yandex.net/services/spellservice")
            .build();

    //builder pattern
    private YandexSpellerSOAP() {
    }

    private HashMap<String, String> params = new HashMap<>();
    private Actions action = Actions.CHECK_TEXT;
    private List<String> texts = new ArrayList<>();

    public static class SOAPBuilder {
        YandexSpellerSOAP soapReq;

        private SOAPBuilder(YandexSpellerSOAP soap) {
            this.soapReq = soap;
        }

        public YandexSpellerSOAP.SOAPBuilder action(Actions action) {
            soapReq.action = action;
            return this;
        }

        public YandexSpellerSOAP.SOAPBuilder text(String text) {
            soapReq.texts.add(text);
            return this;
        }

        public YandexSpellerSOAP.SOAPBuilder texts(String... texts) {
            soapReq.texts.addAll(Arrays.asList(texts));
            return this;
        }

        public YandexSpellerSOAP.SOAPBuilder options(Options ... options) {
            int sum = 0;
            for (Options option : options) {
                sum += option.getCode();
            }
            soapReq.params.put(PARAM_OPTIONS, String.valueOf(sum));
            return this;
        }

        public YandexSpellerSOAP.SOAPBuilder language(Language language) {
            soapReq.params.put(PARAM_LANG, language.langCode());
            return this;
        }

        public Response callSOAP() {
            StringBuilder preparedText = new StringBuilder();
            for (String text : soapReq.texts) {
                preparedText.append("         <spel:text>").append(text).append("</spel:text>\n");
            }
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel=\"http://speller.yandex.net/services/spellservice\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <spel:" + soapReq.action.getReqName() + " lang=" + QUOTES + (soapReq.params.getOrDefault(PARAM_LANG, "en")) + QUOTES
                    + " options=" + QUOTES + (soapReq.params.getOrDefault(PARAM_OPTIONS, "0")) + QUOTES
                    + " format=\"\">\n" +
                    preparedText.toString() +
                    "      </spel:" + soapReq.action.getReqName() + ">\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            soapReq.texts.clear();

            return RestAssured.with()
                    .spec(spellerSOAPreqSpec)
                    .header("SOAPAction", "http://speller.yandex.net/services/spellservice/" + soapReq.action.getMethod())
                    .body(soapBody)
                    .log().all().with()
                    .post().prettyPeek();
        }
    }


    public static SOAPBuilder with() {
        core.YandexSpellerSOAP soap = new YandexSpellerSOAP();
        return new SOAPBuilder(soap);
    }
}

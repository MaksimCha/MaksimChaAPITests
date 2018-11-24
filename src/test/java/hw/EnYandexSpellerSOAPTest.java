package hw;

import base.TestBase;
import core.YandexSpellerSOAP;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;


import java.util.Arrays;

import static core.YandexSpellerConstants.*;

public class EnYandexSpellerSOAPTest extends TestBase {

    @Test
    public void simpleCall(){
                YandexSpellerSOAP
                        .with().text(SimpleWord.UKR_WORD_WITH_DIGITS.wrongVer())
                        .callSOAP()
                        .then()
                        .body(Matchers.stringContainsInOrder
                                (Arrays.asList(SimpleWord.UKR_WORD_WITH_DIGITS.wrongVer(), SimpleWord.UKR_WORD_WITH_DIGITS.corrVer())));
    }

    @Test
    public void useRequestBuilderToChangeParams(){
        YandexSpellerSOAP.with()
                .language(Language.EN)
                .text(SimpleWord.UKR_WORD_WITH_DIGITS.wrongVer())
                .options("6")
                .action(SoapAction.CHECK_TEXTS)
                .callSOAP()
                .then()
                .body(Matchers.stringContainsInOrder
                        (Arrays.asList(SimpleWord.UKR_WORD_WITH_DIGITS.wrongVer(), SimpleWord.UKR_WORD_WITH_DIGITS.corrVer())));
    }
}
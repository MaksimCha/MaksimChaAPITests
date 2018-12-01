package dataProviders;

import enums.Language;
import enums.SimpleWord;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

import static core.YandexSpellerConstants.EN_REPEATED_WORD;
import static core.YandexSpellerConstants.RU_REPEATED_WORD;
import static core.YandexSpellerConstants.UKR_REPEATED_WORD;
import static enums.Language.EN;
import static enums.Language.RU;
import static enums.Language.UK;
import static enums.SimpleWord.*;

public class RestDataProvider {

    @DataProvider(parallel = true)
    public Object[][] ignoreCapitalProvider() {
        return new Object[][]{
                {"VladiVostok is a capital of Great Britain", EN},
                {"ВладиВосток - столица России", RU},
                {"ВладиВосток - столиця Росії", UK}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] capitalProvider() {
        return new Object[][]{
                {"VladiVostok is a capital of Great Britain", EN, EN_VLADIVOSTOK},
                {"ВладиВосток - столица России", RU, RU_VLADIVOSTOK},
                {"ВладиВосток - столиця Росії", UK, RU_VLADIVOSTOK}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] simpleProvider() {
        return new Object[][]{
                {"how how TestSteps need be covered", EN, EN_TEST_STEPS},
                {"записано записано исползованием функций", RU, RU_USING},
                {"лише лише найсолодших столиця", UK, UKR_YOUNG}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] repeatWordProvider() {
        return new Object[][]{
                {"how how TestSteps need be covered", EN, EN_REPEATED_WORD},
                {"записано записано исползованием функций", RU, RU_REPEATED_WORD},
                {"лише лише найсолодших столиця", UK, UKR_REPEATED_WORD}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] digitWordProvider() {
        return new Object[][]{
                {"Age23 how how need be covered", EN, EN_WORD_WITH_DIGITS},
                {"Возраст23 записано записано функций", RU, RU_WORD_WITH_DIGITS},
                {"вік23 лише лише столиця", UK, UKR_WORD_WITH_DIGITS}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] textsProvider() {
        return new Object[][]{
                {getTexts(EN), EN, getWords(EN)},
                {getTexts(RU), RU, getWords(RU)},
                {getTexts(UK), UK, getWords(UK)}
        };
    }

    private List<String> getTexts(Language lang) {
        List<String> texts = new ArrayList<>();
        if(lang == RU){
            texts.add("Возраст23");
            texts.add("исползованием функций");
            texts.add("уравниние равенства");
        }
        if(lang == EN){
            texts.add("Age23");
            texts.add("TestSteps are covered");
            texts.add("TestCase done");
        }
        if(lang == UK){
            texts.add("вік23");
            texts.add("столица Росії");
            texts.add("найсолодших столиця");
        }
        return texts;
    }

    private List<SimpleWord> getWords(Language lang) {
        List<SimpleWord> texts = new ArrayList<>();
        if(lang == RU){
            texts.add(RU_WORD_WITH_DIGITS);
            texts.add(RU_USING);
            texts.add(RU_EQUATION);
        }
        if(lang == EN){
            texts.add(EN_WORD_WITH_DIGITS);
            texts.add(EN_TEST_STEPS);
            texts.add(EN_TEST_CASE);
        }
        if(lang == UK){
            texts.add(UKR_WORD_WITH_DIGITS);
            texts.add(UKR_CAPITAL);
            texts.add(UKR_YOUNG);
        }
        return texts;
    }
}

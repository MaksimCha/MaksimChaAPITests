package base;

import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.getProperty;

public class TestBase {

    protected static String TEXT_EN;
    protected static String TEXT_RU;
    protected static String TEXT_UKR;

    private Properties prop;

    private InputStream input;

    @BeforeTest(alwaysRun = true)
    public void beforeTestEn() throws IOException {
        input = new FileInputStream("en.properties");
        prop.load(input);
        TEXT_EN = getProperty("enText");
        TEXT_RU = getProperty("ruText");
        TEXT_UKR = getProperty("ukrText");
    }
}

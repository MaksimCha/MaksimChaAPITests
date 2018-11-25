package base;

import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class TestBase {

    protected String TEXT_EN;
    protected String TEXT_RU;
    protected String TEXT_UKR;

    private Properties prop = new Properties();

    @BeforeClass(alwaysRun = true)
    public void beforeTestEn() throws IOException {
        InputStream input = new FileInputStream("lang.properties");
        prop.load(new InputStreamReader(input, Charset.forName("Cp1251")));
        TEXT_EN = prop.getProperty("enText");
        TEXT_RU = prop.getProperty("ruText");
        TEXT_UKR = prop.getProperty("ukrText");
    }
}

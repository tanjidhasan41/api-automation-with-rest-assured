package setup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {

    public Properties properties;

    public FileInputStream fileInputStream;

    @BeforeTest
    public void setup() throws IOException {

        properties = new Properties();
        fileInputStream = new FileInputStream("./src/test/resources/config.properties");
        properties.load(fileInputStream);

    }

    @AfterMethod
    public void refresh() throws IOException {

        properties = new Properties();
        fileInputStream = new FileInputStream("./src/test/resources/config.properties");
        properties.load(fileInputStream);

    }

}

package org.dague.qa.base;

import org.dague.qa.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Base {

    WebDriver driver;
    public Properties prop;
    public Properties dataProp;

    public Base() {

         prop = new Properties();
        File propFile = new File(System.getProperty("user.dir")
                +"\\src\\main\\java\\org\\dague\\qa\\config\\config.properties");

        dataProp = new Properties();
        File dataPropFile = new File(System.getProperty("user.dir")
                +"\\src\\main\\java\\org\\dague\\qa\\testdata\\testdata.properties");

        try {
            FileInputStream fis = new FileInputStream(propFile);
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FileInputStream dataFis = new FileInputStream(dataPropFile);
            dataProp.load(dataFis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public WebDriver initializeBrowserAndOpenApplicationURL(String browserName) {

        switch (browserName.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.IMPLICIT_WAIT_TIME));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.PAGE_LOAD_TIME));

        driver.get(prop.getProperty("url"));

        return driver;

    }
}

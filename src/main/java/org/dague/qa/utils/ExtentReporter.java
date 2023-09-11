package org.dague.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExtentReporter {

    public static ExtentReports generateExtentReport() {

        ExtentReports extentReport = new ExtentReports();
        File extentReportFile = new File(System.getProperty("user.dir")
                +"\\test-output\\ExtentReports\\extentReport.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName("Testng Selenium Project Test Results");
        sparkReporter.config().setDocumentTitle("Testng Selenium Project Report");
        sparkReporter.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss");

        extentReport.attachReporter(sparkReporter);

        Properties configProp = new Properties();
        File configPropFile = new File(System.getProperty("user.dir")
                +"\\src\\main\\java\\org\\dague\\qa\\config\\config.properties");

        try {
            FileInputStream fisConfigProp = new FileInputStream(configPropFile);
            configProp.load(fisConfigProp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        extentReport.setSystemInfo("Application URL",configProp.getProperty("url"));
        extentReport.setSystemInfo("Browser Name",configProp.getProperty("browserName"));
        extentReport.setSystemInfo("Email",configProp.getProperty("validEmail"));
        extentReport.setSystemInfo("Password",configProp.getProperty("validPassword"));
        extentReport.setSystemInfo("Operating System",System.getProperty("os.name"));
        extentReport.setSystemInfo("Username",System.getProperty("user.name"));
        extentReport.setSystemInfo("Java Version",System.getProperty("java.version"));

        return extentReport;
    }
}

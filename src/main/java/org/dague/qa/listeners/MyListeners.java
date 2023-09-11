package org.dague.qa.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.dague.qa.utils.ExtentReporter;
import org.dague.qa.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.dague.qa.utils.Utilities.getDriverFromResult;

public class MyListeners implements ITestListener {

    ExtentReports extentReport;
    ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {

        extentReport = ExtentReporter.generateExtentReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReport.createTest(result.getName());
        extentTest.log(Status.INFO,result.getName()+" started executing");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS,result.getName()+" got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            String screenshotPath = Utilities.captureScreenshot(driver, result.getName());
            extentTest.addScreenCaptureFromPath(screenshotPath);
        }
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.FAIL, result.getName() + " failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.log(Status.INFO,result.getThrowable());
        extentTest.log(Status.SKIP, result.getName()+" got skipped");

    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("FINISHED");

        extentReport.flush();

        String pathOfExtentReport = System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html";
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package org.dague.qa.utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

public class Utilities {

    public static final int IMPLICIT_WAIT_TIME = 10;
    public static final int PAGE_LOAD_TIME = 5;

    public static String generateEmailWithTimestamp() {
        Date date = new Date();
        String timestamp = date.toString().replace(" ", "_").replace(":", "_");
        return "test"+timestamp+"@gmail.com";
    }

    public static Object[][] getTestDataFromExcel(String sheetName) {
        File excelFile = new File(System.getProperty("user.dir")
                +"\\src\\main\\java\\org\\dague\\qa\\testdata\\TestngSeleniumTestData.xlsx");
        XSSFWorkbook workbook;

        try {
            FileInputStream fisExcel = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fisExcel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheet(sheetName);

        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rows][cols];
        for(int i=0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i + 1);
            for(int j =0; j < cols; j++) {
                XSSFCell cell = row.getCell(j);

                CellType cellType = cell.getCellType();

                switch (cellType) {

                    case STRING:
                        data[i][j] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        data[i][j] = Integer.toString((int) cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        data[i][j] = cell.getBooleanCellValue();
                        break;
                }
            }
        }

        return data;

    }

    public static String captureScreenshot(WebDriver driver,String testName) {

        File srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String destinationScreenshotPath = System.getProperty("user.dir")+"\\Screenshots\\"+testName+".png";


        try {
            FileHandler.copy(srcScreenshot,new File(destinationScreenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return destinationScreenshotPath;
    }

    public static WebDriver getDriverFromResult(ITestResult result) {

        Field driverField = null;
        try {
            driverField = result.getTestClass().getRealClass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(result.getInstance());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return null;
    }

}

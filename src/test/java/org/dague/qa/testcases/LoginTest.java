package org.dague.qa.testcases;

import org.dague.qa.base.Base;
import org.dague.qa.pageObjects.AccountPage;
import org.dague.qa.pageObjects.HomePage;
import org.dague.qa.pageObjects.LoginPage;
import org.dague.qa.utils.Utilities;
import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends Base {

    LoginPage loginPage;

    public LoginTest() {
        super();
    }

    public WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
        HomePage homePage = new HomePage(driver);
        loginPage = homePage.navigateToLoginPage();
    }

    @AfterMethod
    void tearDown() {
        driver.quit();

    }
    @Test(priority = 1, dataProvider = "validCredentialsSupplier")
    public void verifyLoginWithValidCredentials(String email, String password) {

        AccountPage accountPage = loginPage.login(email, password);

        Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInfoOption());


    }

    @DataProvider(name="validCredentialsSupplier")
    public Object[][] supplyTestData() {
        return Utilities.getTestDataFromExcel("Login");
    }

    @Test(priority = 2)
    public void verifyLoginWithInvalidCredentials() {

        loginPage.login(Utilities.generateEmailWithTimestamp(), dataProp.getProperty("invalidPassword"));

        String actualWarningMessage = loginPage.retrieveEmailAndPasswordNotMatchingWarningMessageText();
        String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
                "Expected Warning message not displayed.");

    }

    @Test(priority = 3)
    public void verifyLoginWithInvalidEmailAndValidPassword() {

        loginPage.login(Utilities.generateEmailWithTimestamp(), prop.getProperty("validPassword"));

        String actualWarningMessage = loginPage.retrieveEmailAndPasswordNotMatchingWarningMessageText();
        String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
                "Expected Warning message not displayed.");

    }

    @Test(priority = 4)
    public void verifyLoginWithValidEmailAndinValidPassword() {

        loginPage.login(prop.getProperty("validEmail"), dataProp.getProperty("invalidPassword"));

        String actualWarningMessage = loginPage.retrieveEmailAndPasswordNotMatchingWarningMessageText();
        String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
                "Expected Warning message not displayed.");

    }

    @Test(priority = 5)
    public void verifyLoginWithoutProvidingCredentials() {

        loginPage.clickOnLoginButton();

        String actualWarningMessage = loginPage.retrieveEmailAndPasswordNotMatchingWarningMessageText();
        String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
        Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),
                "Expected Warning message not displayed.");
    }

}

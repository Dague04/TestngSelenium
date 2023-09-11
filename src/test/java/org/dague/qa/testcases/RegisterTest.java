package org.dague.qa.testcases;

import org.dague.qa.base.Base;
import org.dague.qa.pageObjects.AccountSuccessPage;
import org.dague.qa.pageObjects.HomePage;
import org.dague.qa.pageObjects.RegisterPage;
import org.dague.qa.utils.Utilities;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends Base {

    RegisterPage registerPage;
    AccountSuccessPage accountSuccessPage;
    public WebDriver driver;

    public RegisterTest() {
        super();
    }

    @BeforeMethod
    public void setup() {

        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));

        HomePage homePage = new HomePage(driver);
        registerPage = homePage.navigateToRegisterPage();
    }
    @AfterMethod
    void tearDown() {
        driver.quit();

    }

    @Test(priority = 1)
    public void verifyRegisteringAnAccountWithMandatoryFields() {

        accountSuccessPage = registerPage.RegisterWithMandatoryFields(dataProp.getProperty("firstName"),
                dataProp.getProperty("lastName"), Utilities.generateEmailWithTimestamp(),
                dataProp.getProperty("phoneNumber"), prop.getProperty("validPassword"));

        String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
        Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountSuccessfullyCreatedHeading"),
                "Account Success Page isn't displayed.");

    }

    @Test(priority = 2)
    public void verifyRegisteringAccountByProvidingAllFields() {

        accountSuccessPage = registerPage.registerWithAllFields(dataProp.getProperty("firstName"),
                dataProp.getProperty("lastName"),Utilities.generateEmailWithTimestamp(),
                dataProp.getProperty("phoneNumber"),prop.getProperty("validPassword"));

        String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
        Assert.assertEquals(actualSuccessHeading, dataProp.getProperty("accountSuccessfullyCreatedHeading"),
                "Account Success Page isn't displayed.");

    }


    @Test(priority = 3)
    public void verifyRegisteringAccountWithExistingEmailAddress() {

        registerPage.registerWithAllFields(dataProp.getProperty("firstName"),
                dataProp.getProperty("lastName"),prop.getProperty("validEmail"),
                dataProp.getProperty("phoneNumber"),prop.getProperty("validPassword"));

        String actualWarning = registerPage.retrieveDuplicateEmailAddressWarningMessage();
        Assert.assertTrue(actualWarning.contains(dataProp.getProperty("duplicateEmailWarning")),
                "Warning message isn't displayed.");

    }

    @Test(priority = 4)
    public void verifyRegisteringAccountWithoutFillingAnyDetails() {

        registerPage.clickOnContinue();
        Assert.assertTrue(registerPage.displayStatusOfWarningMessages(dataProp.getProperty("privacyPolicyWarning"),
                dataProp.getProperty("firstNameWarning"),dataProp.getProperty("lastNameWarning"),
                dataProp.getProperty("emailAddressWarning"),dataProp.getProperty("phoneNumberWarning"),
                dataProp.getProperty("passwordWarning")));

    }
}

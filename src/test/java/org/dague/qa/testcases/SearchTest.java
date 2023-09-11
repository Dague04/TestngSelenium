package org.dague.qa.testcases;


import org.dague.qa.base.Base;
import org.dague.qa.pageObjects.HomePage;
import org.dague.qa.pageObjects.SearchPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTest extends Base {

    public WebDriver driver;

    SearchPage searchPage;
    HomePage homePage;

    public SearchTest() {
        super();
    }
    @BeforeMethod
    public void setup() {
        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
        homePage = new HomePage(driver);
    }

    @AfterMethod
    void tearDown() {
        driver.quit();

    }

    @Test(priority = 1)
    public void verifySearchWithValidProduct() {

        homePage.enterProductNameIntoSearchBoxField(dataProp.getProperty("validProduct"));
        searchPage = homePage.clickOnSearchButton();

        Assert.assertTrue(searchPage.isHPProductDisplayed());

    }

    @Test(priority = 2)
    public void verifySearchWithInvalidProduct() {

        homePage.enterProductNameIntoSearchBoxField(dataProp.getProperty("invalidProduct"));
        searchPage = homePage.clickOnSearchButton();

        String actualSearchMessage = searchPage.retrieveNoProductMessageText();
        Assert.assertEquals(actualSearchMessage, dataProp.getProperty("noMatchingProductInSearchResults"),
                "There is no such product");

    }

    @Test(priority = 3)
    public void verifySearchWithoutAnyProduct() {

        searchPage = homePage.clickOnSearchButton();
        
        String actualSearchMessage = searchPage.retrieveNoProductMessageText();
        Assert.assertEquals(actualSearchMessage, dataProp.getProperty("noMatchingProductInSearchResults"),
                "There is no such product");

    }
}

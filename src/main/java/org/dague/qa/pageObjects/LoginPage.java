package org.dague.qa.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;
    @FindBy(id = "input-email")
    private WebElement emailAddressField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-dismissible')]")
    private WebElement emailAndPasswordNotMatchingWarning;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Actions
    public void provideEmailAddress(String emailInputText) {
        emailAddressField.sendKeys(emailInputText);
    }

    public void providePassword(String passwordInputText) {
        passwordField.sendKeys(passwordInputText);
    }

    public AccountPage clickOnLoginButton() {
        loginButton.click();
        return new AccountPage(driver);
    }

    public AccountPage login(String emailText, String passwordText) {
        emailAddressField.sendKeys(emailText);
        passwordField.sendKeys(passwordText);
        loginButton.click();
        return new AccountPage(driver);
    }

    public String retrieveEmailAndPasswordNotMatchingWarningMessageText() {
        return emailAndPasswordNotMatchingWarning.getText();
    }
}

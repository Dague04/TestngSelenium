package org.dague.qa.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.stream.Stream;

public class RegisterPage {

    WebDriver driver;

    @FindBy(id = "input-firstname")
    private WebElement firstNameField;

    @FindBy(id = "input-lastname")
    private WebElement lastNameField;

    @FindBy(id = "input-email")
    private WebElement emailAddressField;

    @FindBy(id = "input-telephone")
    private WebElement phoneNumberField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(id = "input-confirm")
    private WebElement passwordConfirmField;

    @FindBy(xpath = "//input[@name='newsletter'][@value='1']")
    private WebElement yesNewsletterOption;

    @FindBy(name = "agree")
    private WebElement privacyPolicyField;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement continueButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-dismissible')]")
    private WebElement duplicateEmailAddressWarning;

    @FindBy(xpath = "//div[contains(@class, 'alert-dismissible')]")
    private WebElement policyPrivacyWarning;

    @FindBy(xpath = "//input[@id='input-firstname']/following-sibling::div")
    private WebElement firstnameWarning;

    @FindBy(xpath = "//input[@id='input-lastname']/following-sibling::div")
    private WebElement lastnameWarning;

    @FindBy(xpath = "//input[@id='input-email']/following-sibling::div")
    private WebElement emailAddressWarning;

    @FindBy(xpath = "//input[@id='input-telephone']/following-sibling::div")
    private WebElement phoneNumberWarning;

    @FindBy(xpath = "//input[@id='input-password']/following-sibling::div")
    private WebElement passwordWarning;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Actions
    public void enterTextIntoField(WebElement element, String input) {
        element.sendKeys(input);
    }

    public void provideConfirmPassword(String passwordConfirmInputText) {
        passwordConfirmField.sendKeys(passwordConfirmInputText);
    }

    public void selectYesNewsletterOption() {
        yesNewsletterOption.click();
    }

    public void selectPolicyAgreement() {
        privacyPolicyField.click();
    }

    public AccountSuccessPage clickOnContinue() {
        continueButton.click();
        return new AccountSuccessPage(driver);
    }

    public String retrieveText(WebElement element) {
        return element.getText();
    }


    public String retrieveDuplicateEmailAddressWarningMessage() {
        return duplicateEmailAddressWarning.getText();
    }

    public AccountSuccessPage RegisterWithMandatoryFields(String firstnameInputText, String lastnameInputText, String emailInputText,
                                       String phoneInputText, String passwordInputText) {
        enterTextIntoField(firstNameField, firstnameInputText);
        enterTextIntoField(lastNameField, lastnameInputText);
        enterTextIntoField(emailAddressField, emailInputText);
        enterTextIntoField(phoneNumberField, phoneInputText);
        enterTextIntoField(passwordField, passwordInputText);
        enterTextIntoField(passwordConfirmField, passwordInputText);
        privacyPolicyField.click();
        continueButton.click();
        return new AccountSuccessPage(driver);
    }

    public AccountSuccessPage registerWithAllFields(String firstNameText,String lastNameText,String emailText,String telephoneText,String passwordText) {

        firstNameField.sendKeys(firstNameText);
        lastNameField.sendKeys(lastNameText);
        emailAddressField.sendKeys(emailText);
        phoneNumberField.sendKeys(telephoneText);
        passwordField.sendKeys(passwordText);
        passwordConfirmField.sendKeys(passwordText);
        yesNewsletterOption.click();
        privacyPolicyField.click();
        continueButton.click();
        return new AccountSuccessPage(driver);

    }

    public boolean displayStatusOfWarningMessages(String expectedPrivacyPolicyWarning, String expectedFirstNameWarning, String expectedLastNameWarning, String expectedEmailWarning, String expectedTelephoneWarning, String expectedPasswordWarning) {
        return Stream.of(
                retrieveText(policyPrivacyWarning).contains(expectedPrivacyPolicyWarning),
                retrieveText(firstnameWarning).equals(expectedFirstNameWarning),
                retrieveText(lastnameWarning).equals(expectedLastNameWarning),
                retrieveText(emailAddressWarning).equals(expectedEmailWarning),
                retrieveText(phoneNumberWarning).equals(expectedTelephoneWarning),
                retrieveText(passwordWarning).equals(expectedPasswordWarning)
        ).allMatch(status -> status);
    }

}

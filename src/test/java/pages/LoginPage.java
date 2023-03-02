package pages;

import baseEntities.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private By PAGE_OPENED_IDENTIFIER = By.id("userForm");
    private By userNameSelector = By.id("userName");
    private By passwordSelector = By.id("password");
    private By loginButtonSelector = By.id("login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return super.isPageOpened(PAGE_OPENED_IDENTIFIER);
    }

    private WebElement userNameField() {return driver.findElement(userNameSelector);}
    private WebElement passwordField() {return driver.findElement(passwordSelector);}
    private WebElement loginButton() {
        return driver.findElement(loginButtonSelector);
    }

    public void enterUserName(final String password) {
        userNameField().sendKeys(password);
    }

    public void enterPassword(final String password) {
        passwordField().sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton().click();
    }
}


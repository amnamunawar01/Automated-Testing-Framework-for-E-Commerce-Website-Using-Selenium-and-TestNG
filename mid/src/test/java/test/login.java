package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class login {

    WebDriver driver;

    // Element locators
    By usernameField    = By.id("user-name");
    By passwordField    = By.id("password");
    By loginButton      = By.id("login-button");
    By errorMessage     = By.cssSelector("[data-test='error']");

    // Constructor
    public login(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void doLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isErrorDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(loginButton).isDisplayed();
    }

    public boolean isUsernameFieldVisible() {
        return driver.findElement(usernameField).isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        return driver.findElement(passwordField).isDisplayed();
    }
}
package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class signup {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By signupNavBtn    = By.id("signin2");
    By usernameField   = By.id("sign-username");
    By passwordField   = By.id("sign-password");
    By registerBtn     = By.xpath("//button[text()='Sign up']");

    public signup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openSignupModal() throws InterruptedException {
        driver.findElement(signupNavBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        Thread.sleep(1000);
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSignup() {
        driver.findElement(registerBtn).click();
    }

    public void registerAs(String username, String password) throws InterruptedException {
        openSignupModal();
        enterUsername(username);
        Thread.sleep(800);
        enterPassword(password);
        Thread.sleep(800);
        clickSignup();
    }

    public String getAlertText() throws InterruptedException {
        Thread.sleep(2000);
        String text = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return text;
    }
}

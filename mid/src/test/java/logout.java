package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class logout {

    WebDriver driver;
    WebDriverWait wait;

    By loginNavBtn    = By.id("login2");
    By usernameField  = By.id("loginusername");
    By passwordField  = By.id("loginpassword");
    By loginSubmitBtn = By.xpath("//button[text()='Log in']");
    By logoutNavBtn   = By.id("logout2");
    By welcomeText    = By.id("nameofuser");

    public logout(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void doLogin(String username, String password) throws InterruptedException {
        driver.findElement(loginNavBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        Thread.sleep(1000);
        driver.findElement(usernameField).sendKeys(username);
        Thread.sleep(800);
        driver.findElement(passwordField).sendKeys(password);
        Thread.sleep(800);
        driver.findElement(loginSubmitBtn).click();
        Thread.sleep(2000);
    }

    public void doLogout() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutNavBtn));
        driver.findElement(logoutNavBtn).click();
        Thread.sleep(2000);
    }

    public boolean isWelcomeVisible() {
        try {
            WebElement el = driver.findElement(welcomeText);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String display = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0]).display;", el);
            return !display.equals("none");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoutBtnVisible() {
        try {
            WebElement el = driver.findElement(logoutNavBtn);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String display = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0]).display;", el);
            return !display.equals("none");
        } catch (Exception e) {
            return false;
        }
    }
}
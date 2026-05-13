package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class UItest {

    WebDriver driver;

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);
    }

    @Test
    public void uiTest() throws InterruptedException {
        // Logo visible hai
        WebElement logo = driver.findElement(By.id("nava"));
        Assert.assertTrue(logo.isDisplayed(),
                "Logo nahi dikh raha");
        Assert.assertTrue(logo.getText().contains("PRODUCT STORE"),
                "Logo text galat hai");

        // Nav links check karo
        Assert.assertTrue(
                driver.findElement(By.id("login2")).isDisplayed(),
                "Login link nahi dikh raha");
        Assert.assertTrue(
                driver.findElement(By.id("signin2")).isDisplayed(),
                "Signup link nahi dikh raha");
        Assert.assertTrue(
                driver.findElement(By.id("cartur")).isDisplayed(),
                "Cart link nahi dikh raha");

        // Login modal UI check
        driver.findElement(By.id("login2")).click();
        Thread.sleep(1500);

        Assert.assertTrue(
                driver.findElement(By.id("loginusername")).isDisplayed(),
                "Username field nahi dikh raha");
        Assert.assertTrue(
                driver.findElement(By.id("loginpassword")).isDisplayed(),
                "Password field nahi dikh raha");
        Assert.assertTrue(
                driver.findElement(
                        By.xpath("//button[text()='Log in']")).isDisplayed(),
                "Login button nahi dikh raha");
    }

    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
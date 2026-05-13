package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class smoketest {

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
    public void smokeTest() throws InterruptedException {
        // Website khuli ya nahi
        Assert.assertTrue(driver.getTitle().contains("STORE"),
                "Website nahi khuli");

        // Login button visible hai
        Assert.assertTrue(
                driver.findElement(By.id("login2")).isDisplayed(),
                "Login button nahi dikh raha");

        // Signup button visible hai
        Assert.assertTrue(
                driver.findElement(By.id("signin2")).isDisplayed(),
                "Signup button nahi dikh raha");

        // Cart button visible hai
        Assert.assertTrue(
                driver.findElement(By.id("cartur")).isDisplayed(),
                "Cart button nahi dikh raha");

        // Products load hue ya nahi
        Thread.sleep(2000);
        Assert.assertTrue(
                driver.findElements(
                        By.xpath("//div[@class='card h-100']")).size() > 0,
                "Products load nahi hue");
    }

    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
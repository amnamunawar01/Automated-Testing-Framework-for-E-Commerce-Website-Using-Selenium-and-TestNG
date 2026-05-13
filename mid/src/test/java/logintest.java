package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class logintest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
    }

    // TEST CASE : 01 - Valid Login
    @Test
    public void validLogin() throws InterruptedException {
        // Login button click karo navbar mein
        driver.findElement(By.id("login2")).click();
        Thread.sleep(1500);

        // Modal aane ka wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        driver.findElement(By.id("loginusername")).sendKeys("your_username"); // apna registered username
        Thread.sleep(1000);
        driver.findElement(By.id("loginpassword")).sendKeys("your_password"); // apna password
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(3000);

        // Login hone ke baad navbar mein naam aata hai
        String welcomeText = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("nameofuser"))).getText();
        Assert.assertTrue(welcomeText.contains("Welcome"));
    }

    // TEST CASE : 02 - Empty Fields
    @Test
    public void emptyLogin() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        Thread.sleep(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        // Kuch enter mat karo - seedha login click
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        // Alert aata hai empty fields pe
        String alertText = driver.switchTo().alert().getText();
        Assert.assertTrue(alertText.contains("Please fill out"));
        driver.switchTo().alert().accept();
    }

    // TEST CASE : 03 - Wrong Password
    @Test
    public void wrongPassword() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        Thread.sleep(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        driver.findElement(By.id("loginusername")).sendKeys("WRONG USERNAME");
        Thread.sleep(1000);
        driver.findElement(By.id("loginpassword")).sendKeys("empty");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        String alertText = driver.switchTo().alert().getText();
        Assert.assertTrue(alertText.contains("username is wrong"));
        driver.switchTo().alert().accept();
    }

    // TEST CASE : 04 - Wrong Username
    @Test
    public void wrongUsername() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        Thread.sleep(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        driver.findElement(By.id("loginusername")).sendKeys("wrong_user_xyz");
        Thread.sleep(1000);
        driver.findElement(By.id("loginpassword"));
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        String alertText = driver.switchTo().alert().getText();
        Assert.assertTrue(alertText.contains("User does not exist"));
        driver.switchTo().alert().accept();
    }

    // TEST CASE : 05 - Both Wrong
    @Test
    public void bothWrong() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        Thread.sleep(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        driver.findElement(By.id("loginusername")).sendKeys("abc_fake");
        Thread.sleep(1000);
        driver.findElement(By.id("loginpassword")).sendKeys("empty");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        String alertText = driver.switchTo().alert().getText();
        Assert.assertTrue(alertText.contains("Wrong username"));
        driver.switchTo().alert().accept();
    }

    @AfterMethod
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
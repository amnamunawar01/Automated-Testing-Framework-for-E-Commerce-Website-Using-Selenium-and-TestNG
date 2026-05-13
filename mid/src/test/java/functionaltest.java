package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class functionaltest {

    WebDriver driver;
    WebDriverWait wait;

    String validUsername = "your_username";
    String validPassword = "your_password";

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);
    }

    // TC01 - Valid Login functional check
    @Test
    public void validLoginTest() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(validUsername);
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys(validPassword);
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        String welcome = driver.findElement(By.id("nameofuser")).getText();
        Assert.assertTrue(welcome.contains("Welcome"),
                "Valid login fail hua");
    }

    // TC02 - Invalid Login functional check
    @Test
    public void invalidLoginTest() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys("wrong_user");
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys("wrong_pass");
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        String alert = driver.switchTo().alert().getText();
        Assert.assertTrue(alert.contains("User does not exist"),
                "Invalid login error nahi aaya");
        driver.switchTo().alert().accept();
    }

    // TC03 - Valid Registration functional check
    @Test
    public void validRegistrationTest() throws InterruptedException {
        driver.findElement(By.id("signin2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("sign-username")));
        Thread.sleep(1000);

        String newUser = "testuser_" + System.currentTimeMillis();
        driver.findElement(By.id("sign-username")).sendKeys(newUser);
        Thread.sleep(800);
        driver.findElement(By.id("sign-password")).sendKeys("Test@1234");
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();
        Thread.sleep(2000);

        String alert = driver.switchTo().alert().getText();
        Assert.assertTrue(alert.contains("Sign up successful"),
                "Registration fail hui");
        driver.switchTo().alert().accept();
    }

    // TC04 - Add to cart functional check
    @Test
    public void addToCartTest() throws InterruptedException {
        // Login karo
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(validUsername);
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys(validPassword);
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        // Product click karo
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[@class='card h-100']//h4/a)[1]")));
        driver.findElement(
                By.xpath("(//div[@class='card h-100']//h4/a)[1]")).click();
        Thread.sleep(2000);

        // Add to cart
        driver.findElement(
                By.xpath("//a[contains(text(),'Add to cart')]")).click();
        Thread.sleep(2000);

        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Alert nahi aaya");
        }

        // Cart check karo
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(1000);
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(2000);

        Assert.assertTrue(
                driver.findElements(By.xpath("//tbody/tr")).size() > 0,
                "Cart mein item nahi aaya");
    }

    // TC05 - Logout functional check
    @Test
    public void logoutTest() throws InterruptedException {
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(validUsername);
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys(validPassword);
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("logout2")).click();
        Thread.sleep(2000);

        Assert.assertTrue(
                driver.findElement(By.id("login2")).isDisplayed(),
                "Logout ke baad login button nahi aaya");
    }

    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class e2etest {

    WebDriver driver;
    WebDriverWait wait;

    String username = "your_username";
    String password = "your_password";

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);
    }

    // TC01 - Register → Login → Add to Cart → Remove → Logout
    @Test
    public void fullUserJourney() throws InterruptedException {

        // STEP 1 - Register
        driver.findElement(By.id("signin2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("sign-username")));
        Thread.sleep(1000);
        String newUser = "e2euser_" + System.currentTimeMillis();
        driver.findElement(By.id("sign-username")).sendKeys(newUser);
        Thread.sleep(800);
        driver.findElement(By.id("sign-password")).sendKeys("Test@1234");
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();
        Thread.sleep(2000);
        String regAlert = driver.switchTo().alert().getText();
        Assert.assertTrue(regAlert.contains("Sign up successful"),
                "Registration fail hui");
        driver.switchTo().alert().accept();
        Thread.sleep(1000);

        // STEP 2 - Login
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(newUser);
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys("Test@1234");
        Thread.sleep(800);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);
        String welcome = driver.findElement(By.id("nameofuser")).getText();
        Assert.assertTrue(welcome.contains("Welcome"),
                "Login fail hua");

        // STEP 3 - Add to Cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[@class='card h-100']//h4/a)[1]")));
        driver.findElement(
                By.xpath("(//div[@class='card h-100']//h4/a)[1]")).click();
        Thread.sleep(2000);
        driver.findElement(
                By.xpath("//a[contains(text(),'Add to cart')]")).click();
        Thread.sleep(2000);
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Alert nahi aaya");
        }
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(2000);

        // STEP 4 - Cart verify karo
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(2000);
        int cartCount = driver.findElements(By.xpath("//tbody/tr")).size();
        Assert.assertTrue(cartCount > 0,
                "Cart mein item nahi aaya");

        // STEP 5 - Item remove karo
        driver.findElement(By.xpath("//a[text()='Delete']")).click();
        Thread.sleep(2000);

        // Cart empty check karo using JS
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean isEmpty = driver.findElements(
                By.xpath("//tbody/tr")).isEmpty();
        Assert.assertTrue(isEmpty,
                "Cart empty nahi hua");

        // STEP 6 - Logout
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(1000);
        driver.findElement(By.id("logout2")).click();
        Thread.sleep(2000);

        WebElement loginBtn = driver.findElement(By.id("login2"));
        Assert.assertTrue(loginBtn.isDisplayed(),
                "Logout fail hua");
    }

    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
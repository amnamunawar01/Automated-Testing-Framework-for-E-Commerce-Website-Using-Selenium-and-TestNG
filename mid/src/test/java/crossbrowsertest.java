package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class crossbrowsertest {

    WebDriver driver;
    WebDriverWait wait;

    String validUsername = "your_username";
    String validPassword = "your_password";

    // TC01 - Chrome pe smoke test
    @Test
    public void smokeTestChrome() throws InterruptedException {
        driver = new ChromeDriver();
        runSmokeTest("Chrome");
    }

    // TC02 - Edge pe smoke test
    @Test
    public void smokeTestEdge() throws InterruptedException {
        driver = new EdgeDriver();
        runSmokeTest("Edge");
    }

    // TC03 - Chrome pe login test
    @Test
    public void loginTestChrome() throws InterruptedException {
        driver = new ChromeDriver();
        runLoginTest("Chrome");
    }

    // TC04 - Edge pe login test
    @Test
    public void loginTestEdge() throws InterruptedException {
        driver = new EdgeDriver();
        runLoginTest("Edge");
    }

    // TC05 - Chrome pe cart test
    @Test
    public void cartTestChrome() throws InterruptedException {
        driver = new ChromeDriver();
        runCartTest("Chrome");
    }

    // TC06 - Edge pe cart test
    @Test
    public void cartTestEdge() throws InterruptedException {
        driver = new EdgeDriver();
        runCartTest("Edge");
    }

    // Common smoke test steps
    private void runSmokeTest(String browserName) throws InterruptedException {
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);

        Assert.assertTrue(driver.getTitle().contains("STORE"),
                browserName + " pe website nahi khuli");
        Assert.assertTrue(
                driver.findElement(By.id("login2")).isDisplayed(),
                browserName + " pe login button nahi dikh raha");
        Assert.assertTrue(
                driver.findElement(By.id("signin2")).isDisplayed(),
                browserName + " pe signup button nahi dikh raha");

        System.out.println(browserName + " Smoke Test PASSED");
        driver.quit();
    }

    // Common login test steps
    private void runLoginTest(String browserName) throws InterruptedException {
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);

        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(validUsername);
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys(validPassword);
        Thread.sleep(800);
        driver.findElement(
                By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        String welcome = driver.findElement(By.id("nameofuser")).getText();
        Assert.assertTrue(welcome.contains("Welcome"),
                browserName + " pe login fail hua");

        System.out.println(browserName + " Login Test PASSED");
        driver.quit();
    }

    // Common cart test steps
    private void runCartTest(String browserName) throws InterruptedException {
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);

        // Login
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")));
        driver.findElement(By.id("loginusername")).sendKeys(validUsername);
        Thread.sleep(800);
        driver.findElement(By.id("loginpassword")).sendKeys(validPassword);
        Thread.sleep(800);
        driver.findElement(
                By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);

        // Product add karo
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

        // Cart verify karo
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(1000);
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(2000);

        Assert.assertTrue(
                driver.findElements(
                        By.xpath("//tbody/tr")).size() > 0,
                browserName + " pe cart mein item nahi aaya");

        System.out.println(browserName + " Cart Test PASSED");
        driver.quit();
    }
}
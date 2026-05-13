package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;

public class addtocart {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By loginNavBtn    = By.id("login2");
    By usernameField  = By.id("loginusername");
    By passwordField  = By.id("loginpassword");
    By loginSubmitBtn = By.xpath("//button[text()='Log in']");
    By firstProduct   = By.xpath("(//div[@class='card h-100']//h4/a)[1]");
    By addToCartBtn   = By.xpath("//a[contains(text(),'Add to cart')]");
    By cartNavBtn     = By.id("cartur");
    By cartRows       = By.xpath("//tbody/tr");
    By welcomeText    = By.id("nameofuser");

    public addtocart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Login karo
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

    // Login hua ya nahi check karo
    public boolean isLoggedIn() {
        try {
            String text = driver.findElement(welcomeText).getText();
            return text.contains("Welcome");
        } catch (Exception e) {
            return false;
        }
    }

    // Product click karo
    public void clickFirstProduct() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct));
        Thread.sleep(1000);
        driver.findElement(firstProduct).click();
        Thread.sleep(2000);
    }

    // Add to cart click karo
    public void clickAddToCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        driver.findElement(addToCartBtn).click();
        Thread.sleep(2000);
        // Alert handle karo
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Alert nahi aaya");
        }
    }

    // Cart open karo
    public void openCart() throws InterruptedException {
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(1500);
        driver.findElement(cartNavBtn).click();
        Thread.sleep(2000);
    }

    // Cart items count karo
    public int getCartItemCount() {
        try {
            List<WebElement> rows = driver.findElements(cartRows);
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    // Cart empty hai ya nahi
    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }
}
package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class cart {

    WebDriver driver;
    WebDriverWait wait;

    By loginNavBtn    = By.id("login2");
    By usernameField  = By.id("loginusername");
    By passwordField  = By.id("loginpassword");
    By loginSubmitBtn = By.xpath("//button[text()='Log in']");
    By firstProduct   = By.xpath("(//div[@class='card h-100']//h4/a)[1]");
    By addToCartBtn   = By.xpath("//a[contains(text(),'Add to cart')]");
    By cartNavBtn     = By.id("cartur");
    By deleteLinks    = By.xpath("//a[text()='Delete']");
    By cartRows       = By.xpath("//tbody/tr");

    public cart(WebDriver driver) {
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

    public void addProductToCart() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct));
        Thread.sleep(1500);
        driver.findElement(firstProduct).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
        driver.findElement(addToCartBtn).click();
        Thread.sleep(2000);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Alert nahi aaya");
        }
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(2000);
    }

    public void openCart() throws InterruptedException {
        driver.findElement(cartNavBtn).click();
        Thread.sleep(2000);
    }

    public int getCartItemCount() {
        try {
            List<WebElement> rows = driver.findElements(cartRows);
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void deleteFirstItem() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteLinks));
        driver.findElement(deleteLinks).click();
        Thread.sleep(2000);
    }

    public void deleteAllItems() throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> delBtns = driver.findElements(deleteLinks);
        while (!delBtns.isEmpty()) {
            delBtns.get(0).click();
            Thread.sleep(2000);
            delBtns = driver.findElements(deleteLinks);
        }
    }

    public boolean isCartEmpty() {
        try {
            List<WebElement> rows = driver.findElements(cartRows);
            return rows.isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isDeleteBtnVisible() {
        try {
            WebElement el = driver.findElement(deleteLinks);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String display = (String) js.executeScript(
                    "return window.getComputedStyle(arguments[0]).display;", el);
            return !display.equals("none");
        } catch (Exception e) {
            return false;
        }
    }
}
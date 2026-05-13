package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class logouttest {

    WebDriver driver;
    WebDriverWait wait;
    tests.logout logoutPage;

    String validUsername = "your_username";  // apna username
    String validPassword = "your_password";  // apna password

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);
        logoutPage = new tests.logout(driver);
    }

    // TC01 - Valid Logout
    @Test
    public void validLogout() throws InterruptedException {
        logoutPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        Assert.assertTrue(logoutPage.isWelcomeVisible(),
                "Login nahi hua");

        logoutPage.doLogout();
        Thread.sleep(2000);

        Assert.assertFalse(logoutPage.isLogoutBtnVisible(),
                "Logout ke baad bhi logout button dikh raha hai");
    }

    // TC02 - Session khatam after logout
    @Test
    public void sessionEndedAfterLogout() throws InterruptedException {
        logoutPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        logoutPage.doLogout();
        Thread.sleep(2000);

        Assert.assertFalse(logoutPage.isLogoutBtnVisible(),
                "Session khatam nahi hua");

        Assert.assertFalse(logoutPage.isWelcomeVisible(),
                "Welcome text abhi bhi dikh raha hai");
    }

    // TC03 - Refresh ke baad bhi logged out rahe
    @Test
    public void stayLoggedOutAfterRefresh() throws InterruptedException {
        logoutPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        logoutPage.doLogout();
        Thread.sleep(2000);

        driver.navigate().refresh();
        Thread.sleep(3000);

        Assert.assertFalse(logoutPage.isWelcomeVisible(),
                "Refresh ke baad user wapas logged in ho gaya");

        Assert.assertFalse(logoutPage.isLogoutBtnVisible(),
                "Refresh ke baad logout button aa gaya");
    }

    // TC04 - Bina login ke logout button na ho
    @Test
    public void logoutBtnNotVisibleWithoutLogin() throws InterruptedException {
        Thread.sleep(2000);

        Assert.assertFalse(logoutPage.isLogoutBtnVisible(),
                "Bina login ke logout button dikh raha hai");
    }

    // TC05 - Logout ke baad wapas login ho sake
    @Test
    public void loginAgainAfterLogout() throws InterruptedException {
        logoutPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        logoutPage.doLogout();
        Thread.sleep(2000);

        logoutPage.doLogin(validUsername, validPassword);
        Thread.sleep(3000);

        Assert.assertTrue(logoutPage.isWelcomeVisible(),
                "Logout ke baad dobara login fail hua");
    }

    @AfterMethod
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}




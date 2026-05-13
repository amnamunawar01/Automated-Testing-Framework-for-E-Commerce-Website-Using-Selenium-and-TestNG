package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class carttest {

    WebDriver driver;
    WebDriverWait wait;
    tests.cart cartPage;

    String validUsername = "your_username";  // apna username
    String validPassword = "your_password";  // apna password

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);
        cartPage = new tests.cart(driver);
    }

    // TC01 - Registered user product remove kare
    @Test
    public void removeAfterLoginAddToCart() throws InterruptedException {
        cartPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        cartPage.addProductToCart();

        cartPage.openCart();
        Thread.sleep(2000);

        int beforeCount = cartPage.getCartItemCount();
        Assert.assertTrue(beforeCount > 0,
                "Cart mein koi item nahi aaya");

        cartPage.deleteFirstItem();
        Thread.sleep(2000);

        int afterCount = cartPage.getCartItemCount();
        Assert.assertTrue(afterCount < beforeCount,
                "Item remove nahi hua cart se");
    }

    // TC02 - Guest user cart se remove kare
    @Test
    public void removeAsGuestUser() throws InterruptedException {
        cartPage.addProductToCart();

        cartPage.openCart();
        Thread.sleep(2000);

        int beforeCount = cartPage.getCartItemCount();
        Assert.assertTrue(beforeCount > 0,
                "Guest cart mein item nahi aaya");

        cartPage.deleteFirstItem();
        Thread.sleep(2000);

        int afterCount = cartPage.getCartItemCount();
        Assert.assertTrue(afterCount < beforeCount,
                "Guest cart se item remove nahi hua");
    }

    // TC03 - Sab items ek saath remove karo
    @Test
    public void removeAllItems() throws InterruptedException {
        cartPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        cartPage.addProductToCart();
        Thread.sleep(1000);
        cartPage.addProductToCart();
        Thread.sleep(1000);

        cartPage.openCart();
        Thread.sleep(2000);

        cartPage.deleteAllItems();
        Thread.sleep(2000);

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Sab delete karne ke baad bhi cart empty nahi");
    }

    // TC04 - Delete button cart mein visible ho
    @Test
    public void deleteButtonVisible() throws InterruptedException {
        cartPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        cartPage.addProductToCart();

        cartPage.openCart();
        Thread.sleep(2000);

        Assert.assertTrue(cartPage.isDeleteBtnVisible(),
                "Delete button cart mein nahi dikh raha");
    }

    // TC05 - Empty cart mein delete button na ho
    @Test
    public void noDeleteBtnInEmptyCart() throws InterruptedException {
        cartPage.openCart();
        Thread.sleep(2000);

        Assert.assertFalse(cartPage.isDeleteBtnVisible(),
                "Empty cart mein bhi delete button dikh raha hai");
    }

    @AfterMethod
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
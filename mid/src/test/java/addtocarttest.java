package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class addtocarttest {

    WebDriver driver;
    WebDriverWait wait;
    tests.addtocart cartPage;

    String validUsername = "your_username";  // apna username
    String validPassword = "your_password";  // apna password

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);
        cartPage = new tests.addtocart(driver);
    }

    // TC01 - Registered user add to cart
    @Test
    public void addToCartAsRegisteredUser() throws InterruptedException {
        // Login karo
        cartPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        // Confirm login hua
        Assert.assertTrue(cartPage.isLoggedIn(),
                "Login fail hua");

        // Product click karo
        cartPage.clickFirstProduct();

        // Add to cart
        cartPage.clickAddToCart();

        // Cart open karo aur verify karo
        cartPage.openCart();
        Thread.sleep(2000);

        int count = cartPage.getCartItemCount();
        Assert.assertTrue(count > 0,
                "Registered user cart mein item nahi aaya");

        System.out.println("TC01 PASSED - Registered user ne cart mein item add kiya. Count: " + count);
    }

    // TC02 - Guest user add to cart
    @Test
    public void addToCartAsGuestUser() throws InterruptedException {
        // Bina login ke product click karo
        cartPage.clickFirstProduct();

        // Add to cart
        cartPage.clickAddToCart();

        // Cart open karo aur verify karo
        cartPage.openCart();
        Thread.sleep(2000);

        int count = cartPage.getCartItemCount();
        Assert.assertTrue(count > 0,
                "Guest user cart mein item nahi aaya");

        System.out.println("TC02 PASSED - Guest user ne cart mein item add kiya. Count: " + count);
    }

    // TC03 - Registered user multiple items add karo
    @Test
    public void addMultipleItemsAsRegisteredUser() throws InterruptedException {
        // Login karo
        cartPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        Assert.assertTrue(cartPage.isLoggedIn(),
                "Login fail hua");

        // Pehla product add karo
        cartPage.clickFirstProduct();
        cartPage.clickAddToCart();
        Thread.sleep(1000);

        // Wapas jao aur doosra product add karo
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(2000);

        // Doosra product click karo
        driver.findElement(
                By.xpath("(//div[@class='card h-100']//h4/a)[2]")).click();
        Thread.sleep(2000);
        cartPage.clickAddToCart();

        // Cart verify karo
        cartPage.openCart();
        Thread.sleep(2000);

        int count = cartPage.getCartItemCount();
        Assert.assertTrue(count >= 2,
                "Multiple items add nahi hue. Count: " + count);

        System.out.println("TC03 PASSED - Multiple items add hue. Count: " + count);
    }

    // TC04 - Guest user multiple items add karo
    @Test
    public void addMultipleItemsAsGuestUser() throws InterruptedException {
        // Bina login ke pehla product add karo
        cartPage.clickFirstProduct();
        cartPage.clickAddToCart();
        Thread.sleep(1000);

        // Wapas jao aur doosra product add karo
        driver.navigate().to("https://www.demoblaze.com/");
        Thread.sleep(2000);

        driver.findElement(
                By.xpath("(//div[@class='card h-100']//h4/a)[2]")).click();
        Thread.sleep(2000);
        cartPage.clickAddToCart();

        // Cart verify karo
        cartPage.openCart();
        Thread.sleep(2000);

        int count = cartPage.getCartItemCount();
        Assert.assertTrue(count >= 2,
                "Guest user multiple items add nahi kar saka. Count: " + count);

        System.out.println("TC04 PASSED - Guest user ne multiple items add kiye. Count: " + count);
    }

    // TC05 - Cart mein item add hone ke baad cart empty nahi hona chahiye
    @Test
    public void cartNotEmptyAfterAdd() throws InterruptedException {
        // Login karo
        cartPage.doLogin(validUsername, validPassword);
        Thread.sleep(2000);

        // Product add karo
        cartPage.clickFirstProduct();
        cartPage.clickAddToCart();

        // Cart open karo
        cartPage.openCart();
        Thread.sleep(2000);

        Assert.assertFalse(cartPage.isCartEmpty(),
                "Cart empty hai — item add nahi hua");

        System.out.println("TC05 PASSED - Cart empty nahi hai item add karne ke baad");
    }

    @AfterMethod
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        if (driver != null) {
            driver.quit();
        }
    }
}

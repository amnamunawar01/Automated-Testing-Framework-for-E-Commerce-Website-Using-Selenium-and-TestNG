package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class signuptest {

    WebDriver driver;
    tests.signup signupPage;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");
        signupPage = new tests.signup(driver);
    }

    // TC01 - Valid Registration
    @Test
    public void validRegistration() throws InterruptedException {
        signupPage.registerAs("testuser_" + System.currentTimeMillis(), "Test@1234");
        String alert = signupPage.getAlertText();
        Assert.assertTrue(alert.contains("Sign up successful"),
                "Valid registration fail hui: " + alert);
    }

    // TC02 - Empty Fields
    @Test
    public void emptyFields() throws InterruptedException {
        signupPage.openSignupModal();
        // Kuch enter nahi karte
        signupPage.clickSignup();
        String alert = signupPage.getAlertText();
        Assert.assertTrue(alert.contains("Please fill out"),
                "Empty fields error nahi aaya: " + alert);
    }

    // TC03 - Invalid Email Format
    // Note: demoblaze email validate nahi karta
    // Isliye yeh test check karta hai ke invalid email se bhi signup ho jata hai ya nahi
    @Test
    public void invalidEmailFormat() throws InterruptedException {
        signupPage.registerAs("notanemail###", "Test@1234");
        String alert = signupPage.getAlertText();
        // Demoblaze invalid format pe bhi sign up kar leta hai
        // Yeh test document karta hai ke validation missing hai
        Assert.assertTrue(
                alert.contains("Sign up successful") || alert.contains("This user already exist"),
                "Unexpected alert: " + alert
        );
        System.out.println("TC03 Note: Demoblaze email format validate nahi karta — alert: " + alert);
    }

    // TC04 - Weak Password
    // Demoblaze password strength check nahi karta
    // Yeh test document karta hai ke weak password accept ho raha hai
    @Test
    public void weakPassword() throws InterruptedException {
        signupPage.registerAs("weakpassuser_" + System.currentTimeMillis(), "123");
        String alert = signupPage.getAlertText();
        Assert.assertTrue(
                alert.contains("Sign up successful") || alert.contains("This user already exist"),
                "Unexpected alert: " + alert
        );
        System.out.println("TC04 Note: Demoblaze weak password accept kar leta hai — alert: " + alert);
    }

    // TC05 - Already Registered
    @Test
    public void alreadyRegistered() throws InterruptedException {
        // Step 1: Pehli baar register karo
        signupPage.openSignupModal();
        Thread.sleep(1000);
        signupPage.enterUsername("duplicate_user_test");
        Thread.sleep(500);
        signupPage.enterPassword("Test@1234");
        Thread.sleep(500);
        signupPage.clickSignup();
        Thread.sleep(2000);

        // Pehla alert dismiss karo (chahe success ho ya already exist)
        try {
            String firstAlert = driver.switchTo().alert().getText();
            System.out.println("First attempt alert: " + firstAlert);
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Pehla alert nahi aaya");
        }

        Thread.sleep(2000);

        // Step 2: Page refresh karo taake modal fresh mile
        driver.navigate().refresh();
        Thread.sleep(2000);

        // Step 3: Same user dobara register karo
        signupPage.openSignupModal();
        Thread.sleep(1000);
        signupPage.enterUsername("duplicate_user_test");
        Thread.sleep(500);
        signupPage.enterPassword("Test@1234");
        Thread.sleep(500);
        signupPage.clickSignup();
        Thread.sleep(2000);

        // Step 4: Dusra alert check karo
        String secondAlert = driver.switchTo().alert().getText();
        System.out.println("Second attempt alert: " + secondAlert);
        driver.switchTo().alert().accept();

        Assert.assertTrue(secondAlert.contains("This user already exist"),
                "Expected 'already exist' message nahi aaya. Actual: " + secondAlert);
    }

    @AfterMethod
    public void close() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}

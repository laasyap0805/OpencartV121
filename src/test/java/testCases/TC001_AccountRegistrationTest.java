package testCases;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups={"Regression","Master"})
    public void verify_account_registration() {
        logger.info("**** Starting TC001_AccountRegistrationTest *****");
        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on MyAccount Link");
            hp.clickRegister();
            logger.info("Clicked on Register Link");

            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            logger.info("Providing customer details...");
            regpage.setFirstName(randomeString());
            regpage.setLastName(randomeString());
            regpage.setEmail(randomeString() + "@gmail.com");
            regpage.setTelephone(randomeNumber());

            String password = randomeAlphaNumeric();
            regpage.setPassword(password);
            regpage.setConfirmPassword(password);
            regpage.setPrivacyPolicy();
            regpage.clickContinue();

            // handle popup if appears
            try {
                WebElement popup = driver.findElement(By.id("popup-id"));
                if (popup.isDisplayed()) {
                    popup.click();
                }
            } catch (Exception e) {
                logger.info("No popup appeared.");
            }

            // --- wait for confirmation message ---
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[normalize-space()='Your Account Has Been Created!']")));
            String confmsg = successMsg.getText();
            logger.info("Confirmation message displayed: " + confmsg);

            // --- validate message ---
            if (confmsg.equals("Your Account Has Been Created!")) {
                logger.info("Account successfully created.");
                Assert.assertTrue(true);
            } else {
                logger.error("Test failed. Actual message: " + confmsg);
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            logger.error("Test failed due to exception: " + e.getMessage());
            Assert.fail();
        }

        logger.info("***** Finished TC001_AccountRegistrationTest *****");
    }
}

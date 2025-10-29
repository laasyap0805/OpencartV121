package pageObjects;
 import java.time.Duration;

import org. openqa.selenium.WebDriver;
 import org. openqa.selenium.WebElement;
 import org. openqa. selenium. support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage{

public MyAccountPage (WebDriver driver)
{
super (driver);
}
//@FindBy(xpath = "//h2[text()= 'My Account']") // MyAccount Page heading
@FindBy(xpath = "//h2[normalize-space()='My Account']")
WebElement msgHeading;

//@FindBy(xpath = "//div[@class='list-group']//a[text()= 'Logout ' ]")
@FindBy(xpath = "//div[@class='list-group']//a[normalize-space()='Logout']")
WebElement lnkLogout;


public boolean isMyAccountPageExists() {
//{
//try
//{
//return (msgHeading.isDisplayed());
//}
//catch (Exception e)
//{
//return false;
//}
//}
try {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOf(msgHeading));  // waits until heading is visible
    return msgHeading.isDisplayed();
} 
catch (Exception e) {
    return false;
}
}

public void clickLogout()
{
	lnkLogout.click();
}
}
package testCases;
import org.testng.Assert;
import org.testng. annotations. Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{

@Test(groups={"Sanity","Master"})
public void verify_login()
{

logger.info("** Startign TC_002_LoginTest ***");
try
{
HomePage hp=new HomePage(driver) ; 
hp. clickMyAccount(); 
hp. clickLogin();
 
LoginPage lp=new LoginPage(driver);
lp.setEmail(p.getProperty("email"));
lp.setPassword(p.getProperty("password"));
lp.clickLogin();

//MyAccount
MyAccountPage macc=new MyAccountPage(driver);
boolean targetPage=macc.isMyAccountPageExists();
Assert.assertTrue(targetPage);//Assert.assertEquals(targetPage, true, "Login failed");
}
catch(Exception e)
{
Assert.fail();
	//logger.error("Test case failed due to exception: " + e.getMessage());
    //Assert.fail("Exception occurred: " + e.getMessage());
}
logger.info("*Finished TC_002_LoginTest***");
}
}
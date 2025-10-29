package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.time.Duration;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
    
    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException, MalformedURLException {

        // Load config
        FileReader file = new FileReader(".//src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass());
        logger.info("Setting up test on OS: " + os + " using Browser: " + br);

        // ---- Remote Execution ----
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            } else {
                //System.out.println("No matching OS found!");
                //return;
            	System.out.println("No matching OS found! Defaulting to Windows.");
                capabilities.setPlatform(Platform.WIN11);
            }

            switch (br.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("No matching browser found!");
                    return;
            }

            //driver = new RemoteWebDriver(new URL("http://192.168.1.54:4444/wd/hub"), capabilities);
            driver = new RemoteWebDriver(
            	    java.net.URI.create("http://192.168.1.54:4444/wd/hub").toURL(),
            	    capabilities
            	);

        }
        // ---- Local Execution ----
        else {
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(p.getProperty("appURL2"));
        logger.info("Navigated to application successfully");
    }


    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }

    // Random string generator (letters)
    public String randomeString() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();
        return generator.generate(5);
    }

    // Random numeric string
    public String randomeNumber() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .build();
        return generator.generate(10);
    }

    // Random alphanumeric
    public String randomeAlphaNumeric() {
        RandomStringGenerator letters = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();

        RandomStringGenerator numbers = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .build();

        return letters.generate(3) + "@" + numbers.generate(3);
    }

    // Capture screenshot
   // public String captureScreen(String tname) throws IOException {
    public String captureScreen(String tname) throws IOException {
        if (driver == null) {
            System.out.println("Driver is null — screenshot cannot be captured for: " + tname);
            return null;
        }
    	
    	
    	
    	String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}

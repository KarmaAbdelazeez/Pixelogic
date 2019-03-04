package web.Tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import web.PageObjects.HelperClass;
import web.PageObjects.RegistrationPage;

public class RegistrationTest {

	public HelperClass helper = new HelperClass();
	public ExtentReports extent;
	public ExtentTest extentTest;
	private static Properties properties = new Properties();
	RegistrationPage Register;

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/PixelogicTestResults.html", true);
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	@BeforeClass
	public void setUp() throws Exception, MalformedURLException {
		properties.load(new FileReader(new File("test.properties")));
		helper.initiateTheWebDriver();
		Register = new RegistrationPage(helper);
	}

	public String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// After execution "FailedTestsScreenshots" folder found under src
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@Test(priority = 2, enabled = true)
	public void registerUser() throws InterruptedException {
		extentTest = extent.startTest("registerUser");
		String FirstName, LastName, PhoneNumber, Email, Password;
		FirstName = properties.getProperty("FirstName");
		LastName = properties.getProperty("LastName");
		PhoneNumber = properties.getProperty("PhoneNumber");
		Email = helper.generateUniqueEmailAddress(FirstName);
		Password = properties.getProperty("Password");

		Register.goToURL(properties.getProperty("URL"));
		assertTrue(Register.createAccount(FirstName, LastName, PhoneNumber, Email, Password), "SignUp Success");

	}
	
	@Test(priority = 1, enabled = true)
	public void invalidRegistration() throws InterruptedException {
		extentTest = extent.startTest("invalidRegistration");
		String FirstName, LastName, PhoneNumber, Email, Password;
		FirstName = properties.getProperty("FirstName");
		LastName = properties.getProperty("LastName");
		PhoneNumber = properties.getProperty("PhoneNumber");
		Email = properties.getProperty("InvalidEmail");
		Password = properties.getProperty("Password");

		Register.goToURL(properties.getProperty("URL"));
		assertTrue(Register.createAccount(FirstName, LastName, PhoneNumber, Email, Password), "SignUp Success");

	}

	@AfterMethod
	public void endTest(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName());
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable());

			String screenshotPath = getScreenshot(helper.driver, result.getName());
			// To add screenshot in extent report
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath));
			// To add screen video shot
			extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "TEST CASE SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "TEST CASE PASSED IS " + result.getName());
		}
		extent.endTest(extentTest);
		
	}
	
	@AfterClass
	public void tearDown()
	{
		helper.closeTheBrowser();
	}

}

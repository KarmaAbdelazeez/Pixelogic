package web.PageObjects;

import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class HelperClass {
	public WebDriver driver;
	

	public void initiateTheWebDriver() {
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void closeTheBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			Assert.fail("Couldn't close the browser because of " + e.getMessage());
		}		
	}
	
	public void navigateToPage(String url,By b) {
		driver.get(url);
		WebElement element = waitUntil(b, "presenceOfElement");
		assertNotNull(element, "Navigation Failed to this Website "+url);
	}
	
	public WebElement waitUntil(By b, String condition) {
		try {
			WebElement element = null;
			switch (condition) {

			case "presenceOfElement":
				element = (new WebDriverWait(driver,8)).until(ExpectedConditions.presenceOfElementLocated(b));
				return element;

			case "elementToBeClickable":
				element = (new WebDriverWait(driver, 8)).until(ExpectedConditions.elementToBeClickable(b));
				return element;
				

			default:
				element = null;
				Assert.fail("Wrong condition");
			}
		return element ;
		} catch (Exception e) {
			//Assert.fail("Couldn't find the element because of " + e.getMessage());
			return null;
		}
	}
	
	public void takeScreenShot(String name) {
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		System.out.println(src.getAbsolutePath());
		
		String directoryName = System.getProperty("user.dir");
		//String directory = directoryName + "/Screenshot/" + name+".png";
		//System.out.println(directory);
		
		
		try {
		  FileUtils.copyFile(src, new File(directoryName + "/Screenshot/" + name+".png"));
		  
		}
		catch (IOException e)
		 {
		  System.out.println(e.getMessage());
		  
		 }

	}
	//Generate email
	public String generateUniqueEmailAddress(String Name) {
		StringBuilder email = new StringBuilder().append(Name)
				.append(String.valueOf(new Random().nextInt(10000)))
				.append("@").append("gmail.com");
		String name = email.toString().replaceAll(" ","");
		return name;
	}
	

}

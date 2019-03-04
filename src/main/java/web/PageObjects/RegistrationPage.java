package web.PageObjects;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class RegistrationPage {
	
	HelperClass help;
	String presenceOfElement = "presenceOfElement";
	String signUplbl = "//div[contains(text(),'Sign Up')]",
			firstNameloc = "//input[@placeholder = 'First Name']",
			lastNameloc = "//input[@placeholder = 'Last Name']",
			mobileNumloc = "//input[@placeholder = 'Mobile Number']",
			emailLoc = "//input[@placeholder='Email']",
			passwordLoc = "//input[@placeholder='Password']",
			confirmPassLoc = "//input[@placeholder='Confirm Password']",
			signUpBtnLoc = "//button[@class='signupbtn btn_full btn btn-action btn-block btn-lg']",
			userLoggedinLoc = "//h3[contains(text(),'Hi, Karma Abdelazeez')]";

	public RegistrationPage(HelperClass helper) {
		this.help = helper;
		
	}
	
	public void goToURL(String url) {
		help.navigateToPage(url, By.xpath(signUplbl));		
	}

	public boolean createAccount(String FirstName, String LastName, String PhoneNumber,
			String Email, String Password) throws InterruptedException {
		setName(FirstName, LastName);
		setEmail(Email);
		setPassword(Password);
		setPhone(PhoneNumber);

		if (register())
			return true;
		else {
			//help.takeScreenShot("SignUp Error");
			return false;
		}
	}
	
	void setName(String UserFirstName, String UserLastName) {
		WebElement Fname, Lname;
		Fname = help.driver.findElement(By.xpath(firstNameloc));
		Lname = help.driver.findElement(By.xpath(lastNameloc));
		Fname.sendKeys(UserFirstName);
		Lname.sendKeys(UserLastName);
	}
	
	void setEmail(String email) {

		WebElement element;
		element = help.driver.findElement(By.xpath(emailLoc));
		element.sendKeys(email);
		assertTrue(element.getAttribute("value").toString().contains(email));
	}
	
	void setPassword(String pass) {
		WebElement Password, confirmPass;
		Password = help.driver.findElement(By.xpath(passwordLoc));
		Password.sendKeys(pass);
		confirmPass = help.driver.findElement(By.xpath(confirmPassLoc));
		confirmPass.sendKeys(pass); 
		
		assertTrue(Password.getAttribute("value").toString().contains(pass));
	}
	
	void setPhone(String PhoneNumber) {
		WebElement Phone;
		Phone = help.driver.findElement(By.xpath(mobileNumloc));
		Phone.clear();
		//Phone.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
		Phone.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),PhoneNumber);
		
		assertTrue(Phone.getAttribute("value").toString().contains(PhoneNumber));

	}
	
	boolean register() throws InterruptedException {
		WebElement element;
		element = help.driver.findElement(By.xpath(signUpBtnLoc));
		element.click();
		Thread.sleep(5000);
		if (help.waitUntil(By.xpath(userLoggedinLoc), presenceOfElement) == null) {
			help.takeScreenShot("SignIn Error" + String.valueOf((int) (Math.random() * 50 + 1)));
			return false;
		}
		return true;
	}
}

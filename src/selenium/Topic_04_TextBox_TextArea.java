package selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_04_TextBox_TextArea {
	WebDriver driver;
	String customerName, gender, dateOfBirth, address, city, state, pin, phone, email, password, customerId;
	String editAddess, editCity, editState, editPin, editPhone, editEmail;

	//Locate Element
	By cutomerNameTextbox= By.xpath("//input[@name='name']");
	By genderRadio = By.xpath("//input[@value='m']");
	By dateOrBirthTextbox = By.xpath("//input[@id='dob']");
	By addressTextArea = By.xpath("//textarea[@name='addr']");
	By cityTextbox = By.xpath("//input[@name='city']");
	By stateTextbox = By.xpath("//input[@name='state']");
	By pinTextBox = By.xpath("//input[@name='pinno']");
	By phoneTextBox = By.xpath("//input[@name=\"telephoneno\"]");
	By emailTextBox = By.xpath("//input[@name=\"emailid\"]");
	By passwordTextBox = By.xpath("//input[@name=\"password\"]");
	By submitButton = By.xpath("//input[@name=\"sub\"]");

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window();
		driver.get("http://demo.guru99.com/v4/");
		driver.findElement(By.xpath("//input[@name=\"uid\"]")).sendKeys("mngr181358 ");
		driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("berydUp");
		driver.findElement(By.xpath("//input[@name=\"btnLogin\"]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());
		//Init data test for new
		customerName = "Automation Test";
		gender = "male";
		dateOfBirth = "1999-01-01";
		address = "1234 Le Loi";
		city = "Ho Chi Minh";
		state = "Hoan Kiem";
		pin = "600000";
		phone = "012343546573";
		email = "aumationtest" + ranDomEmail() +"@gmail.com";
		password = "123123";
		
		//Init data test for Edit
		editAddess = "2345 Hung Vuong";
		editCity = "Da Nang";
		editState = "Hai Chau";
		editPin = "550000";
		editPhone = "0987653211";
		editEmail = "editautomate"+ranDomEmail()+"@gmail.com";
	}

	@Test
	public void TC_01_CreateNewCustomer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Add New Customer']")).isDisplayed());
		// Input data for new customer
		driver.findElement(cutomerNameTextbox).sendKeys(customerName);
		driver.findElement(genderRadio).click();
		driver.findElement(dateOrBirthTextbox).sendKeys(dateOfBirth);
		driver.findElement(addressTextArea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextBox).sendKeys(pin);
		driver.findElement(phoneTextBox).sendKeys(phone);
		driver.findElement(emailTextBox).sendKeys(email);
		driver.findElement(passwordTextBox).sendKeys(password);
		driver.findElement(submitButton).click();
		// Verify expected data = actual data 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class=\"heading3\" and text()='Customer Registered Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);
		
		customerId = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println("Customer ID at TC 01 = " + customerId);
	}
	
	@Test
	public void TC_02_EditCustomer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(customerId);
		driver.findElement(By.xpath("//input[@name='AccSubmit']")).click();
		
		//Input data for edit customer
		driver.findElement(addressTextArea).clear();
		driver.findElement(addressTextArea).sendKeys(editAddess);
		driver.findElement(cityTextbox).clear();
		driver.findElement(cityTextbox).sendKeys(editCity);
		driver.findElement(stateTextbox).clear();
		driver.findElement(stateTextbox).sendKeys(editState);
		driver.findElement(pinTextBox).clear();
		driver.findElement(pinTextBox).sendKeys(editPin);
		driver.findElement(phoneTextBox).clear();
		driver.findElement(phoneTextBox).sendKeys(editPhone);
		driver.findElement(emailTextBox).clear();
		driver.findElement(emailTextBox).sendKeys(editEmail);
		driver.findElement(By.xpath("//input[@value='Submit']")).click();
		
		// Verify expected data = actual data 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='heading3' and text()='Customer details updated Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editAddess);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editPhone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editEmail);
		
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	public int ranDomEmail() {
		// Create random to email address
		Random email = new Random();
		//Obtain a number between [0-1000]
		int n = email.nextInt(1000);
		// Add 1 to the result to get a number from the required range
		// (i.e., [1 - 100]).
		n += 1;
		return n;
	}
}

package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Topic_02_Xpath_Css_Excercise {
WebDriver driver;
	
	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.guru99.com/");
	}
	
	@Test
	public void TC_01_EmailAndPasswordEmpty() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		
		String emailRequired = driver.findElement(By.id("advice-required-entry-email")).getText();
		/** 
		 * Co 3 cach kiem tra
		 * 1. Kiem tra truong hop dung
		 * 2. Kiem tra truong hop sai
		 * 3. Kiem tra truong hop bang
		 */
		Assert.assertTrue(emailRequired.equals("This is a required field."));// Kiem tra truong hop dung
		Assert.assertFalse(emailRequired.equals("This is a required field fdgvdsf."));// Kiem tra truong hop sai
		Assert.assertEquals(emailRequired, "This is a required field.");// Kiem tra truong hop bang
		
		String passRequired = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertTrue(passRequired.equals("This is a required field."));// Kiem tra truong hop dung
	} 
	
	@Test
	public void TC_02_LoginWithEmailInvalid() {
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		
		String emailInvalid = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertEquals(emailInvalid, "Please enter a valid email address. For example johndoe@domain.com.");// Kiem tra truong hop dung
	} 

	@Test
	public void TC_03_LoginWithPasswordLessThan6Characters() {
	driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		
		String verifyMessagePass = driver.findElement(By.id("advice-validate-password-pass")).getText();
		Assert.assertEquals(verifyMessagePass, "Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_LoginWithPasswordIncorrect() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		
		String verifyMessageEmailandPassword = driver.findElement(By.xpath("//li[@class='error-msg']")).getText();
		Assert.assertEquals(verifyMessageEmailandPassword, "Invalid login or password.");
	}
	
	@Test
	public void TC_05_CreatAnAcc() {
		
	}

	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
}

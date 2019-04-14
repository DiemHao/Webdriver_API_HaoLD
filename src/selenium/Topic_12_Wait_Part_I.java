package selenium;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Topic_12_Wait_Part_I {
	WebDriver driver;
	WebDriverWait waitExplicit;
	By startButton = By.xpath("//div[@id='start']/button");
	By loadingIcon = By.xpath("//div[@id='loading']/img");
	By helloWorld = By.xpath("//div[@id='finish']/h4[text()='Hello World!']");
	
	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
	}
	
	public void TC_01_ImplicitWait_5s() {
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		// 7s để render ra hello world text
		
		Assert.assertTrue(driver.findElement(helloWorld).isDisplayed());
	} 
	
	
	public void TC_02_LoadingIconInvisible() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		waitExplicit = new WebDriverWait(driver, 5);
		
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertTrue(driver.findElement(helloWorld).isDisplayed());
	} 

	public void TC_03_HelloWorldTextVisibleFailed() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		waitExplicit = new WebDriverWait(driver, 2);
		
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();
		
		//By.xxx
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(helloWorld));
		
		// WebElement : findElement
		waitExplicit.until(ExpectedConditions.visibilityOf(driver.findElement(helloWorld)));
		
		Assert.assertTrue(driver.findElement(helloWorld).isDisplayed());
	} 
	
	@Test
	public void TC_04_HelloWorldText_LoadingIcon_NolongerInDOM() {
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		waitExplicit = new WebDriverWait(driver, 5);
		
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		// Invisible + ko có trong DOM
		System.out.println("Start time: " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(helloWorld));
		System.out.println("End time: " + getDateTimeSecond());

		// Invisible + ko có trong DOM
		System.out.println("Start time: " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		System.out.println("End time: " + getDateTimeSecond());

		driver.findElement(startButton).click();

		// Khong visible + có trong DOM
		System.out.println("Start time: " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		System.out.println("End time: " + getDateTimeSecond());

		// Khong visible + có trong DOM
		System.out.println("Start time: " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(startButton));
		System.out.println("End time: " + getDateTimeSecond());
		
		Assert.assertTrue(driver.findElement(helloWorld).isDisplayed());
	} 
	
	
	public Date getDateTimeSecond() {
		Date date = new Date();
		date = new Timestamp(date.getTime());
		return date;
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

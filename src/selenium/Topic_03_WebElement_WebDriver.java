package selenium;

import org.testng.annotations.Test;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

public class Topic_03_WebElement_WebDriver {

	WebDriver driver;
	//Email/ Age (Under 18)/ Education/ Job Role 01/ Interests (Development)/ Slider 01/ Button is enabled
	By emailTxt = By.xpath("//input[@id='mail']");
	By ageUnder18Radio = By.xpath("//input[@id='under_18']");
	By educationTxtArea = By.xpath("//textarea[@id='edu']");
	By jobRole1DropDown = By.xpath("//select[@id='job1']");
	By interestDevCheckbox = By.xpath("//input [@id='development']");
	By slider1 = By.xpath("//input[@id='slider-1']");
	By buttonEnable = By.xpath("//button[@id='button-enabled']");
	//Password / Age (Radiobutton is disabled)/ Biography/ Job Role 02/ Interests (Checkbox is disabled)/ Slider 02/ Button is disabled
	By passWordTxt = By.xpath("//input[@id='password']"); 
	By ageRadiobtn = By.xpath("//input [@id='radio-disabled']");
	By biographyTxtArea = By.xpath("//textarea[@id='bio']");
	By jobRole2Dropdown = By.xpath("//select[@id='job2']");
	By interestsDisCheckbox = By.xpath("//input[@id='check-disbaled']");
	By slider02 = By.xpath("//input[@id='slider-2']");
	By buttonDisable = By.xpath("//button[@id='button-disabled']");

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://daominhdam.github.io/basic-form/index.html");
	} 


	public void TC_01_CheckDisplayed() {

		if(isElementDisplayed(emailTxt)) {
			driver.findElement(emailTxt).sendKeys("Automation Testing");
		}
		if(isElementDisplayed(educationTxtArea)) {
			driver.findElement(educationTxtArea).sendKeys("Automation Testing");
		}
		if(isElementDisplayed(ageUnder18Radio)) {
			driver.findElement(ageUnder18Radio).click();
		}

	}


	@Test(enabled = false)
	public void TC_02_CheckEnable() {
		Assert.assertTrue(isElementEnabled(emailTxt));
		Assert.assertTrue(isElementEnabled(ageUnder18Radio));
		Assert.assertTrue(isElementEnabled(educationTxtArea));
		Assert.assertTrue(isElementEnabled(jobRole1DropDown));
		Assert.assertTrue(isElementEnabled(slider1));
		Assert.assertTrue(isElementEnabled(buttonEnable));
		Assert.assertFalse(isElementEnabled(passWordTxt));
		Assert.assertFalse(isElementEnabled(ageRadiobtn));
		Assert.assertFalse(isElementEnabled(biographyTxtArea));
		Assert.assertFalse(isElementEnabled(jobRole2Dropdown));
		Assert.assertFalse(isElementEnabled(interestsDisCheckbox));
		Assert.assertFalse(isElementEnabled(slider02));
		Assert.assertFalse(isElementEnabled(buttonDisable));
	}

	@Test
	public void TC_03_CheckElementIsSelected() {
		checkToCheckbox(ageUnder18Radio);
		checkToCheckbox(interestDevCheckbox);
		Assert.assertTrue(isElementSelected(ageUnder18Radio));
		Assert.assertTrue(isElementSelected(interestDevCheckbox));
		unCheckToCheckbox(interestDevCheckbox);
		Assert.assertFalse(isElementSelected(interestDevCheckbox));;
	}


	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	public boolean isElementDisplayed(By byVAlue) {
		return driver.findElement(byVAlue).isDisplayed();
	}

	public boolean isElementEnabled(By byValue) {
		if(driver.findElement(byValue).isEnabled()) {
			System.out.println(driver.findElement(byValue).getTagName() + " --- Element is Enabled");
			return true;
		}else {
			System.out.println("Element: [ "+ byValue +"] Element is Disabled");
			return false;
		}
	}
	
	public boolean isElementSelected(By byValue) {
		WebElement element = driver.findElement(byValue);
		  if(element.isSelected()) {
			  System.out.println("Element [ "+byValue+" is selected");
			  return true;
		  } else {
			  System.out.println("Element [ "+byValue+" is de-selected");
			  return false;
		  }
	}
	
	public void checkToCheckbox(By byValue) {
		WebElement element = driver.findElement(byValue);
		  if(!element.isSelected()) {
			  element.click();
		  } 
	}

	public void unCheckToCheckbox(By byValue) {
		WebElement element = driver.findElement(byValue);
		  if(element.isSelected()) {
			  element.click();
		  } 
	}
}

package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Topic_06_Button_Radio_Checkbox_Alert {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	
	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	
	public void TC_01_Button() {
		driver.get("http://live.guru99.com/");
		WebElement myAccountLink = driver.findElement(By.xpath("//div [@class='footer']//a[text()='My Account']"));
		jsExecutor.executeScript("arguments[0].click();", myAccountLink);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");
		WebElement crtAccountLink = driver.findElement(By.xpath("//a[@title='Create an Account']"));
		jsExecutor.executeScript("arguments[0].click();", crtAccountLink);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");
	} 
	
	
	public void TC_02_CustomCheckbox() {
		driver.get("https://demos.telerik.com/kendo-ui/styling/checkboxes");
		//Step 1 - Click
		WebElement dualZoneCheckbox =  driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']//preceding-sibling::input"));
		checkToCheckbox(dualZoneCheckbox);
		//Step 2 - Verify
		Assert.assertTrue(dualZoneCheckbox.isSelected());
		unCheckToCheckbox(dualZoneCheckbox);
		Assert.assertFalse(dualZoneCheckbox.isSelected());
		
	} 

	@Test
	public void TC_03_CustomRadioButton() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		WebElement radiobutton = driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']//preceding-sibling::input"));
		isElementSelected(radiobutton);
	}
	
	public boolean isElementSelected(WebElement element) {
		  if(element.isSelected()) {
			  jsExecutor.executeScript("arguments[0].click();", element);
			  return true;
		  } else {
			  jsExecutor.executeScript("arguments[0].click();", element);
			  return false;
		  }
	}
	
	public void checkToCheckbox(WebElement element) {
		  if(!element.isSelected()) {
			  if(element.isDisplayed()) {
				  element.click();
			  } else {
				  jsExecutor.executeScript("arguments[0].click();", element);
			  }
		  }
	}

	public void unCheckToCheckbox(WebElement element) {
		  if(element.isSelected()) {
			  if(element.isDisplayed()) {
				  element.click();
			  } else {
				  jsExecutor.executeScript("arguments[0].click();", element);
			  }
		  } 
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

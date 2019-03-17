package selenium;

import org.testng.annotations.Test;


import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.List;
//import java.awt.List;
//import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.browserlaunchers.locators.FirefoxLocator;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class Topic_05_DropDownList {
	WebDriver driver;
	WebDriverWait waitExplicit;
	JavascriptExecutor javascriptExecutor;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		waitExplicit = new WebDriverWait(driver, 30);
		javascriptExecutor = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		
	}

	
	public void TC_01_Default_DropDownList() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		Select jobRole1DropDown = new Select(driver.findElement(By.xpath("//select [@id='job1']")));
		AssertJUnit.assertFalse(jobRole1DropDown.isMultiple());
		jobRole1DropDown.selectByVisibleText("Automation Tester");
		AssertJUnit.assertEquals("Automation Tester", jobRole1DropDown.getFirstSelectedOption());
		jobRole1DropDown.selectByValue("manual");
		AssertJUnit.assertEquals("Manual Tester", jobRole1DropDown.getFirstSelectedOption());
		jobRole1DropDown.selectByIndex(3);
		AssertJUnit.assertEquals("Mobile Tester", jobRole1DropDown.getFirstSelectedOption());
		AssertJUnit.assertEquals(5, jobRole1DropDown.getOptions().size());
	}
	
	@Test
	public void TC_02_CustomJqueryDropDown() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdown("//span [@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "19");
		Assert.assertTrue(isElementDisplayed("//span [@id='number-button']//span[@class='ui-selectmenu-text' and text()= '19'] "));
		
		selectItemInCustomDropdown("//span [@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "1");
		Assert.assertTrue(isElementDisplayed("//span [@id='number-button']//span[@class='ui-selectmenu-text' and text()= '1'] "));
		selectItemInCustomDropdown("//span [@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "13");
		Assert.assertTrue(isElementDisplayed("//span [@id='number-button']//span[@class='ui-selectmenu-text' and text()= '13'] "));
		selectItemInCustomDropdown("//span [@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "2");
		Assert.assertTrue(isElementDisplayed("//span [@id='number-button']//span[@class='ui-selectmenu-text' and text()= '2'] "));
	}
	
	@Test
	public void TC_03_CustomAngularDropDown() throws Exception {
		driver.get("https://material.angular.io/components/select/examples");
		selectItemInCustomDropdown("//mat-select[@placeholder='State']", "//mat-option/span", "Wyoming");
		Assert.assertTrue(isElementDisplayed("//mat-select[@placeholder='State']//span[text()='Wyoming']"));
	}
	
	public void selectItemInCustomDropdown(String parentXPath, String allItemXpath, String expectedVAlueItem) throws Exception {
		//1.Click vào 1 cái dropdown cho nó sổ hêt tất cả giá trị ra
		WebElement parentDropDown = driver.findElement(By.xpath(parentXPath));
		javascriptExecutor.executeScript("arguments[0].click()", parentDropDown);
		
		//2. Chờ cho tất cả các giá trị trong dropdown được list ra thành công
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));
		List <WebElement> allItems = driver.findElements(By.xpath(allItemXpath));
		System.out.println("Tất cả các phần tử trong dropdown = "+ allItems.size());
		
//		for (int i = 0; i < allItems.size(); i++) {
//			if (allItems.get(i).getText().equals(expectedVAlueItem)) {
//				System.out.println("Test mỗi lần get = "+allItems.get(i).getText());
//				//3. Scroll đến item cần chọn (nếu như item cần chọn có thể nhìn thấy thì không cần scroll)
//				javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", allItems.get(i));
//				//4. Click vào item cần chọn
//				allItems.get(i).click();
//				break;
//			}
//		}
//		
		//for-each
		for (WebElement childElement : allItems) {
			System.out.println("Test mỗi lần get = "+childElement.getText());
			if(childElement.getText().equals(expectedVAlueItem)) {
				//3. Scroll đến item cần chọn (nếu như item cần chọn có thể nhìn thấy thì không cần scroll)
				javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
				Thread.sleep(1500);
				//4. Click vào item cần chọn
				childElement.click();
				break;
			}
		}
		
	}
	
	public boolean isElementDisplayed(String xPathValue) {
		WebElement element = driver.findElement(By.xpath(xPathValue));
		if (element.isDisplayed()) {
			System.out.println("Element is dispalyed");
			return true;
		} else {
			System.out.println("Element is not dispalyed");
			return false;
		}
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	public int ranDomEmail() {
		Random email = new Random();
		int n = email.nextInt(1000);
		return n;
	}
}

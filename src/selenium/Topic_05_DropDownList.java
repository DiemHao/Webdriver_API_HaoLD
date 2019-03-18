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

	public void TC_03_CustomAngularDropDown() throws Exception {
		driver.get("https://material.angular.io/components/select/examples");
		selectItemInCustomDropdown("//mat-select[@placeholder='State']", "//mat-option/span", "Wyoming");
		Assert.assertTrue(isElementDisplayed("//mat-select[@placeholder='State']//span[text()='Wyoming']"));
	}

	public void TC_04_Telerik() throws Exception {
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");
		selectItemInCustomDropdown("//span[@class='k-dropdown-wrap k-state-default']", "//ul[@id='color_listbox']/li", "Orange");
		Assert.assertTrue(isElementDisplayed("//span[@class='k-dropdown-wrap k-state-default']//span[text()='Orange']"));
	}

	
	public void TC_05_VueJS() throws Exception {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Second Option");
		Assert.assertTrue(isElementDisplayed("//div [@class='btn-group']/li[@class='dropdown-toggle' and contains(text(), 'Second Option')]"));
	}
	
	@Test
	public void TC_06_CustomMultipleItem() throws Exception {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples/#basic.html");
		By contentiFramexPath = By.xpath("//div[@class='content']//iframe");
		String[] items = {"January", "April", "December"};
		String[] newItems = {"January", "April","August", "December"};
		WebElement contentiFrame = driver.findElement(contentiFramexPath);
		driver.switchTo().frame(contentiFrame);
		selectMultiItemInDropdown("//button[@class='ms-choice']", "//div[@class='ms-drop bottom']//span", items);
		Assert.assertTrue(checkItemSelected(items));
		driver.navigate().refresh();
		WebElement contentIframerefresh = driver.findElement(contentiFramexPath);
		driver.switchTo().frame(contentIframerefresh);
		selectMultiItemInDropdown("//button[@class='ms-choice']", "//div[@class='ms-drop bottom']//span", newItems);
		Assert.assertTrue(checkItemSelected(newItems));
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
			//if(childElement.getText().equals(expectedVAlueItem)) { // Trường hợp này chỉ đúng cho text bằng còn text ko bằng nên dùng contains. Tuy nhiên contains chạy sẽ lâu hơn equals
			if(childElement.getText().contains(expectedVAlueItem)) {
				//3. Scroll đến item cần chọn (nếu như item cần chọn có thể nhìn thấy thì không cần scroll)
				javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
				Thread.sleep(1500);
				//4. Click vào item cần chọn
				if (childElement.isDisplayed()) {
					childElement.click();
				} else {
					javascriptExecutor.executeScript("arguments[0].click()", childElement);
				}
				Thread.sleep(1000);
				break;
			}
		}

	}

	public void selectMultiItemInDropdown(String parentXPath, String allItemXpath, String[] expectedVAlueItem) throws Exception {
		//1.Click vào 1 cái dropdown cho nó sổ hêt tất cả giá trị ra
		WebElement parentDropDown = driver.findElement(By.xpath(parentXPath));
		javascriptExecutor.executeScript("arguments[0].click()", parentDropDown);

		//2. Chờ cho tất cả các giá trị trong dropdown được list ra thành công
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));
		List <WebElement> allItems = driver.findElements(By.xpath(allItemXpath));
		System.out.println("Tất cả các phần tử trong dropdown = "+ allItems.size());

		//Duyệt qua hết tất cả các phần tử cho đến khi thảo mãn điều kiện
		for (WebElement childElement : allItems) {
			// January, April, Jully
			for (String item : expectedVAlueItem) {
				if(childElement.getText().equals(item)) {
					//3. Scroll đến item cần chọn (nếu như item cần chọn có thể nhìn thấy thì không cần scroll)
					javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
					Thread.sleep(1500);
					//4. Click vào item cần chọn
						javascriptExecutor.executeScript("arguments[0].click()", childElement);
					Thread.sleep(1000);
					List <WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					System.out.println("Item selected = " + itemSelected.size());
					if (expectedVAlueItem.length == itemSelected.size()) {
						break;
					}
				}
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

	public boolean checkItemSelected(String[] itemSelectedText) {
		List <WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		int numberItemSlected = itemSelected.size();
		
		String allItemSelectedText = driver.findElement(By.xpath("//button[@class='ms-choice']/span")).getText();
		System.out.println("Text da chon: " + allItemSelectedText);
		if (numberItemSlected <= 3 && numberItemSlected > 0) {
			for (String item : itemSelectedText) {
				if (allItemSelectedText.contains(item)) {
					break;
				}
			}
			return true;
		} else {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='" + numberItemSlected + " of 12 selected']")).isDisplayed();
			
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

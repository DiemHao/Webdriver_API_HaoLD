package selenium;

import java.util.List;
import java.util.Set;
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


public class Topic_09_HandleWindowsTabs {
	WebDriver driver;
	JavascriptExecutor javascript;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		javascript = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	
	public void TC_01_() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		String parentID = driver.getWindowHandle();
		System.out.println("Parent window: " + parentID);

		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		Thread.sleep(2000);
		switchToChildWindowByID(parentID);

		String googleTitle = driver.getTitle();
		System.out.println(googleTitle);
		Assert.assertEquals(googleTitle, "Google");
		Assert.assertTrue(closeAllWithoutParentWindows(parentID));

		Thread.sleep(2000);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
	} 

	
	public void TC_02() throws Exception {
		driver.get("http://www.hdfcbank.com/");
		String parentID = driver.getWindowHandle();

		// Handle close pop-up
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> notificationIframe = driver.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		int notificationIframeSize = notificationIframe.size();
		System.out.println("Notification iframe displayed: " + notificationIframeSize);
		if (notificationIframeSize > 0) {
			driver.switchTo().frame(notificationIframe.get(0));
			Assert.assertTrue(driver.findElement(By.xpath("//div[@id='container-div']/img")).isDisplayed());
			javascript.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[@id='div-close']")));
			System.out.println("Close pop-up success!");
			driver.switchTo().defaultContent();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Click vào Agri link
		driver.findElement(By.xpath("//a[text()='Agri']")).click();
		
		//Switch qua Agri page
		switchToWindowByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");
		Thread.sleep(2000);
		
		//Click vào Account Detail link
		driver.findElement(By.xpath("//p[text()='Account Details']")).click();
		
		//Switch qua Account Detail page
		switchToWindowByTitle("Welcome to HDFC Bank NetBanking");
		Thread.sleep(2000);
		
		//Switch qua frame Private Policy link
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='footer']")));
		Thread.sleep(2000);
		
		//Click vào Private Policy link
		driver.findElement(By.xpath("//a[text()='Privacy Policy']")).click();

		//Switch qua Private Policy page
		switchToWindowByTitle("HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		Thread.sleep(2000);
		
		//Click vào CSR link
		driver.findElement(By.xpath("//a[text()='CSR']")).click();
				
		// Đóng tất cả các tab trừ parent window
		Assert.assertTrue(closeAllWithoutParentWindows(parentID));
		Thread.sleep(2000);
	} 

	
	public void TC_00_Simple() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		String parentID = driver.getWindowHandle();
		System.out.println("Parent ID: " + parentID);
		
		//Click vào Here link
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		Thread.sleep(3000);
		
		// Get ra tất cả các ID của các cửa sổ đang có
		Set <String> allWindows = driver.getWindowHandles();
		
		//Switch qua window khác parent 
		for (String childID : allWindows) {
			System.out.println("ID: " + childID);
			if (!childID.equals(parentID)) {
				driver.switchTo().window(childID);
				Thread.sleep(3000);
				break;
			}
		}
		
		Assert.assertEquals(driver.getTitle(), "Google");
		
		//Switch về parent 
		for (String childID : allWindows) {
			System.out.println("ID: " + childID);
			if (childID.equals(parentID)) {
				driver.switchTo().window(childID);
				Thread.sleep(3000);
				break;
			}
		}
		
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	} 

	@Test
	public void TC_04() throws Exception {
		driver.get("http://live.guru99.com/index.php/");
		String parentID = driver.getWindowHandle();
		
		//Click vào Mobile tab
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		Thread.sleep(3000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'This is root of mobile')]")).isDisplayed());
		
		// Click to Add to compare
		driver.findElement(By.xpath("//a[@title='Xperia']/following-sibling::div//a[text()='Add to Compare']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Sony Xperia has been added to comparison list.']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/following-sibling::div//a[text()='Add to Compare']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']")).isDisplayed());
		
		// Click to Compare button
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		
		//Switch qua cửa sổ mới
		switchToWindowByTitle("Products Comparison List - Magento Commerce");
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		
		// Close tab
		driver.close();
		
		//Chuyển về parent window
		driver.switchTo().window(parentID);
		Assert.assertEquals(driver.getTitle(), "Mobile");
	} 

	
	public void switchToChildWindowByID(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(String expectedTitle) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(expectedTitle)) {
				break;
			}
		}
	}

	public boolean closeAllWithoutParentWindows(String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

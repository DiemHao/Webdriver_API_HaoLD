package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Topic_07_UserInteraction {
	WebDriver driver;
	Actions mouseAction;
	
//	@BeforeTest
//	public void beforeTest() {
//		driver = new FirefoxDriver();
//		mouseAction = new Actions(driver);
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
//		
//	}
	
	// Run on Chrome
	@BeforeTest
	public void beforeTest_Chrome() {
		System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		mouseAction = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	
	public void TC_01_HoverMouse() throws Exception {
		driver.get("http://www.myntra.com/");
		//Hover to profile icon
		WebElement profileicon = driver.findElement(By.xpath("//span[contains(@class,'desktop-iconUser')]"));
		mouseAction.moveToElement(profileicon).perform();
		
		Thread.sleep(3000);
		// Click to Login button
		WebElement loginButton = driver.findElement(By.xpath("//a[@class='desktop-linkButton' and text()='log in']"));
		//loginButton.click();
		mouseAction.click(loginButton).perform();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='login-box']")).isDisplayed());
	} 
	
	
	public void TC_02_ClickandHold() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		List <WebElement> numberItems = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		System.out.println("Element truoc khi duoc chon: "+ numberItems.size());
		Thread.sleep(3000);
		mouseAction.clickAndHold(numberItems.get(0)).moveToElement(numberItems.get(3)).release().perform();
		Thread.sleep(3000);
		List <WebElement> numberItemsSelected = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		System.out.println("Element sau khi duoc chon: "+ numberItemsSelected.size());
		Assert.assertEquals(numberItemsSelected.size(), 4);
	} 

	
	public void TC_03_ClickandHold_Random() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		List <WebElement> numberItems = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		mouseAction.keyDown(numberItems.get(0),Keys.CONTROL).perform();
		mouseAction.click(numberItems.get(0));
		mouseAction.click(numberItems.get(2));
		mouseAction.click(numberItems.get(5));
		mouseAction.click(numberItems.get(8));
		mouseAction.keyUp(numberItems.get(0), Keys.CONTROL).perform();
		
		List <WebElement> numberItemsSelected = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		System.out.println("Element sau khi duoc chon: "+ numberItemsSelected.size());
		Assert.assertEquals(numberItemsSelected.size(), 4);
	}
	

	public void TC_04_DoubleClick() throws Exception {
		driver.get("http://www.seleniumlearn.com/double-click");
		WebElement btnDoubleClick = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		mouseAction.doubleClick(btnDoubleClick).perform();
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "The Button was double-clicked.");
		alert.accept();
	}
	
	
	public void TC_05_RightClick() throws Exception {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		
		WebElement rightContext = driver.findElement(By.xpath("//span [@class='context-menu-one btn btn-neutral']"));
		mouseAction.contextClick(rightContext).perform();
		
		WebElement quitContext = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit')]"));
		mouseAction.moveToElement(quitContext).perform();
		
		Assert.assertTrue(driver.findElement(By.xpath("//li [contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]")).isDisplayed());
		quitContext.click();
		
		Assert.assertEquals(driver.switchTo().alert().getText(),"clicked: quit");
		driver.switchTo().alert().accept();
		Thread.sleep(3000);
		
		Assert.assertFalse(quitContext.isDisplayed());
	}
	
	@Test
	public void TC_06_DragandDrop() throws Exception {
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");
		WebElement smallCircle = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement bigCircle = driver.findElement(By.xpath("//div [@id='droptarget']"));
		
		mouseAction.dragAndDrop(smallCircle, bigCircle).perform();
		Assert.assertTrue(driver.findElement(By.xpath("//div [@id='droptarget' and contains(text(),'You did great!')]")).isDisplayed());
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

package selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


public class Topic_11_UploadFile {
	WebDriver driver;
	//Get relative path
	String rootFolder = System.getProperty("user.dir");
	String fileName1 = "Image 01.png";
	String fileName2 = "Image 02.png";
	String fileName3 = "Image 03.png";
	String fileNamePath1 = rootFolder + "\\uploadFile\\" + fileName1;
	String fileNamePath2 = rootFolder + "\\uploadFile\\" + fileName2;
	String fileNamePath3 = rootFolder + "\\uploadFile\\" + fileName3;
	String[] file = {fileNamePath1, fileNamePath2, fileNamePath3};
	
	String chromePath = rootFolder + "\\uploadFile\\chrome.exe";
	String firePath = rootFolder + "\\uploadFile\\firefox.exe";
	String iePath = rootFolder + "\\uploadFile\\ie.exe";
	
	String folderName = "Hao" + ranDomNumber();
	
	@BeforeTest
	public void beforeTest() {
		//Firefox
		//Selenium 2.xx + Firefox <= 47 + ko can dung gecko
		//Selenium 3.xx + Firefox >= 478 + phai can dung gecko
		
		System.setProperty("webdriver.gecko.driver",".\\lib\\geckodriver.exe");
		driver = new FirefoxDriver(); 
//		System.setProperty("webdriver.ie.driver",".\\lib\\IEDriverServer.exe");
//		driver = new InternetExplorerDriver();
		
//		System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	
	public void TC_01_Senkey_Upload_queue() {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		
		for (String file : file) {
			WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
			uploadFile.sendKeys(file);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		driver.findElement(By.xpath("//span[text()='Start upload']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ fileName1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ fileName2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ fileName3 +"']")).isDisplayed());
	} 
	
	
	public void TC_02_Senkey_Upload_Multiple_A_Time() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		//Thread.sleep(5000);

		WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
		uploadFile.sendKeys(fileNamePath1 + "\n" + fileNamePath2 + "\n" + fileNamePath3);
		Thread.sleep(1000);

		List <WebElement> startButtons = driver.findElements(By.xpath("//table//button[@class='btn btn-primary start']"));

		for (WebElement startButton : startButtons) {
			driver.findElement(By.xpath("//span[text()='Start upload']")).click();
			Thread.sleep(1000);
		}

		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ fileName1 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ fileName2 +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='"+ fileName3 +"']")).isDisplayed());
	} 

	
	public void TC_03_AutoIT() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		
		if (driver.toString().contains("chrome")) {
			WebElement uploadFile = driver.findElement(By.cssSelector(".fileinput-button"));
			uploadFile.click();
			Thread.sleep(3000);
			//Execute file exe
			Runtime.getRuntime().exec(new String[] { chromePath, fileNamePath1 });
		} else if (driver.toString().contains("firefox")) {
			WebElement uploadFile = driver.findElement(By.cssSelector(".fileinput-button"));
			uploadFile.click();
			Thread.sleep(3000);
			//Execute file exe
			Runtime.getRuntime().exec(new String[] { firePath, fileNamePath1 });
		} else {
			WebElement uploadFile =  driver.findElement(By.xpath("//input[@type='file']"));
			clickToElementByJS(uploadFile);
			Thread.sleep(3000);
			//Execute file exe
			Runtime.getRuntime().exec(new String[] { iePath, fileNamePath1 });
		}
		Thread.sleep(4000);
	}
	
	
	public void TC_04_Robot() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		
		  // Specify the file location with extension
        StringSelection select = new  StringSelection(fileNamePath1);

        // Copy to clipboard
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

        if (driver.toString().contains("chrome") || driver.toString().contains("firefox")) {
			WebElement uploadFile = driver.findElement(By.cssSelector(".fileinput-button"));
			uploadFile.click();
			Thread.sleep(3000);
			//Execute file exe
		} else {
			WebElement uploadFile =  driver.findElement(By.xpath("//input[@type='file']"));
			clickToElementByJS(uploadFile);
			Thread.sleep(3000);
			//Execute file exe
		}
        Robot robot = new Robot();
        Thread.sleep(1000);

        // Nhan phim Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Nhan xuong Ctrl - V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        // Nha Ctrl - V
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(1000);

        // Nhan Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	@Test
	public void TC_05_UploadFile() throws Exception {
		driver.get("https://encodable.com/uploaddemo/");
		WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
		uploadFile.sendKeys(fileNamePath1);
		Thread.sleep(1000);
		
		Select uploadDropDown = new Select(driver.findElement(By.xpath("//select[@name='subdir1']")));
		uploadDropDown.selectByVisibleText("/uploaddemo/files/");
		
		driver.findElement(By.xpath("//input[@id='newsubdir1']")).sendKeys(folderName);
		driver.findElement(By.xpath("//input[@id='formfield-email_address']")).sendKeys("dam@gmail.com");
		driver.findElement(By.xpath("//input[@id='formfield-first_name']")).sendKeys("DAM DAO");
		
		driver.findElement(By.xpath("//input[@id=\"uploadbutton\"]")).click();
		Thread.sleep(5000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//dd[contains(text(),'dam@gmail.com')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//dd[contains(text(),'DAM DAO')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Image_01.png')]")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='View Uploaded Files']")).click();
		driver.findElement(By.xpath("//a[text()='" + folderName +"']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Image 01.png')]")).isDisplayed());
	}
	
	public int ranDomNumber() {
		Random num = new Random();
		int n = num.nextInt(1000);
		return n;
	}
	
	public Object clickToElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}

	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

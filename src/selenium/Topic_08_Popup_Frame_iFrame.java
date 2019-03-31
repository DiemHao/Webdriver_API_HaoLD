package selenium;

import java.util.List;
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


public class Topic_08_Popup_Frame_iFrame {
	WebDriver driver;
	JavascriptExecutor javascript;
	
	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		javascript = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	
	@Test
	public void TC_02_CheckTitle() throws Exception {
		driver.get("https://www.hdfcbank.com/");

		/*
		 * findElement(Webelement):
		 * - Nếu nó đi find element mà ko tìm thấy thằng nào hết: đánh fail testcase và bắn lỗi NoSuchElementExpectation
		 * - Nếu nó tìm thấy duy nhất một element: thao tác vs element này -> click/displayed/sendkey/getText,...
		 * - Nếu nó tìm thấy lơn hơn 1 element nó sẽ luôn lấy thằng đầu tiên để thao tác 
		 * findELements (List webelement)
		 * - Ko tìm thấy element nào hết: trả về 1 list rỗng và ko đánh fail testcase
		 * - Nếu nó tìm thấy duy nhất 1 element: return lại 1 list chứa element này
		 * - Nếu nó tìm thấy nhiều hơn 1 element: return lại list chứa những element này 
		 */
		
		/* Vừa handle pop-up - vừa handle iframe 
		 * Pop-up:
		 * Case 01 - Nếu nó có xuất hiện thì phải check được displayed -> close đi ->qua step tiếp theo
		 * ==> đang chạy script refresh page vài lần cho nó hiện ra
		 * Case 02 - Nếu nó ko xuất hiện thì sẽ qua step tiếp theo luôn
		 * ==> Chỉ chờ trong khoảng 1 thời gian ngắn thôi chứ ko chờ hết timeout (nó sẽ tầm khoảng 10s)
		 */
		
		//Khai báo notification cho iframe
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> notificationIframe = driver.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		int notificationIframeSize = notificationIframe.size();
		System.out.println("Notification iframe displayed: " + notificationIframeSize);
		if (notificationIframeSize > 0) {
			
			//Switch qua iframe 
			driver.switchTo().frame(notificationIframe.get(0));
			
			//Verify image trong pop-up duoc hien thi
			Assert.assertTrue(driver.findElement(By.xpath("//div[@id='container-div']/img")).isDisplayed());
			
			//Close pop-up đó
			javascript.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[@id='div-close']")));
			Thread.sleep(3000);
			System.out.println("Close pop-up success!");
			
			//Switch to Top Windows (parent)
			driver.switchTo().defaultContent();
		}
		
		System.out.println("Pass handle pop-up!");
		
		//Set time out to default
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Switch qua element chứa iframe
		WebElement lookingforiFrame = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(lookingforiFrame);
		
		// Verify text được hiển thị
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='messageText' and text() = 'What are you looking for?']")).isDisplayed());
		
		//Switch to Top Windows (parent)
		driver.switchTo().defaultContent();
		
		//Switch qua iframe chứa image
		WebElement slidingBannerImage = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(slidingBannerImage);
		
		List <WebElement> bannerImage = driver.findElements(By.xpath("//img[@class='bannerimage']"));
		System.out.println("Banner Image = " + bannerImage.size());
		Assert.assertEquals(bannerImage.size(), 6);
		
		// Check element = image hay không?
		for (WebElement image : bannerImage) {
			Assert.assertTrue(isImageDisplayedSuccess(image));
		}
		
		//Switch to Top Windows (parent)
		driver.switchTo().defaultContent();

		//Switch qua iframe chứa image trong flipbanner
		List <WebElement> flipBannerImages = driver.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));
		System.out.println("Flip banner Image = " + flipBannerImages.size());
		Assert.assertEquals(flipBannerImages.size(), 8);
		// Check element = image hay không?
		for (WebElement image : flipBannerImages) {
			Assert.assertTrue(isImageDisplayedSuccess(image));
			Assert.assertTrue(image.isDisplayed());
		}
	} 

	
	public boolean isImageDisplayedSuccess(WebElement element) {
		return (boolean) javascript.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

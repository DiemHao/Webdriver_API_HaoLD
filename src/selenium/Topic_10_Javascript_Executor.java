package selenium;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Topic_10_Javascript_Executor {
	WebDriver driver;
	
	String customerName, gender, dateOfBirth, address, city, state, pin, phone, email, password;
	public String emailAddressEnter = "haole" + ranDomEmail() + "@gmail.com";
	
	//Locate Element
	By cutomerNameTextbox= By.xpath("//input[@name='name']");
	By genderRadio = By.xpath("//input[@value='m']");
	By dateOrBirthTextboxBy = By.xpath("//input[@id='dob']");
	By addressTextArea = By.xpath("//textarea[@name='addr']");
	By cityTextbox = By.xpath("//input[@name='city']");
	By stateTextbox = By.xpath("//input[@name='state']");
	By pinTextBox = By.xpath("//input[@name='pinno']");
	By phoneTextBox = By.xpath("//input[@name=\"telephoneno\"]");
	By emailTextBox = By.xpath("//input[@name=\"emailid\"]");
	By passwordTextBox = By.xpath("//input[@name=\"password\"]");
	By submitButton = By.xpath("//input[@name=\"sub\"]");

	@BeforeTest
	public void beforeTest() {
		//driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//Init data test for new
		customerName = "Automation Test";
		gender = "male";
		dateOfBirth = "1999-01-01";
		address = "1234 Le Loi";
		city = "Ho Chi Minh";
		state = "Hoan Kiem";
		pin = "600000";
		phone = "012343546573";
		email = "aumationtest" + ranDomEmail() +"@gmail.com";
		password = "123123";
	}

	
	public void TC_01() {
		navigateToUrlByJS("http://live.guru99.com/");
		
		String domainName = executeForBrowser("return document.domain");
		Assert.assertEquals(domainName, "live.guru99.com");
		
		String urlName = executeForBrowser("return document.URL");
		Assert.assertEquals(urlName, "http://live.guru99.com/");
		
		WebElement mobileLink = driver.findElement(By.xpath("//a[text()='Mobile']"));
		highlightElement(mobileLink);
		clickToElementByJS(mobileLink);
		
		WebElement samsungCardbutton = driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button"));
		highlightElement(samsungCardbutton);
		clickToElementByJS(samsungCardbutton);
		
		String innerText = executeForBrowser("return document.documentElement.innerText");
		Assert.assertTrue(innerText.contains("Samsung Galaxy was added to your shopping cart."));
		
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Samsung Galaxy was added to your shopping cart.");
		
		WebElement privacyPolicy = driver.findElement(By.xpath("//a[text()='Privacy Policy']"));
		highlightElement(privacyPolicy);
		clickToElementByJS(privacyPolicy);
		
		String privacyPolicyPageTitle = executeForBrowser("return document.title");
		Assert.assertEquals(privacyPolicyPageTitle, "Privacy Policy");
		
		scrollToBottomPage();
		
		WebElement lineRow = driver.findElement(By.xpath("//th[text()='CATEGORY_INFO']/following-sibling::td[text()='Stores the category info on the page, that allows to display pages more quickly.']"));
		Assert.assertTrue(lineRow.isDisplayed());
		
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		
		String domainGuru = executeForBrowser("return document.domain");
		Assert.assertEquals(domainGuru, "demo.guru99.com");
	} 

	
	public void TC_02() {
		driver.get("http://demo.guru99.com/v4/");
		driver.findElement(By.xpath("//input[@name=\"uid\"]")).sendKeys("mngr187573 ");
		driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("UnuzUju");
		driver.findElement(By.xpath("//input[@name=\"btnLogin\"]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());

		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Add New Customer']")).isDisplayed());
		
		// Input data for new customer
		driver.findElement(cutomerNameTextbox).sendKeys(customerName);
		driver.findElement(genderRadio).click();
	
		WebElement dateOrBirthTextbox = driver.findElement(dateOrBirthTextboxBy);
		removeAttributeInDOM(dateOrBirthTextbox, "type");
		dateOrBirthTextbox.sendKeys(dateOfBirth);
		
		driver.findElement(addressTextArea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextBox).sendKeys(pin);
		driver.findElement(phoneTextBox).sendKeys(phone);
		driver.findElement(emailTextBox).sendKeys(email);
		driver.findElement(passwordTextBox).sendKeys(password);
		driver.findElement(submitButton).click();
		// Verify expected data = actual data 
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class=\"heading3\" and text()='Customer Registered Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);
		
	} 

	@Test
	public void TC_03() throws Exception {
		driver.get("http://live.guru99.com/");
		
		WebElement accountLink = driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']"));
		highlightElement(accountLink);
		clickToElementByJS(accountLink);
		
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@id='header-account']//a[@title='My Account']"));
		highlightElement(myAccountLink);
		clickToElementByJS(myAccountLink);
		
		WebElement createAccount = driver.findElement(By.xpath("//form[@id='login-form']//span[text()='Create an Account']"));
		highlightElement(createAccount);
		clickToElementByJS(createAccount);
		
		WebElement firstName = driver.findElement(By.xpath("//input[@id='firstname']"));
		highlightElement(firstName);
		sendkeyToElementByJS(firstName, "Hao");
		
		WebElement lastName = driver.findElement(By.xpath("//input[@id='lastname']"));
		highlightElement(lastName);
		sendkeyToElementByJS(lastName, "Le");

		WebElement emailAddress = driver.findElement(By.xpath("//input[@id='email_address']"));
		highlightElement(emailAddress);
		sendkeyToElementByJS(emailAddress, emailAddressEnter);
		
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
		highlightElement(password);
		sendkeyToElementByJS(password, "123456");
		
		WebElement confirmPassword = driver.findElement(By.xpath("//input[@id='confirmation']"));
		highlightElement(confirmPassword);
		sendkeyToElementByJS(confirmPassword, "123456");
		
		WebElement registerbtn = driver.findElement(By.xpath("//form[@id='form-validate']//span[text()='Register']"));
		highlightElement(registerbtn);
		clickToElementByJS(registerbtn);

		// Dùng javascript kiểm tra đã đăng ký thành công
		String innerText = executeForBrowser("return document.documentElement.innerText");
		Assert.assertTrue(innerText.contains("Thank you for registering with Main Website Store."));
		
//		String messageSuccessful = driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='Thank you for registering with Main Website Store.']")).getText();
//		Assert.assertEquals(messageSuccessful, "Thank you for registering with Main Website Store.");
		WebElement accountLink2 = driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']"));
		highlightElement(accountLink2);
		clickToElementByJS(accountLink2);
		
		WebElement logOutlink = driver.findElement(By.xpath("//div[@id='header-account']//a[@title='Log Out']"));
		highlightElement(logOutlink);
		clickToElementByJS(logOutlink);

		Thread.sleep(5000);
		String titleGuru = executeForBrowser("return document.title");
		Assert.assertEquals(titleGuru, "Home page");
		
	}
	
	/**
	 * Common function
	 * @param element
	 */
	public void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='6px groove red'", element);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String executeForBrowser(String javaSript) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript(javaSript);
	}

	public Object clickToElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}

	public String sendkeyToElementByJS(WebElement element, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(WebElement element, String attribute) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object scrollToBottomPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public Object navigateToUrlByJS(String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location = '" + url + "'");
	}

	public int ranDomEmail() {
		Random email = new Random();
		int n = email.nextInt(1000);
		n += 1;
		return n;
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

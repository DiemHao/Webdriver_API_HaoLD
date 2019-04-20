package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestNG_01_Annotations {
	
	
  @Test(groups = "customer")
  public void TC_01() {
	  System.out.println("Run test case 01");
  }
  
  @Test(groups = "customer")
  public void TC_02() {
	  System.out.println("Run test case 02");
  }
  
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("Run before Method");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("Run after Method");
  }


//  @DataProvider
//  public Object[][] dp() {
//    return new Object[][] {
//      new Object[] { 1, "a" },
//      new Object[] { 2, "b" },
//    };
//  }
  @BeforeClass
  public void beforeClass() {
	  System.out.println("Run before class");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("Run after class");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("Run before test");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("Run after test");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("Run before suite");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("Run after Siute");
  }

}

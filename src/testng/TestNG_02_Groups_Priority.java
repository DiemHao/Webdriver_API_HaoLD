package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestNG_02_Groups_Priority {
	
	
  @Test(groups = "paynment", description = "Create new customer with all information")
  public void TC_01() {
	  System.out.println("Run test case 01");
	  Assert.assertTrue(false);
  }
  
  @Test(groups = "payment", description = "Edit customer with all information")
  public void TC_02() {
	  System.out.println("Run test case 02");
  }
  
	
  @Test(groups = "customer", description = "Delete customer with all information")
  public void TC_03() {
	  System.out.println("Run test case 03");
  }
  
  @Test(groups = "customer")
  public void TC_04() {
	  System.out.println("Run test case 04");
  }
  
  @Test(groups = "order")
  public void TC_05() {
	  System.out.println("Run test case 05");
  }
  
  @Test(groups = "order")
  public void TC_06() {
	  System.out.println("Run test case 06");
  }

}

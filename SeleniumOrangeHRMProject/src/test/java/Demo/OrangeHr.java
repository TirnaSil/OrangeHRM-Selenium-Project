package Demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class OrangeHr {



	

	
		public String baseUrl="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login" ;
		public WebDriver driver;
		
		@BeforeTest//first execute
	public void setup() {
		
		System.out.println("Before Test Case Executed");
		driver=new ChromeDriver();
			
		
		driver.manage().window().maximize();
		
		//open 
		driver.get(baseUrl);
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));//timer kept as 60 seconds
	}
		
		@Test(priority=1)
		public void dologinwithInvalidCrdential() throws InterruptedException {

			
		driver.findElement(By.xpath("//input[@placeholder=\"Username\"]")).sendKeys("Admin");
			
			driver.findElement(By.xpath("//input[@placeholder=\"Password\"]")).sendKeys("1235");
			
		    driver.findElement(By.xpath("//button[@type=\"submit\"]")).submit();
		    
		    String message_expected="Invalid credentials";
		    
		    String message_actual=driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
		    
		  // Assert.assertTrue(message_actual.contains(message_expected));    
		   Assert.assertEquals(message_expected, message_actual);
		   
			Thread.sleep(1500);
		}
		
		
	@Test(priority=2)
	public void LoginTestWithValidCredential() {
		
		driver.findElement(By.xpath("//input[@placeholder=\"Username\"]")).sendKeys("Admin");
		
		driver.findElement(By.xpath("//input[@placeholder=\"Password\"]")).sendKeys("admin123");
		
	    driver.findElement(By.xpath("//button[@type=\"submit\"]")).submit();
	    
	    //Verify if the login was successful by checking the page title 
	    String Pagetitle =driver.getTitle(); 
	    
	  /*  if(Pagetitle.equals("OrangeHRM")) {
	    	System.out.println("Login Successful!");
	    	
	    }
	    else {
	    	System.out.println("Login failed!");
	    }*/
	    logout();
	    Assert.assertEquals("OrangeHRM",Pagetitle );
	}
	@Test(priority=3)
	public void addEmployee() throws InterruptedException
	{
		 
		login();
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[text()='Add Employee'] ")).click();
		
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Saheb");
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("Sil");
		
		
		 
		   
	
		 
		 
		 Thread.sleep(2000);
		 //save
		 
		 driver.findElement(By.xpath("//button[normalize-space()='Save'] ")).click();
		 
		 //verify if the employee is added successfully
		 String confirmationmessage =driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();
		 
		 if(confirmationmessage.contains("Personal Details"))
		 {
			 System.out.println("employee Added successfully!");
			 
		 }
		 else {
			 
			 System.out.println("Failed to add employee");
		 
			 
		 }
		 logout();
		 Assert.assertEquals("Personal Details",confirmationmessage );

	}
	@Test(priority=4)
public void searchemployeebyname() throws InterruptedException
{
	login();
	
	driver.findElement(By.xpath("//span[text()='PIM']")).click();
	
	//select on employee list
	driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
	
	
	driver.findElements(By.tagName("input")).get(1).sendKeys("Saheb");
	//driver.findElements(By.tagName("input")).get(1).sendKeys("0479");
	//search the employee
	 driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
	 
	 
	 Thread.sleep(5000);

	  JavascriptExecutor executor= (JavascriptExecutor) driver;
	  executor.executeScript("window.scrollBy(0,"+ 500 +")");
	  Thread.sleep(2000);
	 
	List <WebElement> element = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));
	
	
	
	String expected_message="Records Found";
	String message_actual=element.get(0).getText();
	
	System.out.println(message_actual);
	
	logout();
	Assert.assertTrue(message_actual.contains(expected_message));
	//for(int i=0;i<element.size();i++)
	//{
	//System.out.println("At index"+ i +"text is :" +   element.get(i).getText());
	//}
	
}
	/*@Test(priority=5)
	 public void searchEmployeeById() throws InterruptedException {
		 
		 String EmpId="10999";
		 String message_actual="";
		 login();
		 
		 
		 driver.findElement(By.xpath("//span[text()='PIM']")).click();
			
			//select on employee list
			driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
			
			driver.findElements(By.tagName("input")).get(0).sendKeys(EmpId);
			
			driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
			 
			  Thread.sleep(5000);
			  
			  JavascriptExecutor executor= (JavascriptExecutor) driver;
			  executor.executeScript("window.scrollBy(0,"+ 500 +")");
			  
			  Thread.sleep(2000);
			  
			List<WebElement> rows=driver.findElements(By.xpath("(//div[@role='row'])"));
			
			if(rows.size()>1)
			{
				message_actual=	driver.findElement(By.xpath("(//div[@role='row'])[2]//div[@role='cell'][2]")).getText();
			}
			logout();
			Assert.assertEquals(EmpId, message_actual);
	 }
	*/
	
public void login()
{
	driver.findElement(By.xpath("//input[@placeholder=\"Username\"]")).sendKeys("Admin");
	
	driver.findElement(By.xpath("//input[@placeholder=\"Password\"]")).sendKeys("admin123");
	
    driver.findElement(By.xpath("//button[@type=\"submit\"]")).submit();
}

	public void logout() {
		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		
		//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();	
		List <WebElement> elementlist=driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));

	/*	for(int i=0;i<elementlist.size();i++)
		{
			Thread.sleep(1000);
			System.out.println(i + " :" + elementlist.get(i).getText());
		}*/
		
	elementlist.get(3).click();
	}
  /*@Test(priority=5)
	public void DeleteEmployee() throws InterruptedException
	{
		login();

		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		
		//select on employee list
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
		driver.findElements(By.tagName("input")).get(1).sendKeys("bala");
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		
		
		Thread.sleep(3000);
		//////Delete///////
		
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();
		
		driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label danger.orangehrm-button-margin']")).click();
		
		
		String msg=driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();
		Assert.assertEquals(msg, "No Records Found");
		Thread.sleep(4000)
		logout();
	}*/
	
	@Test(priority=5)
	public void ListEmployees() throws InterruptedException {
		
		
		login();
		
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		
		//select on employee list
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
		
		Thread.sleep(4000);
		
   List<WebElement> totalLinkselements=driver.findElements(By.xpath("//ul[@class='oxd-pagination__ul']/li"))	;	
		
   int totalLInks=totalLinkselements.size();
   
   for(int i=0;i<totalLInks;i++)//0,1,2,3
   {
	   try 
	   {
	   
	   String currentLinkText = totalLinkselements.get(i).getText();
	   
	   
		   int page = Integer.parseInt(currentLinkText);
		   
		   System.out.println("Page: "+page);
		   
		   totalLinkselements.get(i).click();
		   
		   Thread.sleep(2000);
		   
		   List<WebElement> emp_list = driver.findElements(By.xpath("(//div[@class='oxd-table-card']/div)/div[3]"));
		   for(int j=0; j<emp_list.size();j++)
		   {
			   String FirstName=emp_list.get(j).getText();
			   System.out.println(FirstName);
			   
		   }
		   
	   }
	   catch(Exception ex)
	   {
		   System.out.println("Not a number");
	   }
	   
	   
   }
	   
   
   Thread.sleep(4000);
		logout();
	}
	
	
	@AfterTest
	public void teardown() throws InterruptedException {
		
		//logout();
		Thread.sleep(5000);//wait for 5 secs before quit
		
		driver.close();
		driver.quit();
	
	}

}
	
	
	


package co.za.absa.Test;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.ScreenCapture;

import co.za.absa.BaseClass.BaseClass;
import co.za.absa.WebPages.LoginPage;
import co.za.absa.WebPages.SplitPasswordPage;

public class LoginPageTest extends BaseClass 
{
	LoginPage loginPage;
	SplitPasswordPage splitPasswordPage;
	
	public LoginPageTest()
	{
		super();
		logger = Logger.getLogger(LoginPageTest.class);
	}
	
	@BeforeMethod
	public void setUp() throws Exception
	{
		closeExistingBrowser();
		initialisation();
		
		logger.info("Driver Initiated");
	}
	
	@Test
	@Parameters({"accountNumber","pinNumber"})
	public void loginUser(@Optional("9050955490")String accountNumber,@Optional("55490")String pinNumber)throws Exception
	{
		extentTest = extentReports.startTest("Login User Test");
		
		logger.info("Test Started: "+extentTest.getTest());
		
		loginPage = new LoginPage();
		extentTest.log(LogStatus.INFO, "Login Page Object Created");
		
		splitPasswordPage = loginPage.loginUser(accountNumber, pinNumber);
		extentTest.log(LogStatus.INFO, "Login User Method Called and Split Password Page Object Created");
		
		logger.info("Login Successful");
		
		extentTest.log(LogStatus.PASS, "Login Succesful",extentTest.addScreenCapture(BaseClass.takeScreenshot(driver, "Login")));
	}
	
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		closeDriver();
		if(result.getStatus() == ITestResult.SUCCESS)
		{
			extentTest.log(LogStatus.PASS, result.getName());
		}
		
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			extentTest.log(LogStatus.FAIL, result.getName());
			extentTest.log(LogStatus.FAIL, result.getThrowable());
		}
		
		else if(result.getStatus() == ITestResult.SKIP)
		{
			extentTest.log(LogStatus.SKIP, result.getName()+" Test Skipped");
		}
		
		extentReports.endTest(extentTest);
		logger.info("Test Ended: "+extentTest.getTest());
	}
	
	@AfterTest
	public void flushReport()
	{
		extentReports.flush();
	}
	
}

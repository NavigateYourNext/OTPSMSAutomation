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

import co.za.absa.BaseClass.BaseClass;
import co.za.absa.WebPages.HomePage;
import co.za.absa.WebPages.LoginPage;
import co.za.absa.WebPages.SplitPasswordPage;

public class SplitPasswordPageTest extends BaseClass
{
	LoginPage loginPage;
	SplitPasswordPage splitPasswordPage;
	HomePage homePage;
	
	public SplitPasswordPageTest()
	{
		super();
		logger = Logger.getLogger(SplitPasswordPageTest.class);
	}
	
	@BeforeMethod
	@Parameters({"accountNumber","pinNumber"})
	public void setUp(@Optional("9050955490")String accountNumber,@Optional("55490")String pinNumber) throws Exception
	{
		closeExistingBrowser();
		initialisation();
		
		extentTest = extentReports.startTest("HomePage LookUp");
		logger.info("Test Started: "+extentTest.getTest());
		
		loginPage = new LoginPage();
		splitPasswordPage = loginPage.loginUser(accountNumber, pinNumber);
		extentTest.log(LogStatus.INFO, "Login Succesful");
		logger.info("Login Succesful");
	}
	
	@Test
	@Parameters({"passphrase"})
	public void enterPara(@Optional("password1")String passphrase)
	{
		homePage = splitPasswordPage.enterParaphrase(passphrase);
		extentTest.log(LogStatus.INFO, "Enter Para Phrase Method Ended");
		
		logger.info("Para Phrase Entered");
		
		extentTest.log(LogStatus.PASS, "ParaPhrase Entered",extentTest.addScreenCapture(BaseClass.takeScreenshot(driver, "ParaPhrase")));
	}
	
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

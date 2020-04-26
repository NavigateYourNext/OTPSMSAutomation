package Util;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

// Twilio Login Id Credentials:-

//akshayshetesa@gmail.com
//akshayshetesa#100

public class AmazonOTPHandle {

	public static final String ACCOUNT_SID = "AC083bc13d00519db79e437bebbb54d11e";
	public static final String AUTH_TOKEN = "ba44c35493e642249469527b34e6f171";

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//a[@data-nav-role='signin']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Create your Amazon account')]")).click();

		driver.findElement(By.id("ap_customer_name")).sendKeys("AkshayTestOTP");
		driver.findElement(By.id("auth-country-picker-container")).click();

		driver.findElement(By.xpath("//ul[@role='application']//li/a[contains(text(),'United States +1')]")).click();
		driver.findElement(By.id("ap_phone_number")).sendKeys("2512862961");
		driver.findElement(By.id("ap_password")).sendKeys("TestAutomation@123");
		driver.findElement(By.id("continue")).click();

		// get the OTP using Twilio APIs:
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody = getMessage();
		System.out.println(smsBody);
		String OTPNumber = smsBody.replaceAll("[^0-9]+", " ");
		System.out.println(OTPNumber);
		
		driver.findElement(By.id("auth-pv-enter-code")).sendKeys(OTPNumber);

	}

	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
				.filter(m -> m.getTo().equals("+12512862961")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
	}

	private static Stream<Message> getMessages() {
		ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
		return StreamSupport.stream(messages.spliterator(), false);
	}

}

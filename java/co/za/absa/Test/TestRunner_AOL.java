package co.za.absa.Test;

import org.testng.TestNG;

public class TestRunner_AOL {
	
	static TestNG testng;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		testng = new TestNG();
		
		testng.setTestClasses(new Class[] {LoginPageTest.class,SplitPasswordPageTest.class});
		
		testng.run();
		
		System.out.println("Done");

	}

}

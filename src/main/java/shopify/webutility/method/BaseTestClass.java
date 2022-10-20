package shopify.webutility.method;

import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;


public class BaseTestClass {

	protected WebUtility wu= WebUtility.getObject();

	protected Properties prop;

	@BeforeSuite
	public void tcOpenApp() {
		
		wu.htmlReport("target//setecommercereports.html");	

	}

	
	
	@DataProvider(name="data")
	public Object[][] dataProviderFromeExcelSheet(Method methName) {
	Object[][] a=wu.readData("Login", methName.getName());
	return a;
	}

	
	
	@BeforeClass
	public void LaunchingMethod() {
		wu.openLoginPage(wu, "chrome", "https://ced-selenium-test.myshopify.com/admin");

	}


	
	
	@BeforeMethod
	public void LoginPageMethod(Method method) {
		String tcName=method.getName();
		wu.setExtentLogger(tcName);		
		wu.browserInfo();
		

	}

	
	
	
	
	
	@AfterMethod
	public void tclogOut(ITestResult result ,Method method) {
		int status=result.getStatus();
		if(status==ITestResult.FAILURE) {
			String tcName=method.getName();
			String imgPath=	wu.takeSnapshot(tcName);
			wu.snapShotCaptureReportattach(imgPath);	
		}
		
	}


	@AfterClass(enabled = false)
	public void closeBrowser() {
		wu.closeBrowser();
		wu.quiteBrowser();


	}



	@AfterSuite
	public void closeApplication() {
		System.out.println("Application is close");	
	}


}

package shopify.login.loginfunctionality;

import org.openqa.selenium.support.PageFactory;

import shopify.webutility.method.WebUtility;

public class LoginPage extends LoginPageOr {
	
	WebUtility webutil;
	
	public LoginPage(WebUtility wu) {
		PageFactory.initElements(wu.getDriver(), LoginPage.this);
		webutil=wu;
	}


	


	public void validLogin() {
		webutil.inpuData(locEmailId, "chiraggupta@cedcommerce.com", "input email id in email box successfully");
		webutil.actionClick(locContinue);
		
		webutil.inpuData(locPassword, "SeleniumTest", "input password in password text box is successfully");
		webutil.actionClick(locLoginBT);
	
	}
	
	

}

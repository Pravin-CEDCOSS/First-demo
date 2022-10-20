package shopify.login.loginfunctionality;

import org.openqa.selenium.support.PageFactory;

import shopify.webutility.method.WebUtility;

public class HomePage extends HomePageOr{

	WebUtility webutil;

	public HomePage(WebUtility wu) {
		PageFactory.initElements(wu.getDriver(), HomePage.this);
		webutil=wu;
	}


	public void homePage() {

		webutil.click(locProducts,"Click on product");
		webutil.click(locAddProducts, "Clicked on Add product");

	}





}

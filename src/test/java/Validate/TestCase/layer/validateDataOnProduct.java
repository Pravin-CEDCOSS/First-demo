package Validate.TestCase.layer;


import org.testng.annotations.Test;

import shopify.login.loginfunctionality.HomePage;
import shopify.login.loginfunctionality.Info;
import shopify.login.loginfunctionality.LoginPage;
import shopify.webutility.method.BaseTestClass;

public class validateDataOnProduct extends BaseTestClass {

	@Test
	public void tc001_Set_Ecommerce() {
		LoginPage loginP = new LoginPage(wu);
		loginP.validLogin();
		HomePage homeP = new HomePage(wu);
	    homeP.homePage();
		Info infoP=new Info(wu);
		infoP.infoData();
	}


}

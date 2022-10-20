package shopify.login.loginfunctionality;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageOr {


	@FindBy(xpath = "//span[text()='Products']")
	WebElement locProducts;

	@FindBy(xpath = "//span[text()='Add product']")
	WebElement locAddProducts;

}

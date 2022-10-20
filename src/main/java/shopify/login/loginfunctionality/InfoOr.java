package shopify.login.loginfunctionality;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InfoOr {

	@FindBy(xpath = "//input[@name='title']")
	WebElement Title;
	
	@FindBy(xpath = "//body[@data-id='product-description']")
	WebElement Description;
	
	@FindBy(xpath = "//input[@id=\"PolarisTextField7\"]")
	WebElement ProductType;
	
	@FindBy(xpath = "//input[@id=\"PolarisTextField8\"]")
	WebElement Vendor;
	
	@FindBy(xpath = "//input[@id=\"PolarisComboboxTextField2\"]")
	WebElement Tags;
	
	@FindBy(xpath = "//input[@id=\"PolarisTextField5\"]")
	WebElement Price;
	
	@FindBy(xpath = "//input[@id=\"PolarisTextField6\"]")
	WebElement Castprice;
	
	@FindBy(xpath = "//input[@id=\"PolarisTextField7\"]")
	WebElement CastParItem;
	
	@FindBy(xpath = "//input[@id=\"InventoryCardSku\"]")
	WebElement SKU;
	
	@FindBy(xpath = "//input[@id=\"InventoryCardBarcode\"]")
	WebElement barcode;
	
	@FindBy(xpath = "//input[@id=\"ShippingCardWeight\"]")
	WebElement Waight;
	
	@FindBy(xpath = "//span[text()='Save']")
	WebElement SaveBT;
	
	@FindBy(xpath = "//span[text()='Products']")
	WebElement ProductsTB;
	
	@FindBy(xpath = "//input[@id='PolarisTextField19']")
	WebElement FilterTF;
}

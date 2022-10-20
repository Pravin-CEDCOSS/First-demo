package shopify.login.loginfunctionality;

import org.openqa.selenium.support.PageFactory;
import shopify.webutility.method.WebUtility;

public class Info extends InfoOr {

	WebUtility webUtil;
	String[][] data;

	public Info(WebUtility wu) {
		PageFactory.initElements(wu.getDriver(), Info.this);
		webUtil = wu;
		try {
			data = webUtil.readCSVData();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void infoData() {

		webUtil.inpuData(Title, data[0][0], "Input title is successfully");
		webUtil.inpuData(ProductType, data[0][2], "Input productvalue is sucessfully");
		webUtil.inpuData(Vendor, data[0][3], "Input vender data is sucessfully");
		webUtil.inpuData(Tags, data[0][4], "Input tags is sucessfully");
		webUtil.inpuData(Price, data[0][5], "Input price is successfully");
		webUtil.inpuData(Castprice, data[0][6], "Input Castprice is  successfully");
		webUtil.inpuData(CastParItem, data[0][7], "Input Cost per item is successfully");
		webUtil.inpuData(SKU, data[0][8], "Input Stock Keeping Unit is successfully");
		webUtil.inpuData(barcode, data[0][9], "Barcode");
		webUtil.inpuData(Waight, data[0][11], "Weight");
		webUtil.click(SaveBT, "Click on save button successfully");
		webUtil.click(ProductsTB, "Click On Product Tab Succeccfully");
		webUtil.inpuData(FilterTF, "Task", "Input Data Succeccfully in filter Tab");

	}

}

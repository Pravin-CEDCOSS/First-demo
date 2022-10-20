package shopify.webutility.method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class WebUtility {

	private WebDriver driver;
	private static WebUtility wu;
	private ExtentTest extTestLogger;
	private ExtentReports extReport;
	private Workbook workbook;
	private Properties prop;
	private Sheet sheetObj;

	private WebUtility() {

	}


	public static WebUtility getObject() {
		if (wu==null) {
			wu=new WebUtility();
		}
		return wu;   
	}


	public void LaunchBrowser(String browsername) {

		if(browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();		
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		}else if(browsername.equalsIgnoreCase("firefox")){
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();		
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		}

	}

	public void browserInfo() {
		if(driver instanceof ChromeDriver) {
			extTestLogger.log(Status.INFO, "Chrome Browser is Launched Success");

		}else if(driver instanceof FirefoxDriver) {
			extTestLogger.log(Status.INFO, "fireFox Browser is Launched Success");

		}else if(driver instanceof InternetExplorerDriver) {
			extTestLogger.log(Status.INFO, " Browser is Launched Success");

		}
	}



	public void closeBrowser() {
		driver.close();
	}	



	public void quiteBrowser() {
		driver.quit();
	}	



	public void flushReport() {
		extReport.flush();
	}	


	public void openUrl(String url) {
		try {
			driver.get(url);
		}catch(Exception e) {		
			extTestLogger.log(Status.ERROR, "url does't open successfully");

		}
	}




	public WebDriver getDriver() {
		return driver;
	}




	public void openLoginPage(WebUtility webUtil, String browserName, String Url) {
		webUtil.LaunchBrowser(browserName);
		webUtil.openUrl(Url);


	}



	public void inpuData(WebElement webEle, String inputvalue, String msg) {

		try {
			webEle.clear();
			webEle.sendKeys(inputvalue);
		}catch(StaleElementReferenceException e){			
			webEle.clear();
			webEle.sendKeys(inputvalue);
		}catch(ElementNotInteractableException e){

		}
		extTestLogger.log(Status.INFO, msg);	


	}


	public void actionClick(WebElement element){

		new Actions(driver).moveToElement(element).build().perform();
		holdOn(5);

		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		holdOn(5);
		new Actions(driver).moveToElement(element).click().build().perform();


	}



	public void jsClick(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}


	public void click( WebElement webEle, String msg) {
		try {
			webEle.click();			
		}catch(NoSuchElementException e){

			new Actions(driver).click(webEle).build().perform();

		}catch(ElementNotInteractableException e){

		}
		extTestLogger.log(Status.INFO, msg);	


	}

	public void verifyInnerText(WebElement weEle, String expectedText, String snapShotName) {
		try {
			String actualText=weEle.getText();
			if(actualText.equalsIgnoreCase(expectedText)) {
				extTestLogger.log(Status.PASS, " Where Actual Text is :- "+actualText+" & Expected is :- "+expectedText);
			}else {
				extTestLogger.log(Status.FAIL, " Where Actual Text is :- "+actualText+" & Expected is :- "+expectedText);
			}
		}catch(Exception e){

			extTestLogger.log(Status.FAIL, "Text is not available");
		}
	}

	public void verifyTextContains(WebElement weEle, String expectedText, String snapShotName){

		String actualText = weEle.getText();
		if(actualText.contains(expectedText)){
			extTestLogger.log(Status.PASS, "Text Verification is Passed where Actual is :- "+actualText+" & Expected is :- "+expectedText);
		}else
			extTestLogger.log(Status.PASS, "Text Verification is Failed where Actual is :- "+actualText+" & Expected is :- "+expectedText);

	}

	public void verifyEnabled(WebElement weEle, String snapShotName) {

		boolean status = weEle.isEnabled();
		if(status){
			extTestLogger.log(Status.PASS, "Element is Enabled");
		}else {
			extTestLogger.log(Status.PASS, "Element is Disabled");

		}
	}

	public void verifyDisabled(WebElement weEle, String snapShotName) {

		boolean status = weEle.isEnabled();
		if(status==false){
			extTestLogger.log(Status.PASS, "Element is Disabled");
		}else {
			extTestLogger.log(Status.PASS, "Element is Enabled");
		}
	}

	public void verifyAttributeValue(WebElement weEle, String attributeName, String expectedAttributeValue, String snapShotName) {

		String actualAttributeValue = weEle.getAttribute(attributeName);
		if(actualAttributeValue.equalsIgnoreCase(expectedAttributeValue)){
			extTestLogger.log(Status.PASS, " Where Actual Attribute Value is :- "+actualAttributeValue+" & Expected Attribute Value is :- "+expectedAttributeValue);
		}else {
			extTestLogger.log(Status.FAIL, " Where Actual Attribute Value is :- "+actualAttributeValue+" & Expected Attribute Value is :- "+expectedAttributeValue);

		}
	}


	public void verifyElementVisible(WebElement weEle, String snapShotName){

		boolean  weStatus = weEle.isDisplayed();
		Dimension dim =  weEle.getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		if(weStatus==true && height>0 && width>0){
			extTestLogger.log(Status.PASS, "Element is Visible");
		}else
			extTestLogger.log(Status.FAIL, "Element is Invisible");

	}

	public void verifyElementInvisible(WebElement weEle, String snapShotName){

		boolean  weStatus = weEle.isDisplayed();
		Dimension dim =  weEle.getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		if(weStatus==true && height>1 && width>1){
			extTestLogger.log(Status.PASS, "Element is Invisible");
		}else
			extTestLogger.log(Status.FAIL, "Element is Visible");

	}


	public void acceptPopUp() {
		driver.switchTo().alert().accept();
	}

	public void dismissPopUp() {
		driver.switchTo().alert().dismiss();
	}

	public void closeBrowser(String msg) {
		driver.close();
		extTestLogger.log(Status.PASS, msg);

	}


	public void holdOn(long miles) {
		try {
			Thread.sleep(miles*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void getWindoHandleByTitle(String ExpTitlevalue,String msg) {		
		Set<String> AllWindowValue=driver.getWindowHandles();		
		for(String AllValue: AllWindowValue) {		
			driver.switchTo().window(AllValue);		
			String  getTitleValue=	driver.getTitle();	
			if(getTitleValue.contains(ExpTitlevalue)) {
				break;
			}

		}
		extTestLogger.log(Status.INFO, msg);
	}

	public void getWindoHandleByUrl(String ExpURLvalue ,String msg) {		
		Set<String> AllWindowValue=driver.getWindowHandles();		
		for(String AllValue: AllWindowValue) {		
			driver.switchTo().window(AllValue);		
			String  getTitleValue=	driver.getCurrentUrl();	
			if(getTitleValue.contains(ExpURLvalue)) {
				break;
			}

		}
		extTestLogger.log(Status.INFO, msg);
	}

	public String getRandomName(int count) {
		String name="";
		for(int i=1; i<=count; i++) {
			int rnd=(int) (Math.random() * 52);
			Character base = (rnd < 26) ? 'A' : 'a' ;
			name =name+base.toString() + rnd%26;
		}
		return name;

	}

	public String timeStamp(){
		SimpleDateFormat sft=new SimpleDateFormat("dd/MM/yyyy hh_mm_ss");
		String Time=sft.format(new Date());
		return Time;
	}


	public String takeSnapshot(String snapshortname) {
		TakesScreenshot scrtsho=(TakesScreenshot) driver;
		File soursefile=scrtsho.getScreenshotAs(OutputType.FILE);
		String Time=timeStamp();
		File distinationFile = new File("reports\\"+snapshortname+Time+".jpeg");
		try {
			Files.copy(soursefile, distinationFile);
		}catch(IOException e) {

		}
		return distinationFile.getAbsolutePath();
	}


	public void snapShotCaptureReportattach(String imgPath) {
		try {
			extTestLogger.addScreenCaptureFromPath(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public void htmlReport(String reportResultName) {
		ExtentHtmlReporter ExtHtmlRport = new ExtentHtmlReporter(reportResultName);
		ExtHtmlRport.config().setReportName("Function Reports Automation");
		ExtHtmlRport.config().setDocumentTitle("Shopify Functionality Reports");
		extReport =new ExtentReports();	
		extReport.attachReporter(ExtHtmlRport);
		extReport.setSystemInfo("username", System.getProperty("user.name"));
		extReport.setSystemInfo("OS", System.getProperty("user.os"));
		extReport.setSystemInfo("Envoirment", "QA");

	}



	public void setExtentLogger(String testCaseName) {
		extTestLogger=extReport.createTest(testCaseName);

	}



	public ExtentTest getLogger() {
		return extTestLogger;
	}

	public Properties getPropObj() {
		return prop;
	}

	public String[][] readCSVData() throws Exception {

		String[][] testData;

		String file="src/test/resources/TestData.csv";

		//Get the workbook
		Reader fileInputStream = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(fileInputStream);


		int numberOfRecords = 0;
		int numberOfColumns = 0;


		for (CSVRecord record : records) {
			System.out.println("Reading record line #" + ++numberOfRecords);
			numberOfColumns = record.size();
		}

		testData = new String[numberOfRecords - 1][numberOfColumns];
		System.out.println("numberOfRecords = " + numberOfRecords);
		System.out.println("numberOfColumns = " + numberOfColumns);


		int currentRecord = 0;


		fileInputStream = new FileReader(file);
		records = CSVFormat.EXCEL.parse(fileInputStream);

		for (CSVRecord record : records) {

			System.out.println("Reading test data ");
			if (record.getRecordNumber() == 1) {
				System.out.println("record = " + record);
				continue;
			}

			for (int i = 0; i < record.size(); i++) {
				testData[currentRecord][i] = record.get(i);

			}
			currentRecord++;
		}


		return testData;
	}



	public void loaderConfigFile() {
		prop=new Properties();
		FileInputStream fis=null;		
		try {
			fis=new FileInputStream("src\\test\\resources\\config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public int getRowCountofTcID(Sheet sheetobj,String testcaseIdName) {
		int activateRowNum=sheetobj.getLastRowNum();
		int count=0;
		for (int i = 1; i <= activateRowNum; i++) {
			Row rowObj=sheetobj.getRow(i);
			Cell cellObj=rowObj.getCell(1);
			String actualCellvalue=cellObj.getStringCellValue();
			if (actualCellvalue.equals(testcaseIdName)) {
				count++;
			}
		}
		return count;

	}



	public Object[][] readData(String sheetName ,String expTestCaseId)  {
		File file=new File("src\\main\\resources\\TestData");
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(file);
			workbook=new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetObj=workbook.getSheet(sheetName);		
		Object[][]arrObj=new Object[getRowCountofTcID(sheetObj,expTestCaseId)][1];
		int dataCount=0;
		int rowcount=sheetObj.getLastRowNum();
		for (int i = 1; i <= rowcount; i++) {		
			Row rowObj=sheetObj.getRow(i);
			Cell xcellObj=rowObj.getCell(1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			String actualTcId=xcellObj.getStringCellValue();
			if (expTestCaseId.equalsIgnoreCase(actualTcId)) {
				Map<String,String> dataMap=new HashMap<String, String>();
				int cellCount=rowObj.getLastCellNum();

				for (int j = 2; j <= cellCount-1; j=j+2) {
					Cell cellDataName=rowObj.getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					Cell cellDataValue=rowObj.getCell(j+1,MissingCellPolicy.CREATE_NULL_AS_BLANK);
					String dataKey=cellDataName.getStringCellValue();
					String dataValue=cellDataValue.getStringCellValue();
					dataMap.put(dataKey, dataValue);
				}
				arrObj[dataCount++][0]=dataMap;
			}
		}
		return arrObj;
	}	








}





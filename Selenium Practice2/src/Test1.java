import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.openqa.selenium.remote.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
//import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Test1 {

	
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, ClientProtocolException, IOException  {
		// TODO Auto-generated method stub
		
		getUrl();
		addRemoveElement();
		handleBrokenImages();
		handleCheckboxes();
		driver.navigate().back();
		handleContextMenu();
		driver.navigate().back();
		dragAndDrop();
		driver.navigate().back();
		handleDropdown();
		driver.navigate().back();
		handleDynamicControls();
		driver.navigate().back();
		handleDynamicLoading();
		//entryAd(); //not working
		//exitIntent(); //not tried
		downloadFile();
		uploadFile();
	}
	
	 public static void getUrl() {
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://the-internet.herokuapp.com/");
		driver.manage().window().maximize();
	 }
	 
	 public static void addRemoveElement() throws InterruptedException
	 {
		 	driver.findElement(By.xpath("//a[@href='/add_remove_elements/']")).click();
			driver.findElement(By.xpath("//button[text()='Add Element']")).click();
			
			WebElement element=driver.findElement(By.xpath("//div[@id='elements']//button[text()='Delete']"));
			if (element.isDisplayed())
				System.out.println("Element added successfully");
			
			element.click();
			List<WebElement> checkElement = driver.findElements(By.xpath("//div[@id='elements']//button[text()='Delete']"));
			if (checkElement.size()==0)
				System.out.println("Element removed successfully");
			
			driver.navigate().back(); 
			Thread.sleep(1000);
	 }
	 
	 public static void handleBrokenImages() throws InterruptedException, ClientProtocolException, IOException {
		 	int invalidImageCount=0;
			driver.findElement(By.xpath("//a[text()='Broken Images']")).click();
			List<WebElement> image_list= driver.findElements(By.tagName("img"));
			Thread.sleep(1000);
			System.out.println("Total Images on the webpage are: "+image_list.size());
			
			
			for (WebElement imgElement : image_list) {
				if (imgElement != null) {
									
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(imgElement.getAttribute("src"));
					HttpResponse response = client.execute(request);
					
					if (response.getStatusLine().getStatusCode() != 200) {
						System.out.println(imgElement.getAttribute("outerHTML")+"is broken");
						invalidImageCount++;
					}
				}
			
			}
			System.out.println("Total Number of broken images are: "+ invalidImageCount);
			Thread.sleep(1000);
			
			//Challenging DOM--Not sure what to do there
			driver.navigate().back();
	 }
	 
	 public static void handleCheckboxes() {
		 driver.findElement(By.xpath("//a[text()='Checkboxes']")).click();
			List<WebElement> Total_checkboxes= driver.findElements(By.xpath("//input[@type='checkbox']"));
			System.out.println("Total Checkboxes available on the page: "+ Total_checkboxes.size() );
			
			for (WebElement chkboxes : Total_checkboxes) {
				if (chkboxes !=null) {
					/*if (chkboxes.isSelected())
						System.out.println("default selected checkbox: "+ chkboxes.getText());//Not able to get the name of checkbox
					*/
					if (!chkboxes.isSelected()) {
						chkboxes.click();
						System.out.println("Selected Checkbox is:" + chkboxes.getText());
					}				
				}
			}
			System.out.println("All Checkboxes are selected now");
			for (WebElement chkboxes : Total_checkboxes) {
				if (chkboxes !=null) {
					
					if (chkboxes.isSelected()) {
						System.out.println("Selected Checkbox is:" + chkboxes.getText());
						chkboxes.click();	
					}
				}
			}
			System.out.println("No Checkbox is selected now");
			driver.findElement(By.xpath("//input[@type='checkbox'][01]")).click();
			System.out.println("First Checkbox selected");
			driver.findElement(By.xpath("//input[@type='checkbox'][02]")).click();
			System.out.println("Second Checkbox selected");
	 }
	 
	 public static void handleContextMenu() throws InterruptedException {
		 	driver.findElement(By.xpath("//a[text()='Context Menu']")).click();
			Thread.sleep(1000);
			WebElement contextElement= driver.findElement(By.xpath("//div[@id='hot-spot']"));
			Actions a= new Actions(driver);
			a.moveToElement(contextElement).contextClick().build().perform();
			Alert alert=driver.switchTo().alert();
			alert.accept();
	 }
	 
	 public static void dragAndDrop() throws InterruptedException{
		 	driver.findElement(By.xpath("//a[text()='Drag and Drop']")).click();
			
			WebElement fromElement= driver.findElement(By.xpath("//div[@id='column-a']"));
			System.out.println("First Column is: " + fromElement.getText() );
			WebElement toElement= driver.findElement(By.xpath("//div[@id='column-b']"));
			System.out.println("Second Column is: " + toElement.getText());
			Actions act = new Actions (driver);
			act.moveToElement(fromElement).clickAndHold().pause(Duration.ofSeconds(1)).dragAndDrop(fromElement,toElement).pause(Duration.ofSeconds(1)).perform();
			Thread.sleep(1000);
	 }
	 
	 public static void handleDropdown() {
		 driver.findElement(By.xpath("//a[text()='Dropdown']")).click();
			
			WebElement drpdown= driver.findElement(By.xpath("//select[@id='dropdown']"));
			
			if(drpdown.isDisplayed() && drpdown.isEnabled())		
				System.out.println("Dropdown is enabled and displayed");
			
			else
				System.out.println("Dropdown is not visible");
			
			
			Select options = new Select (drpdown);
			if (options.isMultiple())
				System.out.println("Dropdown is allowing multiple selections");
			
			else
				System.out.println("Dropdown doesn't allow multiple selections");
			
			int dropdown_size=options.getOptions().size();
			System.out.println("Dropdown size: "+dropdown_size);
			
			
			List<WebElement> values= options.getOptions();
			for (int i=0;i<dropdown_size;i++)
			{
				String list=values.get(i).getText();
				System.out.println(list + "\n");
			}
			options.selectByVisibleText("Option 1");
			System.out.println("Selected option:"+options.getFirstSelectedOption().getText());
	 }
	 
	 public static void handleDynamicControls() throws InterruptedException {
		 driver.findElement(By.xpath("//a[text()='Dynamic Controls']")).click();
			
			List<WebElement> chkbox= driver.findElements(By.xpath("//*[@id='checkbox']/input"));
			chkbox.get(0).click();
			if (chkbox.get(0).isSelected())
				System.out.println("Check box is selected");
			else 
				System.out.println("Check box is not selected");
			
			List<WebElement> remove_button= driver.findElements(By.xpath("//button[text()='Remove']"));
			remove_button.get(0).click();
			Thread.sleep(5000);
			
			WebElement msg= driver.findElement(By.xpath("//p[@id='message']"));
			String str= msg.getText();
			System.out.println(str);
			if (str.equalsIgnoreCase("It's gone!") && chkbox.size()==0)
					System.out.println("Check box has been removed successfully");
			
			
			List<WebElement> add_button= driver.findElements(By.xpath("//button[text()='Add']"));
			add_button.get(0).click();
			Thread.sleep(5000);
			WebElement msg1= driver.findElement(By.xpath("//p[@id='message']"));
			String str1= msg1.getText();
			WebElement chkbox1= driver.findElement(By.xpath("//div/input[@id='checkbox']"));
			if (str1.equalsIgnoreCase("It's back!") && chkbox1.isDisplayed() )
					System.out.println("Check box has been added successfully");
			
			
			driver.navigate().refresh();
			WebElement enableButton= driver.findElement(By.xpath("//button[text()='Enable']"));
			enableButton.click();
			Thread.sleep(1000);
			
			WebElement textBox=driver.findElement(By.xpath("//input[@type='text']"));
			WebElement disableButton= driver.findElement(By.xpath("//form[@id='input-example']/button"));
			String buttonText=disableButton.getAccessibleName();
			System.out.println("Button text: "+buttonText);
			System.out.println("Text box status: "+textBox.isEnabled());
			if (textBox.isEnabled() && buttonText.equalsIgnoreCase("Disable")) {
				System.out.println("Text box is enabled now");
				
			}
			
			
	 }
	 
	 public static void handleDynamicLoading() throws InterruptedException {
		 
		 String parent=driver.getWindowHandle();
		 driver.findElement(By.xpath("//a[@href='/dynamic_loading']")).click();
		
		 driver.findElement(By.xpath("//a[@href='/dynamic_loading/1']")).click();
		 Thread.sleep(1000);
		 System.out.println(driver.getCurrentUrl());
		 driver.findElement(By.xpath("//div[@id='start']/button[text()='Start']")).click();
		 Thread.sleep(5000);
		 WebElement dynamicText = driver.findElement(By.xpath("//div/h4[text()='Hello World!']"));
		 System.out.println("Dynamic Content that was hidden: "+ dynamicText.getText());
		 driver.navigate().back();
		 driver.findElement(By.xpath("//a[@href='/dynamic_loading/2']")).click();
		 driver.findElement(By.xpath("//div[@id='start']/button[text()='Start']")).click();
		 Thread.sleep(5000);
		 WebElement dynamicRenderedText = driver.findElement(By.xpath("//div/h4[text()='Hello World!']"));
		 System.out.println("Dynamic Content rendered: "+ dynamicRenderedText.getText());
		 driver.navigate().to("http://the-internet.herokuapp.com/");
	 }
	 
	 public static void entryAd() {
		
		 driver.findElement(By.xpath("//a[@href='/entry_ad']")).click();
		 driver.switchTo().activeElement();
		 
		 WebElement modal= driver.findElement(By.xpath("//div[@class='modal-title']/h3"));
		 System.out.println(modal.getText());
		
		 //driver.switchTo().alert().accept();
	 }
	 
	 public static void downloadFile() throws InterruptedException {
		
		 File folder = new File ("C:\\selenium\\File download");
		 folder.mkdir();
		 ChromeOptions options = new ChromeOptions ();
		 Map<String, Object> prefs = new HashMap <String, Object>();
		 prefs.put("profile.default_content_settings.popups", 0);
		 prefs.put("download.default_directory",folder.getAbsolutePath());
		 options.setExperimentalOption("prefs", prefs);
		 WebDriver driver = new ChromeDriver(options);
				 
		 driver.get("http://the-internet.herokuapp.com/download");
		 driver.findElement(By.xpath("//a[@href='download/some-file.txt']")).click();
		 Thread.sleep(2000);
		// File dir = new File ("C:\\selenium\\File download");
		 File[] files = folder.listFiles();
		 if (files.length == 0 || files == null) {
	            System.out.println("The directory is empty");
		 }
		 else {
			 for (File listFile : files) {
				 if (listFile.getName().contains("some-file.txt")) {
					 System.out.println("some-file.txt File Downloaded successfully");
				 }
			 }
		 }
		 
	 }
	 
	 public static void uploadFile() throws InterruptedException {
		 driver.navigate().to("http://the-internet.herokuapp.com/upload");
		 System.out.println(driver.getCurrentUrl());
		 WebElement fileInput= driver.findElement(By.xpath("//input[contains(@id,'file')]"));
		 fileInput.sendKeys("C:\\selenium\\File download\\some-file.txt");
		 Thread.sleep(2000);
		 driver.findElement(By.id("file-submit")).click();
		 Thread.sleep(2000);
		 
		 WebElement checkUpload = driver.findElement(By.xpath("//h3[text()='File Uploaded!']"));
		 if (checkUpload.isDisplayed())
			 	System.out.println("File uploaded successfully");
		 
	 }
}

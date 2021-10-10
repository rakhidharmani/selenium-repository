import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
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

public class Test2 {

	
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, ClientProtocolException, IOException  {
		// TODO Auto-generated method stub
		
		getUrl();
		/*formAuthentication();
		handleFrames();
		handleIFrames();
		horizontalSlider();
		hover();
		infiniteScroll();
		jQueryUIMenu();
		javaScriptAlerts();
		keyPress();
		multipleWindows();
		notificationMessage();*/
		secureFileDownload();
		
		
	}
	
	 public static void getUrl() {
		System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://the-internet.herokuapp.com/");
		driver.manage().window().maximize();
	 }
	 
	public static void formAuthentication() {
		driver.findElement(By.xpath("//a[text()='Form Authentication']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("tomsmith");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("SuperSecretPassword!");
		driver.findElement(By.xpath("//button//i[text()=' Login']")).click();
		WebElement flashMsg= driver.findElement(By.id("flash"));
		String msgText= flashMsg.getText();
		if (msgText.contains("logged in")) {
			System.out.println("Login successful!");
		}
		
		driver.findElement(By.xpath("//a[@href='/logout']//i")).click();
		String url= driver.getCurrentUrl();
		WebElement flashMsg1= driver.findElement(By.id("flash"));
		String logoutMsg=flashMsg1.getText();
		if (logoutMsg.contains("logged out") && url.equalsIgnoreCase("http://the-internet.herokuapp.com/login")) {
			System.out.println("Logout successful!");
		}
		driver.navigate().back();
	}
	
	public static void handleFrames() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Frames']")).click();
		driver.findElement(By.xpath("//a[text()='Nested Frames']")).click();
		System.out.println(driver.getCurrentUrl());
		Thread.sleep(5000);
		WebElement leftFrame= driver.findElement(By.xpath("//frame[@src='/frame_left']"));
		Actions actions=new Actions(driver);
		actions.moveToElement(leftFrame);
		actions.build();
		System.out.println("Mouse hovered to left frame!");
		
	}
	
	public static void handleIFrames() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Frames']")).click();
		driver.findElement(By.xpath("//a[text()='iFrame']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='content']/div/div/div[1]/div[1]/div[1]/button[1]/span")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(@id, 'aria-owns')]/div/div/div/div/div[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='tinymce']/p")).sendKeys("sdfsfsfsa");
		//driver.switchTo().activeElement().click();
		//Thread.sleep(1000);
		//driver.findElement(By.xpath("//*[@id='tinymce']/p")).sendKeys("dfdsfdsg");
		//driver.switchTo().activeElement().sendKeys("hhghg");
		//System.out.println(trial.getAccessibleName());
		//driver.switchTo().frame(null)
		//driver.switchTo().activeElement().sendKeys("Testing iframe");
		//WebElement body= driver.findElement(By.xpath("//*[@id='tinymce']/p"));
		//.sendKeys("Testing iframe");
		Thread.sleep(2000);
		
		
	}
	
	public static void horizontalSlider() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Horizontal Slider']")).click();
		WebElement slider=driver.findElement(By.xpath("//div[@id='content']/div/div/span"));
		Dimension sliderSize = slider.getSize();
		int sliderWidth=sliderSize.getWidth();
		//int width=slider.getSize().getWidth();
		System.out.println(slider.getAttribute("id"));
		//Point point = slider.getLocation();
		int x=slider.getLocation().getX();
		//int y=point.getY();
		//System.out.println(x + "\n" + y);
		Actions move= new Actions(driver);
		//move.dragAndDropBy(slider, ((width*25)/100), 0).build().perform();
		//move.clickAndHold(slider).moveByOffset(320, 0).release().build().perform();
		//move.click().keyUp(slider,Keys.ARROW_UP).build().perform();
		//move.moveToElement(slider).clickAndHold().moveByOffset(x+50,y).release().perform();
	    move.moveToElement(slider).click().dragAndDropBy(slider, x+sliderWidth, 0).build().perform();
		Thread.sleep(3000);
		
	}
	
	public static void hover() {
		driver.findElement(By.xpath("//a[text()='Hovers']")).click();
		WebElement Image1=driver.findElement(By.xpath("//div[@class='example']/div[1]"));
		//WebElement Image2=driver.findElement(By.xpath("//div[@class='example']/div[2]"));
		//WebElement Image3=driver.findElement(By.xpath("//div[@class='example']/div[3]"));
		Actions actions=new Actions(driver);
		actions.moveToElement(Image1).perform();
		WebElement userName= driver.findElement(By.xpath("//div[@class='example']/div[1]/div/h5"));
		String uName=userName.getText();
		System.out.println("Username for selected user is: "+uName);
		WebElement profile=driver.findElement(By.xpath("//div[@class='example']/div[1]/div/a"));
		profile.click();
		
	}
	
	public static void infiniteScroll() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Infinite Scroll']")).click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		long intialLength = (long) js.executeScript("return document.body.scrollHeight");
		while(true){
	           js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	           Thread.sleep(1000);
	           long currentLength = (long) js.executeScript("return document.body.scrollHeight");
	           if(intialLength == currentLength) {
	               break;
	           }
	           intialLength = currentLength;
	 
	       }
	}
	
	public static void jQueryUIMenu() throws InterruptedException {
		//*[@id="ui-id-2"]
		driver.findElement(By.xpath("//a[text()='JQuery UI Menus']")).click();
		driver.findElement(By.xpath("//*[@id='ui-id-2']")).click();
		driver.findElement(By.xpath("//*[@id='ui-id-4']")).click();
		driver.findElement(By.xpath("//*[@id='ui-id-8']")).click();
		Thread.sleep(3000);
		
	}
	
	public static void javaScriptAlerts() {
		driver.findElement(By.xpath("//a[text()='JavaScript Alerts']")).click();
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		Alert jsalert= driver.switchTo().alert();
		String alertText= jsalert.getText();
		System.out.println("JS Alert Text: "+alertText);
		jsalert.accept();
		String result=driver.findElement(By.xpath("//p[@id='result']")).getText();
		System.out.println(result);
		
		
		//Click for JS Prompt
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		Alert jsprompt= driver.switchTo().alert();
		String promptText= jsprompt.getText();
		System.out.println("JS Alert Text: "+promptText);
		jsprompt.sendKeys("Sample test");
		jsprompt.accept();
		String result1=driver.findElement(By.xpath("//p[@id='result']")).getText();
		System.out.println(result1);
	}
	
	public static void keyPress() {
		driver.findElement(By.xpath("//a[text()='Key Presses']")).click();
		WebElement textBox= driver.findElement(By.xpath("//input[@id='target']"));
		textBox.sendKeys(Keys.ARROW_UP);
		WebElement result = driver.findElement(By.xpath("//p[@id='result']"));
		System.out.println("result: "+result.getText());
	}
	
	public static void multipleWindows() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Multiple Windows']")).click();
		String parentWindowHandle = driver.getWindowHandle();
		//System.out.println(parentWindowHandle);
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		Set<String> s= driver.getWindowHandles();
		Iterator<String> I1=s.iterator();
		
		while(I1.hasNext())
		{
			String child_window=I1.next();
			if(!parentWindowHandle.equals(child_window))
			{
					driver.switchTo().window(child_window);
					System.out.println(driver.switchTo().window(child_window).getTitle());
					System.out.println(driver.getCurrentUrl());
			}
			
		}
		Thread.sleep(3000);
		driver.switchTo().window(parentWindowHandle);
		System.out.println(driver.getCurrentUrl());
	
	}
	
	public static void notificationMessage() {
		driver.findElement(By.xpath("//a[text()='Notification Messages']")).click();
		WebElement flashMsg= driver.findElement(By.xpath("//div[@id='flash']"));
		String msgText= flashMsg.getText();
		System.out.println(msgText);
		
		
		driver.findElement(By.xpath("//a[text()='Click here']")).click();
		WebElement flashMsg1= driver.findElement(By.xpath("//div[@id='flash']"));
		String msgText1= flashMsg1.getText();
		System.out.println(msgText1);
	}
	
	public static void secureFileDownload() {
		driver.findElement(By.xpath("//a[text()='Secure File Download']")).click();
		
	}

}

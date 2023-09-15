package com.actitime.Jenkins;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyExecution {
	static WebDriver driver;
	ExtentReports extent=new ExtentReports();
	ExtentSparkReporter spark=new ExtentSparkReporter("MyExtentReports.html");
	@BeforeClass
	public void open()
	{
		extent.attachReporter(spark);
		Reporter.log("Open",true);
		if(System.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		else if(System.getProperty("browser").equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
	}
	@BeforeMethod
	public void login() throws IOException
	{
		Reporter.log("Login",true);
		driver.get(System.getProperty("url"));
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");
		driver.findElement(By.xpath("//div[.='Login ']")).click();
		String title = driver.getTitle();
		String base64Code=captureScreenshot();
		String path=captureScreenshot(title);
		extent
		.createTest("This screenshot for failed Test Script","This Screenshot is for log level")
		.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code,title).build());
		
		extent
		.createTest("This is for pass test script","This screenshot is for Test level")
		.addScreenCaptureFromPath(path,title);
		
	}
	@AfterMethod
	public void logout()
	{
		Reporter.log("Logout",true);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.id("logoutLink")).click();
	}
	@AfterClass
	public void close() throws IOException
	{
		Reporter.log("Close",true);
		extent.flush();
		Desktop.getDesktop().browse(new File("MyExtentReports.html").toURI());
	}
public static String captureScreenshot()
{
	TakesScreenshot t=(TakesScreenshot)driver;
	String base64Code = t.getScreenshotAs(OutputType.BASE64);
	return base64Code;
}
public static String captureScreenshot(String filename) throws IOException
{
	TakesScreenshot t=(TakesScreenshot)driver;
	File src = t.getScreenshotAs(OutputType.FILE);
	File f=new File("./Screenshot/"+filename+".png");
	FileUtils.copyFile(src, f);
	return f.getAbsolutePath();
			
}
}

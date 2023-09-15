package com.actitime.Jenkins;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.github.dockerjava.api.model.Info;

public class TestScripts extends MyExecution{

@Test
public void create()
{
	Reporter.log("Create",true);
	extent
	.createTest("This is a login testscript")
	.assignAuthor("Ricku")
	.assignCategory("Regression Test")
	.assignDevice("HP")
	.log(Status.PASS, "It allowed all credentials")
	.pass("It can execute very smoothly");
	
			
}
@Test
public void modify()
{
	Reporter.log("Modify",true);
	extent
	.createTest("This is a logout testscript")
	.assignAuthor("Ricku")
	.assignCategory("Regression Test")
	.assignDevice("HP")
	.log(Status.FAIL, "It didn't allow all credentials")
	.fail("It cann't execute very smoothly")
	.info("This is a info msg")
	.warning("Password alerts displaying");
}
}

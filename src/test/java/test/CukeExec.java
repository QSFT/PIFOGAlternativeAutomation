package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import config.TestContext;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import selenium.SeleniumUtil;


@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/resources/features/PI",
		glue= {"stepDefinitions"},
		monochrome = false,
		plugin ={ "com.cucumber.listener.ExtentCucumberFormatter:" ,"pretty"}
)
public class CukeExec {

	@BeforeClass
	 public static void before() {
		Date startTime = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		TestContext.setTestStartTime(df.format(startTime).toString());
		ReportManager.init(df.format(startTime).toString());
	 }

	@AfterClass
	public static void after() {
		ReportManager.generateReport();
		SeleniumUtil.quitBrowser();
	}

}

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
import stepDefinitions.pitest.fms.FoglightRestSubmissionClient;


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

		getFMSAccessToken();
	 }

	@AfterClass
	public static void after() {
		ReportManager.generateReport();
		SeleniumUtil.quitBrowser();
	}

	private static void getFMSAccessToken() {
		FoglightRestSubmissionClient client = FoglightRestSubmissionClient.getInstance();
		String token;
		try {
			token = client.getAccessToken("foglight", "foglight");
			//Reporter.addStepLog("FMS access token: "+token);
			TestContext.setProperty("foglightAccessToken", token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

package stepDefinitions.pitest;

import java.util.Date;

import com.cucumber.listener.Reporter;

import config.TestContext;
import cucumber.api.java.en.Given;
import cucumber.runtime.java.StepDefAnnotation;
import fms.FMSUtil;
import stepDefinitions.StepDefinition;
import stepDefinitions.pitest.fms.FoglightRestSubmissionClient;
import wcf.Element;
import wcf.Link;
import wcf.Text;
@StepDefAnnotation
public class PITest  extends StepDefinition{


	@Given("^on SQL PI page, click (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?'(.+)' demension$")
	public void clickItemInTopBar(String indexStr, String name) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);

		//String containerLocator = "//div[@id='w1196174023_componentBody' and @class='componentBody']";
		String containerLocator = "";
		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		Date startTime = new Date();
		Link link = new Link(name, containerLocator, index, "contains(@onclick,'system:dbwc_mssql_realtime_performance.1/0/current/1/1/current/current/5/current/5/current/2')");
		if(!link.isShown()) {
			throw new Exception("Can't find link '"+name+"' in top bar");
		}
		link.click();
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		Date endTime = new Date();
		long duration = endTime.getTime() - startTime.getTime();
		this.submitRepsoneTime(startTime.getTime(),duration);
		Reporter.addStepLog("duration: "+duration+"ms");
	}

	@Given("^on SQL PI page, change dimension to '(.+)'$")
	public void changeDim(String name) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);

		//String containerLocator = "//div[@id='w1196174023_componentBody' and @class='componentBody']";
		String containerLocator = "";
		int index = 1;
		//Date startTime = new Date();
		Link link = new Link(name, containerLocator, index, "contains(@onclick,'system:dbwc_mssql_realtime_performance.1/0/current/1/1/current/current/5/current/5/current/2')");
		if(!link.isShown()) {
			throw new Exception("Can't find link '"+name+"' in top bar");
		}
		link.click();
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		//Date endTime = new Date();
		//long duration = endTime.getTime() - startTime.getTime();
		//this.submitRepsoneTime(startTime.getTime(),duration);
		//Reporter.addStepLog("duration: "+duration+"ms");
	}



	@Given("^on SQL PI page, click (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?'(.+)' node in Performance Tree$")
	public void clickItemInPerfTree(String indexStr, String name) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);

		String containerLocator = "(//div[@class='portalMargin' and div/span[contains(.,'Performance Tree')]])[1]//descendant-or-self::table[@class='wcfTableInnerCell']/tbody/tr/td";

		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		Date startTime = new Date();
		Text test = new Text(name, containerLocator, index);
		if(!test.isShown()) {
			throw new Exception("Can't find node '"+name+"' in Performance Tree");
		}
		test.click();
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		Date endTime = new Date();
		long duration = endTime.getTime() - startTime.getTime();
		this.submitRepsoneTime(startTime.getTime(),duration);
		Reporter.addStepLog("duration: "+duration+"ms");
		TestContext.setProperty("currentPIView", name);
	}

	@Given("^on SQL PI page, change time range to last (1h|4h|8h|24h|48h|72h|7d|All)$")
	public void changeTimeRange(String timeRange) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		Text test = new Text("Failed to retrieve data from SQL PI repository. If the issue persists, please contact Quest Software support.", "", 1);
		if(test.isShown()) {
			throw new Exception("Failed to retrieve data from SQL PI repository.");
		}
		Element timeRangeMenu = new Element("(//span[@id='timeRangeMenu'])[1]//span[@class='button']");
		if(timeRangeMenu.isShown()) {
			timeRangeMenu.click();
			FMSUtil.waitForPageLoad(pageLoadTimeout);
		}
		Element timeLineButton = new Element("(//div[@class='panel popup']/div/div[@class='selector']//a[contains(text(),'Timeline')])[1]");
		if(timeLineButton.isShown()) {
			timeLineButton.click();
			FMSUtil.waitForPageLoad(pageLoadTimeout);
		}
		Date startTime = new Date();
		Element lasr = new Element("(//div[@class='panel popup']/div[@class='quickpicks']//a[contains(text(),'"+timeRange+"')])[1]");
		lasr.click();
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		Date endTime = new Date();
		long duration = endTime.getTime() - startTime.getTime();
		TestContext.setProperty("timeline", timeRange);
		this.submitRepsoneTime(startTime.getTime(),duration);
		Reporter.addStepLog("duration: "+duration+"ms");
	}

	@Given("^set AlternativeInstancePerformance '(.+)'$")
	public void setAlternativeInstancePerformance(String name) throws Throwable {
		TestContext.setProperty("AlternativeInstancePerformance", name);
	}

	@Given("^get FMS access token$")
	public void getFMSAccessToken() throws Throwable {
		FoglightRestSubmissionClient client = FoglightRestSubmissionClient.getInstance();
		String token = client.getAccessToken("foglight", "foglight");
		Reporter.addStepLog("FMS access token: "+token);
		TestContext.setProperty("foglightAccessToken", token);
	}


	private String getCurrentDemension() {
		Element demen =  new Element("(//div[contains(@style,'selectMiddle.png')])[1]/a//span");
		return demen.getElementText();

	}

	private String getCurrentView() {
//		Element view =  new Element("(//div[@class='portalMargin' and div/span[contains(.,'Performance Tree')]])[1]//tr[contains(@class,'wcf-table-body-row tableViewComponentSelectedRow selectedRow') or contains(@class,'wcf-table-body-row selectedRow tableViewComponentSelectedRow')]//descendant-or-self::table[@class='wcfTableInnerCell']/tbody/tr/td//span[2]");
		Element view =  new Element();
		view.setLocator("//tr[contains(@class,'wcf-table-body-row tableViewComponentSelectedRow selectedRow') or contains(@class,'wcf-table-body-row selectedRow tableViewComponentSelectedRow')]//descendant-or-self::table[@class='wcfTableInnerCell']/tbody/tr/td//span[2]");
		if(!view.isShown()) {
			if(TestContext.getProperty("currentPIView")!=null) {
				return TestContext.getProperty("currentPIView");
			}
		}
		return view.getElementText();
	}

	private void submitRepsoneTime(long startTime, long responseTime) {
		FoglightRestSubmissionClient client = FoglightRestSubmissionClient.getInstance();
		String timeline = TestContext.getProperty("timeline");
		if(timeline==null||timeline.length()==0) {
			timeline = "1h";
		}
		client.submitSubmissionUsageData2Foglight(TestContext.getProperty("AlternativeInstancePerformance"), startTime, timeline, this.getCurrentDemension(), this.getCurrentView(), responseTime);
	}

}

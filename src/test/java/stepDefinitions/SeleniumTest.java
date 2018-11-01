package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cucumber.listener.Reporter;
import com.google.common.io.Files;

import config.TestContext;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.runtime.java.StepDefAnnotation;
import fms.FMSUtil;
import selenium.SeleniumUtil;
import wcf.CheckBox;
import wcf.Collapsible;
import wcf.ComponentUtil;
import wcf.Dashboard;
import wcf.Drawer;
import wcf.Element;
import wcf.Input;
import wcf.Select;
import wcf.Tree;
import wcf.TreeWcfTable;
@StepDefAnnotation
public class SeleniumTest  extends StepDefinition{

	@Before(order = 1)
	public void beforeScenario(Scenario scenario) {

		//SeleniumUtil.openBrowser();
	}

	@After(order = 1)
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()&&SeleniumUtil.isBrowserOpened()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			try {
				//This takes a screenshot from the driver at save it to the specified location
				File sourcePath = SeleniumUtil.getScreenshotAsFile();

				File screenshotDir=new File(System.getProperty("user.dir") + "/target/cucumber-reports/"+TestContext.getTestStartTime()+"/screenshots/");
				if(!screenshotDir.exists()){
					screenshotDir.mkdir();
				}
				//Building up the destination path for the screenshot to save
				//Also make sure to create a folder 'screenshots' with in the cucumber-report folder
				File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/"+TestContext.getTestStartTime()+"/screenshots/" + screenshotName + ".png");

				//Copy taken screenshot from source location to destination location
				Files.copy(sourcePath, destinationPath);

				//This attach the specified screenshot to the test
				Reporter.addScreenCaptureFromPath(destinationPath.toString());
			} catch (IOException e) {
			}
			finally {
				//SeleniumUtil.closeBrowser();
			}
		}

	}

	@Given("open browser")
	public void openBrowser() {
		SeleniumUtil.openBrowser();
		//Reporter.addStepLog("Open firefox");

	}

	@Given("^navigate to '(.*)'$")
	public void goToPage(String url) throws Throwable {
		SeleniumUtil.openPage(url);
		FMSUtil.waitForPageLoad(pageLoadTimeout);
	}

	@Given("^(?:(?:a|an|the) )?(?:'(.*?)' )?(.+) should( not)? show up(?: in (\\d+) seconds?)?(?: (?:in|on) (?:the )?(.+))?$")
	public void verifyIfViewExists(String name1, String type, String shouldnot, String time, String scope) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);
		int timeout = 30;
		if(time!=null) {
			timeout = Integer.parseInt(time);
		}
		if(name1==null) {
			name1="";
		}
		String eType = type.toLowerCase();
		eType = ComponentUtil.findComponentClassName(eType);
		if(eType==null) {
			throw new Exception("Wrong view type: "+type);
		}
		else {
			Element element = ComponentUtil.getComponent(name1, eType, containerLocator);
			boolean isShown = false;
			if(time!=null) {
				isShown = element.isShown(timeout);
			}
			else isShown = element.isShown();
			if(!isShown) {
				if(shouldnot==null)
				throw new Exception(name1+" "+type+" didn't show up");
			}
			else {
				if(shouldnot!=null)
					throw new Exception(name1+" "+type+" shown up while it is not supposed to show up");
			}
		}
	}



	@Given("^click (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?'(.+)' (button|link|tile|inventory|radio|tab|radiobox|icon|message button|checkbox|action|calendar|drawer|text|dropdownfield)(?: (?:in|on) (?:the )?(.+))?$")
	public void clickItem(String indexStr, String name, String type, String scope) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);
		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		ComponentUtil.clickComponent(name, type, containerLocator, index);
	}


	@Given("^type '(.*)' into (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?(?:'(.+)' )?text field(?: (?:in|on) (?:the )?(.+))?$")
	public void setTextFieldVal(String value, String indexStr, String name, String scope) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);;
		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		Input input = new Input(name, containerLocator, index);
		input.setText(value);
	}

	@Given("^select '(.*)' in (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?(?:'(.+)' )?dropdownlist(?: (?:in|on) (?:the )?(.+))?$")
	public void selectItem(String value, String indexStr, String name, String scope) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);;
		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		Select select = new Select(name, containerLocator, index);
		select.setvalue(value);
	}

	@Given("^(check|uncheck) (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?checkbox (before|after)? '(.+)'(?: (?:in|on) (?:the )?(.+))?$")
	public void checkBoxOpt(String action, String indexStr, String location, String label, String scope) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);
		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		CheckBox cb = new CheckBox(location, label, containerLocator, index);
		if(!cb.isShown()) {
			throw new Exception("checkbox "+location+"'"+label+"' didn't shown up on the page");
		}

		if(action.equals("check")) {
			cb.check();
		}
		else {
			cb.uncheck();
		}
	}

	@Given("^open '(.+)' dashboard$")
	public void openScreen(String screen) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		if(FMSUtil.isLogin()) {
			Drawer drawer = new Drawer("west");
			drawer.expand();
			if(!screen.equals("Login")&&!screen.equals(Dashboard.getHeader())) {
				List<String> screens = new ArrayList<String>(Arrays.asList(screen.split("->")));
					Collapsible collapsible = new Collapsible(screens.remove(0).trim());
					collapsible.expand();
					Tree tree = (Tree) collapsible.getElement("Tree");
					tree.navigate(screens);
				FMSUtil.waitForPageLoad(pageLoadTimeout);
			}
		}
		else {
			throw new Exception("Please login FMS first");
		}
	}

	@Given("^wait (\\d+) seconds$")
	public void waitSeconds(int sec) throws Throwable {
		Thread.sleep(sec*1000);
	}

	@Given("^login FMS$")
	public void loginFms() throws Throwable {
		Element userbox = new Element("//*[@id='user']");
		Element pwdbox = new Element("//*[@id='password']");
		userbox.clear();
		userbox.setValue("foglight");
		pwdbox.clear();
		pwdbox.setValue("foglight");
		Element submitButton = new Element("//*[@id='login']");
		submitButton.click();
	}

	@Given("^change time range to last (1h|4h|8h|24h|48h|72h|7d|All)$")
	public void changeTimeRange(String timeRange) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
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
		Element lasr = new Element("(//div[@class='panel popup']/div[@class='quickpicks']//a[contains(text(),'"+timeRange+"')])[1]");
		lasr.click();
		FMSUtil.waitForPageLoad(pageLoadTimeout);
	}

	@Given("^expand '(.+)' tree table '(.+)'$")
	public void expendTreeGridTable(String treeTableName, String node) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
				List<String> nodes = new ArrayList<String>(Arrays.asList(node.split("->")));
				TreeWcfTable tree = new TreeWcfTable(treeTableName,"");
				tree.navigate(nodes);
				FMSUtil.waitForPageLoad(pageLoadTimeout);

	}

	@Given("^collapse '(.+)' tree table '(.+)'$")
	public void collapseTreeGridTable(String treeTableName, String node) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
				List<String> nodes = new ArrayList<String>(Arrays.asList(node.split("->")));
				TreeWcfTable tree = new TreeWcfTable(treeTableName,"");
				tree.collapse(nodes);
				FMSUtil.waitForPageLoad(pageLoadTimeout);

	}

	@Given("^click node '(.+)' (?:in|on) '(.+)' tree table$")
	public void clickNodeTreeGridTable(String node, String treeTableName) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
				List<String> nodes = new ArrayList<String>(Arrays.asList(node.split("->")));
				TreeWcfTable tree = new TreeWcfTable(treeTableName,"");
				tree.clickElement(nodes);
				FMSUtil.waitForPageLoad(pageLoadTimeout);

	}

	@Given("close browser")
	public void closeBrowser() {
		//SeleniumUtil.closeBrowser();
		SeleniumUtil.quitBrowser();
		//Reporter.addStepLog("Close firefox");
	}

	@Given("refresh browser")
	public void refreshBrowser() {
		//SeleniumUtil.closeBrowser();
		SeleniumUtil.refreshBrowser();
		//Reporter.addStepLog("Close firefox");
	}

}

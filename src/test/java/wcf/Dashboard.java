/**
 *
 */
package wcf;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class Dashboard extends Container {

	/**
	 * @param loc
	 */
	public Dashboard(String header, String under_locator, int index) {
		super(under_locator+"//div[@id='pageContents' and //*[@id='pageHeader']//*[@class='history']//span[contains(.,'"+header+"')]]["+(index>1?index:1)+"]");
		// TODO Auto-generated constructor stub
	}

	public Dashboard(String header, String under_locator) {
		super(under_locator+"//div[@id='pageContents' and //*[@id='pageHeader']//*[@class='history']//span[contains(.,'"+header+"')]][1]");
		// TODO Auto-generated constructor stub
	}

	public Dashboard(String header, int index) {
		super("//div[@id='pageContents' and //*[@id='pageHeader']//*[@class='history']//span[contains(.,'"+header+"')]]["+(index>1?index:1)+"]");
		// TODO Auto-generated constructor stub
	}


	public static String getHeader() {
		String header_path = "//*[@id='pageHeader']//*[@class='history']//span[text()]";
		if(SeleniumUtil.checkIfElementExistsByXpath(header_path)) {
			return SeleniumUtil.findElementByXpath(header_path).getText();
		}
		else return "";
	}

	public static boolean verifyHeader(String screen) {
		return SeleniumUtil.checkIfElementExistsByXpath("//*[@id='pageHeader']//*[@class='history']//span[contains(.,'"+screen+"')]");
	}

	public String getTitle() {
		return SeleniumUtil.findElementByXpath(this.getLocator()+"//div[contains(@class,'pageTitle')]").getText();
	}
}

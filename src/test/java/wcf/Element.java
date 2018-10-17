/**
 *
 */
package wcf;

import org.openqa.selenium.WebElement;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class Element{

	private String locator;

	public Element() {

	}

	public Element(String loc) {
		String anti_hidden_xpath = "[count(ancestor-or-self::*[not(contains(@style,'display: none'))" +
	            " and not(contains(@style,'visibility: hidden'))])=count(ancestor-or-self::*)]";
		locator = loc.replace(anti_hidden_xpath, "")+anti_hidden_xpath;
	}

	public void setLocator(String loc) {
		locator = loc;;
	}

	public String getLocator() {
		return locator;
	}

	public void click() {
		SeleniumUtil.clickElementByXpath(locator);
	}

	public void clear() {
		SeleniumUtil.clearElementByXpath(locator);
	}

	public void setValue(String val) {
		SeleniumUtil.sendKeysByXpath(locator, val);
	}

	public String getAttribute(String name) {
		return SeleniumUtil.findElementByXpath(locator).getAttribute(name);
	}

	public boolean isShown() {
		boolean isShown = false;
		isShown = SeleniumUtil.checkIfElementExistsByXpath(getLocator());
		return isShown;
	}

	public boolean isSelected() {
		boolean isSelected = false;
		isSelected = SeleniumUtil.findElementByXpath(locator).isSelected();
		return isSelected;
	}

	public WebElement getElement() {
		return SeleniumUtil.findElementByXpath(locator);
	}

	public String getElementText() {
		return SeleniumUtil.findElementByXpath(locator).getText();
	}

	public boolean isShown(int timeout) {
		boolean isShown = false;
		if(SeleniumUtil.findElementByXpathWithTimeout(getLocator(), timeout)!=null) {
			isShown = true;
		}
		return isShown;
	}
}

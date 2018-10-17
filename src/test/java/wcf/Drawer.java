/**
 *
 */
package wcf;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class Drawer extends Container {

	/**
	 * @param loc
	 */

	public Drawer(String direction) {
		super("//div[contains(@class,'collapsibleWidget') and contains(@class,'"+direction+"')]");

	}
	public Drawer(String direction, String under_locator) {
		super(under_locator+"//div[contains(@class,'collapsibleWidget') and contains(@class,'"+direction+"')]");

	}

	public boolean isExpanded() {
		return !SeleniumUtil.checkIfElementExistsByXpath(this.getLocator()+"[contains(@class,'collapsed clickable')]");
	}

	public void expand() {
		if(!isExpanded()) {
			toggle();
		}
	}

	public void collapse() {
		if(isExpanded()) {
			toggle();
		}
	}


	public void toggle() {
		SeleniumUtil.findElementByXpath(this.getLocator()).click();
	}
}

/**
 *
 */
package wcf;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class Collapsible extends Container {

	/**
	 * @param loc
	 */
	public Collapsible(String name) {
		super("//div[div[@class='collapsibleHeader' and label[text()='"+name+"']]]");
		// TODO Auto-generated constructor stub
	}

	public boolean isExpanded() {
		return SeleniumUtil.checkIfElementExistsByXpath(this.getLocator()+"/div[@class='collapsibleBody' and not(contains(@style,'display: none'))]");
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
		SeleniumUtil.findElementByXpath(this.getLocator()+"/div[@class='collapsibleHeader']/img").click();
	}

}

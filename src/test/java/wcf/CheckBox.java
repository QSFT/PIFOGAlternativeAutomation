/**
 *
 */
package wcf;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class CheckBox extends Element {

	public CheckBox() {

	}

	public CheckBox(String name, int index) {
		index = index>1 ? index:1;
		setLocator("(//label|//span)[contains(.,'"+name+"')]/input[@type='checkbox']["+index+"]");
		if(!isShown()) {
			setLocator("(//label|//span)[contains(.,'"+name+"')]/following::input[@type='checkbox']["+index+"]");
		}
	}

	public CheckBox(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		setLocator(under_locator+"(//label|//span)[contains(.,'"+name+"')]/input[@type='checkbox']["+index+"]");
	}

	public CheckBox(String name, String under_locator) {
		this(name,under_locator,1);
	}

	public CheckBox(String location, String reference, String under_locator, int index) {
		String label = reference;
		if(location.equals("before")) {
			setLocator("("+under_locator+"//*[contains(text(), '"+label+"')]/preceding::input[@type='checkbox']|//*[contains(text(),'"+label+"')]/child::input[@type='checkbox'])[last()]");
			if(!isShown()) {
				setLocator(under_locator+"//*[contains(text(), '"+label+"')]/preceding::input[@type='checkbox']|//*[contains(text(),'"+label+"')]/child::input[@type='checkbox']["+index+"]");
			}
		}else {
			setLocator(under_locator+"//*[contains(text(), '"+label+"')]/descendant::input[@type='checkbox']["+index+"]");
			if(!isShown()) {
				setLocator(under_locator+"//*[contains(text(), '"+label+"')]/following::input[@type='checkbox']["+index+"]");
			}
		}
	}

	public CheckBox(String locator) {
		super(locator);
	}

	public boolean isChecked() {
		return this.isSelected();
	}

	public void uncheck() {
		if(isChecked()) {
			toggle(null);
		}
	}

	public void check() {
		if(!isChecked()) {
			toggle(null);
		}
	}


	public void toggle(String val) {
		if(val == null) {
			SeleniumUtil.findElementByXpath(getLocator()).click();
			return;
		}
		if(val.equalsIgnoreCase("on")||val.equalsIgnoreCase("check")) {
			check();
		}else if(val.equalsIgnoreCase("off")||val.equalsIgnoreCase("uncheck")){
			uncheck();
		}
	}

}

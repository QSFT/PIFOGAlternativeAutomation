/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Popup extends Container {
	public Popup(String title, String under_locator, int index) {
		String indexStr;
		if(index==0) {
			indexStr="last()";
		}else {
			indexStr=index+"";
		}

		if(title.equals("")||title.equals("[unnamed]")) {
			setLocator(under_locator+"//*[(@id='popup' or @class='popup')]["+indexStr+"]");
		}
		else{
			title = ComponentUtil.generateConcatForXpath(title);
			setLocator(under_locator+"//*[(@id='popup' or @class='popup') and contains(.,"+title+")]["+indexStr+"]");
		}

		// TODO Auto-generated constructor stub
	}

	public Popup(String title, String under_locator) {
		this(title,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Popup(String title, int index) {
		this(title,"",index);
		// TODO Auto-generated constructor stub
	}

	public Popup(String title) {
		this(title,"",0);
		// TODO Auto-generated constructor stub
	}

	public String getPopupName() {
		if(this.isShown()) {
			Element e = new Element("//*[@id='popup' or @class='popup']");
			return e.getElementText();
		}else {
			return null;
		}
	}

	public void select(String option) {
		String loc = getLocator()+"//li[text()='"+option+"']";
		Element e = new Element(loc);
		if(!e.isShown()) {
			loc = getLocator()+"//li/a[text()='"+option+"'] | "+getLocator()+"//*[text()='"+option+"' and (self::div or self::span)]";
			e.setLocator(loc);
			e.click();
		}
		else {
			e.click();
		}
	}
}

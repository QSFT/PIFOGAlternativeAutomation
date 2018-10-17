/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Dialog extends Container {
	public Dialog(String title, String under_locator, int index) {
		index = index>1 ? index:1;
		setLocator(under_locator+
				"//div[@id='prmPopupContainer']/div[contains(@class,'dialog') and div[@class='header']/div[@class='title' and (.//text()="+
				ComponentUtil.generateConcatForXpath(title)+
				" or contains(.,"+ComponentUtil.generateConcatForXpath(title)+
				") or .//*[contains(text,"+ComponentUtil.generateConcatForXpath(title)+
				")])]]["+index+"]");
		// TODO Auto-generated constructor stub
	}

	public Dialog(String title, String under_locator) {
		this(title,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Dialog(String title, int index) {
		this(title,"",index);
		// TODO Auto-generated constructor stub
	}

	public void clickControlButton(String title) {
		Element e = new Element(this.getLocator()+"//div[@class='controls']/img[@title="+ComponentUtil.generateConcatForXpath(title)+"]");
		if(e.isShown()) {
			e.click();
		}else {
			throw new RuntimeException("Button with '"+title+"' title was not found. Usual buttons is: Close, Maximize, Restore.");
		}
	}
}

/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class PopupMenu extends Element {
	public PopupMenu(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		setLocator(under_locator+"//descendant-or-self::ul[contains(@class,'popupMenu') or contains(@class,'PopupMenu')]["+index+"]");

		// TODO Auto-generated constructor stub
	}

	public PopupMenu(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public PopupMenu(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}

	public void select(String option) {
		String loc = getLocator()+"//li[text()='"+option+"']";
		Element e = new Element(loc);
		e.click();
	}
}

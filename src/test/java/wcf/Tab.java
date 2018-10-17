/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Tab extends Container {
	public Tab(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		if(name.matches("^\\[fuzzy\\](.*)$")) {
			name = name.replace("[fuzzy]", "");
			setLocator("//div[contains(@class, 'tab')]/descendant-or-self::*[(@href='#' or @class='tabContent') and contains(text(), '"+name+"')]["+index+"]");
		}
		else{
			setLocator("//div[contains(@class, 'tab')]/descendant-or-self::*[(@href='#' or @class='tabContent') and text()='"+name+"']["+index+"]");
		}
		// TODO Auto-generated constructor stub
	}

	public Tab(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Tab(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}

	public void active() {
		this.click();
	}
}

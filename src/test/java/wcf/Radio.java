/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Radio extends Element {
	public Radio(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		if(name ==null||name.equals("")) {
			setLocator("("+under_locator+"//input[@type='radio']|"+under_locator+"//div[./a/img[contains(@src,'UnselectedRB.png') or contains(@src,'SelectedRB.png')]])["+index+"]");
		}else {
			setLocator("("+under_locator+"//input[@type='radio' and following-sibling::*[contains(text(),'"+name+"')]]|"+under_locator+"//div[./a/img[contains(@src,'UnselectedRB.png') or contains(@src,'SelectedRB.png')] and following-sibling::*[1][contains(.,'"+name+"')]])["+index+"]");
		}

		// TODO Auto-generated constructor stub
	}

	public Radio(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Radio(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}
}

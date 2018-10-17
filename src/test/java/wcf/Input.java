/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Input extends Element {
	public Input(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		String align = "left";
		if(name.equals("")||name.equals("[unnamed]")) {
			setLocator(under_locator+"//descendant-or-self::input[@type='text' or @type='password']["+index+"]");
		}
		else{

			if(name.matches("^\\[right\\](?:.*)|(?:.*)\\[right\\]$")) {
				name = name.replace("[right]", "");
				align = "right";
			}
			Element e =  new Element(under_locator+"//*[contains(text(), '"+name+"')]//input[1]");
			if(e.isShown()) {
				setLocator(under_locator+"//*[contains(text(), '"+name+"')]//input[1]");
			}else {
				if(align.equals("left")) {
					setLocator(under_locator+"//*[contains(text(), '"+name+"')]/following::input[1]");
				}else {
					setLocator(under_locator+"//*[contains(text(), '"+name+"')]/preceding::input[1]");
				}
			}
		}

		// TODO Auto-generated constructor stub
	}

	public Input(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Input(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}

	public void clearValue() {
		clear();
	}

	public void setText(String text) {
		clear();
		setValue(text);
	}
}

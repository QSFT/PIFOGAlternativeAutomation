/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Text extends Element {
	public Text(String text, String under_locator) {
		super(under_locator+"//*[contains(.,'"+text+"')]");
	}

	public Text(String text, String under_locator, int index) {
		super("("+under_locator+"//*[contains(.,'"+text+"')])["+index+"]");
	}
}

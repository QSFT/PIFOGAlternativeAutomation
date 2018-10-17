/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Icon extends Element {
	public Icon(String name, String under_locator) {
		this( name,  under_locator, 1);
	}

	public Icon(String name, String under_locator, int index) {
		//super(under_locator+"//descendant-or-self::*[img[contains(@src, '"+name+"')]]["+index+"]/img");
		super("("+under_locator+"//img[contains(@src, '"+name+"')])["+index+"]");
	}
}

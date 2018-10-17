/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class TreeElement extends Element {

	/**
	 * @param elementLocator
	 */
	public TreeElement(String loc) {
		super(loc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void click() {
		Element e = new Element(getLocator()+"/span[@class]");
		e.click();
	}

}

/**
 *
 */
package wcf;

import java.lang.reflect.InvocationTargetException;

/**
 * @author schen3
 *
 */
public class Container extends Element {

	/**
	 * @param loc
	 */
	public Container(String loc) {
		super(loc);
		// TODO Auto-generated constructor stub
	}

	public Container() {

		// TODO Auto-generated constructor stub
	}

	public Element getElement(String type, String title) {
		Element element = null;
		try {
			Class c = Class.forName("wcf."+type);
			Class[] cArg = new Class[] {String.class,String.class};
			element = (Element) c.getDeclaredConstructor(cArg).newInstance(title, this.getLocator());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return element;
	}

	public Element getElement(String type) {
		return getElement(type, "");
	}
}

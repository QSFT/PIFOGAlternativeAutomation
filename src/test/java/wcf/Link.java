/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Link extends Element {
	public Link(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		String condition = "[(contains(.,'"+name+"') or span[.='"+name+"']) and contains(@onclick,'return')])["+index+"]";
		//String condition = "[(text()='"+name+"' or span[.='"+name+"']) and contains(@onclick,'return')]["+index+"]";
		setLocator("("+under_locator+"//descendant-or-self::a"+condition);
		if(!isShown()) {
			setLocator("("+under_locator+"//descendant-or-self::li"+condition);
		}
		// TODO Auto-generated constructor stub
	}

	public Link(String name, String under_locator, int index, String extraCondition) {
		index = index>1 ? index:1;
		String condition = "[(contains(.,'"+name+"') or span[.='"+name+"']) and contains(@onclick,'return') and "+extraCondition+"])["+index+"]";
		//String condition = "[(text()='"+name+"' or span[.='"+name+"']) and contains(@onclick,'return')]["+index+"]";
		setLocator("("+under_locator+"//descendant-or-self::a"+condition);
		if(!isShown()) {
			setLocator("("+under_locator+"//descendant-or-self::li"+condition);
		}
		// TODO Auto-generated constructor stub
	}

	public Link(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Link(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}
}

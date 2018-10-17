/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Button extends Element {

	public Button(String name, String under_locator, int index) {
		if(index<1) {
			index = 1;
		}
		String additional_conditions = "";
		if(name.contains("[enabled]")){
			additional_conditions = "[not(@disabled) and not(contains(@class, 'disabled'))]";
		}else if(name.contains("[disabled]")) {
			additional_conditions = "[@disabled or contains(@class, 'disabled')]";
		}
		name = name.replaceAll("\\[(disabled|enabled)\\]", "").trim();
		String loc = "("+under_locator+"//descendant-or-self::button[@value='"+name+"' or " +
	            ".//text()='"+name+"' or .//*[contains(text(),'"+name+"')]] | " +
	            under_locator+"//descendant-or-self::*[(@type='button' or @type='submit' or " +
	            "(contains(@class,'button') and @class!='buttons')) " +
	            "and (@value='"+name+"' or .//text()='"+name+"' or .//*[contains(text(),'"+name+"')])])" +
	            "["+index+"]"+additional_conditions;
//		String loc = under_locator+"//*[(@type='button' or @type='submit' or contains(@class,'button')) and (@value='"+name+"' or .//text()='"+name+"')]"+additional_conditions+"["+index+"]";

		setLocator(loc);
	}

	public Button(String name, String under_locator) {
		this(name,under_locator,1);
	}

	public Button(String name, int index) {
		this(name,"",index);
	}
}

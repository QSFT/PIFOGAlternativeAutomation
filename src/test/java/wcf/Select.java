/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Select extends Element {

	public Select(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		if(name==null||name.equals("")||name.equals("[unnamed]")) {
			setLocator(under_locator+"//descendant-or-self::select["+index+"]");
		}
		else{
			String additional_conditions = "";
			if(name.contains("[enabled]")) {
				additional_conditions = "[not(@disabled)]";
			}else if(name.contains("[disabled]")) {
				additional_conditions = "[@disabled]";
			}
			name = name.replaceAll("^\\[(disabled|enabled)\\]$", "").trim();
			setLocator(under_locator+"//descendant-or-self::select[ancestor::*[normalize-space(text())='"+name+"'] or preceding::*[text()][1][normalize-space(text())='"+name+"'] or preceding::*[text()][2][normalize-space(text())='"+name+"']]"+additional_conditions);
			if(!isShown()) {
				setLocator(under_locator+"//descendant-or-self::select[ancestor::*[contains(normalize-space(text()),'"+name+"')] or preceding::*[text()][1][contains(normalize-space(text()),'"+name+"')] or preceding::*[text()][2][normalize-space(text())='"+name+"']]"+additional_conditions);
			}
		}
		// TODO Auto-generated constructor stub
	}

	public Select(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Select(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}

	public Select(String loc) {
		super(loc);
	}

	public void setvalue(String value) {
		Element e = new Element(getLocator()+"/option[text()='"+value+"'] | "+getLocator()+"/optgroup/option[text()='"+value+"']");
		//this.getElement().findElements(By.tagName("option"));
		e.click();
	}

	public boolean containVaule(String value) {
		Element e = new Element(getLocator()+"/option[text()='"+value+"']");
		return e.isShown();
	}

}

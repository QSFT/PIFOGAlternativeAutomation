/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class GridWcfTableRow extends Element {

	public GridWcfTableRow(String loc) {
		super(loc);
	}

	public void check() {
		getCheckBox().check();
	}

	public CheckBox getCheckBox() {
		CheckBox cb = new CheckBox();
		cb.setLocator(getLocator()+"//input[@type='checkbox']");
		return cb;
	}

	public void clickCell(String title) {
		Element e = new Element();
		if(title.equals("")||title.equals("[unnamed]")) {
			e.setLocator(getLocator()+"/td/div[@column-id=preceding::thead[1]/tr/th/div[.//text()='"+title+"']/@column-id]//*[not(*)][last()]");

		}else {
			e.setLocator(getLocator()+"/td//*[not(*)][last()]");
		}
		e.click();
	}

	public void clickCellByColumnIndex(int index) {
		Element e = new Element(getLocator()+"/td[@column-index='"+(index-1)+"']//*[not(*)][last()]");
		e.click();
	}

	public void clickRadio() {
		Element e = new Element(getLocator()+"//input[@type='radio']");
		e.click();
	}

	public void clickAlarm(String alarm) {
		Element e = new Element(getLocator()+"//td/div[contains(@column-id, '"+alarm+"')]/a");
		e.click();
	}

	public void clickIcon() {

	}

	public String getCellValueByTitle(String title) {
		Element e = new Element(getLocator()+"/td[div[@column-id=preceding::thead[1]/tr/th/div[.//text()='"+title+"']/@column-id]]/div");
		return e.getElementText();
	}
}

/**
 *
 */
package wcf;

import java.util.List;

/**
 * @author schen3
 *
 */
public class Tree extends Container {

	/**
	 * @param loc
	 */
	public Tree(String title, String under_locator, int index) {
		index = index>1?index:1;
		if(title.equals("")||title.equals("[unnamed]")) {
			setLocator(under_locator+"//div[@class='tree']["+index+"]");
		}
		else{
			setLocator(under_locator+"//*[text()='"+title+"']/following::div[@class='tree']["+index+"]");
		}
		// TODO Auto-generated constructor stub
	}

	public Tree(String title, String under_locator) {
//		if(title.equals("")||title.equals("[unnamed]")) {
//			setLocator(under_locator+"//div[@class='tree'][1]");
//		}
//		else{
//			setLocator(under_locator+"//*[text()='"+title+"']/following::div[@class='tree'][1]");
//		}
		this(title,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Tree(String title, int index) {
//		if(title.equals("")||title.equals("[unnamed]")) {
//			setLocator("//div[@class='tree'][\"+(index>1?index:1)+\"]");
//		}
//		else{
//			setLocator("//*[text()='"+title+"']/following::div[@class='tree']["+(index>1?index:1)+"]");
//		}
		this(title,"",1);
		// TODO Auto-generated constructor stub
	}


	public boolean isInclude(List<String> path) {
		navigate(path);
		return getElement(path).isShown();
	}

	public void navigate(List<String> path) {
		CollapsibleTreeElement collapsibleTreeElement = new CollapsibleTreeElement(this.getLocator(),path.remove(0).trim());
		collapsibleTreeElement.navigate(path);
		clickElement(collapsibleTreeElement.getPath());
	}

	public String getElementLocator(List<String> path) {
		if(path!=null&&path.size()>0) {
			String locator = getLocator()+"/div[@class='treeNode' and span[text()='"+path.remove(0).trim()+"']]";
			for(String p : path) {
				locator += "/div[@class='children']//div[@class='treeNode' and span[text()='"+p.trim()+"']]";
			}
			return locator;
		}
		else {
			return "";
		}
	}

	public TreeElement getElement(List<String> path) {
		TreeElement te = new TreeElement(getElementLocator(path));
		return te;
	}

	public void clickElement(List<String> path) {
		getElement(path).click();
	}
}

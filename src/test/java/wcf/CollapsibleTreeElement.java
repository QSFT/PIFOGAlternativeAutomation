/**
 *
 */
package wcf;

import java.util.ArrayList;
import java.util.List;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class CollapsibleTreeElement extends Element {

	private List<String> path;

	public CollapsibleTreeElement(String tree_locator, String title) {
		super(tree_locator+"/div[@class='treeNode' and span[text()='"+title+"']]");
		path =  new ArrayList<String>();
		path.add(title);
	}

	public boolean isExpanded() {
		return SeleniumUtil.checkIfElementExistsByXpath(this.getLocator()+"[div[@class='children' and not(contains(@style,'display: none'))]]");
	}

	public void expand() {
		if(!isExpanded()) {
			toggle();
		}
	}

	public void collapse() {
		if(isExpanded()) {
			toggle();
		}
	}

	public void toggle() {
		SeleniumUtil.findElementByXpath(this.getLocator()+"/img").click();
	}

	public List<String> getPath(){
		return path;
	}

	public void navigate(List<String> pathes) {
		if(pathes!=null&&pathes.size()>0) {
			expand();
			String last = pathes.remove(0).trim();
			path.add(last);
			setLocator(getLocator()+"/div[@class='children']/div[@class='treeNode' and span[text()='"+last+"']]");
			navigate(pathes);
		}
	}

}

/**
 *
 */
package wcf;

import java.util.List;

import fms.FMSUtil;

/**
 * @author schen3
 *
 */
public class TreeWcfTable extends Container {

	public TreeWcfTable(String title, String under_locator, int index) {
		index = index>1 ? index:1;
		String css_class_of_tree_grid = "contains(@class,'tree grid wcfTable') or contains(@class,'tree wcfTable') or contains(@class,'wcfTable grid tree') or contains(@class,'wcfTable tree')";
		if(title.equals("")||title.equals("[unnamed]")) {
			setLocator(under_locator+"//table["+css_class_of_tree_grid+"]["+index+"]/tbody");
		}
		else{
			setLocator(under_locator+"//span[text()='"+title+"']/following::table["+css_class_of_tree_grid+"]["+index+"]/tbody");
		}
		// TODO Auto-generated constructor stub
	}

	public TreeWcfTable(String title, String under_locator) {
		this(title,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public TreeWcfTable(String title, int index) {
		this(title,"",index);
		// TODO Auto-generated constructor stub
	}

	public void navigate(List<String> path) throws Exception {
		for(int i=1; i<path.size()+1; i++) {
			FMSUtil.waitForPageLoad();
			TreeWcfTableElement e = getElement(path.subList(0, i), null);
			if(e==null) {
				String p = "";
				for(String node : path.subList(0, i)) {
					if(p.length()==0) {
						p += node;
					}
					else {
						p += " -> "+node;
					}
				}
				throw new Exception("Couldn't find node "+p+" in tree");
			}
			e.expand();
		}
	}

	public void collapse(List<String> path) throws Exception {
		FMSUtil.waitForPageLoad();
		TreeWcfTableElement e = getElement(path, null);
		if(e==null) {
			String p = "";
			for(String node : path) {
				if(p.length()==0) {
					p += node;
				}
				else {
					p += " -> "+node;
				}
			}
			throw new Exception("Couldn't find node "+p+" in tree");
		}
		e.collapse();
	}

	public void collapsePath(List<String> path) throws Exception {
		for(int i=0; i<path.size()+1; i++) {
			FMSUtil.waitForPageLoad();
			TreeWcfTableElement e = getElement(path.subList(0, path.size()-i), null);
			e.collapse();
		}
	}

	public TreeWcfTableElement getElement(List<String> path, String secondCol){
		if(path==null||path.size()<=0) {
			return null;
		}
		else {
			resetScrollbar();
			int retries = 3;
			TreeWcfTableElement e = new TreeWcfTableElement(this.getLocator(), path, secondCol);
			boolean last = false;
			while(true) {
				//SeleniumUtil.moveToElement(e.getElement());
				if(e.isShown()) {
					return e;
				}
				else{
					if(isScrollbarReachBottom()) {

						if(last ) {

							return null;
						}

					}
					else {
						dropScrollbar();
					}
					if(!last) {
						last = true;
						e.getLast();
					}
				}

			}
		}
	}

	public void clickElement(List<String> path) throws Exception {
		Element e = getElement(path, null);
		e.click();
	}

	public void setSearchFieldVaule(String vaule) {
		SearchField sf = new SearchField("", this.getLocator()+"/ancestor::div[div[@class='toolbar']]/div[@class='toolbar']");
		sf.clear();
		sf.setValue(vaule);
	}
}

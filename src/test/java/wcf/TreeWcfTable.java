/**
 *
 */
package wcf;

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

}

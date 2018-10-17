/**
 *
 */
package wcf;

/**
 * @author schen3
 *
 */
public class Tile extends Container {
	public Tile(String name, String under_locator, int index) {
		index = index>1 ? index:1;
		if(name.matches("^\\[fuzzy\\](.*)$")) {
			name = name.replace("[fuzzy]", "");
			setLocator("//span[contains(@class, 'tile')]/descendant-or-self::*[@class='tileName' and contains(text(), '"+name+"')]["+index+"]");
		}
		else{
			setLocator("//span[contains(@class, 'tile')]/descendant-or-self::*[ @class='tileName' and text()='"+name+"']["+index+"]");
		}
		// TODO Auto-generated constructor stub
	}

	public Tile(String name, String under_locator) {
		this(name,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public Tile(String name, int index) {
		this(name,"",index);
		// TODO Auto-generated constructor stub
	}

	public void active() {
		this.click();
	}
}

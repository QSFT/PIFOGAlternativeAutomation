/**
 *
 */
package wcf;

import java.lang.reflect.InvocationTargetException;

/**
 * @author schen3
 *
 */
public class ComponentUtil {

	public static String findComponentClassName(String name) {
		switch(name) {
			case "dashboard":
				return "Dashboard";
			case "dialog":
				return "Dialog";
			case "popup":
				return "Popup";
			case "panel":
				return "Panel";
			case "popupmenu":
				return "PopupMenu";
			case "grid":
				return "GridWcfTable";
			case "tree grid":
				return "TreeWcfTable";
			case "icon":
				return "Icon";
			case "button":
				return "Button";
			case "message":
				return "Text";
			case "link":
				return "Link";
			case "tab":
				return "Tab";
			case "tile":
				return "Tile";
			default:
				return null;

		}
	}

	public static String findContainerClassName(String name) {
		switch(name) {
			case "dashboard":
				return "Dashboard";
			case "dialog":
				return "Dialog";
			case "popup":
				return "Popup";
			case "panel":
				return "Panel";
			case "border":
				return "BorderBox";
			case "customizer":
				return "Customizer";
			case "tab":
				return "Tab";
			default:
				return null;
		}
	}

	public static String generateConcatForXpath(String text) {
		if(text.contains("'")||text.contains("\"")) {
			StringBuilder resultStr = new StringBuilder("concat(");
			//String[] items = resultStr.split("/");
			for(int i = 0; i<text.length() ; i++) {
				char c = text.charAt(i);
				if(c =='\'') {
					resultStr.append("\"'\"");
				}
				else if(c == '"'){
					resultStr.append("'\"'");
				}else {
					resultStr.append("'"+c+"'");
				}
				resultStr.append(",");
				if(i==(text.length()-1)) {
					resultStr.append(")");
				}
			}
			return resultStr.toString();
		}
		else {
			return "concat('"+text+"', '')";
		}
	}

	public static String getContainerLocatorByScope(String scope) {
		if(scope==null||scope.length()==0) {
			return "";
		}
		String name;
		String type;
		int lastSpace = scope.lastIndexOf(" ");
		if(lastSpace>0) {
			name = scope.substring(0, lastSpace);
			name = name.replace("'", "");
			type = scope.substring(lastSpace+1);
		}else {
			name = "";
			type = scope;
		}
		Class containerClass;
		String locator = "";
		try {
			containerClass = Class.forName("wcf."+findContainerClassName(type));
			Class[] cArg = new Class[] {String.class,String.class};
			Element element;
			element = (Element) containerClass.getDeclaredConstructor(cArg).newInstance(name, "");
			locator = element.getLocator();
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

		return locator;
	}

	public static void clickComponent(String name, String type, String containerLocator, int index) {
		switch(type) {
			case "button":
				Button button = new Button(name, containerLocator, index );
				button.click();
				return;
			case "link":
				Link link = new Link(name, containerLocator, index );
				link.click();
				return;
			case "radio":
				Radio radio = new Radio(name, containerLocator, index );
				radio.click();
				return;
			case "text":
				Text text = new Text(name, containerLocator, index );
				text.click();
				return;
			case "icon":
				Icon icon = new Icon(name, containerLocator, index );
				icon.click();
				return;
			case "tile":
				Tile tile = new Tile(name, containerLocator, index );
				tile.click();
				return;
			case "tab":
				Tab tab = new Tab(name, containerLocator, index );
				tab.click();
				return;
			default:
				return;
		}
	}

	public static Element getComponent(String name, String type, String container) {
		Element element=null;

		Class c;
		try {
			c = Class.forName("wcf."+type);
			Class[] cArg = new Class[] {String.class,String.class};
			element = (Element) c.getDeclaredConstructor(cArg).newInstance(name, container);
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

	public static Container getGridFromParentComponent(String pname, String ptype, String ctype) {
		Container grid = null;
		if(ptype==null||ptype.length()==0) {
			pname = "[unnamed]";
			if(ctype.equalsIgnoreCase("grid")) {
				grid = new GridWcfTable(pname, "");
			}else {
				grid = new TreeWcfTable(ptype, "");
			}
		}else {
			try {
				Class c = Class.forName("wcf."+findContainerClassName(ptype));
				Class[] cArg = new Class[] {String.class,String.class};
				Container container = (Container) c.getDeclaredConstructor(cArg).newInstance(pname, "");
				if(ctype.equalsIgnoreCase("grid")) {
					grid = (GridWcfTable) container.getElement("GridWcfTable", "");
				}else {
					grid = (TreeWcfTable) container.getElement("TreeWcfTable", "");
				}
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
		}

		return grid;
	}


}

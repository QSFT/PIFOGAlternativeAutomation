/**
 *
 */
package wcf;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fms.FMSUtil;
import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class Container extends Element {

	/**
	 * @param loc
	 */
	public Container(String loc) {
		super(loc);
		// TODO Auto-generated constructor stub
	}

	public Container() {

		// TODO Auto-generated constructor stub
	}

	public Element getElement(String type, String title) {
		Element element = null;
		try {
			Class c = Class.forName("wcf."+type);
			Class[] cArg = new Class[] {String.class,String.class};
			element = (Element) c.getDeclaredConstructor(cArg).newInstance(title, this.getLocator());

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

	public Element getElement(String type) {
		return getElement(type, "");
	}

	public void dropScrollbar() {
		Element thumb = new Element("("+getLocator()+"/ancestor::div | "+getLocator()+"/following::div)[div[@class='vscrollbar']]/div[@class='vscrollbar']/div[contains(@class,'thumb')]");
		if(thumb.isShown()) {
			 String exec_script = "var evt = document.createEvent('MouseEvents');" +
		                "evt.initMouseEvent('mouseover',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
		                "arguments[0].dispatchEvent(evt);";
			 Element down = new Element("("+getLocator()+"/ancestor::div | "+getLocator()+"/following::div)[div[@class='vscrollbar']]/div[@class='vscrollbar']/div[contains(@class,'down')]");
			 String trackerText ;
			 Element trackerTip = new Element("//div[@id='trackerDwell' and @class='tooltip']");
			 Pattern pattern = Pattern.compile("(\\d+)");

				 SeleniumUtil.executeScript(exec_script, thumb.getElement());
				 FMSUtil.waitForPageLoad();
				 trackerText = trackerTip.getElementText();
				 if(trackerText.matches("^(\\d+)-(\\d+)\\/(\\d+)$")) {
					 Matcher matcher = pattern.matcher(trackerText);
					 matcher.find();
					 int from = Integer.parseInt(matcher.group(1));
					 matcher.find();
					 int to = Integer.parseInt(matcher.group(1));
					 matcher.find();
					 int total = Integer.parseInt(matcher.group(1));
					 int times = to - from;
					 if(times > (total - to)) {
						 times = total - to;
					 }
					 while(times>=1) {
						 down.click();
						 times--;
					 }
				 }
		}
	}

	public void resetScrollbar() {
		Element thumb = new Element("("+getLocator()+"/ancestor::div | "+getLocator()+"/following::div)[div[@class='vscrollbar']]/div[@class='vscrollbar']/div[contains(@class,'thumb')]");
		if(thumb.isShown()) {
			 String exec_script = "var evt = document.createEvent('MouseEvents');" +
		                "evt.initMouseEvent('mouseover',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
		                "arguments[0].dispatchEvent(evt);";
			 String exec_script_mouseout = "var evt = document.createEvent('MouseEvents');" +
                     "evt.initMouseEvent('mouseout',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
                     "arguments[0].dispatchEvent(evt);";
			 Element up = new Element("("+getLocator()+"/ancestor::div | "+getLocator()+"/following::div)[div[@class='vscrollbar']]/div[@class='vscrollbar']/div[contains(@class,'up')]");
			 String trackerText ;
			 Element trackerTip = new Element("//div[@id='trackerDwell' and @class='tooltip']");
			 Pattern pattern = Pattern.compile("(\\d+)");
			 while(true) {
				 SeleniumUtil.executeScript(exec_script, thumb.getElement());
				 FMSUtil.waitForPageLoad();
				 trackerText = trackerTip.getElementText();
				 SeleniumUtil.executeScript(exec_script_mouseout, thumb.getElement());
				 if(trackerText.matches("^(\\d+)-(\\d+)\\/(\\d+)$")) {
					 Matcher matcher = pattern.matcher(trackerText);
					 if(matcher.find()) {
						 int times = Integer.parseInt(matcher.group(1));
						 if(times == 1) {
							 return;
						 }
						 while(times>1) {
							 up.click();
							 times--;
						 }
					 }

				 }
			 }
		}
	}

	public boolean isScrollbarReachTop() {
		Element thumb = new Element("("+getLocator()+"/ancestor::div | "+getLocator()+"/following::div)[div[@class='vscrollbar']]/div[@class='vscrollbar']/div[contains(@class,'thumb')]");
		if(thumb.isShown()) {
			 String exec_script = "var evt = document.createEvent('MouseEvents');" +
		                "evt.initMouseEvent('mouseover',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
		                "arguments[0].dispatchEvent(evt);";
			 String trackerText ;
			 Element trackerTip = new Element("//div[@id='trackerDwell' and @class='tooltip']");
			 Pattern pattern = Pattern.compile("(\\d+)");

				 SeleniumUtil.executeScript(exec_script, thumb.getElement());
				 FMSUtil.waitForPageLoad();
				 trackerText = trackerTip.getElementText();
				 if(trackerText.matches("^(\\d+)-(\\d+)\\/(\\d+)$")) {
					 Matcher matcher = pattern.matcher(trackerText);
					 matcher.find();
					 int from = Integer.parseInt(matcher.group(1));
					 if(from == 1) {
						 return true;
					 }
					 else return false;
				 }
				 else {
					 return true;
				 }
		}
		else return true;
	}

	public boolean isScrollbarReachBottom() {
		Element thumb = new Element("("+getLocator()+"/ancestor::div | "+getLocator()+"/following::div)[div[@class='vscrollbar']]/div[@class='vscrollbar']/div[contains(@class,'thumb')]");
		if(thumb.isShown()) {
			 String exec_script = "var evt = document.createEvent('MouseEvents');" +
		                "evt.initMouseEvent('mouseover',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
		                "arguments[0].dispatchEvent(evt);";
			 String trackerText ;
			 Element trackerTip = new Element("//div[@id='trackerDwell' and @class='tooltip']");
			 Pattern pattern = Pattern.compile("(\\d+)");

				 SeleniumUtil.executeScript(exec_script, thumb.getElement());
				 FMSUtil.waitForPageLoad();
				 trackerText = trackerTip.getElementText();
				 if(trackerText.matches("^(\\d+)-(\\d+)\\/(\\d+)$")) {
					 Matcher matcher = pattern.matcher(trackerText);
					 matcher.find();
					 int from = Integer.parseInt(matcher.group(1));
					 matcher.find();
					 int to = Integer.parseInt(matcher.group(1));
					 matcher.find();
					 int total = Integer.parseInt(matcher.group(1));
					 if(to == total) {
						 return true;
					 }
					 else return false;
				 }
				 else {
					 return true;
				 }
		}
		else return true;
	}
}

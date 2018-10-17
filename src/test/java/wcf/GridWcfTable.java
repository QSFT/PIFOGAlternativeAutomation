/**
 *
 */
package wcf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fms.FMSUtil;
import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class GridWcfTable extends Container {

	public GridWcfTable(String title, String under_locator, int index) {
		index = index>1 ? index:1;
		if(title.equals("")||title.equals("[unnamed]")) {
			setLocator(under_locator+"/descendant::table[.//thead//div[@column-id] and (contains(@class, 'grid') or contains(@class, 'wcfTable'))]["+index+"]");
			if(!isShown()) {
				setLocator(under_locator+"/descendant::table[contains(@class, 'grid') or contains(@class, 'wcfTable')]["+index+"]");
			}
		}
		else{
			setLocator(under_locator+"//span[normalize-space(text())='"+title+"']/following::table[.//thead//div[@column-id] and contains(@class, 'grid')]["+index+"]");
		}
		// TODO Auto-generated constructor stub
	}

	public GridWcfTable(String title, String under_locator) {
		this(title,under_locator,1);
		// TODO Auto-generated constructor stub
	}

	public GridWcfTable(String title, int index) {
		this(title,"",index);
		// TODO Auto-generated constructor stub
	}

	public boolean isInclude(Map<String, String> row) {
		return isInclude(row, 3);
	}

	public boolean isInclude(Map<String, String> row , int timeout) {
		if(getRow(row,timeout)!=null) {
			return true;
		}
		else return false;
	}

	public GridWcfTableRow getRow(Map<String, String> row, int timeout ) {
		StringBuilder condition = new StringBuilder("");
		String nbsp = String.valueOf((char) 160);
		for (Map.Entry<String, String> entry : row.entrySet()) {
			if(condition.length()>0) {
				condition.append(" and ");
			}
			String lastpart = "";
			String header = entry.getKey();
			String value = entry.getValue();
			if(header.matches("^\\[colspan\\](.*)$")) {
				header = header.replace("[colspan]", "");
				GridWcfTableRow trow = new GridWcfTableRow(getLocator()+"/thead[1]/tr/th[div[.//text()='"+header+"']]");
				List<String> childCols = new ArrayList<String>(Arrays.asList(trow.getAttribute("child-columns").split(" ")));
				StringBuilder colCondition = new StringBuilder("");
				for(String colIndex : childCols) {
					if(colCondition.length()>0) {
						colCondition.append(" or ");
					}
					colCondition.append("@column-index='"+colIndex+"'");
				}
				lastpart = "/parent::td["+colCondition.toString()+"]";
			}
			else if(!header.equals("[unnamed]")&&header.length()>0) {
				lastpart = "/@column-id=preceding::thead[1]/tr/th/div[.//text()="+ComponentUtil.generateConcatForXpath(header) +
		                " or normalize-space(.//text())="+ComponentUtil.generateConcatForXpath(header)+"]/@column-id";
			}
			if(value.matches("^\\[img\\](.*)$")) {
				value = value.replace("[img]", "");
				condition.append("td/div[.//img[contains(@src,"+ComponentUtil.generateConcatForXpath(value)+")]]"+lastpart);
			}else if(value.matches("^(>|<|=|!=|>=|<=)-?([1-9]\\d*\\.?\\d*|0\\.?\\d*[1-9]\\d*|0?\\.?0+|0)$")) {
				condition.append("td/div[text()"+value+
						" or translate(text(),',' , '')"+value+
						" or descendant::*[text()"+value+
						"]  or descendant::span[translate(text(),' %' , '')"+value+
						"]  or descendant::*[translate(translate(text(), '"+nbsp+"' , ''),',' , '')"+value+
						"]]"+lastpart);
			}
			else if(value.equals("[spark]")) {
				condition.append("td/div[.//canvas]"+lastpart);
			}else if(value.matches("^\\[fuzzy\\](.*)$")) {
				value = value.replace("[fuzzy]", "");
				condition.append("td/div[.//*[contains(text(),"+ComponentUtil.generateConcatForXpath(value)+")]]"+lastpart);
			}else if(value.equals("[selected]")||value.equals("[unselected]")) {
				condition.append("contains(@class,'SelectedRow')");
			}else if(value.matches("^\\[disable\\].*$")) {
				value = value.replace("[disable]", "");
				condition.append("td/div[a/span[@class ='font-style-deemphasized' and text()='"+value+"']]"+lastpart);
			}else {
				condition.append("td/div[contains(.,"+ComponentUtil.generateConcatForXpath(value)+")]"+lastpart);
			}
		}
		this.resetScrollbar();
		int retries = 3;
		while(true) {
			GridWcfTableRow trow = new GridWcfTableRow(this.getLocator()+"/tbody/tr["+condition+"]");
			if(trow.isShown(timeout)) {
				return trow;
			}
			else if(this.isScrollbarReachBottom()){
				retries--;
				if(retries>0) {
					SeleniumUtil.refreshBrowser();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.resetScrollbar();
				}
				else {
					return null;
				}
			}else {
				this.dropScrollbar();
			}
		}
	}


	public GridWcfTableRow getRow(int index) {
		GridWcfTableRow row = new GridWcfTableRow(this.getLocator()+"/tbody/tr["+index+"]");
		return row.isShown()? row:null;
	}

	public int rowsCount() {
		return SeleniumUtil.findElementsByXpath(this.getLocator()+"/tbody/tr").size();
	}

	public void checkAll() {
		Element e = new Element(this.getLocator()+"/thead/tr/th/div/input[contains(@name, 'selectAllControl')]");
		e.click();
	}

	public boolean isColumnContained(String title, int index) {
		Element column = new Element(this.getLocator()+"/thead/tr/th["+index+"][.//text()="+ComponentUtil.generateConcatForXpath(title)+"]");
		return column.isShown();
	}

	public boolean isColumnContained(String title) {
		Element column = new Element(this.getLocator()+"/thead/tr/th[.//text()="+ComponentUtil.generateConcatForXpath(title)+"]");
		return column.isShown();
	}

	public void clickToolbarButton(String name) {
		Button btn = new Button(name, this.getLocator()+"/ancestor::div[div[@class='toolbar']]/div[@class='toolbar']");
		btn.click();
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
					 while(times>1) {
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

	public void setSearchFieldVaule(String vaule) {

	}


}

/**
 *
 */
package wcf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					FMSUtil.waitForPageLoad();
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

	public void setSearchFieldVaule(String vaule) {
		SearchField sf = new SearchField("", this.getLocator()+"/ancestor::div[div[@class='toolbar']]/div[@class='toolbar']");
		sf.clear();
		sf.setValue(vaule);
	}


}

package stepDefinitions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.runtime.java.StepDefAnnotation;
import fms.FMSUtil;
import wcf.ComponentUtil;
import wcf.Container;
import wcf.GridWcfTable;
import wcf.GridWcfTableRow;
import wcf.TreeWcfTable;

@StepDefAnnotation
public class GridSteps extends StepDefinition{

	@Given("^'(.*)' ?(dashboard|dialog|panel|popup|border|customizer)? (grid|tree grid) should( not)? contain following rows(?: in (\\d+) seconds?)?:$")
	public void viewDataVerify(String pname, String ptype, String ctype, String shouldnot, String time, DataTable table) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		int timeout = 5;
		if(time!=null) {
			timeout = Integer.parseInt(time);
		}
		Container c = ComponentUtil.getGridFromParentComponent(pname, ptype, ctype);
		//Method isInclude = c.getClass().getMethod("isInclude", Map.class);
		Method isIncludeWithTimeout = c.getClass().getMethod("isInclude", Map.class, int.class);

		List<List<String>> data = table.raw();
		List<String> columnNames = data.get(0);
		for(int i =1 ; i<data.size() ; i++) {
			Map<String,String> condition = new HashMap<String,String>();
			for(int j =0 ; j<columnNames.size() ; j++) {
				condition.put(columnNames.get(j), data.get(i).get(j));
			}
			//System.out.println(" | "+data.get(i).toString()+" | ");
			boolean result = (boolean) isIncludeWithTimeout.invoke(c, condition, timeout);
			if(shouldnot == null && !result) {
				throw new Exception("The "+ctype+" didn't contain this row\n"+data.get(i).toString());
			}else if(shouldnot != null && result) {
				throw new Exception("The "+ctype+" did contain this row\n"+data.get(i).toString());
			}
		}
	}

	@Given("^the '(.*)' (grid|tree grid)(?: (?:in|on) (?:the )?(.+))? should( not)? contain following rows(?: in (\\d+) seconds?)?:$")
	public void viewDataVerify1(String gname, String gtype, String scope, String shouldnot, String time, DataTable table) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		int timeout = 5;
		if(time!=null) {
			timeout = Integer.parseInt(time);
		}
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);
		Container c ;
		if(gtype.equals("grid")) {
			c = new GridWcfTable(gname, containerLocator, 1);
		}else {
			c = new TreeWcfTable(gname, containerLocator, 1);
		}

		Method isIncludeWithTimeout = c.getClass().getMethod("isInclude", Map.class, int.class);

		List<List<String>> data = table.raw();
		List<String> columnNames = data.get(0);
		for(int i =1 ; i<data.size() ; i++) {
			Map<String,String> condition = new HashMap<String,String>();
			for(int j =0 ; j<columnNames.size() ; j++) {
				condition.put(columnNames.get(j), data.get(i).get(j));
			}
			//System.out.println(" | "+data.get(i).toString()+" | ");
			boolean result = (boolean) isIncludeWithTimeout.invoke(c, condition, timeout);
			if(shouldnot == null && !result) {
				throw new Exception("The "+gtype+" didn't contain this row\n"+data.get(i).toString());
			}else if(shouldnot != null && result) {
				throw new Exception("The "+gtype+" did contain this row\n"+data.get(i).toString());
			}
		}
	}

	@Given("^(?:on|in) '(.*)' (grid|tree grid)(?: (?:in|on) (?:the )?(.+))? click (?:the )?(?:(\\d+)(?:st|nd|rd|th) )?'(.+)' (link|icon|checkbox|text) in the following row:$")
	public void clickGridItem(String gname, String gtype, String scope, String indexStr, String name, String type, DataTable table) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);
		int index = 1;
		if(indexStr!=null) {
			index = Integer.parseInt(indexStr);
		}
		Container c ;
		if(gtype.equals("grid")) {
			c = new GridWcfTable(gname, containerLocator, 1);
		}else {
			c = new TreeWcfTable(gname, containerLocator, 1);
		}
		Method getRow = c.getClass().getMethod("getRow", Map.class, int.class);
		GridWcfTableRow row = null;
		List<List<String>> data = table.raw();
		List<String> columnNames = data.get(0);
		for(int i =1 ; i<data.size() ; i++) {
			Map<String,String> condition = new HashMap<String,String>();
			for(int j =0 ; j<columnNames.size() ; j++) {
				condition.put(columnNames.get(j), data.get(i).get(j));
			}

			row = (GridWcfTableRow) getRow.invoke(c, condition, 5);
			if(row!=null) {
				ComponentUtil.clickComponent(name, type, row.getLocator(), index);
			}else {
				throw new Exception("The "+gtype+" did contain this row\n"+data.get(i).toString());
			}
		}
	}

	@Given("^(select|check|uncheck|toggle) following rows (?:on|in) '(.*)' (grid|tree grid)(?: (?:in|on) (?:the )?(.+))?:$")
	public void selectGridRows(String action, String gname, String gtype, String scope, DataTable table) throws Throwable {
		FMSUtil.waitForPageLoad(pageLoadTimeout);
		String containerLocator = ComponentUtil.getContainerLocatorByScope(scope);
		Container c ;
		if(gtype.equals("grid")) {
			c = new GridWcfTable(gname, containerLocator, 1);
		}else {
			c = new TreeWcfTable(gname, containerLocator, 1);
		}
		Method getRow = c.getClass().getMethod("getRow", Map.class, int.class);
		GridWcfTableRow row = null;
		List<List<String>> data = table.raw();
		List<String> columnNames = data.get(0);
		for(int i =1 ; i<data.size() ; i++) {
			Map<String,String> condition = new HashMap<String,String>();
			for(int j =0 ; j<columnNames.size() ; j++) {
				condition.put(columnNames.get(j), data.get(i).get(j));
			}

			row = (GridWcfTableRow) getRow.invoke(c, condition, 5);
			if(row!=null) {
				if(action.equals("select")||action.equals("check")) {
					row.getCheckBox().check();
				}
				else {
					row.getCheckBox().uncheck();
				}

			}else {
				throw new Exception("The "+gtype+" did contain this row\n"+data.get(i).toString());
			}

		}
	}

}

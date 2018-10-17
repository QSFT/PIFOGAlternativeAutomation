/**
 *
 */
package test;

import java.io.File;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;

/**
 * @author schen3
 *
 */
public class ReportManager {

	public static void init(String dir) {
		File file=new File("target/cucumber-reports/"+dir+"/");
		if(!file.exists()){
			file.mkdir();
		}
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
	    extentProperties.setReportPath("target/cucumber-reports/"+dir+"/report.html");
	}

	public static void generateReport() {
		Reporter.loadXMLConfig(new File("configs/extent-config.xml"));

	}


}

/**
 *
 */
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



/**
 * @author schen3
 *
 */
public class TestContext {

	private static String testStartTime;

	private static Properties prop = new Properties();

	static{
		try {
			File propTile = new File(System.getProperty("user.dir")+"/test.properties");

			if(propTile.exists()) {
				InputStream input = new FileInputStream(propTile);
				prop.load(input);
				//prop.load(TestContext.class.getClassLoader().getResourceAsStream("test.properties"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getTestStartTime() {
		return testStartTime;
	}

	public static void setTestStartTime(String testStartTime) {
		TestContext.testStartTime = testStartTime;
	}

	public static void setProperty(String name, String value) {
		prop.setProperty(name, value);
	}

	public static String getProperty(String name) {
		return prop.getProperty(name);
	}
}

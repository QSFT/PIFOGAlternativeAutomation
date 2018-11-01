/**
 *
 */
package fms;

import selenium.SeleniumUtil;

/**
 * @author schen3
 *
 */
public class FMSUtil {

	private static String busyIcon = "//img[@id='busyIndicator' and contains(@style,'visible')]";

	private static String loginIcon = "//img[contains(@src, 'quest.svg')]";

	private static int pageTimeout = 300;

	public static void waitForPageLoad(int timeout) {
		SeleniumUtil.waitInvisibilityOfElementByXpath(busyIcon , timeout);
	}

	public static void waitForPageLoad() {
		SeleniumUtil.waitInvisibilityOfElementByXpath(busyIcon , pageTimeout);
	}

	public static boolean isLogin() {
		boolean isLogin = false;
		if(SeleniumUtil.findElementByXpath(loginIcon)!=null) {
			isLogin = true;
		}
		return isLogin;
	}

}

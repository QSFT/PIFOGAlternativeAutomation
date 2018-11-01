/**
 *
 */
package selenium;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestContext;

/**
 * @author schen3
 *
 */
public class SeleniumUtil {

	private static WebDriver browser;

	private static WebDriverWait wait;

	public static void setWaitTime(int sec) {
    	if(browser!=null) {
    			wait = new WebDriverWait(browser, sec);
		}
	}

    public static WebDriver openBrowser() {
    	if(browser!=null) {
			browser.quit();
		}
//    	System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
    	System.setProperty("webdriver.chrome.driver", TestContext.getProperty("webdriver.chrome.driver"));
//    	browser = new FirefoxDriver();
    	browser = new ChromeDriver();
    	setWaitTime(30);
    	browser.manage().window().maximize();
    	return browser;
    }

    public static WebDriver getBrowser() {
    	return browser;
    }

    public static void closeBrowser() {
    	if(browser!=null) {
			browser.close();
			browser = null;
			wait = null;
		}
    }

    public static void quitBrowser() {
    	if(browser!=null) {
			browser.quit();
			browser = null;
			wait = null;
		}
    }

    public static boolean isBrowserOpened() {
    	if(browser!=null) {
			return true;
		}
    	else {
    		return false;
    	}
    }

    public static void openPage(String url) {
    	if(browser!=null) {
    		browser.get(url);
		}
    }

    public static File getScreenshotAsFile() {
    	if(browser!=null) {
    		File src= ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
    		return src;
		}else {
			return null;
		}
    }

    public static boolean checkIfElementExistsByXpath(String xpath) {
    	if(browser!=null) {
    		try {
    			boolean isFonud = (browser.findElements(By.xpath(xpath)).size()!=0);
				return isFonud;
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return false;
    		}
		}
    	else return false;
    }

    public static WebElement findElementByXpath(String xpath) {
    	if(browser!=null&&wait!=null) {
    		try {
    			WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				return we;
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return null;
    		}
		}
    	else return null;
    }

    public static List<WebElement> findElementsByXpath(String xpath) {
    	List<WebElement> wes = null;
    	if(browser!=null) {
    		try {
    			wes = browser.findElements(By.xpath(xpath));
				return wes;
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return wes;
    		}
		}
    	else return wes;
    }

    public static WebElement findElementByXpathWithTimeout(String xpath, int timeout) {
    	if(browser!=null) {
    		WebDriverWait wait = new WebDriverWait(browser, timeout);
			try {
    			WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				return we;
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return null;
    		}
		}
    	else return null;
    }

    public static boolean waitInvisibilityOfElementByXpath(String xpath, int timeout) {
    	if(browser!=null) {
    		WebDriverWait wait = new WebDriverWait(browser, timeout);
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		}
    	else return false;
    }

    public static void clickElementByXpath(String xpath) {
    	if(browser!=null&&wait!=null) {
    		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    	}
    }

    public static void clearElementByXpath(String xpath) {
    	findElementByXpath(xpath).clear();
    }

    public static void sendKeysByXpath(String xpath, String keys) {
    	findElementByXpath(xpath).sendKeys(keys);
    }

    public static void checkByXpath(String xpath, String keys) {
    	if(!findElementByXpath(xpath).isSelected()) {
        	findElementByXpath(xpath).click();
    	}
    }

    public static void uncheckByXpath(String xpath, String keys) {
    	if(findElementByXpath(xpath).isSelected()) {
        	findElementByXpath(xpath).click();
    	}
    }

    public static void moveToElement(WebElement e) {
    	if(browser!=null) {
	    	Actions actions = new Actions(browser);
	    	actions.moveToElement(e);
	    	actions.perform();
    	}
    }

    public static void executeScript(String script, Object arg) {
    	if(browser!=null) {
    		JavascriptExecutor js = (JavascriptExecutor)browser;
    		js.executeScript(script, arg);
    	}
    }

    public static void refreshBrowser() {
    	if(browser!=null) {
    		browser.navigate().refresh();
    	}
    }

}

package run;

import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DriverHelper
{
	public static WebDriver driver;
	public static String platform;
	private Actions builder;
	final static Logger logger = Logger.getLogger(DriverHelper.class);

	public DriverHelper()
	{
		File DRIVER_CHROME_PATH = new File(Config.WEBDRIVER_PATH + "/chromedriver/linux/" + "chromedriver");
		System.setProperty("webdriver.chrome.driver", DRIVER_CHROME_PATH.getAbsolutePath());
		File DRIVER_IE_PATH = new File(Config.WEBDRIVER_PATH + "iexplorer");
		System.setProperty("webdriver.ie.driver", DRIVER_IE_PATH.getAbsolutePath());
		File DRIVER_GECKO_PATH = new File(Config.WEBDRIVER_PATH + "geckodriver");
		System.setProperty("webdriver.gecko.driver", DRIVER_GECKO_PATH.getAbsolutePath());
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

		PropertyConfigurator.configure(System.getProperty("user.dir")+ "/src/run/resources/log4j.properties");
	}

	public WebDriver run(String browser, String platform) throws Exception
	{
		DriverHelper.platform = platform;
		driver = getBrowserInstance(browser);
		driver.manage().window().maximize();
		return driver;
	}

	public void stop()
	{
		driver.quit();
	}

	public void backPage()
	{
		driver.navigate().back();
	}

	public void openURL(String p_url)
	{
		logger.info("Open Url " + p_url);
		driver.get(p_url);
	}

	@Step("Refresh the page")
	public void refreshPage()
	{
		try
		{
			driver.navigate().refresh();
			logger.info("Refresh current page");
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException("refreshPage " + p_ex);
		}
	}

	@Step("Submit form")
	public void submit(By locator) throws Exception
	{
		try
		{
			logger.info("Submit form => [" + locator + "]");
			WebElement element = driver.findElement(locator);
			element.submit();
		}
		catch (Exception ex)
		{
			throw new Exception("Cannot submit form: " + ex);
		}
	}

	@Step("Click element")
	public void click(By locator)
	{
		try
		{
			logger.info("Click on an element => [" + locator + "]");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Wait<WebDriver> wait = new WebDriverWait(driver, Config.TIME_DELAY);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			WebElement element = driver.findElement(locator);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).perform();
			Thread.sleep(1000);
			element.click();
		}
		catch (Exception ex)
		{
			driver.findElement(locator).click();
		}
	}

	@Step("Set text to element")
	public void setElementText(By locator, String text) throws Exception
	{
		try
		{
			logger.info("Set element text => [" + locator + "] value [" + text + "]");
			WebElement element = driver.findElement(locator);
			element.click();
			element.clear();
			element.sendKeys(String.valueOf(text));
		}
		catch (Exception ex)
		{
			throw new Exception("Cannot setElementText: " + ex);
		}
	}

	@Step("Select input by value")
	public void selectByValue(By p_locator, String p_value) throws Exception
	{
		try
		{
			logger.info("Select an option => [" + p_locator + "] by text [" + p_value + "]");
			WebElement webElement = driver.findElement(p_locator);
			Select webElementSelect = new Select(webElement);
			webElementSelect.selectByValue(p_value);
		}
		catch (Exception p_ex)
		{
			throw new Exception("Cannot select an option => " + p_locator + "\n" + p_ex);
		}
	}

	@Step("Wait for element presence")
	public void waitForPresence(By locator)
	{
		try
		{
			logger.info("Wait for element present => [" + locator + "]");
			this.hightlight(driver.findElement(locator));
			Wait<WebDriver> wait = new WebDriverWait(driver, Config.TIME_DELAY);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Step("Verify is element present")
	public boolean isElementDisplayed(By locator)
	{
		try
		{
			logger.info("Verify is element present - > " + locator);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebElement webElement = driver.findElement(locator);
			boolean isElementDisplayed = webElement.isDisplayed();
			return isElementDisplayed;
		}
		catch (Exception ex)
		{
			return false;
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(Config.TIME_DELAY, TimeUnit.SECONDS);
		}
	}

	@Step("Wait for element visibility {1} by locator {0}")
	public void waitForPresence(By locatorOfElement, String p_message, int p_timeOutSeconds)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, p_timeOutSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locatorOfElement));
			logger.info("wait for presence of [" + p_message + "], by locator => [" + locatorOfElement.toString() + "]");

			this.hightlight(driver.findElement(locatorOfElement));
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException(p_message + " | " + p_ex);
		}
	}

	@Step("Get the attribute of Web element by name")
	public String getAttribute(WebElement webElement, String attribute) throws Exception
	{
		String attributeValue = "";
		try
		{
			if (attribute.equals("text"))
			{
				attributeValue = webElement.getText();
			}
			else
			{
				attributeValue = webElement.getAttribute(attribute);
			}
		}
		catch (Exception ex)
		{
			throw new Exception("Cannot get attribute '" + attribute + "' => " + webElement + "\n" + ex);
		}
		return attributeValue;
	}

	@Step("Get the attribute of element by name")
	public String getAttribute(By locator, String attribute) throws Exception
	{
		logger.info("Get attribute => [" + attribute + "] " + locator);
		String attributeValue = "";
		WebElement element = null;

		if (this.isElementPresent(locator))
		{
			try
			{
				element = driver.findElement(locator);
				attributeValue = getAttribute(element, attribute);
			}
			catch (Exception ex)
			{
				throw new Exception("Cannot get attribute '" + attribute + "' => " + locator + "\n" + ex);
			}
		}
		return attributeValue;
	}

	@Step("Get the attribute of element by name")
	public boolean isElementPresent(By locator)
	{
		try
		{
			logger.info("Assert if element present -> " + locator);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			driver.findElement(locator);
			driver.manage().timeouts().implicitlyWait(Config.TIME_DELAY, TimeUnit.SECONDS);
			return true;
		}
		catch (Exception ex)
		{
			System.out.println("Cannot find element: " + locator);
			return false;
		}
	}

	@Step("Take screenshot of element")
	public void takeScreenShot(String fileName)
	{
		try
		{
			logger.info("Take page screenshot");
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(Config.SCREENSHOT_PATH + fileName + ".png"));
			screenshot.delete();
		}
		catch (Exception ex)
		{
			System.out.println("Unable to take a screenshot: " + ex);
		}
	}

	@Step("Get current URL of page")
	public String getCurrentUrl()
	{
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}

	@Step("Verify if url contains some text")
	public boolean isUrlContains(String string) throws Exception
	{
		String currentURL = getCurrentUrl();
		return currentURL.contains(string);
	}

	@Step("Set cookie")
	public void setAdditionalCookie(String domain, String cookieName, String cookieValue)
	{
		try
		{
			logger.info("Set cookie: " + cookieName + " with value " + cookieValue + " to domain " + domain);
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse("2020-01-18 00:00:00.0");
			domain = domain.substring(domain.indexOf("//") + (platform.equals("mob") ? 3 : 5));
			Cookie cookie = new Cookie(cookieName, cookieValue, "." + domain, "/", date);
			driver.manage().addCookie(cookie);
			driver.navigate().refresh();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void deleteAllCookies()
	{
		logger.info("Delete all cookies");
		driver.manage().deleteAllCookies();
	}

	private ChromeOptions getChromeOptions()
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1366,768");
		options.addArguments("--use-fake-ui-for-media-stream", "--use-fake-device-for-media-stream", "--allow-file-access-from-files");
		options.addArguments("--start-maximized");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--disable-notifications");
		return options;
	}

	@Step("Wait for {0}")
	public void wait(int p_timeOut) throws TestException
	{
		logger.info("Pause for => " + p_timeOut + " seconds");

		try
		{
			long delay = 30;
			int i = 0;
			if (p_timeOut > delay)
			{
				int attempt = p_timeOut / 30;
				while (i != attempt)
				{
					logger.debug("Sleeping zzzzz....");
					Thread.sleep(delay * 1000L);
					getCurrentUrl();
					i++;
					p_timeOut -= delay;
				}
				Thread.sleep(p_timeOut * 1000L);
			}
			else
			{
				Thread.sleep(p_timeOut * 1000L);
			}
		}
		catch (InterruptedException p_ex)
		{
			throw new WebDriverException("Cannot pause =>" + p_ex);
		}
	}

	private DesiredCapabilities getFirefoxCapabilities()
	{
		FirefoxProfile profile = new FirefoxProfile();

		profile.setPreference("general.useragent.override",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36"
		);

		LoggingPreferences preferences = new LoggingPreferences();
		preferences.enable(LogType.BROWSER, Level.OFF);
		preferences.enable(LogType.CLIENT, Level.OFF);
		preferences.enable(LogType.DRIVER, Level.OFF);
		preferences.enable(LogType.PERFORMANCE, Level.OFF);
		preferences.enable(LogType.SERVER, Level.OFF);

		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, preferences);

		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		return capabilities;
	}

	private DesiredCapabilities getChromeCapabilities()
	{
		ChromeOptions options = getChromeOptions();
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}

	private DesiredCapabilities getIExplorerCapabilities()
	{
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setBrowserName("iexplore");
		return capabilities;
	}

	public WebDriver getBrowserInstance(String browser) throws Exception
	{
		switch (browser)
		{
			case "firefox":
				return new FirefoxDriver(getFirefoxCapabilities());
			case "chrome":
				return new ChromeDriver(getChromeCapabilities());
			case "iexplorer":
				return new InternetExplorerDriver(getIExplorerCapabilities());
			default:
				throw new Exception("Browser is not supported => " + browser);
		}
	}

	public void hightlight(WebElement p_element)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.outline='3px solid green'", p_element);
		}
		catch (Exception p_ex)
		{
			System.out.println(p_ex.toString());
		}
	}

	public WebElement getWebElement(By p_locator, String p_description)
	{
		try
		{
			logger.debug("Get web element " + p_description + ", by locator => [" + p_locator + "]");
			this.hightlight(driver.findElement(p_locator));
			return driver.findElement(p_locator);
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException("Get web element " + p_description + ", by locator => [" + p_locator + "], Error => " + p_ex.toString());
		}
	}

	@Step("Mouse over {3} by locator {0} with offset x {1} y {2}")
	public void mouseOver(By p_locator, int xOffSet, int yOffSet, String p_description)
	{
		try
		{
			this.hightlight(driver.findElement(p_locator));
			WebElement element = getWebElement(p_locator, p_description);
			scrollWithOffset(element, xOffSet, yOffSet);
			this.mouseOver(element);
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException(
					"mouseOver: " + p_description + ", By locator " + p_locator + " " + p_ex);
		}
	}

	public void mouseOver(WebElement p_onElement)
	{
		try
		{
			logger.info("Move mouse to element [" + p_onElement + "]");
			builder = new Actions(driver);
			builder.moveToElement(p_onElement).build().perform();
		}
		catch (Exception p_ex)
		{
			throw new AssertionError(p_ex.toString());
		}
	}

	public void mouseOverJS(WebElement element)
	{
		try
		{
			String mouseOverScript = "if(document.createEvent)"
					+ "{var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initEvent('mouseover',	true, false); "
					+ "arguments[0].dispatchEvent(evObj);} "
					+ "else if(document.createEventObject){ arguments[0].fireEvent('onmouseover');}";

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(mouseOverScript, element);
		}
		catch (Exception p_ex)
		{
			throw new AssertionError(p_ex.toString());
		}
	}

	public WebElement scrollWithOffset(WebElement p_webElement, int x, int y)
	{
		try
		{
			logger.debug("Scroll to web element: " + p_webElement);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			wait(2);
			executor.executeScript("window.scroll(" + (p_webElement.getLocation().x + x) + ","
					+ (p_webElement.getLocation().y + y) + ");");
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			return p_webElement;
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException("scrollWithOffset " + p_webElement + "\n" + p_ex);

		}

	}

	public List<WebElement> getWebElements(By p_webElementsBlock, String p_description)
	{
		try
		{
			waitForPresence(p_webElementsBlock, p_description);
			logger.info("Get list of element : " + p_description + " [" + p_webElementsBlock + "]");
			return driver.findElements(p_webElementsBlock);
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException("Get list of element : " + p_description + " [" + p_webElementsBlock + "], Error " + p_ex.toString());
		}
	}

	@Step("Wait for element presence {1} by locator {0}")
	public void waitForPresence(By locatorOfElement, String p_message)
	{
		this.waitForPresence(locatorOfElement, p_message, 60);
	}

	@Step("Get text {1} by locator {0}")
	public String getText(By p_locator, String p_description)
	{
		try
		{
			logger.info("getText: " + p_description + ", By locator: " + p_locator.toString());
			waitForPresence(p_locator, p_description);
			WebElement webElement = driver.findElement(p_locator);
			String txt = webElement.getText();
			logger.info("getText: Found text is " + txt);
			return txt;
		}
		catch (Exception p_ex)
		{
			throw new WebDriverException("getText => " + p_locator.toString() + "\n" + p_ex.toString());
		}
	}

}

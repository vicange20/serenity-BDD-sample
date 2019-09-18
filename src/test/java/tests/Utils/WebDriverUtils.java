package tests.Utils;

import com.google.common.base.Predicate;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class WebDriverUtils extends PageObject{

    public static void setEnvironment() {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String property = variables.getProperty("environment.settings");
        setWeb();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Pages.class);

    private static boolean isIOS;
    private static boolean isAndroid;
    private static boolean isWEB;

    //------------------------------------------------------------------------------------------------------------------
    //region Page Object Utils
    //------------------------------------------------------------------------------------------------------------------

    public int getScreenHeight() {
        return getDriver().manage().window().getSize().getHeight();
    }

    public int getScreenWidth() {
        return getDriver().manage().window().getSize().getHeight();
    }

    public void changeIOSContext(String context) {
        getIOSDriver(getDriver()).context(context);
    }

    public WebElement waitForElement(WebElement element) {
        return waitForElement(element, 7);
    }

    public WebElement waitForElement(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    public static void sleep(int seconds) {
        int time = seconds * 1000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public WebElement waitForElementToBeClickable(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public WebElement waitForElementWithoutTimeout(WebElement element, int timeout, int polling) {
        try {
            new WebDriverWait(getDriver(), 10)
                    .pollingEvery(polling, MILLISECONDS)
                    .withTimeout(timeout, MILLISECONDS)
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            LOGGER.info(e.toString());
        }
        return element;
    }

    public void waitWhileElementsAreDisplayed(List<WebElement> element, int seconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
    }

    public boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
        return true;
    }

    //endregion

    //------------------------------------------------------------------------------------------------------------------
    //region Driver Utils
    // Some items are left here in preparation for Web testing on mobile.
    //------------------------------------------------------------------------------------------------------------------

    public static AppiumDriver getAppiumDriver(WebDriver driver) {
        if (isIOS()) {
            return getIOSDriver(driver);
        } else if (isAndroid()) {
            return getAndroidDriver(driver);
        }
        return null;
    }

    public static WebDriver getWebDriver(WebDriver driver) {
        if (isWEB()) {
            try {
                WebDriverFacade webDriverFacade = (WebDriverFacade) driver;
                WebDriver webDriver = (WebDriver) webDriverFacade.getProxiedDriver();
                setWeb();
                return webDriver;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static IOSDriver getIOSDriver(WebDriver driver) {
        try {
            WebDriverFacade webDriverFacade = (WebDriverFacade) driver;
            IOSDriver iosDriver = (IOSDriver) webDriverFacade.getProxiedDriver();
            setIOS();
            return iosDriver;
        } catch (Exception e) {
            return null;
        }
    }

    public static AndroidDriver getAndroidDriver(WebDriver driver) {
        try {
            WebDriverFacade webDriverFacade = (WebDriverFacade) driver;
            AndroidDriver androidDriver = (AndroidDriver) webDriverFacade.getProxiedDriver();
            setAndroid();
            return androidDriver;
        } catch (Exception e) {
            return null;
        }
    }

    public void getCurrentDriver() {
        getAndroidDriver(getDriver());
        getIOSDriver(getDriver());
        getWebDriver(getDriver());
    }

    public static boolean isAndroid() {
        return isAndroid;
    }

    public static void setAndroid() {
        isAndroid = true;
    }

    public static boolean isIOS() {
        return isIOS;
    }

    public static boolean isWEB() {
        return isWEB;
    }

    public static void setIOS() {
        isIOS = true;
    }

    public static void setWeb(){
        isWEB = true;
    }

    public String getAlertText(final int seconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        return alert.getText();
    }

    public void acceptAlert() {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public static boolean isAttributePresent(WebElement element, String attribute) {
        Boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null) {
                result = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public void rotateDeviceTo(String orientation) {
        try {
            if (orientation.toLowerCase().equals("portrait")) {
                if (isIOS()) {
                    getIOSDriver(getDriver()).rotate(ScreenOrientation.PORTRAIT);
                } else if (isAndroid()) {
                    getAndroidDriver(getDriver()).rotate(ScreenOrientation.PORTRAIT);
                }
            } else if (orientation.toLowerCase().equals("landscape")) {
                if (isIOS()) {
                    getIOSDriver(getDriver()).rotate(ScreenOrientation.LANDSCAPE);
                } else if (isAndroid()) {
                    getAndroidDriver(getDriver()).rotate(ScreenOrientation.LANDSCAPE);
                }
            }
        } catch (Exception e) {
            System.out.println("Device has not changed the orientation.");
            Exception ignore;
        }
    }

    public Point getElementPosition(WebElement element) {
        Point point = element.getLocation();
        return point;
    }



    public static void changeDriverContextToWeb(AppiumDriver driver) {
        Set<String> allContext = driver.getContextHandles();
        for (String context : allContext) {
            if (context.contains("WEBVIEW"))
                driver.context(context);
        }
    }

    public static void changeDriverContextToNative(AppiumDriver driver) {
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.contains("NATIVE"))
                driver.context(contextName);
        }
    }

    //endregion

    //------------------------------------------------------------------------------------------------------------------
    //Driver Helper Utils
    //------------------------------------------------------------------------------------------------------------------

    public int getXPositionOfElement(WebElement element) {
        return element.getLocation().x;
    }

    public int getYPositionOflement(WebElement element) {
        return element.getLocation().y;
    }

    public Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public Date tomorrow() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        return cal.getTime();
    }

    public Date plusOneYEar() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, +1);
        return cal.getTime();
    }
}

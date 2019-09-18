package tests.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import tests.Utils.TimeConstants;
import tests.Utils.WebDriverUtils;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

@DefaultUrl("https://www.google.com/")
public class ResultsPage extends WebDriverUtils {

    @FindBy(name="btnK")
    private WebElementFacade searchButton;

    @FindBy(css = "table[id=\"nav\"]")
    public WebElementFacade navigationTable;

    public void waitForSearchResults(){
        waitForElement(navigationTable, TimeConstants.MEDIUM_WAIT_TIME).isDisplayed();
    }

    public boolean pageTitleContains(String expected){
        return getDriver().getTitle().contains(expected);
    }
}
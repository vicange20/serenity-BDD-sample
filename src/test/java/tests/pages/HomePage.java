package tests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;
import tests.Utils.WebDriverUtils;

import java.util.List;

@DefaultUrl("https://www.google.com/")
public class HomePage extends WebDriverUtils {

    @FindBy(name="q")
    private WebElementFacade searchInput;

    @FindBy(name="btnK")
    private WebElementFacade searchButton;

    @FindBy(xpath="//a[contains(text(),'No, thanks')]")
    private WebElementFacade dismissButton;

    public void searchForString(String searchItem){
        searchInput.sendKeys(searchItem + Keys.ENTER);
    }

    public boolean iSeeTheDismissButton(){
        boolean exitResult=false;
        try{
            Thread.sleep(1000);
            exitResult=waitForElement(dismissButton).isDisplayed();
        } catch(Exception e) {
        }
        return exitResult;
    }

    public void clickDismissButton(){
         waitForElement(dismissButton).click();
    }

}
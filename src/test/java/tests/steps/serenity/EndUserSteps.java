package tests.steps.serenity;

import net.thucydides.core.steps.ScenarioSteps;
import org.openqa.selenium.Keys;
import tests.Utils.TimeConstants;
import tests.pages.HomePage;
import net.thucydides.core.annotations.Step;
import tests.pages.ResultsPage;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {
    private static final long serialVersionUID = 1L;

    private HomePage homePage;
    private ResultsPage searchResultsPage;

    @Step("Open page")
    public void openPage() {
        homePage.open();
        homePage.getDriver().manage().window().maximize();
    }

    @Step("Search for Anna")
    public void searchForAnna(){
        searchForKeyword("Anna");
    }

    @Step("Search for keyword")
    public void searchForKeyword(String keyword){
        homePage.searchForString(keyword);
    }

    @Step("Wait for search to be executed")
    public void waitForSearch(){
        searchResultsPage.waitForSearchResults();
    }

    @Step("Validate we searched for Anna")
    public void validateWeSearchedForAnna(){
        validateWeSearchedForKeyword("Anna");
    }

    @Step("Validate we searched for keyword")
    public void validateWeSearchedForKeyword(String expression){
        try {
            Thread.sleep(2000);
        } catch (Exception e){}
        assertTrue("Have we not searched for " + expression + "?", searchResultsPage.pageTitleContains(expression));
    }
}
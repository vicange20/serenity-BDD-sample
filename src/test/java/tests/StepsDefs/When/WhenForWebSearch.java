package tests.StepsDefs.When;

import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.steps.serenity.EndUserSteps;

import static tests.Utils.WebDriverUtils.setEnvironment;

public class WhenForWebSearch {
    private static final Logger LOG = LoggerFactory.getLogger(WhenForWebSearch.class);

    @Steps
    private EndUserSteps searchSteps;

    @Before
    public void setUp() {
        Serenity.initializeTestSession();
        setEnvironment();
    }

    @When("^I search Google for \"([^\"]*)\"$")
    public void i_search_google_for_something(String expression) {
        searchSteps.searchForKeyword(expression);
    }
}
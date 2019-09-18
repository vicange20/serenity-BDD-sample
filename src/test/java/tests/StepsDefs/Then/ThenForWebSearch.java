package tests.StepsDefs.Then;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.steps.serenity.EndUserSteps;

import static tests.Utils.WebDriverUtils.setEnvironment;

public class ThenForWebSearch {
    private static final Logger LOG = LoggerFactory.getLogger(ThenForWebSearch.class);

    @Steps
    private EndUserSteps searchSteps;

    @Before
    public void setUp() {
        Serenity.initializeTestSession();
        setEnvironment();
    }

    @Then("^the results I get are related to \"([^\"]*)\"$")
    public void the_results_i_get_are_related_to_something(String keyword) {
        searchSteps.validateWeSearchedForKeyword(keyword);
    }
}
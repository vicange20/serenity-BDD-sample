package tests.StepsDefs.Given;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.steps.serenity.EndUserSteps;

import static tests.Utils.WebDriverUtils.setEnvironment;

public class GivenForWebSearch {
    private static final Logger LOG = LoggerFactory.getLogger(GivenForWebSearch.class);

    @Steps
    private EndUserSteps searchSteps;

    @Before
    public void setUp() {
        Serenity.initializeTestSession();
        setEnvironment();
    }

    @Given("^I open the Google home page$")
    public void i_see_the_google_home_page() {
        searchSteps.openPage();
    }
}
package gianluca.com.stepDefinitions;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.TestContext;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.TestCasePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestCasePageStep extends BaseStepDefinition {
	private static final Logger log = LogManager.getLogger(TestCasePageStep.class);

	private HomePage home;

	private TestCasePage testCasePage;

	public TestCasePageStep(TestContext context) {
		super(context);
	}

	// given the browser is launched esiste gia
	// and I navigate to http_automationexercise_com esiste gia in signu up step
	// then the home page should be visible successfully esiste gia

	private HomePage hp() {
		if (home == null)
			home = context.getHomePage();
		if (home == null)
			throw new IllegalStateException("HomePage non inizializzata. Controlla il Background/Given.");
		return home;
	}

	@When("I click on the button test cases")
	public void clickTestCasePage() {

		log.info("the user click on the page test cases");
		testCasePage = hp().clickPageTestCase();
	}

	@Then("I should be redirected to the {string} page successfully")
	public void verifyTestCasePage(String titlePage) {
		String titleExtracted = testCasePage.getTextTitle();
		log.info("verify that the user is on the page test Cases TITLE PAGE:{}  TITLE EXTRACTED: {}", titlePage,
				titleExtracted);
		boolean ver=titlePage.equalsIgnoreCase(titleExtracted);
		assertTrue(ver, "the title not is equals: test failed");
		log.info("RESULT VERIFY: {}",ver);

	}

}

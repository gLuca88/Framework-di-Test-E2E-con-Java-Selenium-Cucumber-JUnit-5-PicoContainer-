package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.TestContext;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpExisistingEmailStep extends BaseStepDefinition {

	private static final Logger logger = LogManager.getLogger(SignUpExisistingEmailStep.class);

	private HomePage home;
	private LoginPage login;

	// Test data (keep here or move to a data factory/ScenarioContext if you prefer)
	private final String name = "gianluca";
	private final String email = "automationtest@mail.com";
	private final String expectedError = "Email Address already exist!";

	public SignUpExisistingEmailStep(TestContext context) {
		super(context);
	}

	@Given("the browser is launched")
	public void launchedBrowser() {
		logger.info("Browser is launched via Hooks. WebDriver should be available in TestContext.");
	}

	@And("I navigate to http_automationexercise_com")
	public void navigateHomePage() {
		logger.info("Navigating to home page: http://automationexercise.com");
		// goToHomePage() already constructs HomePage(driver) and navigates to the URL
		// (per your TestContext).
		home = context.goToHomePage();

		// Defensive check
		assertTrue(home != null, "HomePage is null. Ensure TestContext.goToHomePage() constructs it correctly.");

		// Accept cookies if banner is present (do not fail the test if not present)
		try {
			logger.info("Attempting to accept cookie banner (if present)...");
			home.clickAccept();
			logger.info("Cookie banner accepted (if it was present).");
		} catch (Exception e) {
			logger.warn("Cookie banner not present or not clickable. Proceeding. Details: {}", e.getMessage());
		}
	}

	@Then("the home page should be visible successfully")
	public void verifyTheHomePage() {
		logger.info("Verifying home page is visible (logo present)...");
		assertTrue(home != null, "HomePage is null before verification.");
		boolean visible = home.verifyLogoPageContainer();
		logger.info("Home page logo visibility result: {}", visible);
		assertTrue(visible, "Home page logo is not visible.");
	}

	@When("I click on Signup_Login button")
	public void clickLoginButton() {
		logger.info("Clicking on 'Signup / Login' button...");
		assertTrue(home != null, "HomePage is null before clicking on 'Signup / Login'.");
		home.clickButtonLogin();

		// Create and store the LoginPage after navigation
		login = new LoginPage(context.getDriver());
		context.setLoginPage(login);
		logger.info("'Signup / Login' button clicked. LoginPage initialized in TestContext.");

		// Optional: wait for a unique element of the login page if you have an explicit
		// wait method
		// login.waitUntilLoaded();
	}

	@Then("I should see New User Signup! section")
	public void verifyVisibilityUserSignUp() {
		logger.info("Verifying that 'New User Signup!' section is visible...");
		// Retrieve from context in case another step populated it
		if (login == null) {
			login = context.getLoginPage();
		}
		assertTrue(login != null, "LoginPage is null before verifying 'New User Signup!'.");

		boolean present = login.messageVerificationRegisterUser();
		logger.info("'New User Signup!' visibility result: {}", present);
		assertTrue(present, "'New User Signup!' section is not visible.");
	}

	@When("I enter name and email already registered")
	public void insertNameEmail() {
		logger.info("Entering registration data with existing email. Name='{}', Email='{}'", name, email);
		if (login == null) {
			login = context.getLoginPage();
		}
		assertTrue(login != null, "LoginPage is null before entering registration data.");
		login.registrationDataEntry(name, email);
		logger.info("Registration data entered.");
	}

	@And("I click on Signup_button")
	public void clickButtonSignUp() {
		logger.info("Clicking on 'Signup' button to submit registration...");
		if (login == null) {
			login = context.getLoginPage();
		}
		assertTrue(login != null, "LoginPage is null before clicking on 'Signup' button.");
		login.clickRegisterSubmit();
		logger.info("'Signup' button clicked.");
	}

	@Then("I should see the error message Email Address already exist!")
	public void verifyTheMessageUserExisting() {
		logger.info("Verifying error message for existing email. Expected='{}'", expectedError);
		if (login == null) {
			login = context.getLoginPage();
		}
		assertTrue(login != null, "LoginPage is null before verifying the error message.");

		String actual = login.getTextMexErrorRegister();
		logger.info("Actual error message='{}', Expected='{}'", actual, expectedError);
		assertTrue(actual != null && actual.equalsIgnoreCase(expectedError),
				String.format("Wrong error message. Expected='%s', Actual='%s'", expectedError, actual));
	}
}

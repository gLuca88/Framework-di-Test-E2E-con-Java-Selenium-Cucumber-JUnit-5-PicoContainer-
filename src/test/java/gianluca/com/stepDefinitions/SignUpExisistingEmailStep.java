package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.TestRegistrerDelete.SignupData;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpExisistingEmailStep extends BaseStepDefinition {

	private static final Logger logger = LogManager.getLogger(SignUpExisistingEmailStep.class);

	private SignupData user;

	public SignUpExisistingEmailStep(TestContext context) {
		super(context);
	}

	private HomePage hp() {
		HomePage h = context.getHomePage();
		if (h == null)
			throw new IllegalStateException("HomePage non inizializzata. Esegui la navigazione alla home nel Given.");
		return h;
	}

	private LoginPage lp() {
		LoginPage l = context.getLoginPage();
		if (l == null)
			throw new IllegalStateException("LoginPage non presente. Clicca prima su Signup/Login.");
		return l;
	}

	private SignupData user() {
		if (user == null) {
			// prende l’istanza caricata dall’@Before @login e la mette in cache
			user = context.getScenarioContext().require(ContextKey.SIGNUP_DATA, SignupData.class);
		}
		return user; // dalle chiamate successive ritorna sempre la stessa istanza
	}

	@Given("the browser is launched")
	public void launchedBrowser() {
		logger.info("Browser is launched via Hooks. WebDriver should be available in TestContext.");
	}

	@And("I navigate to http_automationexercise_com")
	public void navigateHomePage() {
		logger.info("Navigating to home page: http://automationexercise.com");
		HomePage home = context.goToHomePage();

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
		boolean visible = hp().verifyLogoPageContainer();
		logger.info("Home page logo visibility result: {}", visible);
		assertTrue(visible, "Home page logo is not visible.");
	}

	@When("I click on Signup_Login button")
	public void clickLoginButton() {
		logger.info("Clicking on 'Signup / Login' button...");
		LoginPage login = hp().clickButtonLogin();
		context.setLoginPage(login);
		logger.info("'Signup / Login' button clicked. LoginPage initialized in TestContext.");
	}

	@Then("I should see New User Signup! section")
	public void verifyVisibilityUserSignUp() {
		logger.info("Verifying that 'New User Signup!' section is visible...");
		assertTrue(lp() != null, "LoginPage is null before verifying 'New User Signup!'.");
		boolean present = lp().messageVerificationRegisterUser();
		logger.info("'New User Signup!' visibility result: {}", present);
		assertTrue(present, "'New User Signup!' section is not visible.");
	}

	@When("I enter name and email already registered")
	public void insertNameEmail() {

		SignupData user = user();
		logger.info("Entering registration data with existing email. Name='{}', Email='{}'", user.getName(),
				user.getEmail());
		lp().registrationDataEntry(user.getName(), user.getEmail());
		logger.info("Registration data entered.");
	}

	@And("I click on Signup_button")
	public void clickButtonSignUp() {
		logger.info("Clicking on 'Signup' button to submit registration...");

		lp().clickRegisterSubmit();
		logger.info("'Signup' button clicked.");
	}

	@Then("I should see the error message Email Address already exist!")
	public void verifyTheMessageUserExisting() {
		SignupData user = user();
		logger.info("Verifying error message for existing email. Expected='{}'", user.getExpectedError());

		String actual = lp().getTextMexErrorRegister();
		logger.info("Actual error message='{}', Expected='{}'", actual, user.getExpectedError());
		assertTrue(actual != null && actual.equalsIgnoreCase(user.getExpectedError()),
				String.format("Wrong error message. Expected='%s', Actual='%s'", user.getExpectedError(), actual));
	}
}

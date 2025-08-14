package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.testLogin.LoginData;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginValidStep extends BaseStepDefinition {

	private static final Logger logger = LogManager.getLogger(LoginValidStep.class);

	private HomePage home;
	private LoginPage login;
	private LoginData user;

	public LoginValidStep(TestContext context) {
		super(context);
	}

	private LoginData user() {
		if (user == null) {
			// prende l’istanza caricata dall’@Before @login e la mette in cache
			user = context.getScenarioContext().require(ContextKey.LOGIN_USER, LoginData.class);
		}
		return user; // dalle chiamate successive ritorna sempre la stessa istanza
	}
	

	private HomePage hp() {
		if (home == null)
			home = context.getHomePage();
		if (home == null)
			throw new IllegalStateException("HomePage non inizializzata. Controlla il Background/Given.");
		return home;
	}

	private LoginPage lp() {
		if (login == null)
			login = context.getLoginPage();
		if (login == null)
			throw new IllegalStateException("LoginPage non presente. Esegui prima il click su Signup/Login.");
		return login;
	}

	@Given("the user opens the browser and navigates to the homepage")
	public void goTheHome() {
		logger.info("Navigating to homepage...");
		home = context.goToHomePage(); // salva anche nel context
		logger.debug("Homepage loaded, attempting to accept cookie banner if present...");
		home.clickAccept();
	}

	@When("the user clicks on the signup_login button")
	public void clickLoginButton() {
		logger.info("Clicking on the Signup/Login button...");
		login = hp().clickButtonLogin();
		context.setLoginPage(login); // tieni sincronizzato il TestContext
	}

	@And("the user enters valid email and password and clicks the login button")
	public void insertCredential() {
		LoginData user = user();
		assertNotNull(user, "Login user not found in scenario context");
		logger.info("Entering credentials for user: {}", user.getEmail());
		lp().insertCredential(user.getEmail(), user.getPassword());
		logger.debug("Login user in context: {}", user.getEmail());
	}

	@Then("the user should see the logout button and delete account button")
	public void verifyLogOutButtonDeleteButton() {
		logger.info("Verifying presence of logout and delete account buttons...");
		boolean logoutVisible = hp().verifyButtonLogOut();
		boolean deleteVisible = hp().verifyButtonDeleteAccount();

		assertTrue(logoutVisible, "Logout button should be visible");
		assertTrue(deleteVisible, "Delete account button should be visible");

		logger.info("Verification successful: logout={}, delete={}", logoutVisible, deleteVisible);
	}

	@And("the user enters {string} and {string} as invalid credentials")
	public void insertInvalidCredential(String email, String password) {
		logger.info("Entering invalid credentials email={} password=******", email);
		lp().insertCredential(email, password);
	}

	@Then("the user should see the error message {string}")
	public void verifyInvalidLoginMessage(String expectedMessage) {
		logger.info("Checking error message: {}", expectedMessage);
		boolean shown = lp().verifyMexError(expectedMessage);
		assertTrue(shown, "Expected error message not found: " + expectedMessage);
		logger.info("Error message present: {}", shown);
	}

	@And("the user clicks on the logout button")
	public void clickButtonLogOut() {
		boolean containerVisible = hp().verifyContainerLoggerVisibility();
		assertTrue(containerVisible, "User container should be visible before logout");
		logger.info("Container visible before logout: {}", containerVisible);

		logger.info("User clicks on the Logout button...");
		hp().clickButtonLogOut();
	}

	@Then("the user should be redirected to the login page and the user info should not be visible")
	public void verifyTheLoggerButtonAndUrl() {

		LoginData user = user();// usa la cache, non ricrea nulla
		logger.info("Check that the logged-in user container is no longer visible after logout");
		boolean containerVisible = hp().verifyContainerLoggerVisibility();
		assertFalse(containerVisible, "User container should not be visible after logout");
		logger.info("Container visible after logout: {}", containerVisible);

		logger.info("Check that URL is the login page");
		String actualUrl = lp().getUrlLogin();
		String expectedUrl = user.getUrlAtteso();
		assertEquals(expectedUrl, actualUrl, "User should be redirected to login page");
		logger.info("Actual URL: {} | Expected URL: {}", actualUrl, expectedUrl);
	}
}

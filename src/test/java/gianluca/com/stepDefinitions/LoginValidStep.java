package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.LoginData;
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

	public LoginValidStep(TestContext context) {
		super(context);
	}

	LoginData user = context.getScenarioContext().get(ContextKey.LOGIN_USER, LoginData.class);

	@Given("the user opens the browser and navigates to the homepage")
	public void goTheHome() {
		logger.info("Navigating to homepage...");
		home = context.goToHomePage();
		logger.debug("Homepage loaded, attempting to accept cookie banner if present...");
		home.clickAccept();

	}

	@When("the user clicks on the signup_login button")
	public void clickLoginButton() {
		logger.info("Clicking on the Signup/Login button...");
		login = home.clickButtonLogin();
	}

	@And("the user enters valid email and password and clicks the login button")
	public void insertCredential() {
		assertNotNull(user, "Login user not found in scenario context");
		logger.info("Entering credentials for user: {}", user.getEmail());
		login.insertCredential(user.getEmail(), user.getPassword());
		logger.debug("Login user in context: {}", user.getEmail());
	}

	@Then("the user should see the logout button and delete account button")
	public void verifyLogOutButtonDeleteButton() {
		logger.info("Verifying presence of logout and delete account buttons...");
		boolean logoutVisible = home.verifyButtonLogOut();
		boolean deleteVisible = home.verifyButtonDeleteAccount();

		assertTrue(logoutVisible, "Logout button should be visible");
		assertTrue(deleteVisible, "Delete account button should be visible");

		logger.info("Verification successful: logout={}, delete={}", logoutVisible, deleteVisible);
	}

	@And("the user enters {string} and {string} as invalid credentials")
	public void insertInvalidCredential(String email, String password) {
		logger.info("Entering credentials invalid like user: {} password {}", email, password);
		login.insertCredential(email, password);
	}

	@Then("the user should see the error message {string}")
	public void verifyInvalidLoginMessage(String expectedMessage) {
		logger.info("Checking error message: {}", expectedMessage);
		boolean shown = login.verifyMexError(expectedMessage);
		assertTrue(shown, "Expected error message not found: " + expectedMessage);
		logger.info("Error message present: {}", shown);
	}

	@And("the user clicks on the logout button")
	public void clickButtonLogOut() {
		boolean containerVisible = home.verifyContainerLoggerVisibility();
		assertTrue(containerVisible, "User container should be visible before logout");
		logger.info("verify if container user is present before log out...---->{}", containerVisible);

		logger.info("User clicks on the button logOut after login...");
		home.clickButtonLogOut();
	}

	@Then("the user should be redirected to the login page and the user info should not be visible")
	public void verifyTheLoggerButtonAndUrl() {
		logger.info("check after logging out that the div container with the logged in user data is no longer visible");
		boolean containerVisible = home.verifyContainerLoggerVisibility();
		assertFalse(containerVisible, "User container should not be visible after logout");
		logger.info("result control div:-->{}", containerVisible);

		logger.info("second check that the URL is that of the login page");
		String actualUrl = login.getUrlLogin();
		String expectedUrl = user.getUrlAtteso();
		assertEquals(actualUrl, expectedUrl, "User should be redirected to login page");
		logger.info("result url estratto:-->{}  url atteso-->{}", actualUrl, expectedUrl);
	}
}

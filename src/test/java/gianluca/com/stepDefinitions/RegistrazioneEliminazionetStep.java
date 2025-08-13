package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.TestContext;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;
import gianluca.com.pageobject.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrazioneEliminazionetStep extends BaseStepDefinition {

	private static final Logger logger = LogManager.getLogger(RegistrazioneEliminazionetStep.class);

	String name = "tester gianluca";
	String email = "testergianluca@example.com";
	String title = "Enter Account Information";

	String password = "Password2!";

	String day = "10";
	String month = "12";
	String year = "1995";

	public RegistrazioneEliminazionetStep(TestContext context) {
		super(context);
	}

	private HomePage homePage;
	private LoginPage login;
	private RegisterPage registrationPage;
	String expectedText = "New User Signup!";

	// given navigazione nell'home è in login steps

	@Then("the home page must be correctly visible")
	public void verifyThePresenceOfHomePage() {
		if (homePage == null) {
			homePage = context.getHomePage();
		}
		if (homePage == null) {
			throw new IllegalStateException(
					"HomePage not initialized in TestContext. Check the Given step in the Background.");
		}
		logger.info("User navigates to the home page.");
		boolean ver = homePage.verifyLogoPageContainer();
		assertTrue(ver);
		logger.info("Home page visibility check result: {}", ver);
	}

	// when-->click login button is in the login steps and the object login it's
	// created in that step

	@Then("The text Register new user! must be visible")
	public void messageVerificationRegisterUser() {
		if (login == null) {
			login = context.getLoginPage();
		}
		if (login == null) {
			throw new IllegalStateException(
					"LoginPage not found in TestContext. Perform the click on Signup/Login and store it in context first.");
		}
		logger.info("Verifying that the 'New User Signup!' message is visible.");
		boolean ver = login.messageVerificationRegisterUser();
		logger.info("Message visibility check result: {}", ver);
		assertTrue(ver);
		String textPage = login.getTextContanerRegisterUser();
		logger.info("Expected text: '{}' | Actual text: '{}'", expectedText, textPage);
		assertEquals(expectedText, textPage, "The text on the page does not match the expected value.");
		logger.info("Page text successfully verified ✅");
	}

	@When("The user enters his_her name and email address to register")
	public void InsertNameEmailRegistration() {
		if (login == null) {
			login = context.getLoginPage();
		}
		if (login == null) {
			throw new IllegalStateException(
					"LoginPage not found in TestContext. Perform the click on Signup/Login first.");
		}
		logger.info("Entering name and email for registration.");
		login.registrationDataEntry(name, email);
		logger.info("Name entered: '{}' | Email entered: '{}'", name, email);
	}

	@And("the user clicks on the Register button")
	public void clickRegister() {
		if (login == null) {
			login = context.getLoginPage();
		}
		if (login == null) {
			throw new IllegalStateException(
					"LoginPage not found in TestContext. Perform the click on Signup/Login first.");
		}
		logger.info("Clicking on the Register button and navigating to the registration page.");
		registrationPage = login.clickRegisterSubmit();
	}

	@Then("the text ENTER ACCOUNT INFORMATION must be visible")
	public void verifyTheMassageEnterAccount() {
		logger.info("Verifying that the title 'ENTER ACCOUNT INFORMATION' is visible.");
		boolean ver = registrationPage.verifyTextContainerRegistration(title);
		assertTrue(ver);
		logger.info("Expected title: '{}' | Result: {}", title, ver);
	}

	@When("the user enters the account data")
	public void insertDataAccount() {

		logger.info("Selecting the title radio button.");
		logger.info("Entering account name: '{}' | Email: '{}' | Password: '{}'", name, email, password);
		registrationPage.insertAccountDataPartOne(name, password);
		logger.info("Entering date of birth: {}/{}/{}", day, month, year);
		registrationPage.insertData(day, month, year);

	}

	@And("select the Subscribe to our newsletter! checkbox")
	public void clickNewSletter() {
		logger.info("Selecting the 'Subscribe to our newsletter!' checkbox.");
		registrationPage.clickNewSletter();
	}

	@And("Select the Receive special offers from our partners! checkbox")
	public void clickNewReceiveOffers() {
		logger.info("Selecting the 'Receive special offers from our partners!' checkbox.");
		registrationPage.clickRiceverOffert();
	}

	String nome = "Test";
	String cognome = "User";
	String azienda = "MyCompany";
	String indirizzo = "Via Roma 1";
	String Indirizzo2 = "Scala A";
	String paese = "United States";
	String provincia = "Lazio";
	String citta = "Roma";
	String cap = "00100";
	String numeroCell = "3331234567";
	String mexAttesoCreatedAcc = "Account Created!";
	String mexDeleteAccount = "Account Deleted!";

	@And("the user enters the address data")
	public void insertAddressData() throws InterruptedException {
		logger.info("Filling in the address details.");
		registrationPage.insertAddressData(nome, cognome, azienda, indirizzo, Indirizzo2, paese, provincia, citta, cap,
				numeroCell);

	}

	@And("the user clicks on the Create Account button")
	public void clickButtonCreateAccount() {
		logger.info("Clicking on the 'CREATE ACCOUNT' button.");
		registrationPage.clickCreateAccount();

	}

	@Then("the text ACCOUNT CREATED! must be visible")
	public void verifyMexAccountCreated() {
		logger.info("Verifying that the 'ACCOUNT CREATED!' message is visible.");
		String mexPagina = registrationPage.verifiyMexCreatedAccount(mexAttesoCreatedAcc);
		logger.info("Message on page: '{}' | Expected message: '{}'", mexPagina, mexAttesoCreatedAcc);
		assertTrue(mexPagina != null && mexPagina.equalsIgnoreCase(mexAttesoCreatedAcc), "Il messaggio non coincide");

	}

	@When("the user clicks the Continue button")
	public void clickContinueButtonAfterRegister() {
		logger.info("Clicking the 'Continue' button after registration.");
		registrationPage.clickButtonContinueAfterRegistrer();
	}

	@Then("You are logged in with your username should be visible")
	public void verifyLoggedUser() {
		logger.info("Checking if the logged-in user container is visible and extracting the username.");
		boolean ver = homePage.verifyContainerLoggerVisibility();
		logger.info("Container visibility: {}", ver);
		assertTrue(ver);
		String textLogged = homePage.getTextLoggedInAs();
		logger.info("Displayed name: '{}' | Expected account name: '{}'", textLogged, name);
		assertTrue(textLogged.contains(name));
	}

	@When("the user clicks the Delete Account button")
	public void clickDeleteAccount() {
		logger.info("Clicking the 'Delete Account' button.");
		homePage.clickDeleteButton();
	}

	@Then("the text ACCOUNT DELETED! should be visible")
	public void verifyMexDeleteAccount() {
		logger.info("Verifying that the 'ACCOUNT DELETED!' message is visible.");
		String textPage = homePage.getTextMex();
		logger.info("Message on page: '{}' | Expected message: '{}'", textPage, mexDeleteAccount);
		assertTrue(textPage != null && textPage.equalsIgnoreCase(mexDeleteAccount), "Message does not match.");

	}

	@And("the user clicks on the Continue button")
	public void clickContinueAfterDeleteAccount() {
		logger.info("Clicking the 'Continue' button after deleting the account.");
		homePage.clickButtonContinue();
		logger.info("END OF TEST EXECUTION");

	}

}

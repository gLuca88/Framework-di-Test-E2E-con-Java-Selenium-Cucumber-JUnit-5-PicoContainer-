package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.testRegistrerDelete.Address;
import gianluca.com.model.testRegistrerDelete.BirthDate;
import gianluca.com.model.testRegistrerDelete.RegistrationData;
import gianluca.com.model.testRegistrerDelete.RegistrationUiText;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;
import gianluca.com.pageobject.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrazioneEliminazionetStep extends BaseStepDefinition {

	private static final Logger logger = LogManager.getLogger(RegistrazioneEliminazionetStep.class);

	public RegistrazioneEliminazionetStep(TestContext context) {
		super(context);
	}

	
	private RegisterPage registrationPage; // ipotizzo il nome della pagina di registrazione

	// CACHE per lo scenario
	private RegistrationData reg;
	private RegistrationUiText ui;

	private RegistrationData reg() {
		if (reg == null) {
			reg = context.getScenarioContext().require(ContextKey.REGISTRATION_DATA, RegistrationData.class);
		}
		return reg;
	}

	private RegistrationUiText ui() {
		if (ui == null) {
			ui = context.getScenarioContext().require(ContextKey.REGISTRATION_UI_TEXT, RegistrationUiText.class);
		}
		return ui;
	}

	 private HomePage hp() {
	        HomePage h = context.getHomePage();
	        if (h == null) throw new IllegalStateException("HomePage not initialized. Check Given/Background.");
	        return h;
	    }

	 private LoginPage lp() {
	        LoginPage l = context.getLoginPage();
	        if (l == null) throw new IllegalStateException("LoginPage not available. Click Signup/Login first.");
	        return l;
	    }
	 private RegisterPage rp() {
	        if (registrationPage == null)
	            throw new IllegalStateException("RegistrationPage not initialized. Click 'Register' first.");
	        return registrationPage;
	    }

	@Then("the home page must be correctly visible")
	public void verifyThePresenceOfHomePage() {
		logger.info("Verify HomePage visibility");
		assertTrue(hp().verifyLogoPageContainer(), "Home page not visible");
	}

	@Then("The text Register new user! must be visible")
	public void messageVerificationRegisterUser() {
		logger.info("Verify UI text 'New User Signup!': expected='{}'", ui().getNewUserSignupMessage());
		assertTrue(lp().messageVerificationRegisterUser(), "'New User Signup!' not visible");
		String actual = lp().getTextContanerRegisterUser();
		assertEquals(ui().getNewUserSignupMessage(), actual, "Unexpected 'New User Signup!' text");
	}

	@When("The user enters his_her name and email address to register")
	public void insertNameEmailRegistration() {
		logger.info("Enter name='{}' email='{}'", reg().getName(), reg().getEmail());
		lp().registrationDataEntry(reg().getName(), reg().getEmail());
	}

	@And("the user clicks on the Register button")
	public void clickRegister() {
		logger.info("Click 'Register' and navigate to registration page");
		registrationPage = lp().clickRegisterSubmit(); // deve restituire la pagina
		if (registrationPage == null)
			throw new IllegalStateException("RegisterPage is null after clicking Register.");
	}

	@Then("the text ENTER ACCOUNT INFORMATION must be visible")
	public void verifyTheMassageEnterAccount() {
		logger.info("Verify section title: '{}'", ui().getEnterAccountInformationTitle());
		boolean ok = rp().verifyTextContainerRegistration(ui().getEnterAccountInformationTitle());
		assertTrue(ok, "'ENTER ACCOUNT INFORMATION' not visible or wrong");
	}

	@When("the user enters the account data")
	public void insertDataAccount() {
		logger.info("Fill account data + birth date");
		rp().insertAccountDataPartOne(reg().getName(), reg().getPassword());
		BirthDate b = reg().getBirthDate();
		rp().insertData(b.getDay(), b.getMonth(), b.getYear());
	}

	@And("select the Subscribe to our newsletter! checkbox")
	public void clickNewSletter() {
		if (reg().getPreferences() != null && reg().getPreferences().isSubscribeNewsletter()) {
			logger.info("Select 'Subscribe to our newsletter!'");
			rp().clickNewSletter();
		} else {
			logger.info("Preference subscribeNewsletter=false: skip checkbox");
		}
	}

	@And("Select the Receive special offers from our partners! checkbox")
	public void clickNewReceiveOffers() {
		if (reg().getPreferences() != null && reg().getPreferences().isReceiveOffers()) {
			logger.info("Select 'Receive special offers from our partners!'");
			rp().clickRiceverOffert();
		} else {
			logger.info("Preference receiveOffers=false: skip checkbox");
		}
	}

	@And("the user enters the address data")
	public void insertAddressData() {
		logger.info("Fill address data");
		Address a = reg().getAddress();
		rp().insertAddressData(a.getFirstName(), a.getLastName(), a.getCompany(), a.getAddress1(),
				a.getAddress2(), a.getCountry(), a.getState(), a.getCity(), a.getZip(), a.getPhone());
	}

	@And("the user clicks on the Create Account button")
	public void clickButtonCreateAccount() {
		logger.info("Click 'CREATE ACCOUNT'");
		rp().clickCreateAccount();
	}

	@Then("the text ACCOUNT CREATED! must be visible")
	public void verifyMexAccountCreated() {
		logger.info("Verify 'ACCOUNT CREATED!': expected='{}'", ui().getAccountCreatedMessage());
		String actual = rp().verifiyMexCreatedAccount(ui().getAccountCreatedMessage());
		assertTrue(actual != null && actual.equalsIgnoreCase(ui().getAccountCreatedMessage()),
				"Account creation message mismatch");
	}

	@When("the user clicks the Continue button")
	public void clickContinueButtonAfterRegister() {
		logger.info("Click 'Continue' after registration");
		rp().clickButtonContinueAfterRegistrer();
	}

	@Then("You are logged in with your username should be visible")
	public void verifyLoggedUser() {
		logger.info("Verify 'Logged in as' contains name '{}'", reg().getName());
		assertTrue(hp().verifyContainerLoggerVisibility(), "Logged-in user container not visible");
		String textLogged = hp().getTextLoggedInAs();
		assertTrue(textLogged != null && textLogged.contains(reg().getName()),
				"Logged-in container does not contain expected user name");
	}

	@When("the user clicks the Delete Account button")
	public void clickDeleteAccount() {
		logger.info("Click 'Delete Account'");
		hp().clickDeleteButton();
	}

	@Then("the text ACCOUNT DELETED! should be visible")
	public void verifyMexDeleteAccount() {
		logger.info("Verify 'ACCOUNT DELETED!': expected='{}'", ui().getAccountDeletedMessage());
		String textPage = hp().getTextMex();
		assertTrue(textPage != null && textPage.equalsIgnoreCase(ui().getAccountDeletedMessage()),
				"Account deletion message mismatch");
	}

	@And("the user clicks on the Continue button")
	public void clickContinueAfterDeleteAccount() {
		logger.info("Click 'Continue' after account deletion");
		hp().clickButtonContinue();
		logger.info("END OF TEST EXECUTION");
	}
}

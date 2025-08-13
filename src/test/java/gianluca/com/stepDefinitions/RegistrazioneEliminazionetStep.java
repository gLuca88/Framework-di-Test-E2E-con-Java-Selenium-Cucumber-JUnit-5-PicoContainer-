package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.TestRegistrerDelete.Address;
import gianluca.com.model.TestRegistrerDelete.RegistrationData;
import gianluca.com.model.TestRegistrerDelete.RegistrationUiText;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;
import gianluca.com.pageobject.RegisterPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrazioneEliminazionetStep extends BaseStepDefinition {

	private static final Logger logger = LogManager.getLogger(RegistrazioneEliminazionetStep.class);

	// ========= campi "stile login" (scenario-scoped) =========
	private RegistrationData reg; // caricato una volta in @Before
	private RegistrationUiText ui; // caricato una volta in @Before

	private HomePage homePage;
	private LoginPage login;
	private RegisterPage registrationPage;

	public RegistrazioneEliminazionetStep(TestContext context) {
		super(context);
	}

	// Carico i POJO dal ScenarioContext una sola volta, dopo l'hook JSON (order=1)
	@Before(value = "@register", order = 2)
	public void cacheRegistrationDataFromContext() {
		this.reg = context.getScenarioContext().get(ContextKey.REGISTRATION_DATA, RegistrationData.class);
		if (this.reg == null) {
			throw new IllegalStateException(
					"REGISTRATION_DATA mancante in ScenarioContext. Verifica l'hook @Before @account (order=1).");
		}
		this.ui = context.getScenarioContext().get(ContextKey.REGISTRATION_UI_TEXT, RegistrationUiText.class);
		if (this.ui == null) {
			throw new IllegalStateException(
					"REGISTRATION_UI_TEXT mancante in ScenarioContext. Verifica l'hook @Before @account (order=1).");
		}
		logger.info("[Cache] REG & UI caricati nello step: {}, {}", reg, ui);
	}

	// ===== util lazy per le Page =====
	private HomePage hp() {
		if (homePage == null)
			homePage = context.getHomePage();
		if (homePage == null) {
			throw new IllegalStateException(
					"HomePage non inizializzata nel TestContext. Controlla il Given/Background.");
		}
		return homePage;
	}

	private LoginPage lp() {
		if (login == null)
			login = context.getLoginPage();
		if (login == null) {
			throw new IllegalStateException(
					"LoginPage non presente nel TestContext. Esegui il click su Signup/Login prima.");
		}
		return login;
	}

	// ===================== STEPS =====================

	@Then("the home page must be correctly visible")
	public void verifyThePresenceOfHomePage() {
		logger.info("Verifica visibilità HomePage");
		boolean visible = hp().verifyLogoPageContainer();
		logger.info("Home page visibility: {}", visible);
		assertTrue(visible);
	}

	@Then("The text Register new user! must be visible")
	public void messageVerificationRegisterUser() {
		logger.info("Verifico messaggio '{}' nella pagina di login", ui.getNewUserSignupMessage());
		boolean visible = lp().messageVerificationRegisterUser();
		assertTrue(visible, "Messaggio 'New User Signup!' non visibile");

		String actual = lp().getTextContanerRegisterUser();
		logger.info("Atteso: '{}' | Attuale: '{}'", ui.getNewUserSignupMessage(), actual);
		assertEquals(ui.getNewUserSignupMessage(), actual, "Il testo pagina non coincide con l'atteso.");
	}

	@When("The user enters his_her name and email address to register")
	public void insertNameEmailRegistration() {
		logger.info("Inserisco name='{}' email='{}'", reg.getName(), reg.getEmail());
		lp().registrationDataEntry(reg.getName(), reg.getEmail());
	}

	@And("the user clicks on the Register button")
	public void clickRegister() {
		logger.info("Click su 'Register' e navigazione alla pagina di registrazione");
		registrationPage = lp().clickRegisterSubmit();
		if (registrationPage == null) {
			throw new IllegalStateException("RegisterPage è null dopo il click su Register.");
		}
	}

	@Then("the text ENTER ACCOUNT INFORMATION must be visible")
	public void verifyTheMassageEnterAccount() {
		logger.info("Verifico titolo sezione account: '{}'", ui.getEnterAccountInformationTitle());
		boolean ok = registrationPage.verifyTextContainerRegistration(ui.getEnterAccountInformationTitle());
		assertTrue(ok, "Titolo sezione 'Enter Account Information' non visibile o errato");
	}

	@When("the user enters the account data")
	public void insertDataAccount() {
		logger.info("Inserimento dati account + data di nascita");
		registrationPage.insertAccountDataPartOne(reg.getName(), reg.getPassword());
		registrationPage.insertData(reg.getBirthDate().getDay(), reg.getBirthDate().getMonth(),
				reg.getBirthDate().getYear());
	}

	@And("select the Subscribe to our newsletter! checkbox")
	public void clickNewSletter() {
		if (reg.getPreferences() != null && reg.getPreferences().isSubscribeNewsletter()) {
			logger.info("Seleziono checkbox 'Subscribe to our newsletter!'");
			registrationPage.clickNewSletter();
		} else {
			logger.info("Preferenza newsletter=false: salto selezione checkbox");
		}
	}

	@And("Select the Receive special offers from our partners! checkbox")
	public void clickNewReceiveOffers() {
		if (reg.getPreferences() != null && reg.getPreferences().isReceiveOffers()) {
			logger.info("Seleziono checkbox 'Receive special offers from our partners!'");
			registrationPage.clickRiceverOffert();
		} else {
			logger.info("Preferenza receiveOffers=false: salto selezione checkbox");
		}
	}

	@And("the user enters the address data")
	public void insertAddressData() {
		logger.info("Inserimento dati indirizzo");
		Address a = reg.getAddress();
		registrationPage.insertAddressData(a.getFirstName(), a.getLastName(), a.getCompany(), a.getAddress1(),
				a.getAddress2(), a.getCountry(), a.getState(), a.getCity(), a.getZip(), a.getPhone());
	}

	@And("the user clicks on the Create Account button")
	public void clickButtonCreateAccount() {
		logger.info("Click su 'CREATE ACCOUNT'");
		registrationPage.clickCreateAccount();
	}

	@Then("the text ACCOUNT CREATED! must be visible")
	public void verifyMexAccountCreated() {
		logger.info("Verifico messaggio creazione account: '{}'", ui.getAccountCreatedMessage());
		String actual = registrationPage.verifiyMexCreatedAccount(ui.getAccountCreatedMessage());
		logger.info("Atteso: '{}' | Pagina: '{}'", ui.getAccountCreatedMessage(), actual);
		assertTrue(actual != null && actual.equalsIgnoreCase(ui.getAccountCreatedMessage()),
				"Il messaggio di creazione account non coincide");
	}

	@When("the user clicks the Continue button")
	public void clickContinueButtonAfterRegister() {
		logger.info("Click su 'Continue' post-registrazione");
		registrationPage.clickButtonContinueAfterRegistrer();
	}

	@Then("You are logged in with your username should be visible")
	public void verifyLoggedUser() {
		logger.info("Verifico container 'Logged in as' visibile e che contenga il nome '{}'", reg.getName());
		boolean visible = hp().verifyContainerLoggerVisibility();
		assertTrue(visible, "Container utente loggato non visibile");

		String textLogged = hp().getTextLoggedInAs();
		logger.info("Testo visualizzato: '{}'", textLogged);
		assertTrue(textLogged != null && textLogged.contains(reg.getName()),
				"Il container non contiene il nome utente atteso");
	}

	@When("the user clicks the Delete Account button")
	public void clickDeleteAccount() {
		logger.info("Click su 'Delete Account'");
		hp().clickDeleteButton();
	}

	@Then("the text ACCOUNT DELETED! should be visible")
	public void verifyMexDeleteAccount() {
		logger.info("Verifico messaggio eliminazione account: '{}'", ui.getAccountDeletedMessage());
		String textPage = hp().getTextMex();
		logger.info("Atteso: '{}' | Pagina: '{}'", ui.getAccountDeletedMessage(), textPage);
		assertTrue(textPage != null && textPage.equalsIgnoreCase(ui.getAccountDeletedMessage()),
				"Il messaggio di eliminazione non coincide");
	}

	@And("the user clicks on the Continue button")
	public void clickContinueAfterDeleteAccount() {
		logger.info("Click su 'Continue' post-eliminazione account");
		hp().clickButtonContinue();
		logger.info("END OF TEST EXECUTION");
	}
}

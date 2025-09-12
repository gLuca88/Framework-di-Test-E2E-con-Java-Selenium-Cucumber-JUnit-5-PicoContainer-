package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.TestContacts.ContactData;
import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.PageContacts;
import gianluca.com.utilis.FilePathUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactUsFormSubmissionStep extends BaseStepDefinition {

	private static final Logger log = LogManager.getLogger(ContactUsFormSubmissionStep.class);
	private PageContacts contacts;
	private ContactData datiContacts;

	public ContactUsFormSubmissionStep(TestContext context) {
		super(context);

	}

	private HomePage hp() {
		HomePage h = context.getHomePage();
		if (h == null) {
			throw new IllegalStateException(
					"HomePage non inizializzata. Assicurati di aver navigato alla home nel Given.");
		}
		return h;
	}

	private PageContacts cp() {
		if (contacts == null) {
			throw new IllegalStateException("ContactUs page non inizializzata. Esegui prima il click su 'Contact Us'.");
		}
		return contacts;
	}

	private ContactData datiJSON() {
		if (datiContacts == null) {
			// prende l’istanza caricata dall’@Before @login e la mette in cache
			datiContacts = context.getScenarioContext().require(ContextKey.CONTACTS_DATA, ContactData.class);
		}
		return datiContacts; // dalle chiamate successive ritorna sempre la stessa istanza
	}

	// given and AND IN STEP PRECEDENTI

	@When("I click on Contact Us")
	public void clickContactUs() {
		log.info("[ContactUs] Clicking on 'Contact Us' button");
		contacts = hp().clickContactUs();
		log.info("[ContactUs] User is redirected to the Contact Us page");
	}

	@Then("GET IN TOUCH is visible")
	public void verifyThatMexIsVisible() {
		ContactData datiContacts = datiJSON();
		log.info("[ContactUs] Extracting header text from Contact Us page...");
		String mexExtracted = cp().getTextContainerMexForm();
		log.info("[ContactUs] Message extracted: '{}' | Expected: '{}'", mexExtracted,
				datiContacts.getMessageExpected());
		System.out.println(mexExtracted);
		assertNotNull(mexExtracted, "❌ Il messaggio estratto è null, impossibile confrontare!");
		assertEquals(datiContacts.getMessageExpected().toLowerCase(), mexExtracted.toLowerCase(),
				"❌ Extracted message does not match the expected one (case-insensitive)!");
	}

	@When("I enter name name, email , subject , and message")
	public void insertNameEmailSubject() {
		ContactData datiContacts = datiJSON();
		log.info("[ContactUs] Inserting data -> Name: '{}', Email: '{}', Subject: '{}', Message: '{}'",
				datiContacts.getName(), datiContacts.getEmail(), datiContacts.getSubject(), datiContacts.getMessage());
		cp().contactDataEntry(datiContacts.getName(), datiContacts.getEmail(), datiContacts.getSubject(),
				datiContacts.getMessage());

	}

	@And("I upload the file filePath")
	public void insertFilePath() {
		ContactData datiContacts = datiJSON();
		log.info("[ContactUs] Uploading file: {}", datiContacts.getFilePath());
		String absolute = FilePathUtils.toAbsolutePath(datiContacts.getFilePath());
		log.info("[ContactUs] Absolute path resolved: {}", absolute);
		cp().insertPathFile(absolute);
	}

	@And("I click Submit")
	public void clickSubMit() {
		log.info("[ContactUs] Clicking on 'Submit' button...");
		cp().clickSubMitButton();

	}

	@And("I accept the confirmation dialog")
	public void acceptAlert() {
		log.info("[ContactUs] Accepting confirmation alert...");
		cp().acceptAlert();
	}

	@Then("the success message Success! Your details have been submitted successfully. is visible")
	public void verifyTheMassageConfirm() {
		ContactData datiContacts = datiJSON();
		log.info("[ContactUs] Verifying success message after form submission...");
		String messageExtracted = cp().textMessageSuccess();
		log.info("[ContactUs] Message extracted: '{}' | Expected: '{}'", messageExtracted,
				datiContacts.getMessageExpectedAfetrConfirm());
		assertNotNull(messageExtracted, "❌ Extracted message is null, cannot compare!");
		assertEquals(datiContacts.getMessageExpectedAfetrConfirm().toLowerCase(), messageExtracted.toLowerCase(),
				"❌ Extracted message does not match the expected one (case-insensitive)!");
	}

	@When("I click on Home")
	public void clickHome() {
		log.info("[ContactUs] Clicking on 'Home' button...");
		cp().clickHome();
	}

	// verifica home signupexisitingstep

}

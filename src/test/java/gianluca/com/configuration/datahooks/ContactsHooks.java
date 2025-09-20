package gianluca.com.configuration.datahooks;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.testContacts.ContactData;
import gianluca.com.ConfigReader;
import gianluca.com.utilis.JsonReader;
import io.cucumber.java.Before;

public class ContactsHooks {

	private static final Logger log = LogManager.getLogger(ContactsHooks.class);
	private final TestContext context;

	public ContactsHooks(TestContext context) {
		super();
		this.context = context;
	}

	@Before(value = "@contact", order = 1) // dati scenario login
	public void prepareConatctsData() {
		String jsonPath = ConfigReader.getProperty("contacts.data.json");
		List<ContactData> dati = JsonReader.readList(jsonPath, ContactData.class);
		if (dati == null || dati.isEmpty()) {
			throw new IllegalStateException("login.valid.json vuoto o non trovato: " + jsonPath);
		}
		ContactData setDati = dati.get(0);

		// usa ScenarioContext
		context.getScenarioContext().set(ContextKey.CONTACTS_DATA, setDati);

		log.info("[Data] LoginUser caricato da JSON: {}", setDati);
	}

}

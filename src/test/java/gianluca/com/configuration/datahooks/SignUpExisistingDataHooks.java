package gianluca.com.configuration.datahooks;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.TestRegistrerDelete.SignupData;
import gianluca.com.ConfigReader;
import gianluca.com.utilis.JsonReader;
import io.cucumber.java.Before;

public class SignUpExisistingDataHooks {
	private static final Logger log = LogManager.getLogger(SignUpExisistingDataHooks.class);
	private final TestContext context;

	public SignUpExisistingDataHooks(TestContext context) {
		this.context = context;
	}

	@Before(value = "@signup", order = 1) // dati scenario login
	public void prepareSignUpData() {
		String jsonPath = ConfigReader.getProperty("signupexsisting.data.json");
		List<SignupData> users = JsonReader.readList(jsonPath, SignupData.class);
		if (users == null || users.isEmpty()) {
			throw new IllegalStateException("login.valid.json vuoto o non trovato: " + jsonPath);
		}
		SignupData user = users.get(0);

		// NEW: usa ScenarioContext
		context.getScenarioContext().set(ContextKey.SIGNUP_DATA, user);

		log.info("[Data] LoginUser caricato da JSON: {}", user);
	}
	
	
	
}

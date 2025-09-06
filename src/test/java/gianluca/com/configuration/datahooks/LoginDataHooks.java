package gianluca.com.configuration.datahooks;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.testLogin.LoginData;
import gianluca.com.ConfigReader;
import gianluca.com.utilis.JsonReader;
import io.cucumber.java.Before;

public class LoginDataHooks {
    private static final Logger log = LogManager.getLogger(LoginDataHooks.class);
    private final TestContext context;

    public LoginDataHooks(TestContext context) {
        this.context = context;
    }

    @Before(value = "@login", order = 1) // dati scenario login
	public void prepareLoginData() {
		String jsonPath = ConfigReader.getProperty("login.valid.json");
		List<LoginData> users = JsonReader.readList(jsonPath, LoginData.class);
		if (users == null || users.isEmpty()) {
			throw new IllegalStateException("login.valid.json vuoto o non trovato: " + jsonPath);
		}
		LoginData user = users.get(0);

		// NEW: usa ScenarioContext
		context.getScenarioContext().set(ContextKey.LOGIN_USER, user);

		log.info("[Data] LoginUser caricato da JSON: {}", user);
	}
    
    
}

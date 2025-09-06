package gianluca.com.configuration.datahooks;

import java.util.List;

import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.TestRegistrerDelete.RegistrationData;
import gianluca.com.model.TestRegistrerDelete.RegistrationUiText;
import gianluca.com.ConfigReader;
import gianluca.com.utilis.JsonReader;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterDataHooks {
	private static final Logger log = LogManager.getLogger(RegisterDataHooks.class);
	private final TestContext context;

	public RegisterDataHooks(TestContext context) {
		this.context = context;
	}

	@Before(value = "@registration", order = 2)
	public void loadRegistrationData() {
	    String dataPath = ConfigReader.getProperty("registration.data.json"); // es: /testdata/registration_data.json
	    String uiPath   = ConfigReader.getProperty("registration.ui.json");   // es: /testdata/registration_ui.json

	    List<RegistrationData> list = JsonReader.readList(dataPath, RegistrationData.class);
	    if (list == null || list.isEmpty()) {
	        throw new IllegalStateException("registration_data.json empty or not found: " + dataPath);
	    }
	    RegistrationData data = list.get(0);

	    List<RegistrationUiText> uiList = JsonReader.readList(uiPath, RegistrationUiText.class);
	    if (uiList == null || uiList.isEmpty()) {
	        throw new IllegalStateException("registration_ui.json empty or not found: " + uiPath);
	    }
	    RegistrationUiText ui = uiList.get(0);

	    context.getScenarioContext().set(ContextKey.REGISTRATION_DATA, data);
	    context.getScenarioContext().set(ContextKey.REGISTRATION_UI_TEXT, ui);

	    log.info("[Hook@registration] REGISTRATION_DATA set: email={}, name={}", data.getEmail(), data.getName());
	    log.info("[Hook@registration] REGISTRATION_UI_TEXT set: {}", ui.getNewUserSignupMessage());
	}

}

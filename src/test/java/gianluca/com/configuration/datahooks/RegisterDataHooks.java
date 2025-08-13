package gianluca.com.configuration.datahooks;

import java.util.List;

import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.TestRegistrerDelete.RegistrationData;
import gianluca.com.model.TestRegistrerDelete.RegistrationUiText;
import gianluca.com.utilis.ConfigReader;
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

	@Before(value = "@register", order = 1)
	public void prepareAccountData() {
		String regPath = ConfigReader.getProperty("registration.data.json");
		String uiPath = ConfigReader.getProperty("registration.ui.json");

		List<RegistrationData> regs = JsonReader.readList(regPath, RegistrationData.class);
		if (regs == null || regs.isEmpty()) {
			throw new IllegalStateException("registration.data.json vuoto o non trovato: " + regPath);
		}
		RegistrationData reg = regs.get(0);

		List<RegistrationUiText> uis = JsonReader.readList(uiPath, RegistrationUiText.class);
		if (uis == null || uis.isEmpty()) {
			throw new IllegalStateException("registration.ui.json vuoto o non trovato: " + uiPath);
		}
		RegistrationUiText ui = uis.get(0);

		context.getScenarioContext().set(ContextKey.REGISTRATION_DATA, reg);
		context.getScenarioContext().set(ContextKey.REGISTRATION_UI_TEXT, ui);

		log.info("[Data] RegistrationData da JSON: {}", reg);
		log.info("[Data] RegistrationUiText da JSON: {}", ui);
	}

}

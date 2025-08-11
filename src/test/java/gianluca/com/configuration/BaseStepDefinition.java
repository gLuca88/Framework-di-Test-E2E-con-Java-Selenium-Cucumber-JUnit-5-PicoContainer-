package gianluca.com.configuration;

import org.openqa.selenium.WebDriver;

public class BaseStepDefinition {

	protected final TestContext context;

	public BaseStepDefinition(TestContext context) {
		this.context = context;
	}

	protected WebDriver getDriver() {
		return context.getDriver(); // sempre aggiornato
	}

}

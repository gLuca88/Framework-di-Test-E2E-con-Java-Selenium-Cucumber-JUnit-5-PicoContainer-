package gianluca.com.configuration;

import org.openqa.selenium.WebDriver;

import gianluca.com.pageobject.HomePage;

public class TestContext {

	private WebDriver driver;
	private final ScenarioContext scenarioContext = new ScenarioContext();

	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public HomePage goToHomePage() {
		HomePage homePage = new HomePage(driver);
		homePage.navigateHomePage();
		return homePage;
	}

}

package gianluca.com.configuration;

import org.openqa.selenium.WebDriver;

import gianluca.com.pageobject.HomePage;
import gianluca.com.pageobject.LoginPage;

public class TestContext {

	private WebDriver driver;
	private final ScenarioContext scenarioContext = new ScenarioContext();

	private HomePage homePage;
	private LoginPage loginPage;

	public LoginPage getLoginPage() {

		if (loginPage == null) {
			if (driver == null)
				throw new IllegalStateException("Driver null in TestContext");
			loginPage = new LoginPage(driver);
		}
		return loginPage;
	}

	public void setLoginPage(LoginPage loginPage) {
		this.loginPage = loginPage;
	}

	public HomePage getHomePage() {
		return homePage;
	}

	public void setHomePage(HomePage homePage) {
		this.homePage = homePage;
	}

	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// salva anche in context prima di restituire
	public HomePage goToHomePage() {
		this.homePage = new HomePage(driver);
		this.homePage.navigateHomePage();
		return this.homePage;
	}

}

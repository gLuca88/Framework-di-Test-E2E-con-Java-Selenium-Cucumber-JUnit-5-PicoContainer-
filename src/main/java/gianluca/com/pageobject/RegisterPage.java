package gianluca.com.pageobject;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import gianluca.com.SeleniumWrapper;
import gianluca.com.UiLocators;

public class RegisterPage extends SeleniumWrapper {

	private static final Logger logger = LogManager.getLogger(RegisterPage.class);

	private By titlesLocator = By.xpath("//div[@class='login-form']//h2[@class='title text-center']");
	private By radioButtonLocators = By.id("id_gender1");

	private By inputNameAccount = By.id("name");
	// private By inputEmail = By.id("email");-->inserimento automatico dopo il
	// primo step di registrazione
	private By inputPassword = By.xpath("//input[@id='password']");

	private By dayLocators = By.id("days");
	private By monthSel = By.id("months");
	private By yearSel = By.id("years");
	private By newSletterButton = By.id("newsletter");
	private By offersButton = By.id("optin");

	private By firstName = By.id("first_name");
	private By lastName = By.id("last_name");
	private By company = By.id("company");
	private By address = By.id("address1");
	private By address2 = By.id("address2");
	private By selectCountry = By.id("country");

	private By state = By.id("state");
	private By city = By.id("city");
	private By zipCode = By.id("zipcode");
	private By phoneNumber = By.id("mobile_number");
	private By buttonClickCreateAccount = By.xpath("//div[@class='login-form']//button");
	private By containerMexCreatedAccount = UiLocators.Common.CONTAINER_MSG;
	private By buttonContinueAfterRegistrer = UiLocators.Common.BUTTON_CONTINUE;

	public RegisterPage(WebDriver driver) {
		super(driver);

	}

	public boolean verifyTextContainerRegistration(String expectedTitle) {
		List<WebElement> titles = waitForElementsPresent(titlesLocator);

		if (titles.isEmpty()) {
			logger.warn("Nessun titolo trovato per il locator {}", titlesLocator);
			return false;
		}

		boolean trovato = titles.stream().map(el -> el.getText().trim()) // estrai e pulisci testo
				.peek(text -> logger.info("Titolo trovato: {}", text)) // log di debug
				.anyMatch(text -> text.equalsIgnoreCase(expectedTitle.trim())); // confronto

		if (!trovato) {
			logger.warn("Titolo atteso '{}' non presente nella lista", expectedTitle);
		}
		return trovato;
	}

	public void insertAccountDataPartOne(String name, String passsword) {
		click(radioButtonLocators);
		type(inputNameAccount, name);
		type(inputPassword, passsword);
	}

	public void insertData(String day, String month, String year) {
		new Select(waitForElementToBeVisible(dayLocators)).selectByValue(day);
		new Select(waitForElementToBeVisible(monthSel)).selectByValue(month);
		new Select(waitForElementToBeVisible(yearSel)).selectByValue(year);
	}

	public void clickNewSletter() {
		WebElement newSletter = waitForElementToBeClickable((newSletterButton));
		scrollIntoView(getDriver(), newSletter);
		newSletter.click();
	}

	public void clickRiceverOffert() {
		WebElement receverOffers = waitForElementToBeClickable((offersButton));
		receverOffers.click();
	}

	public void insertAddressData(String name, String lastNameValue, String companyValue, String addressValue,
			String addressValueDue, String stateValueUno, String stateValueDue, String cityValue, String zipCodeValue,
			String phoneNumberValue) {

		type(firstName, name);
		type(lastName, lastNameValue);
		type(company, companyValue);
		type(address, addressValue);
		type(address2, addressValueDue);
		new Select(waitForElementToBeVisible(selectCountry)).selectByValue(stateValueUno);
		type(state, stateValueDue);
		type(city, cityValue);
		type(zipCode, zipCodeValue);
		type(phoneNumber, phoneNumberValue);
	}

	public void clickCreateAccount() {
		click(buttonClickCreateAccount);
	}

	public String verifiyMexCreatedAccount(String mexExpected) {

		return getText(containerMexCreatedAccount);
	}

	public void clickButtonContinueAfterRegistrer() {
		click(buttonContinueAfterRegistrer);
	}

}

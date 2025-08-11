package gianluca.com.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import gianluca.com.SeleniumWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentPage extends SeleniumWrapper {

	
	private static final Logger log = LogManager.getLogger(PaymentPage.class);
	public PaymentPage(WebDriver driver) {
		super(driver);

	}

	private By nameCard = By.xpath("//*[@name='name_on_card']");
	private By cardNumber = By.xpath("//*[@name='card_number']");
	private By cvc = By.xpath("//*[@name='cvc']");
	private By month = By.xpath("//*[@name='expiry_month']");
	private By year = By.xpath("//*[@name='expiry_year']");
	private By buttonClick = By.id("submit");

	private By containerMex = By.cssSelector(".col-sm-9 p");

	public void paymentDataEntry(String name, String cardNumb, String cvct, String montht, String yeart) {

		type(nameCard, name);
		type(cardNumber, cardNumb);
		type(cvc, cvct);
		type(month, montht);
		type(year, yeart);
		WebElement el = waitForElementToBeVisible(buttonClick);
		scrollIntoView(getDriver(), el);
		el.click();

	}

	public boolean verifyMexConfirm(String mex) {

		String mexContainer = getText(containerMex).trim();
		String confront = mex.trim();
		log.info("Messaggio estrapolato: {}", confront);
		return mexContainer.equals(confront);
	}

}

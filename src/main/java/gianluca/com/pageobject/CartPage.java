package gianluca.com.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import gianluca.com.SeleniumWrapper;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartPage extends SeleniumWrapper {

	private static final Logger log = LogManager.getLogger(CartPage.class);

	public CartPage(WebDriver driver) {
		super(driver);
	}

	private By containerProduct = By.xpath("//table[@id='cart_info_table']//td[@class='cart_description']/h4/a");
	private By buttonContinueCheckOut = By.cssSelector(".col-sm-6 a");
	private By liDeliveryAddress = By.xpath("//ul[@id='address_delivery']/li[normalize-space()]");// deliveryAddress
	private By buttonPlaceHorder = By.xpath("//a[@href='/payment']");

	public boolean verifyProductInCart(String nameProducts) {

		log.info("Prodotto dal carrello: {}", getText(containerProduct));
		return nameProducts.equals(getText(containerProduct));
	}

	public void clickProceedToCheckOut() {

		WebElement button = waitForElementToBeVisible(buttonContinueCheckOut);
		scrollIntoView(getDriver(), button);
		button.click();
	}

	public List<WebElement> getDeliveryItems() {
		return getDriver().findElements(liDeliveryAddress); // se non trova, restituisce []
	}


	public PaymentPage confirmOrder() {
		WebElement el = waitForElementToBeVisible(buttonPlaceHorder);
		scrollIntoView(getDriver(), el);
		PaymentPage payPage = new PaymentPage(getDriver());
		el.click();
		return payPage;
	}

}

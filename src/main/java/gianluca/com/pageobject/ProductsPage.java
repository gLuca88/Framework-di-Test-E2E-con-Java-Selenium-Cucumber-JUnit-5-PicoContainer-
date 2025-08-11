package gianluca.com.pageobject;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import gianluca.com.SeleniumWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductsPage extends SeleniumWrapper {
	private static final Logger log = LogManager.getLogger(ProductsPage.class);

	public ProductsPage(WebDriver driver) {
		super(driver);

	}

	private By buttonProducts = By.xpath("//a[@href='/products']");
	private By inputSearchProducts = By.id("search_product");
	private By buttonSearch = By.id("submit_search");
	private By buttonAddCart = By.xpath("(//a[contains(@class,'add-to-cart') and @data-product-id='2'])[2]");
	private By buttonContinue = By.cssSelector(".modal-dialog button");

	private By nameProductsSearch = By.xpath("//div[@class='productinfo text-center']/p");

	private By containerAddCart = By.cssSelector(".productinfo");

	private By buttonCart = By.xpath("//a[@href='/view_cart']");

	public void goToProducts() {
		click(buttonProducts);
	}

	public void searchProducts(String product) {

		type(inputSearchProducts, product);
		click(buttonSearch);
	}

	public String getNameProductsSearch() {

		return getText(nameProductsSearch);
	}

	public boolean verifySearchResult(String nameProduct) {
		log.info("[Search] Prodotto della pagina: {}", getNameProductsSearch());
		return nameProduct.equals(getNameProductsSearch());
	}

	public void addTocart() {
		Actions action = new Actions(getDriver());
		WebElement container = waitForElementToBeVisible(containerAddCart);
		scrollIntoView(getDriver(), container);
		action.moveToElement(container).perform();
		waitForElementToBeVisible(buttonAddCart).click();
		waitForElementToBeVisible(buttonContinue).click();
	}

	public CartPage goToCartPage() {
		waitForElementToBeVisible(buttonCart).click();
		CartPage cartPage = new CartPage(getDriver());
		return cartPage;
	}

}

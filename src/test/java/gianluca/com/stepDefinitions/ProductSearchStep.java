package gianluca.com.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.configuration.BaseStepDefinition;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.products.DetailsProductTest;

import gianluca.com.pageobject.AllProductsPage;
import gianluca.com.pageobject.HomePage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProductSearchStep extends BaseStepDefinition {
	private static final Logger log = LogManager.getLogger(ProductSearchStep.class);

	public ProductSearchStep(TestContext context) {
		super(context);
	}
	// given and then are present in other steps

	private AllProductsPage productsPage;
	private DetailsProductTest detailsProduct;

	private DetailsProductTest datiJSON() {
		if (detailsProduct == null) {
			// prende l’istanza caricata dall’@Before @login e la mette in cache
			detailsProduct = context.getScenarioContext().require(ContextKey.PRODUCTS_DATA1, DetailsProductTest.class);
		}
		return detailsProduct; // dalle chiamate successive ritorna sempre la stessa istanza
	}

	private HomePage hp() {

		HomePage home = context.getHomePage();
		if (home == null) {
			throw new IllegalStateException("home is not inizilizate navigate in the given");
		}
		return home;
	}

	@When("I click on the Products_button")
	public void clickButtonProduct() {
		log.info("click on the button products");
		productsPage = hp().clickButtonProducts();
		log.info("after click i created page products");
	}

	@Then("I should be on the ALL PRODUCTS_page")
	public void verifyCorrectlyPage() {
		DetailsProductTest data = datiJSON();
		log.info("verify url and title  in the page");
		String urlExtracted = productsPage.getCurrentUrl();
		log.info("URL PAGE {}  URL EXTRACTED: {}", data.getUrlPage(), urlExtracted);
		boolean verUrl = data.getUrlPage().equalsIgnoreCase(urlExtracted);
		log.info("RESULT VERIFY URL: {}", verUrl);
		assertTrue(verUrl);
		String titleExtracted = productsPage.extractedTextTitle();
		log.info("TITLE IN THE PAGE {}  TITLE EXTRACTED: {}", data.getTitleInPage(), titleExtracted);
		boolean verTitle = data.getTitleInPage().equalsIgnoreCase(titleExtracted);
		log.info("RESULT VERIFY TITLE: {}", verTitle);
		assertTrue(verTitle);

	}

	@And("the product list should be visible")
	public void verifyListIsVisible() {
		log.info("verify the product list is visible");
		boolean ver = productsPage.isProductListVisible();
		log.info("RESULT VERIFY LIST: {}", ver);
		assertTrue(ver);
	}

	@When("I click on  View Product for the first product")
	public void clickWievProduct() {
		log.info("click first product of the list");
		productsPage.clickOnWievProduct();
	}

	@Then("I should be on the product details page")
	public void verifyTheDeatilsCardisPresent() {
		DetailsProductTest data = datiJSON();
		String urlExpected = productsPage.getCurrentUrl();
		log.info("verify that url after click is correct url atteso:{}", urlExpected);
		assertTrue(urlExpected.equalsIgnoreCase(data.getUrlDetailProduct()), "Url is not equals");
	}

	@And("the product details should be visible:")
	public void verifyDeatilsProduct(DataTable dataTable) {
		log.info("verify that the field detail product are visible in the dom");
		List<String> locators = dataTable.asList();
		for (String loc : locators) {
			String field = loc.trim();
			boolean ver = productsPage.isDetailVisible(field);
			assertTrue(ver, "Expected field: " + field + " to be visible on the product page");
		}
	}

}

package gianluca.com.configuration.datahooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gianluca.com.ConfigReader;
import gianluca.com.configuration.ContextKey;
import gianluca.com.configuration.TestContext;
import gianluca.com.model.products.DetailsProductTest;

import gianluca.com.utilis.JsonReader;
import io.cucumber.java.Before;

public class ProductsDataHooks {

	private static final Logger log = LogManager.getLogger(ContactsHooks.class);
	private final TestContext context;

	public ProductsDataHooks(TestContext context) {
		super();
		this.context = context;
	}

	@Before(value = "@produtcs", order = 1) // dati scenario login
	public void prepareProductsData() {
		String jsonPath = ConfigReader.getProperty("detailsProduct.data.json");
		DetailsProductTest dati = JsonReader.readObject(jsonPath, DetailsProductTest.class);
		if (dati == null) {
			throw new IllegalStateException("login.valid.json vuoto o non trovato: " + jsonPath);
		}

		// usa ScenarioContext
		context.getScenarioContext().set(ContextKey.PRODUCTS_DATA1, dati);

		log.info("[Data] LoginUser caricato da JSON: {}", dati);
	}
}

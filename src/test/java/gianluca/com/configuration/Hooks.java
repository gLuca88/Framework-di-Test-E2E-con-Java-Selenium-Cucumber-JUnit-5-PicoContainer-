package gianluca.com.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

import gianluca.com.WebDriverFactory;
import gianluca.com.configurationReportScreen.LogConfigurator;
import gianluca.com.configurationReportScreen.PathManager;
import gianluca.com.configurationReportScreen.ReportManager;
import gianluca.com.configurationReportScreen.ScreenshotManager;
import gianluca.com.model.LoginData;
import gianluca.com.utilis.ConfigReader;
import gianluca.com.utilis.JsonReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks extends BaseStepDefinition {

	private static final Logger log = LogManager.getLogger(Hooks.class);

	public Hooks(TestContext context) {
		super(context);
	}

	// SUITE
	@BeforeAll
	public static void beforeAll() throws Exception {
		Files.createDirectories(PathManager.baseDir());
		Files.createDirectories(PathManager.logsDir());
		Files.createDirectories(PathManager.screenshotsDir());

		// Configura log su file
		LogConfigurator.addRunFileAppender(PathManager.logsDir());

		ReportManager.init();
		log.info("==> Suite init. Report: {}", PathManager.reportHtml());
	}

	@AfterAll
	public static void afterAll() {
		ReportManager.flush();
		log.info("==> Suite chiusa. Report scritto su: {}", PathManager.reportHtml());
	}

	// scenario
	@Before(order = 0)
	public void setUp(Scenario scenario) {
		log.info("[Before Scenario] Avvio browser per: {}", scenario.getName());

		String browser = ConfigReader.getBrowser();
		WebDriver driver = WebDriverFactory.createBrowser(browser);
		driver.manage().window().maximize();
		context.setDriver(driver);

		String testName = safeName(scenario.getName());
		ReportManager.startTest(testName);
		ReportManager.test().assignCategory("Test Ecommerce");
		ReportManager.test().assignAuthor("Gianluca");
		ReportManager.test().log(Status.INFO, "Scenario started: " + scenario.getName());
		ReportManager.test().log(Status.INFO, "Browser: " + browser);

		// Link relativo ai log
		ReportManager.test().info("📜 Log run: <a href='logs/execution.log' target='_blank'>apri</a>");
	}

	@After(order = 0)
	public void tearDown(Scenario scenario) {
		WebDriver driver = context.getDriver();
		try {
			if (scenario.isFailed()) {
				Path dir = PathManager.scenarioDir(safeName(scenario.getName()));
				String shotName = "FAIL_" + now();
				ScreenshotManager.take(driver, dir, shotName); // salviamo su disco

				// Screenshot in BASE64 per Extent
				String b64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
				ReportManager.test().fail("Scenario FAILED: " + scenario.getName())
						.addScreenCaptureFromBase64String(b64, shotName);

				// Screenshot anche in Cucumber
				byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(png, "image/png", shotName + ".png");

				log.error("Scenario FAILED: {}", scenario.getName());
			} else {
				ReportManager.test().pass("Scenario PASSED");
				log.info("Scenario PASSED: {}", scenario.getName());
			}
		} catch (Exception e) {
			ReportManager.test().warning("Errore teardown/allegato screenshot: " + e.getMessage());
			log.warn("Problema in tearDown per {}: {}", scenario.getName(), e.getMessage());
		} finally {
			
			context.getScenarioContext().clear();
			context.getScenarioContext().clear();
			ReportManager.endTest();
			if (driver != null)
				driver.quit();
			log.info("[After Scenario] Chiudo browser per: {}", scenario.getName());
		}
		
		
	}

	@BeforeStep
	public void beforeStep(Scenario scenario) {
		log.info("[Before Step] Inizio step: {}", scenario.getName());
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		log.info("[After Step] Fine step: {}", scenario.getName());
	}

	private static String safeName(String s) {
		return s.replaceAll("[^a-zA-Z0-9\\-_]", "_");
	}

	private static String now() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss_SSS"));
	}

	@Before(value = "@login", order = 1)//dati scenario login
	public void prepareLoginData() {
		String jsonPath = ConfigReader.getProperty("login.valid.json");
		List<LoginData> users = JsonReader.readList(jsonPath, LoginData.class);
		if (users == null || users.isEmpty()) {
			throw new IllegalStateException("login.valid.json vuoto o non trovato: " + jsonPath);
		}
		LoginData user = users.get(0);

		// NEW: usa ScenarioContext
		context.getScenarioContext().set(ContextKey.LOGIN_USER, user);

		log.info("[Data] LoginUser caricato da JSON: {}", user);
	}

}

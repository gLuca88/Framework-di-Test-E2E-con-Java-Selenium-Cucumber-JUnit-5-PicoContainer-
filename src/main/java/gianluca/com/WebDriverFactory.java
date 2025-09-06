package gianluca.com;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

	public static WebDriver createBrowser(String browserName) {

		if (browserName == null || browserName.isEmpty()) {
			throw new IllegalArgumentException("name of browser cannot be null or empty");
		}

		WebDriver driver;

		switch (browserName.toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			if (ConfigReader.isHeadless()) {
				chromeOptions.addArguments("--headless=new");
				chromeOptions.addArguments("--window-size=1920,1080");
			}
			driver = new ChromeDriver(chromeOptions);
			break;

		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (ConfigReader.isHeadless()) {
				firefoxOptions.addArguments("-headless");
				// Firefox non ha un flag --window-size,
				// quindi settiamo la dimensione dopo aver creato il driver
			}
			driver = new FirefoxDriver(firefoxOptions);
			if (ConfigReader.isHeadless()) {
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
			}
			break;

		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			if (ConfigReader.isHeadless()) {
				edgeOptions.addArguments("--headless=new");
				edgeOptions.addArguments("--window-size=1920,1080");
			}
			driver = new EdgeDriver(edgeOptions);
			break;

		default:
			throw new IllegalArgumentException("the browser is not supported --> " + browserName);
		}

		// Timeout globali
		// int timeout = ConfigReader.getTimeOut();
		/*
		 * driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
		 * driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeout));
		 */

		// Solo se non è headless → massimizza la finestra
		if (!ConfigReader.isHeadless()) {
			try {
				driver.manage().window().maximize();
			} catch (Exception e) {
				System.err.println("[WebDriverFactory] Impossibile massimizzare finestra: " + e.getMessage());
			}
		}

		return driver;
	}
}

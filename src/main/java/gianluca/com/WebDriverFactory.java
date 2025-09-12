package gianluca.com;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    // ThreadLocal: ogni thread/scenario avrà il suo driver separato
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    public static WebDriver createBrowser(String browserName) {
        if (browserName == null || browserName.isEmpty()) {
            throw new IllegalArgumentException("name of browser cannot be null or empty");
        }

        WebDriver driver;

        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (ConfigReader.isHeadless()) {
                    chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (ConfigReader.isHeadless()) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                if (ConfigReader.isHeadless()) {
                    driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
                }
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (ConfigReader.isHeadless()) {
                    edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("the browser is not supported --> " + browserName);
        }

        // se non è headless, massimizza
        if (!ConfigReader.isHeadless()) {
            try {
                driver.manage().window().maximize();
            } catch (Exception e) {
                System.err.println("[WebDriverFactory] Impossibile massimizzare finestra: " + e.getMessage());
            }
        }

        // salva nel ThreadLocal
        TL_DRIVER.set(driver);

        return driver;
    }

    // recupera il driver associato al thread corrente
    public static WebDriver getDriver() {
        WebDriver d = TL_DRIVER.get();
        if (d == null) {
            throw new IllegalStateException("WebDriver non inizializzato per questo thread. Chiama createBrowser() prima.");
        }
        return d;
    }

    // chiude e pulisce il ThreadLocal
    public static void quitDriver() {
        WebDriver d = TL_DRIVER.get();
        if (d != null) {
            try {
                d.quit();
            } catch (Exception ignored) {}
            TL_DRIVER.remove();
        }
    }
}

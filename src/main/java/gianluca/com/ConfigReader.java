package gianluca.com;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class ConfigReader {

	private static Properties prop = new Properties();

	static {
		try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {

			if (is != null) {
				prop.load(is);
			} else {
				System.out.println("properties not found");
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to up date file");
		}
	}

	public static String getBrowser() {
		return System.getProperty("browser", "chrome");
	}

	public static int getTimeOut() {
		String value = System.getProperty("timeout", prop.getProperty("timeout", "5"));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			System.err.println("[ConfigReader] Timeout non valido: " + value + " -> uso default=5");
			return 5;
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static boolean isHeadless() {
		String value = System.getProperty("headless", prop.getProperty("headless", "false"));
		return Boolean.parseBoolean(value);
	}

	public static String getUrl() {
		return prop.getProperty("url");
	}

}

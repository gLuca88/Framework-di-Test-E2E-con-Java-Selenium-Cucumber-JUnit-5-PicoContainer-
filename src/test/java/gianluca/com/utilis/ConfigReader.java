package gianluca.com.utilis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties prop;

	static {
		try {

			FileInputStream file = new FileInputStream("src/main/resources/config.properties");
			prop = new Properties();
			prop.load(file);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to up date file");
		}
	}

	public static String getBrowser() {
		return System.getProperty("browser", "chrome");
	}

	public static int getTimeOut() {
		return Integer.parseInt(System.getProperty("timeout", "5"));
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}

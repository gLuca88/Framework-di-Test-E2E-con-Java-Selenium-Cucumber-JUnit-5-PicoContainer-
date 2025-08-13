package gianluca.com.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/RegisterUser")// cartella su classpath (es: src/test/resources/features)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gianluca.com.stepDefinitions,gianluca.com.configuration")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html, json:target/cucumber.json")
// esegue SOLO gli scenari/tag con @register
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@register")
public class RegisterDeleteRunnerTest {
}
/*
 mvn clean test -Dtest=RegisterDeleteRunnerTest
 */
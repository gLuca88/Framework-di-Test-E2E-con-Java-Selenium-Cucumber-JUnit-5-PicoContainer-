package gianluca.com.runners;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/RegisterUser")
@ConfigurationParameter(key = "cucumber.glue", value = "gianluca.com.stepDefinitions,gianluca.com.configuration")
@ConfigurationParameter(key = "cucumber.plugin", value =
    "pretty, html:target/cucumber-report.html, json:target/cucumber.json")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@signup")
public class SignUpExisistingEmailRunnerTest {

}

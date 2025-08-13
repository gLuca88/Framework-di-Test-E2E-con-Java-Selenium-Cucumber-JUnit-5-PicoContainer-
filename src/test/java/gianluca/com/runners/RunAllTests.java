package gianluca.com.runners;

import static io.cucumber.junit.platform.engine.Constants.*;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gianluca.com.stepDefinitions,gianluca.com.configuration")
public class RunAllTests {
}
//  mvn -Dtest=RunAllTests test
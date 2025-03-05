package runners;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		features="src/test/java/features",
		glue= {"stepDefinitions"},
		plugin= {"pretty","html:reports/cucumber-reports.html"},
		monochrome=true
		
		)
public class TestRunner extends AbstractTestNGCucumberTests{

}



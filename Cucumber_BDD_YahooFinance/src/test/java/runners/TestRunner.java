package runners;

import org.testng.annotations.Listeners;
import listeners.TestListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//import junit.framework.TestListener;

@CucumberOptions(
		features="src/test/resources/features",
		glue= {"stepDefinitions"},
		plugin= {"pretty","html:reports/cucumber-reports.html"},
		monochrome=true
		
		)
@Listeners(TestListener.class)
public class TestRunner extends AbstractTestNGCucumberTests{

}



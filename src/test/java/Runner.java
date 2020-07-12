import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "pretty",
		"html:target/cucumber-report",
		"json:target/cucumber-report/cucumber.json",
		"rerun:target/cucumber-report/rerun.txt" },
		features = "src/test/resources/features",
		monochrome = true,
		glue = {"org.cartaxcheck.steps"})
public class Runner {

}


package Runner.TestingFile;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@CucumberOptions(
        features = ("src/test/resources/Features/Onboarding.feature"),
        //        ,plugin = { "pretty:STDOUT","html:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-pretty",
//                    "json:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-json",
//                    "com.cucumber.listener.ExtentCucumberForatter:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-extent/report.html"}
        glue = ("Steps")
//        , tags={"@smoke,@P0"}
        ,tags={"@onboarding"}
)

public class TactOnboardingRunCukesFullyReset extends AbstractTestNGCucumberTests {
    @Test
    private void test(){
        System.out.println("@Tact Onboarding Run FullyReset");
    }
}


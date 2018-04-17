package Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

public class IOSTestInncerRunCukesClass {

    //onboarding
    @CucumberOptions(
            features = ("src/test/resources/Features/Onboarding.feature"),
            glue = ("Steps")
            ,tags={"@onboarding"}
    )
    public class OnboardingRunCukesFullyReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Tact Onboarding Run FullyReset");
        }
    }

    //Email
    @CucumberOptions(
            features = ("src/test/resources/Features/Email.feature")
            ,glue = ("Steps")
            ,tags={"@gmailSend"}
    )
    public class EmailFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //logout
    @CucumberOptions(
            features = ("src/test/resources/Features/login.feature"),
            glue = ("Steps")
            ,tags={"@logout"}
    )
    public class TactLogoutRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test login RunCukesTest");
        }
    }

    //login
    @CucumberOptions(
            features = ("src/test/resources/Features/login.feature"),
            glue = ("Steps")
            ,tags={"@login"}
    )
    public class TactLoginRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature")
//        ,plugin = { "pretty:STDOUT","html:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-pretty",
//                    "json:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-json",
//                    "com.cucumber.listener.ExtentCucumberForatter:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-extent/report.html"}
            ,glue = ("Steps")
//        , tags={"@smoke,@P0"} "@note,@SFTask,@Event"
//        ,tags={"@Task,@Log,@Event"}
            ,tags={"@Log"}
    )
    public class TactContactsFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }

    //DataSources
    @CucumberOptions(
            features = ("src/test/resources/Features/DataSources.feature")
            ,glue = ("Steps")
//        ,tags={"@P0"}
            ,tags = {"@LinkedIn"}
    )
    public class TactDataSourcesFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }

    //Delete
    @CucumberOptions(
            features = ("src/test/resources/Features/login.feature"),
            glue = ("Steps")
            ,tags={"@deleteAccount"}
    )
    public class TactDeleteAccountRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //Lead
    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature")
            ,glue = ("Steps")
            ,tags={"@createLead"}
    )
    public class TactLeadFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }
}

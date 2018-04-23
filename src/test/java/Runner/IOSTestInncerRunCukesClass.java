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
            , format = {
                    "pretty",
                    "html:target/report/cucumber-pretty-OnboardingRunCukesFullyReset",
                    "json:target/report/OnboardingRunCukesFullyReset.json"}
    )
    public class OnboardingRunCukesFullyReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Tact Onboarding Run FullyReset");
        }
    }

    //appVersion
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature")
            ,glue = ("Steps")
            ,tags={"@getAppVersion"}
    )
    public class TactVersionFeatureCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //Email
    @CucumberOptions(
            features = ("src/test/resources/Features/Email.feature")
            ,glue = ("Steps")
            ,tags={"@P0"}
            , format = {
                    "pretty",
                    "html:target/report/cucumber-pretty-AddEmailFromTabFeatureRunCukesNoReset",
                    "json:target/report/AddEmailFromTabFeatureRunCukesNoReset.json"}
    )
    public class AddEmailFromTabFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //Email
    @CucumberOptions(
            features = ("src/test/resources/Features/Email.feature")
            ,glue = ("Steps")
            ,tags={"@P1,@P2"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-SendEmailFeatureRunCukesNoReset",
                "json:target/report/SendEmailFeatureRunCukesNoReset.json"}
    )
    public class SendEmailFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //logout
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("Steps")
            ,tags={"@logout"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactLogoutRunCukesNoReset",
                "json:target/report/TactLogoutRunCukesNoReset.json"}
    )
    public class TactLogoutRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test login RunCukesTest");
        }
    }

    //login
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("Steps")
            ,tags={"@login"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactLoginRunCukesNoReset",
                "json:target/report/TactLoginRunCukesNoReset.json"}
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
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactContactsFeatureRunCukesNoReset",
                "json:target/report/TactContactsFeatureRunCukesNoReset.json"}
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
            ,tags = {"@addLinkedIn"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactDataSourcesFeatureRunCukesNoReset",
                "json:target/report/TactDataSourcesFeatureRunCukesNoReset.json"}
    )
    public class TactDataSourcesFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }

    //Delete
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("Steps")
            ,tags={"@deleteAccount"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactDeleteAccountRunCukesNoReset",
                "json:target/report/TactDeleteAccountRunCukesNoReset.json"}
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
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactLeadFeatureRunCukesNoReset",
                "json:target/report/TactLeadFeatureRunCukesNoReset.json"}
    )
    public class TactLeadFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }
}

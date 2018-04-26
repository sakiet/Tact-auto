package Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

public class AndroidTestInnerRunCukesClass {

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
            ,glue = ("Steps")
            ,tags={"@SFTask,@Event"}
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
}

package Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

public class AndroidTestInnerRunCukesClass {

    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature"),
            glue = ("Steps")
//        , tags={"@smoke,@P0"}
            ,tags={"@SFTask,@Event"}
    )
    public class AndroidTactContactsFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }
    }
}

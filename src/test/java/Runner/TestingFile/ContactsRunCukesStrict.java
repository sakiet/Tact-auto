package Runner.TestingFile;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@CucumberOptions(
        features = ("src/test/resources/Features/ContactsAPP/contactsAPP.feature"),
        glue = ("Steps")
//        , tags={"@smoke,@P0"}
)

public class ContactsRunCukesStrict extends AbstractTestNGCucumberTests {
    @Test
    private void test(){
        System.out.println("@Test RunCukesTest");
    }
}

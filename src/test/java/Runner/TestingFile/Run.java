package Runner.TestingFile;

import com.paypal.selion.annotations.MobileTest;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.ITest;
import org.testng.annotations.*;

@CucumberOptions(
        features = ("src/test/resources/Features/login.feature"),
        glue = ("Steps")
)
public class Run extends AbstractTestNGCucumberTests implements ITest {
    private TestNGCucumberRunner tcr;
    private String featureName;

    @BeforeClass (alwaysRun = true)
    public void beforeClass(Object[] params) throws Exception {
        System.out.println("RunCukesTest - Beforeclass");
        tcr = new TestNGCucumberRunner(this.getClass());
//                CucumberFeatureWrapper cucumberFeature = (CucumberFeatureWrapper) params[0];
//                featureName = cucumberFeature.getCucumberFeature().getGherkinFeature().getName();

    }

    @BeforeMethod
    public void beforeMethod(Object[] params) {
        System.out.println("RunCukesTest - beforeMethod - beforeMethod");
        CucumberFeatureWrapper cucumberFeature = (CucumberFeatureWrapper) params[0];
        featureName = cucumberFeature.getCucumberFeature().getGherkinFeature().getName();
    }


    @MobileTest(
            appPath = "/Users/sakie/workspace/testingEnv/SeLion_Demo-1/Applications/Tact.app",
            device = "iphone:11.2",
            deviceType = "iPhone Simulator",
            mobileNodeType = "appium",
            additionalCapabilities = {
                    "deviceName:iPhone 8 Plus",
                    "noReset:true","unicodeKeyboard:true","resetKeyboard:true"
            }
    )
    @Test(groups = "cucumber", description = "Runs CucumberFeature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        System.out.println("RunCukesTest-test - feature");
        tcr.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @Override
    public String getTestName() {
        return featureName;
    }

    @DataProvider
    public Object[][] features() {
        return tcr.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {

        System.out.println("RunCukesTest - afterclass - afterClass ");
        tcr.finish();
    }
}

package Runner.TestingFile;

import Utils.CustomPicoContainer;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;
import Utils.dataObjects.UserInfor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class ContactsTestRunner extends CustomPicoContainer {

    private TestNGCucumberRunner  testNGCucumberRunner;

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        System.out.println("TestRunner - BeforeClass - setUpClass");
    }

    @MobileTest(
            device = "iphone:11.2",
            deviceType = "iPhone Simulator",
            mobileNodeType = "appium",
            additionalCapabilities = {
                    "bundleId:com.apple.MobileAddressBook",
                    "deviceName:iPhone 8 Plus",
                    "newCommandTimeout:120",
                    "unicodeKeyboard:true","resetKeyboard:true"
                    , "noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    //, "fullReset:true"  //restart the iPhone/simulator and install the app
            }
    )

    //w/ data provider
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "yamlDataProvider")
    private void contactsAPPDeletefeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;

        //w/o data provider
//    @Test(groups = "cucumber", description = "Runs Cucumber Feature")
//    private void feature() throws InterruptedException {
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
        testNGCucumberRunner = new TestNGCucumberRunner(ContactsRunCukesStrict.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        System.out.println("TestRunner - AfterClass - tearDownClass");
        testNGCucumberRunner.finish();
    }

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
}

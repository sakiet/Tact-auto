package Runner;

import Utils.Appium;
import Utils.CustomPicoContainer;
import Utils.DriverUtils;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.testng.TestNGCucumberRunner;
import Utils.dataObjects.UserInfor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class AndroidTactSanityTesting extends CustomPicoContainer {

    private TestNGCucumberRunner  testNGCucumberRunner;
    //iOS
//    static final String appPath = "/Users/sakie/workspace/testingEnv/CucumberBasics/Applications/TactAuth.app";
//    static final String device = "iphone:11.2";
//    static final String deviceType = "iPhone Simulator";
//    static final String platformName = "iOS";
    //Android
    static final String appPath = "/Users/sakie/workspace/testingEnv/CucumberBasics/Applications/TactApplication-dev-debug.apk";
    static final String device = "android:8.1";
    static final String deviceType = "Android Emulator";
    static final String platformName = "Android";

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        System.out.println("TestRunner - BeforeClass - setUpClass");
        DriverUtils.clearChromeData();
//        Appium.startServer("127.0.0.1","2345","3456");
        Appium.restartAppium();
    }

    //    @MobileTest(  //Android
////            appPath = appPath,
////            device = device,
////            deviceType = deviceType,
////            mobileNodeType = "appium",
//            locale = "US",
//            additionalCapabilities = {
////                    "platformName:"+platformName,
////                    "newCommandTimeout:120",
    //                "browserName:Chromium",   //chromeDriver
//                    "chromedriverExecutable:/Users/sakie/node_modules/appium/node_modules/appium-chromedriver/chromedriver/mac/chromedriver",   //chrome driver
//                    "unicodeKeyboard:true","resetKeyboard:true"
////                    , "noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
////                    , "fullReset:false"  //restart the iPhone/simulator and install the app
//                    ,
//                     "noReset:false"
//                    , "fullReset:true"
//            }
//    )
    @MobileTest(  //Android
            appPath = "/Users/sakie/workspace/testingEnv/CucumberBasics/Applications/TactApplication-alpha-debug.apk",
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",
                    "fullReset:false"
                    //for Alpha only, dev do not need this part
                    , "appPackage:com.tactile.tact.alpha",
                    "appActivity:com.tactile.tact.onboarding.SignInActivity"
            }
    )
    //w/ data provider
    @Test(groups = "Tact-login", description = "Runs Tact - login", dataProvider = "yamlDataProvider", priority = 0)
    void TactContactFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;

        //w/o data provider
//    @Test(groups = "cucumber", description = "Runs Cucumber Feature")
//    private void feature() throws InterruptedException {
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLoginRunCukesNoReset.class); //login
//        testNGCucumberRunner = new TestNGCucumberRunner(TactOnboardingRunCukesFullyReset.class); //onboarding
        testNGCucumberRunner.runCukes();
        System.out.println("testNGCucumberRunner.finish(); FINISHED");
        testNGCucumberRunner.finish();

        testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.AndroidTactContactsFeatureRunCukesNoReset.class);
//        testNGCucumberRunner = new TestNGCucumberRunner(AndroidTactContactsFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
        System.out.println("testNGCucumberRunner.finish(); FINISHED");
        testNGCucumberRunner.finish();

        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLogoutRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
        System.out.println("testNGCucumberRunner.finish(); FINISHED");
        testNGCucumberRunner.finish();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        System.out.println("TestRunner - AfterClass - tearDownClass");

        DriverUtils.clearChromeData();

        Appium.stopServer();
        if ( !Appium.checkIfServerIsRunnning("4723") ) {
            System.out.println("Appium does not run");
        } else {
            System.out.println("Appium does run, and stop again");
            Appium.stopServer();
        }
//        testNGCucumberRunner.finish();

//        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
    }

}

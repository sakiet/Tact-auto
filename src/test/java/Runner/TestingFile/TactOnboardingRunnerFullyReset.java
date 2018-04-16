package Runner.TestingFile;


import Runner.IOSTestInncerRunCukesClass;
import Utils.Appium;
import Utils.CustomPicoContainer;
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

public class TactOnboardingRunnerFullyReset extends CustomPicoContainer {

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
        Appium.restartAppium();
    }

    //    @MobileTest(  //iOS
////            appPath = appPath,
////            device = device,
////            deviceType = deviceType,
////            mobileNodeType = "appium",
//            locale = "US",
//            additionalCapabilities = {
////                    "platformName:"+platformName,
//                    "newCommandTimeout:120",
//                    "unicodeKeyboard:true","resetKeyboard:true"
////                    , "noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
////                    , "fullReset:false"  //restart the iPhone/simulator and install the app
//                    , "noReset:false"
//                    , "fullReset:true"
//            }
//    )
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

    @MobileTest(
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:false",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:true"  //restart the iPhone/simulator and install the app
            }
    )

    //w/ data provider
//    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "yamlDataProvider")
//    private void feature(UserInfor userInfor) throws InterruptedException {
//        CustomPicoContainer.userInfor = userInfor;

    //w/o data provider
    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "yamlDataProvider", priority = 0)
    private void TactOnboardingFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;

        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
        testNGCucumberRunner = new TestNGCucumberRunner(TactOnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();
        testNGCucumberRunner.finish();

        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLogoutRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
        testNGCucumberRunner.finish();

    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        System.out.println("TestRunner - AfterClass - tearDownClass");

        Appium.stopServer();
        if ( !Appium.checkIfServerIsRunnning("4723") ) {
            System.out.println("Appium does not run");
        } else {
            System.out.println("Appium does run, and stop again");
            Appium.stopServer();
        }
    }

}


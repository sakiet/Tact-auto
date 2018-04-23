package Runner;

import Utils.Appium;
import Utils.CustomPicoContainer;
import Utils.DriverUtils;
import Utils.GenerateReport;
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

public class IOSTactSanityTesting extends CustomPicoContainer {

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
//        Appium.startServer("0.0.0.0","1234","2345");
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:false",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:true"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "cucumber", description = "Runs Cucumber Feature - onboarding", dataProvider = "yamlDataProvider", priority = 0)
    private void TactOnboardingFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //onboarding
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();
//        testNGCucumberRunner.finish();
    }

    //AddEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "yamlDataProvider", dependsOnMethods = "TactGetAppVersion")
    void TactAddEmailFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //AddEmailAccountFromTab
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.AddEmailFromTabFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //ReAuth
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "yamlDataProvider", dependsOnMethods = "TactAddEmailFeature")
    void TactBeReauthExchangeFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //logout
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLogoutRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
        //login
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLoginRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //SendEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "yamlDataProvider", dependsOnMethods = "TactBeReauthExchangeFeature")
    void TactSendEmailFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //SendEmail
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.SendEmailFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Contact
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
    void TactContactsFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactContactsFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //DataSources
    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
    void TactDataSourcesTest(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //DataSources
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactDataSourcesFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
//        System.out.println("testNGCucumberRunner.finish(); FINISHED");
//        testNGCucumberRunner.finish();
    }

    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact-login", description = "Runs Tact - login", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
    void TactGetAppVersion(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;
        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Email
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactVersionFeatureCukesNoReset.class);
        testNGCucumberRunner.runCukes();

    }

    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact-login", description = "Runs Tact - delete Account", dataProvider = "yamlDataProvider", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.userInfor = userInfor;

        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
//        System.out.println("testNGCucumberRunner.finish(); FINISHED");
//        testNGCucumberRunner.finish();
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


        System.out.println("Finished running");
        GenerateReport.getReport();

        System.out.println("testNGCucumberRunner.finish(); FINISHED");
//        testNGCucumberRunner.finish();
    }
}



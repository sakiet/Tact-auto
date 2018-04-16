package Steps;


import Utils.CustomPicoContainer;
import Utils.DriverUtils;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactAccessSFPage;
import com.paypal.selion.testcomponents.mobile.TactAlertsPopUpPage;
import com.paypal.selion.testcomponents.mobile.TactSetting.ExchangePage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;

public class Hooks extends CustomPicoContainer{

    private CustomPicoContainer customPicoContainer;

    private TactAccessSFPage tactAccessSFPage = new TactAccessSFPage();
    private ExchangePage exchangePage = new ExchangePage();
    private TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

    public Hooks(CustomPicoContainer customPicoContainer) {
        this.customPicoContainer = customPicoContainer;
    }

    @Before()
//    @MobileTest(
//            appPath = "/Users/sakie/workspace/testingEnv/SeLion_Demo-1/Applications/Tact.app",
//            device = "iphone:11.2",
//            deviceType = "iPhone Simulator",
//            mobileNodeType = "appium",
//            additionalCapabilities = {
//                    "deviceName:iPhone 8 Plus",
//                    "noReset:true","unicodeKeyboard:true","resetKeyboard:true"
//            }
//    )
//    public void InitializeTest() {
//
//        System.out.println("Grid.driver().getCapabilities() ==> \n" + Grid.driver().getCapabilities() + "\n");
//    }
//    public void InitializaTest() throws MalformedURLException {
//        System.out.println("Open the InitializeTest");
//
//        DesiredCapabilities cap = DesiredCapabilities.iphone();
//        DesiredCapabilities cap1 = DesiredCapabilities.iphone();
//        cap.setCapability(MobileCapabilityType.APP, "/Users/sakie/workspace/testingEnv/SeLion_Demo-1/Applications/Tact.app");
//        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8 Plus");
//        cap.setCapability(MobileCapabilityType.UDID, "7846EF2B-7588-47FA-BE12-FA8008E21902");
//
//        //not sure whether need them
//        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
//        cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.2");
//        cap.setCapability("bundleId", "com.tactile.Tact-Dev");
//
//        String androidLocalURL = "http://localhost:4444/wd/hub";
//        String iOSLocalURL = "http://0.0.0.0:4723/wd/hub";
//        SeLionAppiumIOSDriver seLionAppiumIOSDriver = new SeLionAppiumIOSDriver(new URL(iOSLocalURL), cap);
////        System.out.println("Grid.driver().getCapabilities() ==> \n" + Grid.driver().getCapabilities() + "\n");
//
//        //Passing a dummy WebDriver instance
//        base.appiumDriver = seLionAppiumIOSDriver;
//
//    }
    public void InitializeTest() {

        System.out.println("Hook - before - relaunch the app");

        Capabilities capabilities = Grid.driver().getCapabilities();
        if ((boolean)capabilities.getCapability("noReset")){
            ((AppiumDriver) Grid.driver()).launchApp();
        }

        System.out.println("Grid.driver().getCapabilities() ==> " +
                Grid.driver().getCapabilities() + "\n");
    }

    @After
    public void TearDownTest(Scenario scenario){

        if (scenario.isFailed()) {
            System.out.println(scenario.getName());
        }
        System.out.println("Hook - After - Close");
    }
}

package Steps;

import Utils.CustomPicoContainer;
import com.paypal.selion.testcomponents.mobile.TactWelcomePage;
import cucumber.api.java.en.Given;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import io.appium.java_client.remote.MobilePlatform;

public class LoginStepTesting extends CustomPicoContainer {

    private CustomPicoContainer customPicoContainer;
    private TactWelcomePage tactWelcomePage;

    public LoginStepTesting(CustomPicoContainer customPicoContainer) {

        this.customPicoContainer = customPicoContainer;
        tactWelcomePage = new TactWelcomePage();
    }

    @Given("^I navigate to the login page$")
    public void iNavigateToTheLoginPage() throws Throwable {

//        WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getLoginButton());
//        tactWelcomePage.getLoginButton().tap();
//        WebDriverWaitUtils.waitUntilElementIsVisible((MobileElement)Grid.driver().findElementById("Log In"));

//        Grid.driver().findElementById("Start Trial").click();
        Thread.sleep(10*1000);


//        System.out.println(("The driver is " + customPicoContainer.StepInfo));
        System.out.println("iNavigateToTheLoginPage");


        /*
         * V2: Old way to find elements and action on the elements
         */
//        WebElement lego = Grid.driver().findElement(By.name("Log In"));
//        Grid.driver().findElement(By.xpath("//XCUIElementTypeButton[@name=\"Log In\"]")).click();
//        Thread.sleep(5*1000);

        /*
         * V3: Generate the element from the yaml
         */
//        tactWelcomePage.getLoginButton().tap(loginPage.getLoginLabel());
//        loginPage.getUserEmailTextField().setText(userInformation.getSalesforceAccountName());
//        loginPage.getPwdTextField().setText(userInformation.getSalesforcePwd());
//        Thread.sleep(5*10000);

    }
}

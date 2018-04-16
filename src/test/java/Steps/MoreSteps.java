package Steps;

import Utils.DriverUtils;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactAlertsPopUpPage;
import com.paypal.selion.testcomponents.mobile.TactNavigateTabBarPage;
import com.paypal.selion.testcomponents.mobile.TactSetting.TactSettingsPage;
import com.paypal.selion.testcomponents.mobile.TactWelcomePage;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class MoreSteps implements En {

    private TactWelcomePage tactWelcomePage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private TactSettingsPage tactSettingsPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;

    public MoreSteps() {

        tactWelcomePage = new TactWelcomePage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();
        tactSettingsPage = new TactSettingsPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();

        And("^More: I switch to \"([^\"]*)\" option in more page$", (String option) -> {
            System.out.println("^More: I switch to " + option + " option in more page$");

            switch (option) {
                case "Notebook":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotebookButton());
                    tactNavigateTabBarPage.getNotebookButton().tap();
                    break;
                case "Notification":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotificationsButton());
                    tactNavigateTabBarPage.getNotificationsButton().tap();
                    break;
                case "Settings":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getSettingsButton());
                    tactNavigateTabBarPage.getSettingsButton().tap(tactSettingsPage.getSettingsTitleLabel());
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String (Notebook|Notifications|Settings)");
            }
        });
        And("^More: I log out from the app$", () -> {
            System.out.println("^More: I log out from the app$");

            if ( DriverUtils.isAndroid() ){
                tactSettingsPage.getAndroidMoreOptionButton().tap(tactSettingsPage.getLogOutButton());
                tactSettingsPage.getLogOutButton().tap(tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton());
                tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
            } else {
                tactSettingsPage.getLogOutButton().tap();
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
        });
        And("^More: I delete current account from the app$", () -> {
            System.out.println("^More: I delete current account from the app$");

            if ( DriverUtils.isIOS() ) {
                tactSettingsPage.getIosAccountButton().tap(tactSettingsPage.getDeleteAccountButton());
            }
            tactSettingsPage.getDeleteAccountButton().tap(tactAlertsPopUpPage.getTactDeleteButton());
            tactAlertsPopUpPage.getTactDeleteButton().tap();
            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getWelcomeTactLabel());
        });
    }
}

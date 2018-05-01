package Steps;

import Utils.CustomPicoContainer;
import Utils.DriverUtils;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactAccessSFPage;
import com.paypal.selion.testcomponents.mobile.TactAlertsPopUpPage;
import com.paypal.selion.testcomponents.mobile.TactContact.*;
import com.paypal.selion.testcomponents.mobile.TactContact.TactContactsMainPage;
import com.paypal.selion.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import com.paypal.selion.testcomponents.mobile.TactNavigateTabBarPage;
import com.paypal.selion.testcomponents.mobile.TactSetting.ExchangePage;
import cucumber.api.java8.En;

public class CommonSteps implements En {

    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private TactContactsMainPage tactContactsMainPage;
    private TactContactObjPage tactContactObjPage;
    private TactMailBoxesPage tactMailBoxesPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;
    private TactAccessSFPage tactAccessSFPage;
    private ExchangePage exchangePage;

    public CommonSteps() {

        tactNavigateTabBarPage = new TactNavigateTabBarPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();
        tactAccessSFPage = new TactAccessSFPage();

        tactContactObjPage = new TactContactObjPage();
        tactContactsMainPage = new TactContactsMainPage();
        tactMailBoxesPage = new TactMailBoxesPage();
        exchangePage = new ExchangePage();

        And("^I going check the new step cucumber-java(\\d+)$", (Integer arg0) -> {
            System.out.println("^I going check the new step cucumber-java(\\d+)$");
        });
        And("^I get the data from dataprovider$", () -> {
            System.out.println("^I get the data from dataprovider$");
            DriverUtils.sleep(60);
        });
        And("^I click login button$", () -> {
            System.out.println("^I click login button$");
        });
        Then("^I should see the userform page$", () -> {
            System.out.println("^I should see the userform page$");
        });
        And("^Common: I switch to \"([^\"]*)\" driver$", (String driverContext) -> {
            System.out.println("^Common: I switch to " + driverContext + " driver$");

            if (driverContext.equalsIgnoreCase("Webview") && DriverUtils.isIOS() ){
                System.out.println("-> convert To Webview driver <-");
                DriverUtils.convertToWebviewDriver();
            } else if ( driverContext.equalsIgnoreCase("Native_APP") && DriverUtils.isIOS() ){
                System.out.println("-> convert To NativeAPP driver <-");
                DriverUtils.convertToNativeAPPDriver();
            }
        });
        When("^Common: I switch to \"([^\"]*)\" page from tab bar$", (String tabBarOption) -> {
            System.out.println("^Common: I switch to " + tabBarOption + " page from tab bar$");

            DriverUtils.sleep(2);

            if (Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size()!=0){
                System.out.println("Start waiting for \"Syncing ...\"");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingLabel());
                System.out.println("\"Syncing...\" finished");
                DriverUtils.sleep(2);
            } else {
                System.out.println("No \"Syncing...\"");
            }

            //Syncing data to your phone Page - iOS only
            if (    DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactNavigateTabBarPage.getTactMoreButton().getLocator()).size() == 0 &&
                    Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel().getLocator()).size()!=0) {
                System.out.println("Start waiting for \"Syncing data to your phone.\"");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
                System.out.println("\"Syncing data to your phone.\" finished");
                DriverUtils.sleep(2);
            } else {
                System.out.println("No \"Syncing data to your phone.\"");
            }
            //exchangeSync
            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactExchangeSyncErrorMsgTitleLabel().getLocator()).size() != 0){
                System.out.println("Start waiting for \"exchange Reauth Sync\"");
                tactAlertsPopUpPage.getReauthorizeButton().tap(exchangePage.getExchangeTitleLabel());

                String exchangePwdText = CustomPicoContainer.userInfor.getExchangeIOSEmailPwd();
                exchangePage.getExchangePwdTextField().sendKeys(exchangePwdText);
                exchangePage.getSubmitButton().tap();
                System.out.println("\"exchange Reauth Sync\" finished");
                DriverUtils.sleep(2);
            } else {
                System.out.println("No \"exchange Reauth Sync\"");
            }
            resyncPopUp();

            //More : Notebook, Notifications, Settings
            if ( tabBarOption.equalsIgnoreCase("More") ||
                 tabBarOption.equalsIgnoreCase("Notebook") ||
                 tabBarOption.equalsIgnoreCase("Notifications") ||
                 tabBarOption.equalsIgnoreCase("Settings")) {
                if (DriverUtils.isIOS()) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
                    tactNavigateTabBarPage.getTactMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                    System.out.println("after click more button in iOS");
                } else {    //old android version
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton());
                    tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton().tap(tactNavigateTabBarPage.getTactAndroidOldVSettingsButton());
                    tactNavigateTabBarPage.getTactAndroidOldVSettingsButton().tap();
                    System.out.println("after click more button in Android");
                }

                if (resyncPopUp() ) {
                    System.out.println("after resync pop up done");
                    DriverUtils.sleep(2);
                    DriverUtils.sleep(20);
                    tactNavigateTabBarPage.getTactMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                }
                System.out.println(">>>>>>");
                System.out.println("after resync after click more button");
                System.out.println("<<<<<<");
            }

//            if ( DriverUtils.isAndroid() ) {
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidNavigateButton());
//                tactNavigateTabBarPage.getTactAndroidNavigateButton().tap(tactNavigateTabBarPage.getTactContactsButton());
//            }

            switch (tabBarOption) {
                case "Email":   //Android not support
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
                    tactNavigateTabBarPage.getTactEmailButton().tap();
                    resyncPopUp();
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
                    if ( Grid.driver().findElementsByXPath(tactMailBoxesPage.getMailBoxesTitleLabel().getLocator()).size() == 0 ) {
                        tactMailBoxesPage.getBackToMailBoxesButton().tap();
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getMailBoxesTitleLabel());
                    }
                    resyncPopUp();
                    break;
                case "Calendar":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactCalendarButton());
                    tactNavigateTabBarPage.getTactCalendarButton().tap();
                    resyncPopUp();
                    break;
                case "Contacts":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactContactsButton());
                    tactNavigateTabBarPage.getTactContactsButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
                    resyncPopUp();
                    break;
                case "Opportunities":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactOpportunitiesButton());
                    tactNavigateTabBarPage.getTactOpportunitiesButton().tap();
                    resyncPopUp();
                    break;
                case "Notebook":
                    if (DriverUtils.isAndroid() ||
                        //iOS - notebook button is in navigation bar
                        Grid.driver().findElementsByXPath(tactNavigateTabBarPage.getNotebookButton().getLocator()).size() != 0 ) {
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotebookButton());
                        tactNavigateTabBarPage.getNotebookButton().tap();
                        resyncPopUp();
                    } else {
                        //iOS - notebook is in more navigation bar
                        tactNavigateTabBarPage.getNotebookMoreButton().tap();
                    }
                    break;
                case "Notifications":   //Android not support
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotificationsButton());
                    tactNavigateTabBarPage.getNotificationsButton().tap();
                    break;
                case "Settings":
                    System.out.println("Goes to Settings Page");
                    if (DriverUtils.isIOS()) {
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getSettingsButton());
                        System.out.println("finding the setting button");
                        tactNavigateTabBarPage.getSettingsButton().tap();
                    }
                    System.out.println("after click setting button");
                    break;
                default:
                    if ( DriverUtils.isIOS() ){
                        SeLionAsserts.verifyFalse(true,
                                "Please give a correct String (Email|Calendar|Contacts|Opportunities|More|Notebook|Notifications|Settings)");
                    } else {
                        SeLionAsserts.verifyFalse(true,
                                "Please give a correct String (Calendar|Contacts|Notebook|Opportunities|Settings)");
                    }
            }

            System.out.println("swtich to " + tabBarOption);

//            DriverUtils.sleep(60);
        });
        Then("^Common: I click back icon$", () -> {
            System.out.println("^Common: I click back icon$");

            tactContactObjPage.getGoBackToContactsMainPageButton().tap();
//            while ( Grid.driver().findElementsByXPath(tactContactsMainPage.getContactsPlusIconButton().getLocator()).size() != 0
//                    && DriverUtils.isAndroid() ){
//                System.out.println("From Contact Obj to Contacts list");
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap();
//            }
//            if ( DriverUtils.isIOS() ){
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
//            }
        });
    }

    public boolean resyncPopUp(){
        //Tact needs to resync pop_up
        if (DriverUtils.isIOS() &&
                Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactNeedsToResyncTitleLabel().getLocator()).size()!=0) {
            System.out.println("Start waiting for \"Tact need to resync\"");
            tactAlertsPopUpPage.getResyncButton().tap();
//            WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
            WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingItemsLabel());
            System.out.println("\"Tact need to resync\" finished");
            WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
            return true;
        } else {
            System.out.println("No \"Tact need to resync\"");
            return false;
        }
    }
}

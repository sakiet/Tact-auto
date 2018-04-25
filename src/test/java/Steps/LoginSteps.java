package Steps;

import Utils.CustomPicoContainer;
import Utils.DriverUtils;
import java.util.List;

import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.html.Button;
import com.paypal.selion.platform.html.CheckBox;
import com.paypal.selion.platform.html.TextField;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.*;
import com.paypal.selion.testcomponents.mobile.TactCalendar.TactCalendarMainPage;
import com.paypal.selion.testcomponents.mobile.TactSetting.ExchangePage;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import cucumber.api.DataTable;
import io.appium.java_client.remote.MobilePlatform;

public class LoginSteps implements En {

    private TactWelcomePage tactWelcomePage;
    private SFLoginWebviewPage sfLoginWebviewPage;
    private TactAccessSFPage tactAccessSFPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private TactCalendarMainPage tactCalendarMainPage;
    private ExchangePage exchangePage;

    public LoginSteps() {

        tactWelcomePage = new TactWelcomePage();
        sfLoginWebviewPage = new SFLoginWebviewPage();
        tactAccessSFPage = new TactAccessSFPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();
        tactCalendarMainPage = new TactCalendarMainPage();
        exchangePage = new ExchangePage();

        Given("^Login: I click connect with SF button$", () -> {
            System.out.println("^Login: I click connect with SF button$");

            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
            tactWelcomePage.getConnectWithSFButton().tap();
        });
        And("^Login-Webview: I \"([^\"]*)\" send usage to google chrome and \"([^\"]*)\" sign in Chrome$", (String isSend, String isSignIn) -> {
            System.out.println("^Login-Webview: I " + isSend + " send usage to google chrome and " + isSignIn + " sign in Chrome$");

            if ( Grid.driver().findElementsById(sfLoginWebviewPage.getChromeSendReportCheckboxButton().getLocator()).size() != 0 ){
                if ( !isSend.equalsIgnoreCase("do") ) {
                    sfLoginWebviewPage.getChromeSendReportCheckboxButton().tap();
                }
                sfLoginWebviewPage.getChromeAcceptContinueButton().tap(sfLoginWebviewPage.getChromeSignInNoThanksButton());

                if ( !isSignIn.equalsIgnoreCase("do") ) {
                    sfLoginWebviewPage.getChromeSignInNoThanksButton().tap(sfLoginWebviewPage.getSfLogoImage());
                }
            }
        });
        And("^Login-Webview: I enter the user email address with dataTable$", (DataTable userData) -> {
            System.out.println("^Login: I enter the user email address with dataTable$");

//            //get Data from UserDetails using raw, and print the out
            List<List<String>> data = userData.raw();
            for ( int i=0; i<data.size(); i++ ){
                System.out.println(data.get(i).toString());
            }

            String sfAccountName = data.get(0).get(1).toString();
            String sfPWD = data.get(1).get(1).toString();

            if ( DriverUtils.isIOS() ){
                //Webview - DriverUtils.convertToWebviewDriver();
                sfAccountName = CustomPicoContainer.userInfor.getSalesforceIOSAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField userNameSFTextField = new TextField( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField pwdSFTextField = new TextField( sfLoginWebviewPage.getPwdTextField().getLocator() );

                userNameSFTextField.type(sfAccountName);
                pwdSFTextField.type(sfPWD);
            } else {
                sfAccountName = CustomPicoContainer.userInfor.getSalesforceAndroidAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getSfLogoImage());
                sfLoginWebviewPage.getUserEmailTextField().setText(sfAccountName);
                sfLoginWebviewPage.getPwdTextField().sendKeys(sfPWD);
            }

            System.out.println("salesforce UserName : " + sfAccountName);
        });
        And("^Login-Webview: I enter the user email address$", () -> {
            System.out.println("^Login: I enter the user email address$");

            String sfAccountName = null;
            String sfPWD = CustomPicoContainer.userInfor.getSalesforcePwd();

            //iOS
            if ( DriverUtils.isIOS() ){
                System.out.println("IOS");
                sfAccountName = CustomPicoContainer.userInfor.getSalesforceIOSAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField userNameSFTextField = new TextField( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField pwdSFTextField = new TextField( sfLoginWebviewPage.getPwdTextField().getLocator() );

                userNameSFTextField.type(sfAccountName);
                pwdSFTextField.type(sfPWD);

            //Android
            } else {
                System.out.println("Android");
                sfAccountName = CustomPicoContainer.userInfor.getSalesforceAndroidAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getSfLogoImage());
                sfLoginWebviewPage.getUserEmailTextField().setText(sfAccountName);
                sfLoginWebviewPage.getPwdTextField().sendKeys(sfPWD);
            }

            System.out.println("salesforce UserName : " + sfAccountName);
        });
        And("^Login-Webview: I \"([^\"]*)\" check remember me$", (String isCheck) -> {
            System.out.println("^Login-Webview: I " + isCheck + " check remember me$");

            if ( isCheck.equalsIgnoreCase("do")){
                if ( DriverUtils.isIOS() ) {
                    CheckBox rememberMeCheckBox = new CheckBox( sfLoginWebviewPage.getRememberMeCheckBoxButton().getLocator() );
                    rememberMeCheckBox.check();
                } else {
                    sfLoginWebviewPage.getRememberMeCheckBoxButton().tap();
                }
            }
        });
        And("^Login-Webview: I click login button$", () -> {
            System.out.println("^Login: I click login button$");

            Button loginButton = new Button( sfLoginWebviewPage.getLoginButton().getLocator() );
            loginButton.click();
        });
        When("^Login-Webview: Login with existing user$", () -> {
            System.out.println("^Login: Login with existing user$");

            //Webview - DriverUtils.convertToWebviewDriver();

            WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getTactLoginWithExistingUserButton() );
            System.out.println("before get the button");
            Button loginWithExistingUserButton = new Button( sfLoginWebviewPage.getTactLoginWithExistingUserButton().getLocator() );

            System.out.println("local : " + loginWithExistingUserButton.getLocator());

            loginWithExistingUserButton.click();
            System.out.println("After click the existing button");
            DriverUtils.sleep(5);
        });
        Then("^Login: Allow Tact to access salesforce user data$", () -> {
            System.out.println("^Login: Allow Tact to access salesforce user data$");

            DriverUtils.scrollToBottom();
            if ( DriverUtils.isIOS() && Grid.driver().findElementsById(sfLoginWebviewPage.getTactAccessAllowButton().getLocator()).size() !=0 ){
                System.out.println("This is the 1st time to access SF from Tact App");
                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getTactAccessAllowButton());
                sfLoginWebviewPage.getTactAccessAllowButton().tap();
            }
        });
        Then("^Login: After SF connected, then Add Contacts to Tact$", () -> {
            System.out.println("^Login: After SF connected, then Add Contacts to Tact$");

            WebDriverWaitUtils.waitUntilElementIsVisible( tactAccessSFPage.getAddContactToTactTitleLabel() );
            tactAccessSFPage.getAddContactsButton().tap( );
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible( tactAlertsPopUpPage.getTactAccessContactsMsgLabel());
                tactAlertsPopUpPage.getAlertsOKButton().tap();
            } else if (Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size()!=0) {
                tactAlertsPopUpPage.getAlertsAllowButton().tap();
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingLabel());
        });
        And("^Login: Waiting for Syncing finished in \"([^\"]*)\" process$", (String processOption) -> {
            System.out.println("^Login: Waiting for Syncing finished in " + processOption + " process$");
            //processOption: onboarding | login

            //login processing begin
            if (Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size()!=0) {
                System.out.println("Start waiting for sync...");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingLabel());
                System.out.println("sync finished");
                DriverUtils.sleep(2);
            } else {
                if (processOption.equalsIgnoreCase("login")){
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
                }
                System.out.println("Tact Syncing is done");
            }
            //Syncing data to your phone.
            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel().getLocator()).size()!=0) {
                System.out.println("Start waiting for \"Syncing data to your phone.\"");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingItemsLabel());
                System.out.println("\"Syncing data to your phone.\" finished");
            } else {
                System.out.println("Tact Syncing Data to Phone is done");
            }
            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login")) {
                DriverUtils.sleep(5);
            }
            //exchangeSync
            if (DriverUtils.isIOS() &&
                    processOption.equalsIgnoreCase("login") &&
                    Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactExchangeSyncErrorMsgTitleLabel().getLocator()).size() != 0){
                System.out.println("Start waiting for \"exchange Reauth Sync\"");
                tactAlertsPopUpPage.getReauthorizeButton().tap(exchangePage.getExchangeTitleLabel());

                String exchangePwdText = CustomPicoContainer.userInfor.getExchangeIOSEmailPwd();
                System.out.println("exchange pwd " + exchangePwdText);
                exchangePage.getExchangePwdTextField().sendKeys(exchangePwdText);
                exchangePage.getSubmitButton().tap();
                System.out.println("\"exchange Reauth Sync\" finished");
                DriverUtils.sleep(2);
            }
            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("onboarding")){
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
            } else if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login")) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
            }
            DriverUtils.sleep(2);
            System.out.println(">>>>>>>After login");
            //login processing finish
        });
        And("^Login: Allow all access for Tact$", () -> {
            System.out.println("^Login: Allow all access for Tact$");

            //notification
            if ( DriverUtils.isIOS() ) {
                System.out.println("Allow to Send Notifications");
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
                tactAlertsPopUpPage.getAlertsAllowButton().tap(tactNavigateTabBarPage.getTactCalendarButton());

                //switch to Calendar tab
                tactNavigateTabBarPage.getTactCalendarButton().tap(tactCalendarMainPage.getConnectYourCalendarAndRemindersButton());

                //Connect Calendars and Reminders
                System.out.println("Connect Calendars and Reminders");
                tactCalendarMainPage.getConnectYourCalendarAndRemindersButton().tap(tactAlertsPopUpPage.getAlertsOKButton());
                tactAlertsPopUpPage.getAlertsOKButton().tap(tactCalendarMainPage.getConnectCalendarTitleLabel());
                //Connect Calendars
                System.out.println("Calenders");
                tactCalendarMainPage.getCalendarRemindersDoneButton().tap(tactAlertsPopUpPage.getAlertsOKButton());
                tactAlertsPopUpPage.getAlertsOKButton().tap(tactCalendarMainPage.getConnectRemindersTitleLabel());
                //Connect Reminders
                System.out.println("Reminders");
                tactCalendarMainPage.getCalendarRemindersDoneButton().tap(tactNavigateTabBarPage.getTactCalendarButton());
            } else {    //Android - Connect Calendars
                System.out.println("Connect Calendars and Reminders");
                tactCalendarMainPage.getConnectYourCalendarAndRemindersButton().tap(tactAlertsPopUpPage.getAlertsAllowButton());
                tactAlertsPopUpPage.getAlertsAllowButton().tap();
                DriverUtils.tapAndroidHardwareBackBtn();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton());
                DriverUtils.sleep(2);
            }
        });
    }

}

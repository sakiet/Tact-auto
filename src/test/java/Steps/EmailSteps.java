package Steps;

import Utils.CustomPicoContainer;
import Utils.DriverUtils;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.html.Button;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactEmail.NewMessagePage;
import com.paypal.selion.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import com.paypal.selion.testcomponents.mobile.TactNavigateTabBarPage;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.sql.Driver;
import java.util.List;

public class EmailSteps implements En {

    private String sendEmailAddress = "";
    private String sendEmailSubjectText = "";
    private boolean isSendEmail = false;
    private boolean isSaveDraftEmail = false;

    private TactMailBoxesPage tactMailBoxesPage;
    private NewMessagePage newMessagePage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;

    public EmailSteps() {

        tactMailBoxesPage = new TactMailBoxesPage();
        newMessagePage = new NewMessagePage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();

        When("^Email: I send an new email from \"([^\"]*)\" mailType, \"([^\"]*)\" option in MailBoxes page$", (String mailType, String option) -> {
            System.out.println("^Email: I send an new email from " + mailType + " mailType, " + option + " option in MailBoxes page$");


            WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getMailBoxesTitleLabel());

            if (mailType.equalsIgnoreCase("Exchange")){
                System.out.println("email type : " + mailType);

                switch (option) {
                    case "Inbox":
                        tactMailBoxesPage.getExchangeOrGmailInboxsButton().tap();
                        break;
                    case "Archive":
                        tactMailBoxesPage.getExchangeArchiveButton().tap();
                        break;
                    case "Deleted Items":
                        tactMailBoxesPage.getExchangeDeletedItemsButton().tap();
                        break;
                    case "Junk Email":
                        tactMailBoxesPage.getExchangeJunkEmailButton().tap();
                        break;
                    case "Sent Items":
                        tactMailBoxesPage.getSentItemsButton().tap();
                        break;
                    default:
                        SeLionAsserts.verifyFalse(true, "Please give a correct String " +
                                "(Inbox|Archive|Deleted Items|Junk Email|Sent Items)");
                }
            } else if (mailType.equalsIgnoreCase("Google") && mailType.equalsIgnoreCase("gmail")){
                System.out.println("email type is : " + mailType);

                switch (option) {
                    case "Inbox":
                        tactMailBoxesPage.getExchangeOrGmailInboxsButton().tap();
                        break;
                    case "Sent":
                        tactMailBoxesPage.getAllOrGmailSentButton().tap();
                        break;
                    default:
                        SeLionAsserts.verifyFalse(true, "Please give a correct String " +
                                "(Inbox|Sent)");
                }
            } else {
                switch (option) {
                    case "All Inboxes":
                        tactMailBoxesPage.getAllInboxedButton().tap();
                        break;
                    case "Priority":
                        tactMailBoxesPage.getPriorityButton().tap();
                        break;
                    case "Sent":
                        tactMailBoxesPage.getAllOrGmailSentButton().tap();
                        break;
                    case "Tracked":
                        tactMailBoxesPage.getTrackedButton().tap();
                        break;
                    default:
                        SeLionAsserts.verifyFalse(true, "Please give a correct String " +
                                "(All Inboxes|Priority|Sent|Tracked)");
                }
            }

            WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getComposeButton());
            tactMailBoxesPage.getComposeButton().tap(newMessagePage.getNewMessageTitleLabel());

        });
        Then("^Email: I create a simply email To \"([^\"]*)\", Subject \"([^\"]*)\" and body \"([^\"]*)\"$", (String toEmail, String subject, String body) -> {
            System.out.println("^Email: I create a simply email To " + toEmail + ", Subject " + subject + " and body " + body + "$");

            WebDriverWaitUtils.waitUntilElementIsVisible(newMessagePage.getNewMessageTitleLabel());

            sendEmailAddress = newMessagePage.getFromLabel().getValue();
            String emailType = sendEmailAddress.split("@")[1].split("\\.")[0];
            System.out.println("send from " + sendEmailAddress + ", emailType: " + emailType);

            //To:
            if (toEmail.equalsIgnoreCase("anotherPlatformExchangeEmail")){
                if (DriverUtils.isIOS()){
                    toEmail = CustomPicoContainer.userInfor.getExchangeAndroidEmailAddress();
                } else {
                    toEmail = CustomPicoContainer.userInfor.getExchangeIOSEmailAddress();
                }
            }
            newMessagePage.getToNewMessageTextField().sendKeys(toEmail + "\n");

            //Subject
            String dateMonthTime = DriverUtils.currentDateInfo("mm") + "/" + DriverUtils.currentDateInfo("date") +
                    "/" + DriverUtils.currentDateInfo("hours") + DriverUtils.currentDateInfo("mins");
            String currentUser;
            if(DriverUtils.isIOS()){
                currentUser = CustomPicoContainer.userInfor.getSalesforceIOSAccountName();
            } else {
                currentUser = CustomPicoContainer.userInfor.getSalesforceAndroidAccountName();
            }
            currentUser = currentUser.split("\\.")[1];
            sendEmailSubjectText = dateMonthTime + "_sendFrom_TactAPP_" + currentUser;
            if (!DriverUtils.isTextEmpty(subject)){
                sendEmailSubjectText = sendEmailSubjectText + "_" + subject;
            }
            System.out.println("subject text : " + sendEmailSubjectText);
            newMessagePage.getSubjectNewMessageTextField().sendKeys(sendEmailSubjectText);

            DriverUtils.tapXY(188, 388);
            newMessagePage.getBodyTextField().sendKeys(body);

        });
        And("^Email: I \"([^\"]*)\" send email and \"([^\"]*)\" save draft$", (String isSend, String isSaveDraft) -> {
            System.out.println("^Email: I " + isSend + " send email and " + isSaveDraft + " save draft$");

            if(!DriverUtils.isTextEmpty(isSend)){
                isSendEmail = true;
                System.out.println("send new message");
                newMessagePage.getSendNewMessageButton().tap();
            } else {
                isSendEmail = false;
                System.out.println("Do not send new message");
                newMessagePage.getCancelNewMessageButton().tap(newMessagePage.getCancelSaveDraftButton());
                if (DriverUtils.isTextEmpty(isSaveDraft)){
                    isSaveDraftEmail = false;
                    System.out.println("Delete Draft");
                    newMessagePage.getCancelDeleteDraftButton().tap();
                } else {
                    isSaveDraftEmail = true;
                    System.out.println("Save Draft");
                    newMessagePage.getCancelSaveDraftButton().tap();
                }
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
        });
        And("^Email: I verify the email$", () -> {
            System.out.println("^Email: I verify the email$");

            tactNavigateTabBarPage.getTactEmailButton().tap(tactMailBoxesPage.getMailBoxesTitleLabel());

            if (isSendEmail){
                if ( isGmailType(sendEmailAddress) ){
                    DriverUtils.scrollToBottom();
                    Button googleSent = new Button("//XCUIElementTypeImage[@name='sent']");
                    googleSent.click();
                    WebDriverWaitUtils.waitUntilElementIsVisible("//XCUIElementTypeNavigationBar[@name='Sent']");
                } else {
                    tactMailBoxesPage.getSentItemsButton().tap();
                    WebDriverWaitUtils.waitUntilElementIsVisible("//XCUIElementTypeNavigationBar[@name='Sent Items']");
                }
            } else if (isSaveDraftEmail) {
                tactMailBoxesPage.getDraftsButton().tap();
                WebDriverWaitUtils.waitUntilElementIsVisible("//XCUIElementTypeNavigationBar[@name='Drafts']");
            }
            if ( isSendEmail || isSaveDraftEmail ) {
                System.out.println("isSendEmail " + isSendEmail + "; isSaveDraftEmail " + isSaveDraftEmail + "\nloc " + getSubjectLoc());

                DriverUtils.scrollToTop();

                Assert.assertTrue(Grid.driver().findElementsByXPath(getSubjectLoc()).size() != 0, "Did not find the expected email");
            }
        });
        When("^Email: I connect with \"([^\"]*)\" email account inside Email tab bar$", (String emailOption) -> {
            System.out.println("^Email: I connect with " + emailOption + " email account inside Email tab bar$");

            if ( Grid.driver().findElementsByXPath(tactMailBoxesPage.getExchangeGmailConnectedButton().getLocator()).size() == 0 ){
                String connectButtonLoc = tactMailBoxesPage.getExchangeGmailConnectButton().getLocator();

                if (emailOption.equalsIgnoreCase("exchange")){
                    Grid.driver().findElementsByXPath(connectButtonLoc).get(0).click();
                } else {
                    Grid.driver().findElementsByXPath(connectButtonLoc).get(1).click();
                }
            } else {
                tactMailBoxesPage.getExchangeGmailConnectButton().tap();
            }
        });
    }

    public boolean isGmailType(String email){
        if (email.contains("gmail")){
            return true;
        } else {
            return false;
        }
    }

    public String getSubjectLoc() {
        String emailSubjectLoc = "//*[contains(@name, 'subjectText')]";

        //only show 38 chars
        if (sendEmailSubjectText.length() > 30){
            //38 chars + ...
            emailSubjectLoc = emailSubjectLoc.replaceAll("subjectText",sendEmailSubjectText.substring(0,30));
        } else {
            emailSubjectLoc = emailSubjectLoc.replaceAll("subjectText", sendEmailSubjectText);
        }
        System.out.println("emailSub Loc" + emailSubjectLoc);
        return emailSubjectLoc;
    }
}

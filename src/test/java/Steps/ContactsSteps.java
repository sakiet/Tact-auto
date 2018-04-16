package Steps;

import Utils.DriverUtils;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactContact.TactAddNewContactPage;
import com.paypal.selion.testcomponents.mobile.TactContact.TactContactObjPage;
import com.paypal.selion.testcomponents.mobile.TactLead.TactAddNewLeadPage;
import com.paypal.selion.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import com.paypal.selion.testcomponents.mobile.TactContact.TactContactsMainPage;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class ContactsSteps implements En {

    private TactContactsMainPage tactContactsMainPage;
    private TactContactObjPage tactContactObjPage;
    private TactAddNewContactPage tactAddNewContactPage;
    private TactAddNewLeadPage tactAddNewLeadPage;
    private TactAddNewCompanyPage tactAddNewCompanyPage;

    public ContactsSteps() {

        tactContactsMainPage = new TactContactsMainPage();
        tactContactObjPage = new TactContactObjPage();
        tactAddNewContactPage = new TactAddNewContactPage();
        tactAddNewLeadPage = new TactAddNewLeadPage();
        tactAddNewCompanyPage = new TactAddNewCompanyPage();

        Then("^Contacts: I go to create a new \"([^\"]*)\" page$", (String userType) -> {
            System.out.println("^Contacts: I go to create a new " + userType + " page$");

            switch (userType) {
                case "Contact":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewContactPage.getTactAddNewContactButton());
                    tactAddNewContactPage.getTactAddNewContactButton().tap(tactAddNewContactPage.getNewContactTitleLabel());
                    break;
                case "Lead":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewLeadPage.getTactAddNewLeadButton());
                    tactAddNewLeadPage.getTactAddNewLeadButton().tap(tactAddNewLeadPage.getNewLeadTitleLabel());
                    System.out.println("inside Lead");
                    DriverUtils.sleep(30);
                    break;
                case "Company":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewCompanyPage.getTactAddNewCompanyButton());
                    tactAddNewCompanyPage.getTactAddNewCompanyButton().tap(tactAddNewCompanyPage.getNewCompanyTitleLabel());
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String (Contact|Lead|Company)");
            }

        });
        When("^Contacts: I search one user \"([^\"]*)\" from contacts list and select it$", (String name) -> {
            System.out.println("^Contacts: I search one user " + name + " from contacts list and select it$");
            //search the name from search field
            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            if ( DriverUtils.isAndroid() ){
                tactContactsMainPage.getAndroidSearchIconButton().tap(tactContactsMainPage.getSearchAllContactsTextField());
            }
            tactContactsMainPage.getSearchAllContactsTextField().setText(name);

            //get the name location, and click it
            if (name.contains(",") && !name.contains(", ")) { //Testing,fname
                name = name.split(",")[1] + " " + name.split(",")[0];
            } else if (name.contains(", ")) {
                name = name.split(", ")[1] + " " + name.split(", ")[0];
            }

            System.out.println("name :" + name);
            String nameLoc = tactContactsMainPage.getNameEditButton().getLocator().replace("contactName", name);
            System.out.println("loc is ==> " + nameLoc);
            DriverUtils.sleep(1);

            Grid.driver().findElementByXPath(nameLoc).click();
        });
        And("^Contacts: I click \"([^\"]*)\" action in contact obj page$", (String actionOption) -> {
            System.out.println("^Contacts: I click " + actionOption + " action in contact obj page$");

            switch (actionOption) {
                case "Add Task":
                    tactContactObjPage.getAddTaskActionButton().tap();
                    break;
                case "Connect LinkedIn":
                    if ( Grid.driver().findElementsByXPath(tactContactObjPage.getConnectLinkedInActionButton().getLocator()).size()!=0 ) {
                        tactContactObjPage.getConnectLinkedInActionButton().tap();
                    }
                    break;
                case "Add Opportunity":
                    if ( Grid.driver().findElementsByXPath(tactContactObjPage.getAddOpportunityActionButton().getLocator()).size() !=0 ) {
                        DriverUtils.slideDown();
                    }
                    tactContactObjPage.getAddOpportunityActionButton().tap();
                    break;
                case "emailIcon":
                    tactContactObjPage.getProfileEmailActionButton().tap();
                    break;
                case "phoneIcon":
                    tactContactObjPage.getProfilePhoneActionButton().tap();
                    break;
                case "mapIcon":
                    tactContactObjPage.getMapIconActionButton().tap();
                    break;
                case "commentIcon":
                    tactContactObjPage.getCommentProfileActionButton().tap();
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Add Task|Connect LinkedIn|Add Opportunity|emailIcon|phoneIcon|mapIcon|commentIcon)");
            }
        });
        Then("^Contacts: I back to Contacts Main page from \"([^\"]*)\" page$", (String page) -> {
            System.out.println("^Contacts: I back to Contacts Main page from " + page + " page$");

            switch (page) {
                case "Contact":
                    tactContactObjPage.getProfilePhoneActionButton().tap();
                    break;
                case "Lead":
                    System.out.println("Not implement yet");
                    break;
                case "Company":
                    System.out.println("Not implement yet");
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Contact|Lead|Company)");
            }
        });
    }
}

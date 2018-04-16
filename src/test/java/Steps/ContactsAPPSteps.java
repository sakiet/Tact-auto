package Steps;

import Utils.DriverUtils;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.ContactsAPP.ContactsAppPage;
import cucumber.api.java8.En;

public class ContactsAPPSteps implements En {

    ContactsAppPage contactsAppPage;

    public ContactsAPPSteps() {

        contactsAppPage = new ContactsAppPage();

        And("^ContactsAPP: I am in Contacts APP$", () -> {
            System.out.println("^ContactsAPP: I am in Contacts APP$");

            WebDriverWaitUtils.waitUntilElementIsVisible(contactsAppPage.getContactsTitleLabel());
        });
        Then("^I search for contact's name \"([^\"]*)\"$", (String contactName) -> {
            System.out.println("^I search for contact's name \"([^\"]*)\"$");

            if (contactName.contains(",")) { //Testing,fname
                contactName = contactName.split(",")[1] + " " + contactName.split(",")[0];
            }

            contactsAppPage.getSearchTextField().setText(contactName);
            //"Add //XCUIElementTypeStaticText[@name='contactName']"
            System.out.println("name :" + contactName);
            String contactNameLoc = contactsAppPage.getNameEditButton().getLocator().replace("contactName", contactName);
            System.out.println("loc is ==> " + contactNameLoc);
            Grid.driver().findElementByXPath(contactNameLoc).click();
        });
        And("^I edit and delete it from Contacts App$", () -> {
            System.out.println("^I edit and delete it from Contacts App$");

            WebDriverWaitUtils.waitUntilElementIsVisible(contactsAppPage.getEditContactButton());
            contactsAppPage.getEditContactButton().tap(contactsAppPage.getCancelEditButton());

            DriverUtils.scrollToBottom();
            WebDriverWaitUtils.waitUntilElementIsVisible(contactsAppPage.getDeleteContactButton());

            contactsAppPage.getDeleteContactButton().tap(contactsAppPage.getConfirmDeleteContactButton());
            contactsAppPage.getConfirmDeleteContactButton().tap(contactsAppPage.getContactsTitleLabel());
        });


    }
}

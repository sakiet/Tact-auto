package Steps;

import Utils.DriverUtils;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactAlertsPopUpPage;
import com.paypal.selion.testcomponents.mobile.TactContact.*;
import com.paypal.selion.testcomponents.mobile.TactContact.TactContactsMainPage;
import cucumber.api.java8.En;

public class AddDeleteContactSteps implements En {

    private TactAddNewContactPage tactAddNewContactPage;
    private TactContactsMainPage tactContactsMainPage;
    private TactContactObjPage tactContactObjPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;

    public AddDeleteContactSteps() {

        tactAddNewContactPage = new TactAddNewContactPage();
        tactContactsMainPage = new TactContactsMainPage();
        tactContactObjPage = new TactContactObjPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();

        When("^AddContact: I \"([^\"]*)\" save to Phone and \"([^\"]*)\" send to Salesforce$", (String saveToPhone, String sendToSF) -> {
            System.out.println("^AddContact: I " + saveToPhone + " save to Phone and " + sendToSF + " send to Salesforce$");

            //Save to Phone
            if ( Grid.driver().findElementsByXPath(tactAddNewContactPage.getSaveToIPhoneSwitch().getLocator()).size() !=0
                    && DriverUtils.isTextEmpty(saveToPhone)){
                tactAddNewContactPage.getSaveToIPhoneSwitch().changeValue();
            }
            if (DriverUtils.isTextEmpty(sendToSF)){
                tactAddNewContactPage.getSendToSaleforceSwitch().changeValue();
            }
        });
        When("^AddContact: I input a user name \"([^\"]*)\" and \"([^\"]*)\"$", (String contactName, String isSave) -> {
            System.out.println("^AddContact: I input user name " + contactName);

            String fName = null;
            String lName = contactName;

            if (contactName.contains(" ") && !contactName.contains(", ")){
                fName = contactName.split(" ")[0];
                lName = contactName.split(" ")[1];
            } else if (contactName.contains(",") && !contactName.contains(", ")){
                fName = contactName.split(",")[1];
                lName = contactName.split(",")[0];
            } else if (contactName.contains(", ")){
                fName = contactName.split(", ")[1];
                lName = contactName.split(", ")[0];
            }

            if (fName != null) {
                tactAddNewContactPage.getFirstNameTextField().setText(fName);
            }
            tactAddNewContactPage.getLastNameTextField().setText(lName);

            if ( !DriverUtils.isTextEmpty(isSave) ){
                tactAddNewContactPage.getSaveNewContactButton().tap();

                if ( DriverUtils.isAndroid() ){
                    tactContactObjPage.getGoBackToContactsMainPageButton().tap();
                }
            } else {
                System.out.println("w/o save");

                tactAddNewContactPage.getCancelAddNewContactButton().tap();
                if ( DriverUtils.isAndroid() ) {
                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
                }
//                if ( Grid.driver().findElementsById(tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().getLocator()).size() !=0 ){
//                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
//                }
            }



            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            System.out.println("save ? " + isSave);
            DriverUtils.sleep(10);
        });
    }
}

package Steps;

import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactCalendar.TactCalendarMainPage;
import com.paypal.selion.testcomponents.mobile.TactEvent.TactEventPage;
import com.paypal.selion.testcomponents.mobile.TactPinPage;
import com.paypal.selion.testcomponents.mobile.TactTask.TactTaskPage;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class CalendarSteps implements En {

    private TactCalendarMainPage tactCalendarMainPage;
    private TactPinPage tactPinPage;
    private TactEventPage tactEventPage;
    private TactTaskPage tactTaskPage;


    public CalendarSteps() {
        tactCalendarMainPage = new TactCalendarMainPage();
        tactPinPage = new TactPinPage();
        tactEventPage = new TactEventPage();
        tactTaskPage = new TactTaskPage();

        When("^Calendar: I click plus icon and select \"([^\"]*)\" option$", (String option) -> {
            System.out.println("^Calendar: I click plus icon and select " + option + " option$");

            WebDriverWaitUtils.waitUntilElementIsVisible(tactCalendarMainPage.getCalendarAddPlusButton());

            switch (option) {
                case "Task":
                    tactCalendarMainPage.getCalendarAddPlusButton().tap(tactPinPage.getAddTaskButton());
                    tactPinPage.getAddTaskButton().tap(tactTaskPage.getNewTaskTitleLabel());
                    break;
                case "Event":
                    tactCalendarMainPage.getCalendarAddPlusButton().tap(tactPinPage.getAddEventButton());
                    tactPinPage.getAddEventButton().tap(tactEventPage.getNewEventTitleLabel());
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String (Task|Event)");
            }
        });
    }
}

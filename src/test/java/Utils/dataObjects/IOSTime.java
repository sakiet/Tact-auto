package Utils.dataObjects;

import Utils.DriverUtils;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.testcomponents.mobile.TactTimer.IOSDateTimePage;
import com.sun.tools.javac.tree.DCTree;
import org.openqa.selenium.WebElement;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IOSTime {

    private static IOSDateTimePage iOSDateTimePage = new IOSDateTimePage();

    private static String expectDayMonth;   //Mar 2
    private static String expectDay;
    private static Month expectMonth;
    private static String expectYear;
    private static String expectHours;
    private static String expectMins;
    private static IsAMPM expectIsAMPM;

    private static String displayDayMonth;
    private static Month displayMonth;
    private static String displayDate;
    private static String displayYear;


    public IOSTime(){}

    //select date and time
    public static void changeDateAndTime (String date, String expectTime){
        WebDriverWaitUtils.waitUntilElementIsVisible(iOSDateTimePage.getDateTimeDoneLabel());

        displayDayMonth();

        if (date.equalsIgnoreCase("today")){
            getCurrentDayMonth();
        } else {
            convertDayMonth(date);
        }
        convertTime(expectTime);

        List<WebElement> allElements = Grid.driver().findElementsByClassName(iOSDateTimePage.getDateTimePicker().getLocator());
        //select day and month
        int loop = expectMonth.showNum() - displayMonth.showNum();
        String sendKey;
        int month = displayMonth.showNum();
        if ( loop > 0 ){
            while ( loop != 0 ) {
                sendKey = getMonthForInt(month).substring(0, 3) + " " + 28;
                allElements.get(0).sendKeys(sendKey);
                month++;
                loop--;
            }
        }
        if ( loop < 0 ){
            while ( loop!=0 ) {
                sendKey = getMonthForInt(month).substring(0, 3) + " " + 1;
                allElements.get(0).sendKeys(sendKey);
                month--;
                loop++;
            }
        }
        allElements.get(0).sendKeys(expectDayMonth);
        //select Time
        allElements.get(1).sendKeys(expectHours);
        allElements.get(2).sendKeys(expectMins);
        allElements.get(3).sendKeys(expectIsAMPM.toString());

        System.out.println(expectDayMonth + " " + expectHours + " " + expectMins + " " + expectIsAMPM );

        iOSDateTimePage.getDateTimeDoneButton().tap();
    }

    public static void changeDate (String date){
        WebDriverWaitUtils.waitUntilElementIsVisible(iOSDateTimePage.getDateTimeDoneLabel());

        displayDayMonth();
        if (date.equalsIgnoreCase("today")){
            getCurrentDayMonth();
        } else {
            convertDayMonth(date);
        }

        List<WebElement> allElements = Grid.driver().findElementsByClassName(iOSDateTimePage.getDateTimePicker().getLocator());
        //select day and month
        int loop = expectMonth.showNum() - displayMonth.showNum();
        System.out.println("loop " + loop);
        String sendKey;
        int month = displayMonth.showNum();
        //select month
        if ( loop > 0 ){
            while ( loop != 0 ) {
                sendKey = getMonthForInt(month);
                System.out.println("send key " + sendKey);
                allElements.get(1).sendKeys(sendKey);
                month++;
                loop--;
            }
        }
        if ( loop < 0 ){
            while ( loop!=0 ) {
                sendKey = getMonthForInt(month).substring(0, 3) + " " + 1;
                allElements.get(1).sendKeys(sendKey);
                month--;
                loop++;
            }
        }
        allElements.get(0).sendKeys(expectDay);
        System.out.println("expectDay : " + expectDay + " ==== expectMonth : " + expectMonth );

        System.out.println(expectDayMonth );

        iOSDateTimePage.getDateTimeDoneButton().tap();
    }

    private static void currentDate(){

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        System.out.println("date : " + date);

        displayMonth = Month.valueOf(new SimpleDateFormat("MMM").format(date));
        displayDate = new SimpleDateFormat("dd").format(date);
        displayYear = new SimpleDateFormat("yyyy").format(date);

        System.out.println("current date " + displayMonth.showNum() + " " + displayDate + " " + displayYear);
    }

    public static String getCurrentDate(){

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        System.out.println("date : " + date);

        displayMonth = Month.valueOf(new SimpleDateFormat("MMM").format(date));
        displayDate = new SimpleDateFormat("dd").format(date);
        displayYear = new SimpleDateFormat("yyyy").format(date);

        return displayMonth.showNum() + "/" + displayDate + "/" + displayYear;
    }

    private static void convertTime(String expectTime){
        //12:25 am
        expectHours = expectTime.split(" ")[0].split(":")[0];
        expectMins = expectTime.split(" ")[0].split(":")[1];
        expectIsAMPM = IsAMPM.valueOf(expectTime.split(" ")[1].toUpperCase());
    }

    //Sat, Sep 1
    private static void displayDayMonth(){
        List<WebElement> allElements = Grid.driver().findElementsByClassName(iOSDateTimePage.getDateTimePicker().getLocator());
        String date = allElements.get(0).getText();
        System.out.println("display date " + date);
        if ( date.equalsIgnoreCase("Today")){

            displayYear = DriverUtils.currentDateInfo("year");
            displayDate = DriverUtils.currentDateInfo("date");
            displayMonth = Month.valueOf(DriverUtils.currentDateInfo("month"));

        }else if (date.contains(",")){  //xx mmm dd, hh
            displayDayMonth = date.split(", ")[1];
            displayMonth = Month.valueOf(displayDayMonth.split(" ")[0]);
            displayDate = displayDayMonth.split(" ")[1];

            System.out.println(displayDate + "/" + displayMonth);
        } else {
            displayDate = date.split(" ")[1];   //xxx dd
            String month = allElements.get(1).getText();
            System.out.println("display month " + month);
            displayMonth = Month.valueOf(month.substring(0,3));

            displayYear = allElements.get(2).getText();

            System.out.println(displayDate + "/" + displayMonth + "/" + displayYear);
        }
    }

    //10/10/2018   | w/o      | Jan 1, 2019
    private static void convertDayMonth(String date){
        if(date.contains("/")){
            expectMonth = Month.valueOf(getMonthForInt(Integer.parseInt(date.split("/")[0])).substring(0,3));
            expectDay = date.split("/")[1];
            expectYear = date.split("/")[2];
            expectDayMonth = expectMonth + " " + expectDay;
        }  else {
            expectDayMonth = date.split(", ")[0];
            expectMonth = Month.valueOf(expectDayMonth.split(" ")[0]);
            expectDay = expectDayMonth.split(" ")[1];
            expectYear = date.split(", ")[1];
        }
        System.out.println("expect date " + expectMonth +  " " + expectDay + " " + expectYear);
    }

    private static void getCurrentDayMonth(){
        Date date = DriverUtils.currentDate();

        expectYear = new SimpleDateFormat("yyyy").format(date);
        expectDay = new SimpleDateFormat("dd").format(date);
        expectMonth = Month.valueOf(new SimpleDateFormat("MMM").format(date));
    }

    private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 1 && num <= 12 ) {
            month = months[num-1];
        }
        return month;
    }

    public static void main(String[] arg){
        IsAMPM isAMPM = IsAMPM.AM;
        System.out.println( isAMPM + " >>>> " + isAMPM.toString());
        convertDayMonth("10/12/2019");

        System.out.println(System.getProperty("user.dir"));

    }
}

import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;

public class Date implements Comparable <Date> {
    private final int month;
    private final int day;
    private final int year;

    // constructor
    public Date (int[] parsedDate) {
        month = parsedDate[0];
        day = parsedDate[1];
        year = parsedDate[2];

    }


    // methods
    public static boolean validMonthDayYearTriplet (int[] parsedDate) {
        int month = parsedDate[0];
        int day = parsedDate[1];
        int year = parsedDate[2];
        int currentYear = Year.now().getValue();
        // invalid month or year
        if (month < 1 || month > 12 || year < 1 || year > currentYear) {
            return false;
        } else { // invalid day
                                //JA, FE, MA, AP, MA, JN, JL, AU, SE, OC, NO, DE
            int[] daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (day > daysInMonths[month - 1] || day < 1) {
                return false;
            }
        }
        if (year == currentYear) { // year is the current year
            int currentMonth = YearMonth.now().getMonthValue();
            if (month > currentMonth) { // month is in the future
                return false;
            } else if (month == currentMonth) { // month is the current month
                // check if day is the current day or in the past
                return day <= MonthDay.now().getDayOfMonth();
            } else { // month is in the past
                return true;
            }
        }
        return true;
    }

    // to string
    public String toString () {
        return String.format("%02d/%02d/%d", month, day, year);
    }

    //comparable
    public int compareTo (Date d) {
        int yearDifference;
        if ((yearDifference = this.year - d.year) == 0) {
            int monthDifference;
            if ((monthDifference = this.month - d.month) == 0) {
                return this.day - d.day;
            } else {
                return monthDifference;
            }
        } else {
            return yearDifference;
        }
    }

    // equals
    public boolean equals (Object o) {
        if (!(o instanceof Date d)) {
            return false;
        }
        return this.month == d.month && this.day == d.day && this.year == d.year;
    }
}

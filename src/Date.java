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
    public static boolean invalidMonthDayYearTriplet (int[] parsedDate) {
        int month = parsedDate[0];
        int day = parsedDate[1];
        int year = parsedDate[2];
        int currentYear = Year.now().getValue();
        //JA, FE, MA, AP, MA, JN, JL, AU, SE, OC, NO, DE
        int[] daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // invalid month or year
        if (day < 1 || month < 1 || month > 12 || year < 1996 || year > currentYear) {
            return true;
        } else if (year == 1996) { // check if day is before Pokémon creation
            if (month < 10) { // month is before october
                return true;
            } else if (month == 10) { // month is october
                return day < 20;
            } else { // the month is after october
                return day > daysInMonths[month - 1];
            }
        } else if (year == currentYear) { // year is the current year
            int currentMonth = YearMonth.now().getMonthValue();
            if (month > currentMonth) { // month is in the future
                return true;
            } else if (month == currentMonth) { // month is the current month
                return day > MonthDay.now().getDayOfMonth(); // check if day is the current day or in the past
            } else { // month is in the past
                return day > daysInMonths[month - 1];
            }
        } else { // year is not the current year or 1996
            return day > daysInMonths[month - 1];
        }
    }

    // to string
    public String toString () {
        return String.format("%02d/%02d/%04d", month, day, year);
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

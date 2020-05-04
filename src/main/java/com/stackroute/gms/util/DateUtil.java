package com.stackroute.gms.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/*
 * This is a utility class for performing various business functions based on date
 */
public class DateUtil {

    /*
     * This method calculates age based on the dob passed
     * @param LocalDate
     * @ return int
     * */
    public static int calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    /*
     * This method calculates years of service based on the date of joining passed
     * @param LocalDate
     * @ return int
     * */
    public static int getYearsOfService(LocalDate dateOfJoining) {
        return Period.between(dateOfJoining, LocalDate.now()).getYears();
    }

    /*
     * This method calculates months of booking based on the date of enrollment passed
     * @param LocalDate
     * @ return int
     */
    public static int getMothsOfBooking(LocalDate enrollmentDate) {

        return enrollmentDate.plus(3, ChronoUnit.MONTHS).getMonthValue();
    }

    /*
     * This method checks if the given date is null
     * @param LocalDate
     * @ return boolean
     */

    public static boolean isNull(LocalDate givenDate) {

        return givenDate == null;
    }


}

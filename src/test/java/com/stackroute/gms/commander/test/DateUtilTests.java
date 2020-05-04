package com.stackroute.gms.commander.test;


import com.stackroute.gms.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateUtilTests {


    @Test
    public void givenClassWhenGetYearsOfServiceThenReturnInteger() {
        assertEquals(14, DateUtil.getYearsOfService(LocalDate.of(2005, 10, 12)));
    }

    @Test
    public void givenClassWhenCalculateAgeThenReturnInteger() {
        assertEquals(74, DateUtil.calculateAge(LocalDate.of(1945, 12, 2)));
    }

    @Test
    public void givenClassWhenCheckNullsThenReturnBoolean() {
        assertTrue(DateUtil.isNull(null));
    }

}

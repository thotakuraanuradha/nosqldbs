package com.stackroute.gms.commander.test;

import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.repo.ProgramRepo;
import com.stackroute.gms.service.ProgramReportsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramReportsServiceImplIT {

    private ProgramRepo programRepo;
    private ProgramReportsServiceImpl programReportsService;
    private List<Program> programs;

    @BeforeEach
    public void setUp() {
        System.setProperty("sqlite4java.library.path", "target/dependencies");
        programReportsService = new ProgramReportsServiceImpl();
        Map<String, String> customerBookingsMap;
        Map<String, BigDecimal> yogaPackageCostMap;
        Map<String, BigDecimal> aerobicsPackageCostMap;
        String value = "Yoga booked with time slot 6:00 am to 7:00 am";
        String value2 = "Aerobics booked with time slot 4:00 pm to 5:00 pm";
        customerBookingsMap = new HashMap<>();
        customerBookingsMap.put("1", value);
        programs = new ArrayList<>();
        yogaPackageCostMap = new HashMap<>();
        aerobicsPackageCostMap = new HashMap<>();
        String[] yogaSlots = new String[3];
        String[] aerobicSlots = new String[3];
        yogaSlots[0] = "6:00 am to 7:00 am";
        yogaSlots[1] = "7:30 am to 8:30 am";
        yogaSlots[2] = "9:00 am to 10:00 am";
        aerobicSlots[0] = "4:00 pm to 5:00 pm";
        aerobicSlots[1] = "5:00 pm to 6:00 pm";
        aerobicSlots[2] = "6:00 pm to 7:00 pm";
        yogaPackageCostMap.put("3 months Yoga", BigDecimal.valueOf(10000.000));
        aerobicsPackageCostMap.put("3 months Aerobics", BigDecimal.valueOf(20000.000));
        Program yogaProgram = new Program(1, "Yoga", "1 hr");
        Program aerobicsProgram = new Program(2, "Aerobics", "1 hr");
        yogaProgram.setAvailableTimeSlots(Arrays.asList(yogaSlots));
        yogaProgram.setPackageCostMap(yogaPackageCostMap);
        yogaProgram.setTimeSlotBookingsMap(new TimeSlotsBooking());
        yogaProgram.setCustomerBookingsMap(customerBookingsMap);
        Map<String, String> aerobiscBookingsMap = new HashMap<>();
        aerobiscBookingsMap.put("2", value2);
        aerobicsProgram.setAvailableTimeSlots(Arrays.asList(aerobicSlots));
        aerobicsProgram.setPackageCostMap(aerobicsPackageCostMap);
        aerobicsProgram.setTimeSlotBookingsMap(new TimeSlotsBooking());
        aerobicsProgram.setCustomerBookingsMap(aerobiscBookingsMap);
        programs.add(yogaProgram);
        programs.add(aerobicsProgram);
    }

    @AfterEach
    public void tearDown() {
        programs = null;
    }

    @Test
    public void givenObjectWhenCountBookingsByProgramThenReturnEmptyMap() {
        Map<String, Long> bookingsMap = programReportsService.countBookingsByProgram();
        Map<String, Integer> expectedMap = new HashMap<>();
        assertEquals(expectedMap.toString(), bookingsMap.toString());
    }

}

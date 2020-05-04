package com.stackroute.gms.commander.test;

import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.repo.ProgramRepo;
import com.stackroute.gms.service.ProgramReportsServiceImpl;
import com.stackroute.gms.service.StaffMemberReportsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramReportsServiceImplTests {


    @Mock
    private ProgramRepo programRepo;
    @InjectMocks
    private ProgramReportsServiceImpl programReportsService;
    private List<Program> programs;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Map<String, String> customerBookingsMap;
        Map<String, BigDecimal> yogaPackageCostMap;
        Map<String, BigDecimal> aerobicsPackageCostMap;
        String yogaBooking = "Yoga booked with time slot 6:00 am to 7:00 am";
        String aerobicsBooking = "Aerobics booked with time slot 4:00 pm to 5:00 pm";
        customerBookingsMap = new HashMap<>();
        customerBookingsMap.put("1", yogaBooking);
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
        aerobiscBookingsMap.put("2", aerobicsBooking);
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
    public void givenObjectWhenCountBookingsByProgramThenReturnMap() {
        Mockito.when(programRepo.retrievePrograms()).thenReturn(programs);
        Map<String, Long> bookingsMap = programReportsService.countBookingsByProgram();
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("Yoga", 1);
        expectedMap.put("Aerobics", 1);
        assertEquals(expectedMap.toString(), bookingsMap.toString());
    }


    @Test
    public void givenObjectWhenCountBookingsByProgramThenReturnEmptyMap() {
        Map<String, Long> bookingsMap = programReportsService.countBookingsByProgram();
        Map<String, Integer> expectedMap = new HashMap<>();
        assertEquals(expectedMap.toString(), bookingsMap.toString());
    }

    public static class StaffReportsServiceImplIT {
        private StaffMemberReportsServiceImpl staffReportsService;
        private StaffMember firstStaff;
        private StaffMember secondStaff;
        private List<StaffMember> staffMembers;

        @BeforeEach
        public void setUp() {
            staffReportsService = new StaffMemberReportsServiceImpl();

            staffMembers = new ArrayList<>();
            firstStaff = new StaffMember(2, "Andrew", "Trainer", "New York", "Yoga",
                    LocalDate.of(1975, 4, 8), BigDecimal.valueOf(30000));
            firstStaff.setGender("Male");
            firstStaff.setDateOfJoining(LocalDate.of(1999, 4, 1));
            firstStaff.setPhoneNumber("+919932485611");
            firstStaff.setEmail("andrew@goldysgym.com");

            List<String> certs = new ArrayList<>();

            certs.add("Privileged");
            certs.add("Padmabhushan");

            firstStaff.setAwardedCertificates(certs);
            secondStaff = new StaffMember(1, "Hu Chong", "Trainer",
                    "Los Angeles", "Aerobics",
                    LocalDate.of(1977, 8, 3), BigDecimal.valueOf(500000.00));
            secondStaff.setGender("Male");
            secondStaff.setDateOfJoining(LocalDate.of(2002, 4, 8));
            secondStaff.setPhoneNumber("+919234567810");
            secondStaff.setEmail("chong@goldysgym.com");
            secondStaff.setAwardedCertificates(certs);
            staffMembers.add(firstStaff);
            staffMembers.add(secondStaff);
        }

        @AfterEach
        public void tearDown() {
            firstStaff = null;
            secondStaff = null;
            staffMembers = null;
        }


        @Test
        public void givenObjectWhenGetStaffJoinedCurrentYearThenReturnList() {
            List<String> staffList = staffReportsService.getStaffJoinedInCurrentYear();
            assertEquals(0, staffList.size());
        }


        @Test
        public void givenObjectWhenFindTrainerByAgeThenReturnList() {
            List<String> staffList = staffReportsService.findTrainerByAge(25);
            assertEquals(2, staffList.size());
        }

        @Test
        public void givenObjectWhenCountStaffByProgramThenReturnMap() {
            Map<String, Integer> staffMap = staffReportsService.countStaffByProgram();
            Map<String, Integer> expectedMap = new HashMap<>();
            expectedMap.put("Yoga", 1);
            expectedMap.put("Aerobics", 1);
            assertEquals(expectedMap, staffMap);

        }


    }
}

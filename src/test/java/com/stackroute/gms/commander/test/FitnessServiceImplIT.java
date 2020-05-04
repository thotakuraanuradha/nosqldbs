package com.stackroute.gms.commander.test;

import com.stackroute.gms.common.FitnessDto;
import com.stackroute.gms.model.Customer;
import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.repo.CustomerRepo;
import com.stackroute.gms.repo.ProgramRepo;
import com.stackroute.gms.repo.StaffMemberRepo;
import com.stackroute.gms.service.FitnessServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FitnessServiceImplIT {

    private CustomerRepo customerRepo;
    private ProgramRepo programRepo;
    private StaffMemberRepo staffRepo;
    private FitnessServiceImpl fitnessService;
    private Customer firstCustomer;
    private StaffMember firstStaff;
    private Program yogaProgram;


    @BeforeEach
    public void setUp() {
        System.setProperty("sqlite4java.library.path", "target/dependencies");

        fitnessService = new FitnessServiceImpl();
        Map<String, BigDecimal> yogaPackageCostMap = new HashMap<>();
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
        firstCustomer = new Customer(1, "Carol", LocalDate.of(1985, 4, 8), "Male", "Software Engineer", "New York");
        firstCustomer.setDob(LocalDate.of(1995, 9, 14));
        firstCustomer.setCustomerType("Regular");
        firstCustomer.setTotalBillAmount(BigDecimal.valueOf(0.00));
        firstCustomer.setPhoneNumber("+1355553535");
        firstCustomer.setEmail("carol@goldiesgym.com");
        String[] yogaSlots = new String[3];
        String timeslot = "6:00 am to 7:00 am";
        yogaSlots[0] = timeslot;
        yogaPackageCostMap.put("3 months Yoga", BigDecimal.valueOf(10000.000));
        yogaProgram = new Program(1, "Yoga", "1 hr");
        yogaProgram.setAvailableTimeSlots(Arrays.asList(yogaSlots));
        yogaProgram.setPackageCostMap(yogaPackageCostMap);
        yogaProgram.setTimeSlotBookingsMap(new TimeSlotsBooking());
    }

    @AfterEach
    public void tearDown() {
        firstCustomer = null;
    }

    @Test
    public void givenObjectWhenEnrollCustomerThenReturnMap() {

        String timeSlot = "6:00 am to 7:00 am";
        FitnessDto fitnessDto = new FitnessDto(1, 1, 1, LocalDate.now(), timeSlot);
        Map<String, Object> resultMap = fitnessService.enrollCustomer(fitnessDto);
        Map<String, Map<String, Integer>> dateTimeSlotMap = new HashMap<>();
        String dateTimeSlot = LocalDate.now().toString() + ":" + timeSlot;
        Map<String, Integer> timeSlotMap = new HashMap<>();
        timeSlotMap.put(timeSlot, 1);
        dateTimeSlotMap.put(dateTimeSlot, timeSlotMap);
        Map<Integer, String> enrolmentMap = new HashMap<>();
        Map<Integer, String> staffEnrolmentMap = new HashMap<>();
        staffEnrolmentMap.put(1, "Yoga 6:00 am to 7:00 am");
        enrolmentMap.put(1, "program : 3 months Yoga booked with Time slot : 6:00 am to 7:00 am");
        assertEquals(enrolmentMap.toString(), ((Customer) resultMap.get("Customer")).getEnrollmentInfo().toString());
        assertEquals(dateTimeSlotMap.toString(), ((Program) resultMap.get("Program")).getTimeSlotBookingsMap().getBookingsByDay().toString());
        assertEquals(staffEnrolmentMap.toString(), ((StaffMember) resultMap.get("Staff")).getEnrollmentInfo().toString());
    }

    @Test
    public void givenObjectWhenEnrollCustomerWithWrongTimeSlotThenThrowException() {
        String timeSlot = "6:00 am to 7:00 am";
        FitnessDto fitnessDto = new FitnessDto(1, 1, 1, LocalDate.now(), "timeSlot");
        Map<String, Object> resultMap = fitnessService.enrollCustomer(fitnessDto);
        Map<String, Map<String, Integer>> dateTimeSlotMap = new HashMap<>();
        String dateTimeSlot = LocalDate.now().toString() + ":" + timeSlot;
        Map<String, Integer> timeSlotMap = new HashMap<>();
        timeSlotMap.put(timeSlot, 1);
        dateTimeSlotMap.put(dateTimeSlot, timeSlotMap);
        Map<Integer, String> enrolmentMap = new HashMap<>();
        Map<Integer, String> staffEnrolmentMap = new HashMap<>();
        staffEnrolmentMap.put(1, "Yoga 6:00 am to 7:00 am");
        enrolmentMap.put(1, "program : 3 months Yoga booked with Time slot : 6:00 am to 7:00 am");
        assertThrows(NullPointerException.class, ()-> ((Customer) resultMap.get("Customer")).getEnrollmentInfo().toString());
    }

    @Test
    public void givenObjectWhenGenerateBillThenReturnBigDecimal() {

        Map<Integer, String> enrolmentMap = new HashMap<>();
        enrolmentMap.put(1, "program : 3 months Yoga booked with Time slot : 6:00 am to 7:00 am");
        firstCustomer.setEnrollmentInfo(enrolmentMap);
        BigDecimal result = fitnessService.generateBill(firstCustomer);
        assertEquals(BigDecimal.valueOf(11000.0), (result));
    }

    @Test
    public void givenObjectWhenGenerateBillWithoutEnrollmentThenReturnNull() {

        Map<Integer, String> enrolmentMap = new HashMap<>();
        enrolmentMap.put(1, "program : 3 months Yoga booked with Time slot : 6:00 am to 7:00 am");
        BigDecimal result = fitnessService.generateBill(firstCustomer);
        assertNull(result);
    }
}

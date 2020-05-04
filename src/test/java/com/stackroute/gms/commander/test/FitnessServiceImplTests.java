package com.stackroute.gms.commander.test;

import com.stackroute.gms.common.FitnessDto;
import com.stackroute.gms.model.Customer;
import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.repo.CustomerRepo;
import com.stackroute.gms.repo.ProgramRepo;
import com.stackroute.gms.repo.StaffMemberRepo;
import com.stackroute.gms.service.CustomerReportsServiceImpl;
import com.stackroute.gms.service.FitnessServiceImpl;
import com.stackroute.gms.service.ProgramReportsServiceImpl;
import com.stackroute.gms.service.StaffMemberReportsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FitnessServiceImplTests {

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private ProgramRepo programRepo;
    @Mock
    private StaffMemberRepo staffRepo;
    @InjectMocks
    private CustomerReportsServiceImpl customerReportsService;
    @InjectMocks
    private StaffMemberReportsServiceImpl staffMemberReportsService;
    @InjectMocks
    private ProgramReportsServiceImpl programReportsService;
    @InjectMocks
    private FitnessServiceImpl fitnessService;
    private Customer firstCustomer;
    private StaffMember firstStaff;
    private Program yogaProgram;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        Mockito.when(customerRepo.getCustomerById(Matchers.any(Class.class), Matchers.<Map<String, String>>anyInt())).thenReturn(firstCustomer);
        Mockito.when(programRepo.getProgramById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(yogaProgram);
        Mockito.when(staffRepo.getStaffMemberById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(firstStaff);
        Mockito.when(staffRepo.saveStaffMember(Matchers.any(StaffMember.class))).thenReturn(firstStaff);
        Mockito.when(customerRepo.saveCustomer(Matchers.any(Customer.class))).thenReturn(firstCustomer);
        Mockito.when(programRepo.saveProgram(Matchers.any(Program.class))).thenReturn(yogaProgram);
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
        Mockito.when(customerRepo.getCustomerById(Matchers.any(Class.class), Matchers.<Map<String, String>>anyInt())).thenReturn(firstCustomer);
        Mockito.when(programRepo.getProgramById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(yogaProgram);
        Mockito.when(staffRepo.getStaffMemberById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(firstStaff);
        Mockito.when(staffRepo.saveStaffMember(Matchers.any(StaffMember.class))).thenReturn(firstStaff);
        Mockito.when(customerRepo.saveCustomer(Matchers.any(Customer.class))).thenReturn(firstCustomer);
        Mockito.when(programRepo.saveProgram(Matchers.any(Program.class))).thenReturn(yogaProgram);
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

        assertThrows(NullPointerException.class, () -> ((Customer) resultMap.get("Customer")).getEnrollmentInfo().toString());
    }

    @Test
    public void givenObjectWhenGenerateBillThenReturnBigDecimal() {

        Map<Integer, String> enrolmentMap = new HashMap<>();
        enrolmentMap.put(1, "program : 3 months Yoga booked with Time slot : 6:00 am to 7:00 am");
        firstCustomer.setEnrollmentInfo(enrolmentMap);
        Mockito.when(programRepo.getProgramById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(yogaProgram);
        Mockito.when(customerRepo.getCustomerById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(firstCustomer);
        Mockito.when(customerRepo.getCustomerById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(firstCustomer);
        Mockito.when(customerRepo.saveCustomer(Matchers.any(Customer.class))).thenReturn(firstCustomer);
        BigDecimal result = fitnessService.generateBill(firstCustomer);
        assertEquals(BigDecimal.valueOf(11000.0), (result));
    }

    @Test
    public void givenObjectWhenGenerateBillWithoutEnrollmentThenReturnNull() {

        Map<Integer, String> enrolmentMap = new HashMap<>();
        enrolmentMap.put(1, "program : 3 months Yoga booked with Time slot : 6:00 am to 7:00 am");
        Mockito.when(programRepo.getProgramById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(yogaProgram);
        Mockito.when(customerRepo.getCustomerById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(firstCustomer);
        Mockito.when(customerRepo.getCustomerById(Matchers.any(Class.class), Matchers.anyInt())).thenReturn(firstCustomer);
        Mockito.when(customerRepo.saveCustomer(Matchers.any(Customer.class))).thenReturn(firstCustomer);
        BigDecimal result = fitnessService.generateBill(firstCustomer);
        assertNull(result);
    }

    @SpringBootTest(classes = StaffMemberReportsServiceImpl.class)
    public static class StaffReportsServiceImplIntgTest {
        private StaffMemberReportsServiceImpl staffReportsService;
        private StaffMember firstStaff;
        private StaffMember secondStaff;
        private List<StaffMember> staffMembers;

        @BeforeEach
        public void setUp() {
            System.setProperty("sqlite4java.library.path", "target/dependencies");

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
            staffReportsService = new StaffMemberReportsServiceImpl();
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

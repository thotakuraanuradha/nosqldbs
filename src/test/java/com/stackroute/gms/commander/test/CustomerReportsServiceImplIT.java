package com.stackroute.gms.commander.test;

import com.stackroute.gms.model.Customer;
import com.stackroute.gms.model.Program;
import com.stackroute.gms.repo.CustomerRepo;
import com.stackroute.gms.repo.ProgramRepo;
import com.stackroute.gms.service.CustomerReportsServiceImpl;
import com.stackroute.gms.service.FitnessService;
import com.stackroute.gms.service.FitnessServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerReportsServiceImplIT {

    private Customer firstCustomer;
    private Customer secondCustomer;
    private CustomerRepo customerRepo;

    private ProgramRepo programRepo;

    private CustomerReportsServiceImpl customerReportsService;
    private List<Customer> customers;
    private List<Program> programs = new ArrayList<>();
    private Map<LocalDate, List<String>> programTimeSlotsMap;
    private Map<String, BigDecimal> yogaPackageCostMap;
    private Map<String, BigDecimal> aerobicsPackageCostMap;
    private FitnessService fitnessService;

    @BeforeEach
    public void setUp() {
        System.setProperty("sqlite4java.library.path", "target/dependencies");

        fitnessService = new FitnessServiceImpl();

        customerRepo = new CustomerRepo();

        customerReportsService = new CustomerReportsServiceImpl();

        customers = new LinkedList<>();
        programs = new LinkedList<>();
        String timeSlot1 = "6:00 am to 7:am";
        String timeSlot2 = "4:00 pm to 5:00 pm";

        programTimeSlotsMap = new HashMap<>();
        yogaPackageCostMap = new HashMap<>();
        aerobicsPackageCostMap = new HashMap<>();

        firstCustomer = new Customer(1, "Carol", LocalDate.of(1985, 4, 8), "Male", "Software Engineer", "New York");
        firstCustomer.setDob(LocalDate.of(1995, 9, 14));
        firstCustomer.setCustomerType("Regular");
        firstCustomer.setTotalBillAmount(BigDecimal.valueOf(0));
        firstCustomer.setPhoneNumber("+1355553535");
        firstCustomer.setEmail("carol@goldiesgym.com");

        secondCustomer = new Customer(2, "Clinton", LocalDate.of(1972, 3, 8), "Male",
                "Agriculturist", "Los Angeles");
        secondCustomer.setDob(LocalDate.of(1992, 2, 14));
        secondCustomer.setCustomerType("Regular");
        secondCustomer.setTotalBillAmount(BigDecimal.valueOf(0));
        secondCustomer.setPhoneNumber("+135555432");
        secondCustomer.setEmail("clinton@goldiesgym.com");

        Map<Integer, String> yogaEnrollmentInfo = new HashMap<>();
        Map<Integer, String> aerobicsEnrollmentInfo = new HashMap<>();
        String yogaBooking = "Yoga booked with time slot 6:00 am to 7:00 am";
        String aerobicsBooking = "Aerobics booked with time slot 4:00 pm to 5:00 pm";
        Map<String, String> customerBookingsMap = new HashMap<>();

        customerBookingsMap.put("1", yogaBooking);

        Map<String, String> aerobicsBookingsMap = new HashMap<>();
        aerobicsBookingsMap.put("2", aerobicsBooking);
        String enrollYoga = "program : 3 months Yoga booked with Time slot : " + timeSlot1;
        String enrollAerobics = "Aerobics booked with Time slot : " + timeSlot2;
        aerobicsEnrollmentInfo.put(2, enrollAerobics);

        yogaEnrollmentInfo.put(1, enrollYoga);
        firstCustomer.setEnrollmentInfo(yogaEnrollmentInfo);
        secondCustomer.setEnrollmentInfo(aerobicsEnrollmentInfo);

        customers.add(firstCustomer);
        customers.add(secondCustomer);

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
        yogaProgram.setCustomerBookingsMap(customerBookingsMap);

        aerobicsProgram.setAvailableTimeSlots(Arrays.asList(aerobicSlots));
        aerobicsProgram.setPackageCostMap(aerobicsPackageCostMap);
        aerobicsProgram.setCustomerBookingsMap(aerobicsBookingsMap);

        programs.add(yogaProgram);
        programs.add(aerobicsProgram);
    }

    @AfterEach
    public void tearDown() {
        firstCustomer = null;
        secondCustomer = null;
    }

    @Test
    public void givenObjectWhenSortCustomersByAgeThenReturnList() {

        Map<Integer, Customer> customersMap = new LinkedHashMap<>();
        // List<Customer> customersList = customerRepo.retrieveCustomers();
        firstCustomer.setEnrollmentInfo(null);
        firstCustomer.setEnrollmentInfo(null);

        secondCustomer.setEnrollmentInfo(null);
        customersMap.put(1, firstCustomer);
        customersMap.put(2, secondCustomer);


        assertEquals(customersMap.toString(), customerReportsService.sortCustomersByAge().toString());

    }

    @Test
    public void givenObjectWhenGetCustomersByAgeThenReturnList() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add(secondCustomer.getName());
        List<String> customersList = customerReportsService.getCustomersByAge(25);
        assertEquals(expectedList.toString(), customersList.toString());
    }

    @Test
    public void givenObjectWhenGetCustomersByPreferredProgramThenThrowException() {

        assertThrows(NullPointerException.class, () -> customerReportsService
                .getCustomersByPreferredProgram());
    }


}

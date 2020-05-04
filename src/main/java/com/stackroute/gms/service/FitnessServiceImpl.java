package com.stackroute.gms.service;


import com.stackroute.gms.common.FitnessDto;
import com.stackroute.gms.exception.FitnessException;
import com.stackroute.gms.model.Customer;
import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.repo.CustomerRepo;
import com.stackroute.gms.repo.ProgramRepo;
import com.stackroute.gms.repo.StaffMemberRepo;
import com.stackroute.gms.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This Class represent the transactions performed in the application.*
 */

public class FitnessServiceImpl implements FitnessService {

    private static final Logger logger = LogManager.getLogger(FitnessServiceImpl.class);

    private CustomerRepo customerRepo;
    private StaffMemberRepo staffMemberRepo;
    private ProgramRepo programRepo;

    public FitnessServiceImpl() {

        customerRepo = new CustomerRepo();
        staffMemberRepo = new StaffMemberRepo();
        programRepo = new ProgramRepo();
    }


    /*
     * This method enrolls a customer to a selected program based on the availability of the slots
     * using streams and Lambda expressions,forEach  method to iterate where necessary
     * @param fitnessDto
     * @return Map<String,Object>
     */
    @Override
    public Map<String, Object> enrollCustomer(FitnessDto fitnessDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            int trainerId = fitnessDto.getTrainerId();
            int customerId = fitnessDto.getCustomerId();
            int programId = fitnessDto.getProgramId();
            LocalDate enrollFrom = fitnessDto.getEnrollFrom();
            String timeSlot = fitnessDto.getTimeSlot();

            String customerKey = "Customer";
            String programKey = "Program";
            String staffKey = "Staff";
            /*Getting all objects*/

            Program programObject = programRepo.getProgramById(Program.class, programId);
            Customer customerObject = customerRepo.getCustomerById(Customer.class, customerId);
            StaffMember staffObject = staffMemberRepo.getStaffMemberById(StaffMember.class, trainerId);

            /*End getting all objects*/

            List<String> availableSlots = programObject.getAvailableTimeSlots();


            if (availableSlots.contains(timeSlot)) {
                Map<String, Integer> timeSlotMap = new HashMap<>();
                //check for the bookings in that time slot
                if (programObject.getTimeSlotBookingsMap().getBookingsByDay() == null || (programObject
                        .getTimeSlotBookingsMap().getBookingsByDay().isEmpty())) {//TBD
                    /*None of the time slots booked*/
                    timeSlotMap.put(timeSlot, 1);
                    Map<String, Map<String, Integer>> dateTimeSlotsMap = new HashMap<>();
                    dateTimeSlotsMap.put(enrollFrom.toString() + ":" + timeSlot, timeSlotMap);
                    TimeSlotsBooking bookings = new TimeSlotsBooking();
                    bookings.setBookingsByDay(dateTimeSlotsMap);
                    programObject.setTimeSlotBookingsMap(bookings);

                    Map<String, String> updatedCustomersBooking = programObject.
                            getCustomerBookingsMap() == null ? new HashMap<>() :
                            programObject.getCustomerBookingsMap();

                    updatedCustomersBooking.put(((Integer) customerObject.getCustomerId()).toString(),
                            ((Map) programObject.getPackageCostMap()).keySet().iterator().next() +
                                    " booked with time slot :" + timeSlot);
                    programObject.setCustomerBookingsMap(updatedCustomersBooking);
                    programRepo.saveProgram(programObject);
                    /*customer is not enrolled enroll and update*/

                    Customer customerObjectRet = updateCustomerInfo(programObject, customerObject, trainerId,
                            timeSlot);

                    customerRepo.saveCustomer(customerObjectRet);


                    //trainer assignment
                    StaffMember staffObjectRet = updateStaffInfo(programObject, staffObject, customerId,
                            timeSlot);
                    staffMemberRepo.saveStaffMember(staffObjectRet);

                    resultMap.put(customerKey, customerObjectRet);
                    resultMap.put(staffKey, staffObjectRet);
                    resultMap.put(programKey, programObject);


                } else {

                    /*some of the time slots are booked*/
                    TimeSlotsBooking bookingsByDayObject = programObject.getTimeSlotBookingsMap();
                    Map<String, Map<String, Integer>> slotBookingsMap =
                            bookingsByDayObject.getBookingsByDay();
                    Map<String, Integer> countTimeSlots = slotBookingsMap.get(enrollFrom.toString() +
                            ":" + timeSlot) == null ? new HashMap<>() :
                            slotBookingsMap.get(enrollFrom.toString() + ":" + timeSlot);

                    int bookings = (countTimeSlots.get(timeSlot) == null) ? 0 :
                            countTimeSlots.get(timeSlot);

                    if (bookings < 10) {
                        //update program object
                        countTimeSlots.put(timeSlot, bookings + 1);
                        slotBookingsMap.put(enrollFrom.toString() + ":" + timeSlot, countTimeSlots);
                        bookingsByDayObject.setBookingsByDay(slotBookingsMap);
                        programObject.setTimeSlotBookingsMap(bookingsByDayObject);

                        Map<String, String> updatedCustomersBooking = programObject.getCustomerBookingsMap()
                                == null ? new HashMap<>() : programObject.getCustomerBookingsMap();
                        updatedCustomersBooking.put(((Integer) customerObject.getCustomerId()).toString(),
                                (programObject.getPackageCostMap()).keySet().iterator().next() +
                                        " booked with time slot :" + timeSlot);
                        programObject.setCustomerBookingsMap(updatedCustomersBooking);
                        programRepo.saveProgram(programObject);

                        if (customerObject.getEnrollmentInfo() == null ||
                                (customerObject.getEnrollmentInfo()).isEmpty()) {
                            //Slots are available - enroll and update
                            Customer customerObjectRet = updateCustomerInfo(programObject, customerObject, trainerId,
                                    timeSlot);
                            customerRepo.saveCustomer(customerObjectRet);

                            //trainer assignment
                            StaffMember staffObjectRet = updateStaffInfo(programObject, staffObject, customerId,
                                    timeSlot);
                            staffMemberRepo.saveStaffMember(staffObjectRet);

                            resultMap.put(customerKey, customerObjectRet);
                            resultMap.put(staffKey, staffObjectRet);
                            resultMap.put(programKey, programObject);

                        } else {
                            customerObject.setEnrollmentStatus("Customer has already been enrolled for :" +
                                    programObject.getProgramName() + ".Please cancel the same and rebook");
                            customerRepo.saveCustomer(customerObject);
                            resultMap.put(customerKey, customerObject);
                            resultMap.put(staffKey, staffObject);
                            resultMap.put(programKey, programObject);
                        }

                    } else {
                        customerObject.setEnrollmentStatus("Selected slot is not  available for  :" +
                                programObject.getProgramName() + ".Please select other slot");
                        customerRepo.saveCustomer(customerObject);

                        resultMap.put(customerKey, customerObject);
                        resultMap.put(staffKey, staffObject);
                        resultMap.put(programKey, programObject);

                    }
                }
            } else {

                customerObject.setEnrollmentStatus("No slots available,Please choose another slot");
                customerRepo.saveCustomer(customerObject);
                resultMap.put(customerKey, customerObject);
                resultMap.put(staffKey, staffObject);
                resultMap.put(programKey, programObject);
            }


        } catch (Exception ex) {
            String message = "Exception in enrolling a customer";
            logger.info(ex.getMessage());
            try (Formatter formatter = new Formatter()) {
                formatter.format(message, "%s");
                logger.info(formatter.toString());
                throw new FitnessException(formatter.toString());
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        return resultMap;
    }

    private StaffMember updateStaffInfo(Program programObject, StaffMember staffObject, Integer customerId, String timeSlot) {


        Map<Integer, String> staffEnrollment = staffObject.getEnrollmentInfo();
        staffEnrollment = staffEnrollment == null ? new HashMap<>() : staffEnrollment;
        staffEnrollment.put(customerId, programObject.getProgramName() + " " + timeSlot);
        staffObject.setEnrollmentInfo(staffEnrollment);
        return staffObject;

    }

    private Customer updateCustomerInfo(Program programObject, Customer customerObject, Integer trainerId,
                                        String timeSlot) {
        Map<Integer, String> enrollMap = new HashMap<>();
        enrollMap.put(trainerId, "program : " + (programObject.getPackageCostMap()).keySet().iterator().next() + " booked with Time slot : " + timeSlot);
        customerObject.setEnrollmentInfo(enrollMap);
        customerObject.setEnrollmentDate(LocalDate.now());
        customerObject.setEnrollmentStatus("Confirmed");
        return customerObject;
    }

    /*
     * This method is used to generate bill for a particular
     *  customer based on his/her enrollments
     * @param Customer
     * @return BigDecimal
     * */
    public BigDecimal generateBill(Customer customer) {
        final Map<String, BigDecimal> totalCostMap = new HashMap<>();

        try {
            StringBuilder programKey = new StringBuilder();
            if (customer.getEnrollmentInfo() != null) {
                customer.getEnrollmentInfo().entrySet().forEach(element -> {
                            if (element.getValue().contains("Yoga")) {
                                programKey.append(1);
                            } else {
                                programKey.append(2);

                            }
                            String key = "";
                            int discountPercent = 0;
                            int gstPercent = 10;
                            Program program = programRepo.getProgramById(Program.class, (Integer.parseInt(programKey.toString())));

                            if (program.getProgramName().equalsIgnoreCase("Yoga"))
                                key = "3 months Yoga";
                            else
                                key = "3 months Aerobics";

                            Object initialCost = ((program.getPackageCostMap()).get(key));
                            BigDecimal initialCosts = BigDecimal.valueOf(Double.valueOf(initialCost.toString()));
                            if (DateUtil.calculateAge(customer.getDob()) > 40)
                                discountPercent = 10;

                            BigDecimal actualDiscount = (initialCosts.multiply(new BigDecimal(discountPercent))).divide(new BigDecimal(100));
                            BigDecimal actualGST = (initialCosts.multiply(new BigDecimal(gstPercent))).divide(new BigDecimal(100));
                            BigDecimal totalCost = (initialCosts.add(actualGST)).subtract(actualDiscount);
                            customer.setTotalBillAmount(totalCost);

                            totalCostMap.put("totalCost", totalCost);
                            String cost = String.format("Total cost is %s", String.valueOf(totalCost));
                            logger.info(cost);
                            customerRepo.saveCustomer(customer);

                        }


                );

            }
        } catch (Exception ex) {
            String message = "Exception in generating bill for customer" + customer.getName();
            try (Formatter formatter = new Formatter()) {
                formatter.format(message, "%s");
                logger.error(formatter.toString());
                throw new FitnessException(formatter.toString());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return totalCostMap.get("totalCost");
    }


}



package com.stackroute.gms.service;

import com.stackroute.gms.model.Customer;
import com.stackroute.gms.repo.CustomerRepo;
import com.stackroute.gms.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static java.util.stream.Collectors.toMap;

/*
 * This Class represents the customer reports.*
 */
public class CustomerReportsServiceImpl implements CustomerReportsService {

    private CustomerRepo customerRepo;
    private static final Logger logger = LogManager.getLogger(CustomerReportsServiceImpl.class);


    public CustomerReportsServiceImpl() {
        customerRepo = new CustomerRepo();
    }

    /*
     * This method sorts customer by  age using DBCursorObject method to collect the result into a List
     * @param
     * @return Map<Integer Customer>
     */
    @Override
    public Map<Integer, Customer> sortCustomersByAge() {
        List<Customer> customers = customerRepo.retrieveCustomers();
        logger.info("Processing sort customer");
        Map<Integer, Customer> customerMap = new HashMap<>();

        for (Customer customer : customers) {

            customerMap.put(customer.getCustomerId(), customer);
        }

        return customerMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Customer>comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (firstOperand, secondOperand) -> firstOperand, LinkedHashMap::new));

    }

    /*
     * This method finds customer based on  age greater than the input
     *  using streams and Lambda expressions,
     * filter method to filter the map of customers
     *  and collect method to collect the result into a List
     * @param int
     * @return List<String>
     */
    @Override
    public List<String> getCustomersByAge(int age) {

        List<String> customerResultList = new ArrayList<>();

        customerRepo.retrieveCustomers().stream().filter(e -> DateUtil.calculateAge(e.getDob()) > age).
                forEach(ex -> customerResultList.add(ex.getName()));

        logger.info("Processing getCustomersByAge");

        return customerResultList;

    }

    /*
     * This method gets customer by  preferred program using streams and Lambda expressions,
     * forEach method is used to iterate  the map of customers
     *  and collect method to collect the result into a map
     * @param int
     * @return Map<String, String>
     */

    public Map<String, String> getCustomersByPreferredProgram() {

        logger.info("Processing getCustomersByPreferredProgram");
        Map<String, String> customerPreferences = new HashMap<>();

        customerRepo.retrieveCustomers().stream().forEach(customerObj -> {

            System.out.println(customerObj.getEnrollmentInfo());
            customerObj.getEnrollmentInfo().entrySet().stream().forEach(bookings -> {


                if (bookings.getValue().contains("Yoga")) {
                    customerPreferences.put(customerObj.getName(), "Yoga");

                } else {
                    customerPreferences.put(customerObj.getName(), "Aerobics");
                }


            });


        });

        return customerPreferences;

    }

}
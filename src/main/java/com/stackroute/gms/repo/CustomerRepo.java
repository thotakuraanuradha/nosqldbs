package com.stackroute.gms.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.stackroute.gms.model.Customer;
import com.stackroute.gms.util.AWSDynamoDBUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 *This class represents customer data manipulation from the application in the DynamoDB.
 *Basically contain all the CRUD operations on the customer entity
 */

public class CustomerRepo {


    private DynamoDBMapper mapper;

    /*No-args -Constructor*/
    public CustomerRepo() {
        mapper = AWSDynamoDBUtil.startServer("Customer", "customerId");
        createCustomer();

    }


    /*
     * this method is used to create the customer data
     * @param
     * @return
     */
    public void createCustomer() {
        List<Customer> customers = new ArrayList<>();
        Customer firstCustomer = new Customer(1, "Carol", LocalDate.of(1985, 4,
                8), "Male", "Software Engineer", "New York");
        firstCustomer.setDob(LocalDate.of(1995, 9, 14));
        firstCustomer.setCustomerType("Regular");
        firstCustomer.setTotalBillAmount(BigDecimal.valueOf(0.00));
        firstCustomer.setPhoneNumber("+1355553535");
        firstCustomer.setEmail("carol@goldiesgym.com");

        Customer secondCustomer = new Customer(2, "Clinton", LocalDate.of(1972, 3,
                8), "Male", "Agriculturist", "Los Angeles");
        secondCustomer.setDob(LocalDate.of(1992, 2, 14));
        secondCustomer.setCustomerType("Regular");
        secondCustomer.setTotalBillAmount(BigDecimal.valueOf(0.00));
        secondCustomer.setPhoneNumber("+135555432");
        secondCustomer.setEmail("clinton@goldiesgym.com");
        customers.add(firstCustomer);
        customers.add(secondCustomer);
        mapper.batchSave(customers);
    }

    /*
     * this method is used to retrieve the customer data
     * @param
     * @return List<Customer>
     */
    public List<Customer> retrieveCustomers() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(Customer.class, scanExpression);
    }

    /*
     * this method is used to save the customer data
     * @param Customer
     * @return Customer
     */
    public Customer saveCustomer(Customer customer) {
        mapper.save(customer);
        return this.mapper.load(Customer.class, customer.getCustomerId());
    }

    /*
     * this method is used to delete the customer data
     * @param Customer
     * @return Customer
     */
    public void deleteCustomer(Customer customer) {
        this.mapper.delete(customer);
    }

    /*
     * this method is used to get the customer data by Id
     * @param Customer,@int
     * @return Customer
     */
    public Customer getCustomerById(Class<Customer> customer, int customerId) {
        return mapper.load(customer, customerId);
    }

}

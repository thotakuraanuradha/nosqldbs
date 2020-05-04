package com.stackroute.gms.commander.test;

import com.stackroute.gms.model.Customer;
import com.stackroute.gms.repo.CustomerRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepoTests {

    private CustomerRepo repo;
    private Customer customer;

    @BeforeEach
    public void setUp() {

        repo = new CustomerRepo();

        customer = new Customer(1, "Carol", LocalDate.of(1985, 4,
                8), "Male", "Software Engineer", "New York");
        customer.setDob(LocalDate.of(1995, 9, 14));
        customer.setCustomerType("Regular");
        customer.setTotalBillAmount(BigDecimal.valueOf(0.00));
        customer.setPhoneNumber("+1355553535");
        customer.setEmail("carol@goldiesgym.com");
    }

    @AfterEach
    public void tearDown() {
        repo = null;
        customer = null;
    }

    @Test
    public void givenObjectWhenSaveCustomerThenReturnObject() {
        Customer savedCustomer = repo.saveCustomer(customer);
        assertEquals(customer.getName(), savedCustomer.getName());
        assertNull(savedCustomer.getEnrollmentInfo());
        assertEquals(customer.getDob(), savedCustomer.getDob());
        assertEquals(customer.getCustomerType(), savedCustomer.getCustomerType());
    }


    @Test
    public void givenObjectWhenRetrieveCustomersThenReturnList() {

        assertEquals(2, repo.retrieveCustomers().size());

    }


    @Test
    public void givenObjectWhenRetrieveCustomersThenReturnExactList() {

        assertNotEquals(20, repo.retrieveCustomers().size());

    }

    @Test
    public void givenObjectWhenDeleteCustomerThenReturnNone() {
        repo.deleteCustomer(customer);
        assertNull(repo.getCustomerById(Customer.class, 1));

    }

    @Test
    public void givenObjectWhenGetCustomerThenReturnObject() {
        assertNotNull(repo.getCustomerById(Customer.class, 1));
    }

    @Test
    public void givenObjectWhenGetCustomerThenReturnNull() {

        assertNull(repo.getCustomerById(Customer.class, 199));
    }
}

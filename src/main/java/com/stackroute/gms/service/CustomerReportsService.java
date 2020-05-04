package com.stackroute.gms.service;

import com.stackroute.gms.model.Customer;

import java.util.List;
import java.util.Map;

/*
 * Customer reports interface with abstract methods related to the reports of the customer
 */

public interface CustomerReportsService {

    Map<Integer, Customer> sortCustomersByAge();

    List<String> getCustomersByAge(int age);

    Map<String, String> getCustomersByPreferredProgram();
}

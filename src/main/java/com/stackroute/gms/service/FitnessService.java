package com.stackroute.gms.service;


import com.stackroute.gms.common.FitnessDto;
import com.stackroute.gms.model.Customer;

import java.math.BigDecimal;
import java.util.Map;

/*
 * FitnessService interface with abstract methods related to the transactions in the application
 */

public interface FitnessService {


    /*Transactions*/

    Map<String, Object> enrollCustomer(FitnessDto fitnessDto);

    BigDecimal generateBill(Customer customer);
}

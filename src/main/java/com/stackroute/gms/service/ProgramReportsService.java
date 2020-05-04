package com.stackroute.gms.service;

import java.util.Map;


/*
 * Program reports interface with abstract methods related to the reports of the program
 */

public interface ProgramReportsService {


    Map<String, Long> countBookingsByProgram();

}

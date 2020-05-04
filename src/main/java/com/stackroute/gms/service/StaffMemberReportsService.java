package com.stackroute.gms.service;

import java.util.List;
import java.util.Map;


/*
 * Staff reports interface with abstract methods related to the reports of the staff
 */
public interface StaffMemberReportsService {
    List<String> findTrainerByAge(int requiredAge);

    Map<String, Integer> countStaffByProgram();

    List<String> getStaffJoinedInCurrentYear();


}

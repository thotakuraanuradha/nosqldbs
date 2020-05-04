package com.stackroute.gms.service;


import com.stackroute.gms.repo.ProgramRepo;

import java.util.HashMap;
import java.util.Map;

/*
 * This Class represents the program reports.
 */
public class ProgramReportsServiceImpl implements ProgramReportsService {

    private ProgramRepo programRepo;

    public ProgramReportsServiceImpl() {

        programRepo = new ProgramRepo();

    }

    /*
     * This method counts number of booking per program using streams and Lambda expressions,forEach method is used to iterate the  the map of Programs
     *  and  collect the result into a map
     * @param
     * @return Map<String,Long>
     */

    @Override
    public Map<String, Long> countBookingsByProgram() {
        Map<String, Long> bookingsByProgram = new HashMap<>();
        programRepo.retrievePrograms().stream().forEach(programQueries -> {
            if (((Map<String, String>) programQueries.getCustomerBookingsMap()).entrySet().stream()
                    .anyMatch(match -> ((String) (match).getValue()).contains("Yoga")))
                bookingsByProgram.put("Yoga", ((Map) programQueries.getCustomerBookingsMap())
                        .entrySet().stream().count());
            else if (((Map<String, String>) programQueries.getCustomerBookingsMap()).entrySet().stream()
                    .anyMatch(match -> ((String) (match).getValue()).contains("Aerobics")))

                bookingsByProgram.put("Aerobics", ((Map) programQueries.getCustomerBookingsMap())
                        .entrySet().stream().count());

        });

        return bookingsByProgram;
    }


}

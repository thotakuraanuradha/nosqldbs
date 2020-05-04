package com.stackroute.gms.service;


import com.stackroute.gms.repo.StaffMemberRepo;
import com.stackroute.gms.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This Class represents the StaffMember reports.
 */
public class StaffMemberReportsServiceImpl implements StaffMemberReportsService {

    private StaffMemberRepo staffMemberRepo;

    /*No-args Constructor*/
    public StaffMemberReportsServiceImpl() {
        staffMemberRepo = new StaffMemberRepo();
    }

    /*
     * This method finds Staff by  age using streams and Lambda expressions,
     * @param int
     * @return List<Staff>
     */
    @Override
    public List<String> findTrainerByAge(int requiredAge) {
        List<String> staffResultList = new ArrayList<>();
        staffMemberRepo.retrieveStaffMembers().stream().filter(e -> DateUtil.calculateAge(e.getDob()) > requiredAge).
                forEach(ex -> staffResultList.add(ex.getName()));
        return staffResultList;
    }

    /*
     * This method counts Staff by  program using streams and Lambda expressions,forEach
     *  method to iterate over the  map
     *  and collect the result into a Map
     * @param
     * @return Map<String, Integer>
     */
    @Override
    public Map<String, Integer> countStaffByProgram() {
        String aerobicsKey = "Aerobics";
        String yogaKey = "Yoga";
        Map<String, Integer> staffByProgram = new HashMap<>();
        staffMemberRepo.retrieveStaffMembers().stream().forEach(staffQueries -> {
            if (((String) staffQueries.getProgram()).equalsIgnoreCase(yogaKey)) {
                Integer count = staffByProgram.get("Yoga") == null ? 0 : staffByProgram.get(yogaKey);
                staffByProgram.put(yogaKey, count + 1);
            } else {
                Integer count = staffByProgram.get(aerobicsKey) == null ? 0 : staffByProgram.get(aerobicsKey);
                staffByProgram.put(aerobicsKey, count + 1);
            }

        });

        return staffByProgram;
    }

    /*
     * This method gets the  Staff whose year of joining is current year
     * @param
     * @return List<String>
     */
    @Override
    public List<String> getStaffJoinedInCurrentYear() {
        List<String> staffJoinedCurrentYear = new ArrayList<>();
        staffMemberRepo.retrieveStaffMembers().stream().filter(e -> (e.getDateOfJoining()).getYear() == (LocalDate.now().getYear()))
                .forEach(ex -> staffJoinedCurrentYear.add(ex.getName()));
        return staffJoinedCurrentYear;
    }
}

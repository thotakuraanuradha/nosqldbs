package com.stackroute.gms.commander.test;

import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.repo.StaffMemberRepo;
import com.stackroute.gms.service.StaffMemberReportsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class StaffReportsServiceImplTests {

    @Mock
    private StaffMemberRepo staffMemberRepo;
    @InjectMocks
    private StaffMemberReportsServiceImpl staffReportsService;

    private StaffMember firstStaff;
    private StaffMember secondStaff;
    private List<StaffMember> staffMembers;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        staffMembers = new ArrayList<>();
        firstStaff = new StaffMember(2, "Andrew", "Trainer", "New York", "Yoga",
                LocalDate.of(1975, 4, 8), BigDecimal.valueOf(30000));
        firstStaff.setGender("Male");
        firstStaff.setDateOfJoining(LocalDate.of(1999, 4, 1));
        firstStaff.setPhoneNumber("+919932485611");
        firstStaff.setEmail("andrew@goldysgym.com");

        List<String> certs = new ArrayList<>();

        certs.add("Privileged");
        certs.add("Padmabhushan");

        firstStaff.setAwardedCertificates(certs);
        secondStaff = new StaffMember(1, "Hu Chong", "Trainer",
                "Los Angeles", "Aerobics",
                LocalDate.of(1977, 8, 3), BigDecimal.valueOf(500000.00));
        secondStaff.setGender("Male");
        secondStaff.setDateOfJoining(LocalDate.of(2002, 4, 8));
        secondStaff.setPhoneNumber("+919234567810");
        secondStaff.setEmail("chong@goldysgym.com");
        secondStaff.setAwardedCertificates(certs);
        staffMembers.add(firstStaff);
        staffMembers.add(secondStaff);
    }

    @AfterEach
    public void tearDown() {
        firstStaff = null;
        secondStaff = null;
        staffMembers = null;
    }


    @Test
    public void givenObjectWhenGetStaffJoinedCurrentYearThenReturnList() {
        Mockito.when(staffMemberRepo.retrieveStaffMembers()).thenReturn(staffMembers);
        List<String> staffList = staffReportsService.getStaffJoinedInCurrentYear();
        assertEquals(0, staffList.size());
        verify(staffMemberRepo, times(1)).retrieveStaffMembers();
    }


    @Test
    public void givenObjectWhenFindTrainerByAgeThenReturnList() {
        Mockito.when(staffMemberRepo.retrieveStaffMembers()).thenReturn(staffMembers);
        List<String> staffList = staffReportsService.findTrainerByAge(25);
        assertEquals(2, staffList.size());
        verify(staffMemberRepo, times(1)).retrieveStaffMembers();
    }

    @Test
    public void givenObjectWhenCountStaffByProgramThenReturnMap() {
        Mockito.when(staffMemberRepo.retrieveStaffMembers()).thenReturn(staffMembers);
        Map<String, Integer> staffMap = staffReportsService.countStaffByProgram();
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("Yoga", 1);
        expectedMap.put("Aerobics", 1);
        assertEquals(expectedMap, staffMap);
        verify(staffMemberRepo, times(1)).retrieveStaffMembers();

    }


    @Test
    public void givenObjectWhenCountStaffByProgramThenReturnEmptyMap() {
        Map<String, Integer> staffMap = staffReportsService.countStaffByProgram();
        Map<String, Integer> expectedMap = new HashMap<>();
        assertEquals(expectedMap, staffMap);
        verify(staffMemberRepo, times(1)).retrieveStaffMembers();

    }
}

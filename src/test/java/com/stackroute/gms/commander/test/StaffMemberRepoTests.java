package com.stackroute.gms.commander.test;


import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.repo.StaffMemberRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffMemberRepoTests {

    private StaffMemberRepo repo;
    private StaffMember staffMember;

    @BeforeEach
    public void setUp() {
        repo = new StaffMemberRepo();
        List<String> certs = new ArrayList<>();

        certs.add("Privileged");
        certs.add("Padmabhushan");

        staffMember = new StaffMember(1, "Hu Chong", "Trainer",
                "Los Angeles", "Aerobics", LocalDate.of(1977, 8, 3), BigDecimal.valueOf(500000.00));
        staffMember.setGender("Male");
        staffMember.setDateOfJoining(LocalDate.of(2002, 4, 8));
        staffMember.setPhoneNumber("+919234567810");
        staffMember.setEmail("chong@goldysgym.com");
        staffMember.setAwardedCertificates(certs);
    }

    @AfterEach
    public void tearDown() {

        repo = null;
        staffMember = null;
    }

    @Test
    public void givenObjectWhenSaveStaffMemberThenReturnObject() {
        StaffMember staffMemberMember = repo.saveStaffMember(staffMember);
        assertEquals(staffMember.getStaffMemberId(), staffMemberMember.getStaffMemberId());
        assertEquals(staffMember.getName(), staffMemberMember.getName());
        assertEquals(staffMember.getProgram(), staffMemberMember.getProgram());
        assertEquals(staffMember.getAwardedCertificates().size(), staffMemberMember.getAwardedCertificates().size());
        assertNull(staffMemberMember.getEnrollmentInfo());
    }

    @Test
    public void givenObjectWhenRetrieveStaffMembersThenReturnList() {
        List<StaffMember> members = repo.retrieveStaffMembers();
        assertEquals(2, members.size());
    }


    @Test
    public void givenObjectWhenRetrieveStaffMembersThenReturnExactList() {
        List<StaffMember> members = repo.retrieveStaffMembers();
        assertNotEquals(222, members.size());
    }

    @Test
    public void givenObjectWhenCallGetStaffMemberByIdThenReturnObject() {
        repo.saveStaffMember(staffMember);
        assertNotNull(repo.getStaffMemberById(StaffMember.class, 1));
    }


    @Test
    public void givenObjectWhenCallGetStaffMemberByNonIdThenReturnNull() {
        repo.saveStaffMember(staffMember);
        assertNull(repo.getStaffMemberById(StaffMember.class, 1000));
    }


    @Test
    public void givenObjectWhenDeleteStaffMemberThenReturnNone() {
        repo.deleteStaffMember(staffMember);
        assertNull(repo.getStaffMemberById(StaffMember.class, 1));
    }
}

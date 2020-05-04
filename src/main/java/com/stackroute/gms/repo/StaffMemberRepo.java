package com.stackroute.gms.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.stackroute.gms.model.StaffMember;
import com.stackroute.gms.util.AWSDynamoDBUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 *This class represents StaffMember data manipulation from the application in the DynamoDB.
 *Basically contain all the CRUD operations on the StaffMember entity
 */


public class StaffMemberRepo {

    private DynamoDBMapper mapper;



    /*No-args constructor*/

    public StaffMemberRepo() {
        this.mapper = AWSDynamoDBUtil.startServer("StaffMember", "staffMemberId");
        createStaffMemberList();
    }



    /*
     * This  method  is used to create the StaffMembers data
     * @param
     * @return List<StaffMember>
     */

    public List<StaffMember> createStaffMemberList() {
        List<StaffMember> staffMembers = new ArrayList<>();
        StaffMember firstStaff = new StaffMember(2, "Andrew", "Trainer", "New York", "Yoga",
                LocalDate.of(1975, 4, 8), BigDecimal.valueOf(30000));
        firstStaff.setGender("Male");
        firstStaff.setDateOfJoining(LocalDate.of(1999, 4, 1));
        firstStaff.setPhoneNumber("+919932485611");
        firstStaff.setEmail("andrew@goldysgym.com");
        List<String> certs = new ArrayList<>();
        certs.add("Privileged");
        certs.add("Padmabhushan");
        firstStaff.setAwardedCertificates(certs);
        StaffMember secondStaff = new StaffMember(1, "Hu Chong", "Trainer",
                "Los Angeles", "Aerobics",
                LocalDate.of(1977, 8, 3), BigDecimal.valueOf(500000.00));
        secondStaff.setGender("Male");
        secondStaff.setDateOfJoining(LocalDate.of(2002, 4, 8));
        secondStaff.setPhoneNumber("+919234567810");
        secondStaff.setEmail("chong@goldysgym.com");
        secondStaff.setAwardedCertificates(certs);
        staffMembers.add(firstStaff);
        staffMembers.add(secondStaff);
        mapper.batchSave(staffMembers);
        return staffMembers;
    }

    /*
     * this method is used to retrieve the StaffMember data
     * @param
     * @return List<StaffMember>
     */
    public List<StaffMember> retrieveStaffMembers() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(StaffMember.class, scanExpression);
    }

    /*
     * this method is used to save the StaffMember data
     * @param StaffMember
     * @return StaffMember
     */
    public StaffMember saveStaffMember(StaffMember staffMember) {
        mapper.save(staffMember);
        return mapper.load(StaffMember.class, staffMember.getStaffMemberId());
    }

    /*
     * this method is used to delete the StaffMember data
     * @param StaffMember
     * @return
     */
    public void deleteStaffMember(StaffMember staffMember) {
        mapper.delete(staffMember);
    }


    /*
     * this method is used to get the StaffMember data by Id
     * @param StaffMember,@int
     * @return StaffMember
     */

    public StaffMember getStaffMemberById(Class<StaffMember> staffMember, int staffMemberId) {
        return mapper.load(staffMember, staffMemberId);
    }


}

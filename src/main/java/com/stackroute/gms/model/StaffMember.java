package com.stackroute.gms.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.stackroute.gms.util.DateConverter;
import com.stackroute.gms.util.ListConverter;
import com.stackroute.gms.util.MapConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/*
 * This class represents StaffMember details.
 *
 * */

@DynamoDBTable(tableName = "StaffMember")
public class StaffMember {

    /*Properties*/
    private int staffMemberId;
    private String name;
    private String occupation;
    private String address;
    private String program;
    private String gender;
    private String phoneNumber;
    private String email;
    private BigDecimal salary;
    private LocalDate dateOfJoining;
    private LocalDate dob;

    /*No-args constructor*/
    public StaffMember() {
    }

    /*Parameterized  constructor*/
    public StaffMember(int staffMemberId, String name, String occupation, String address, String program, LocalDate dob, BigDecimal salary) {
        this.staffMemberId = staffMemberId;
        this.name = name;
        this.occupation = occupation;
        this.address = address;
        this.program = program;
        this.dob = dob;
        this.salary = salary;
    }


    @DynamoDBAttribute(attributeName = "enrollmentInfo")
    private Map<Integer, String> enrollmentInfo;

    @DynamoDBAttribute(attributeName = "awardedCertificates")
    private List<String> awardedCertificates;

    /*Getters and Setters*/

    public void setStaffMemberId(int staffMemberId) {
        this.staffMemberId = staffMemberId;
    }

    @DynamoDBHashKey(attributeName = "staffMemberId")
    public int getStaffMemberId() {
        return staffMemberId;
    }

    @DynamoDBTypeConverted(converter = ListConverter.class)
    public List<String> getAwardedCertificates() {
        return awardedCertificates;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setAwardedCertificates(List<String> awardedCertificates) {
        this.awardedCertificates = awardedCertificates;
    }

    @DynamoDBTypeConverted(converter = MapConverter.class)
    public Map<Integer, String> getEnrollmentInfo() {
        return enrollmentInfo;
    }

    public void setEnrollmentInfo(Map<Integer, String> enrollmentInfo) {
        this.enrollmentInfo = enrollmentInfo;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }
}

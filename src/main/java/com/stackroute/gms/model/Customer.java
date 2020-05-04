package com.stackroute.gms.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.stackroute.gms.util.DateConverter;
import com.stackroute.gms.util.DateUtil;
import com.stackroute.gms.util.MapConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/*
 * This class represents the Customer (who opts to enroll in the Goldies Gym) personal details
 * It also captures type of customer whether regular or fresher
 * Based the available slots of the program,regular or fresh customers can book appointment
 * Based on the package opted by the customer,the sessions  will be allocated.
 * After the completion of the package the bill wil be generated which will be paid by the customer
 *
 * Create Customer details
 * Enroll a customer for a program
 */
@DynamoDBTable(tableName = "Customer")
public class Customer implements Comparable<Customer> {

    private int customerId;
    private String name;
    private LocalDate dob;
    private String gender;
    private String occupation;
    private String address;
    private Map<String, Integer> fitnessProfile;
    private LocalDate enrollmentDate;
    private Map<Integer, String> enrollmentInfo;
    private String enrollmentStatus;
    private String customerType;
    private String paymentStatus;
    private BigDecimal totalBillAmount;
    private String phoneNumber;
    private String email;

    /*Default constructor*/
    public Customer() {
    }

    /*Parameterized constructor*/
    public Customer(int customerId, String name, LocalDate dob, String gender, String occupation, String address) {

        this.customerId = customerId;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.occupation = occupation;
        this.address = address;
    }

    /*Getters and Setters*/
    @DynamoDBHashKey(attributeName = "customerId")
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "dob")
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @DynamoDBAttribute(attributeName = "gender")
    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    @DynamoDBAttribute(attributeName = "occupation")
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @DynamoDBTypeConverted(converter = MapConverter.class)
    @DynamoDBAttribute(attributeName = "fitnessProfile")
    public Map getFitnessProfile() {
        return fitnessProfile;
    }

    public void setFitnessProfile(Map fitnessProfile) {
        this.fitnessProfile = fitnessProfile;
    }

    @DynamoDBTypeConverted(converter = MapConverter.class)
    @DynamoDBAttribute(attributeName = "enrollmentInfo")
    public Map<Integer, String> getEnrollmentInfo() {
        return enrollmentInfo;
    }

    public void setEnrollmentInfo(Map<Integer, String> enrollmentInfo) {
        this.enrollmentInfo = enrollmentInfo;
    }

    @DynamoDBAttribute(attributeName = "enrollmentStatus")
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @DynamoDBAttribute(attributeName = "customerType")
    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @DynamoDBAttribute(attributeName = "paymentStatus")
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @DynamoDBAttribute(attributeName = "totalBillAmount")
    public BigDecimal getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(BigDecimal totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "enrollmentDate")
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }


    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @DynamoDBAttribute(attributeName = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", fitnessProfile=" + fitnessProfile +
                ", enrollmentDate=" + enrollmentDate +
                ", enrollmentInfo=" + enrollmentInfo +
                ", enrollmentStatus='" + enrollmentStatus + '\'' +
                ", customerType='" + customerType + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalBillAmount=" + totalBillAmount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /*Overrides compareTo method of the comparable in order to sort the customers by age
     * @param Customer
     * @return int
     */
    @Override
    public int compareTo(Customer customer) {
        /*let's sort the customer based on dob in ascending order
        returns a negative integer, zero, or a positive integer as this dob
        is less than, equal to, or greater than the specified object.*/
        return (DateUtil.calculateAge(this.dob) - DateUtil.calculateAge(customer.dob));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
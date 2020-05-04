package com.stackroute.gms.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.stackroute.gms.util.MapConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/*
 * This class represents the Program () details
 * It also captures type of customer Booking information and time slots booked on a particular day
 * Based the available slots of the program,regular or fresh customers can book appointment
 * Based on the package opted by the customer,the sessions  will be allocated.
 * After the completion of the package the bill wil be generated which will be paid by the customer
 *
 * Create Program details
 * Enroll a customer for a package
 * */
@DynamoDBTable(tableName = "Program")
public class Program {

    private int programId;
    private String programName;
    private String duration;
    private List<String> availableTimeSlots;
    private Map<String, BigDecimal> packageCostMap;//"3 months Yoga,6:00 am to 7:00 am",10000.000
    private Map<String, String> customerBookingsMap;//customerId+programId,"30 days Yoga,6:00 am to 7:00 am"
    private TimeSlotsBooking timeSlotBookingsMap;//date["6-7" 10]

    /*Default constructor*/
    public Program() {
    }

    /*Parameterised constructor*/
    public Program(int programId, String programName, String duration) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
    }


    /*Getters and setters*/
    @DynamoDBTypeConverted(converter = MapConverter.class)
    @DynamoDBAttribute(attributeName = "packageCostMap")
    public Map<String, BigDecimal> getPackageCostMap() {
        return packageCostMap;
    }

    public void setPackageCostMap(Map<String, BigDecimal> packageCostMap) {
        this.packageCostMap = packageCostMap;
    }

    @DynamoDBHashKey(attributeName = "programId")
    public int getProgramId() {

        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }


    @DynamoDBAttribute(attributeName = "programName")
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @DynamoDBAttribute(attributeName = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @DynamoDBTypeConverted(converter = MapConverter.class)
    @DynamoDBAttribute(attributeName = "customerBookingsMap")
    public Map<String, String> getCustomerBookingsMap() {
        return customerBookingsMap;
    }

    public void setCustomerBookingsMap(Map<String, String> customerBookingsMap) {
        this.customerBookingsMap = customerBookingsMap;
    }

    @DynamoDBAttribute(attributeName = "timeSlotBookingsMap")
    public TimeSlotsBooking getTimeSlotBookingsMap() {
        return timeSlotBookingsMap;
    }

    public void setTimeSlotBookingsMap(TimeSlotsBooking timeSlotBookingsMap) {
        this.timeSlotBookingsMap = timeSlotBookingsMap;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Program{" +
                "programId=" + programId +
                ", programName='" + programName + '\'' +
                ", duration='" + duration + '\'' +
                ", customerTimeslotMap=" + customerBookingsMap +
                ", timeSlotBookingsMap=" + timeSlotBookingsMap +
                '}';
    }

    @DynamoDBAttribute(attributeName = "availableTimeSlots")
    public List<String> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    public void setAvailableTimeSlots(List<String> availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
    }
}

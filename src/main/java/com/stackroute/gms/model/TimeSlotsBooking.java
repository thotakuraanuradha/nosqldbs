package com.stackroute.gms.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.stackroute.gms.util.DateConverter;
import com.stackroute.gms.util.MapConverter;

import java.time.LocalDate;
import java.util.Map;

/*
 * This class represents the booking details of a customer for a particlar time slot
 * */
@DynamoDBDocument
public class TimeSlotsBooking {

    /*Properties*/

    private Integer bookingId;
    private LocalDate enrollFrom;
    private String timeSlot;
    private Integer bookings;
    private Map<String, Map<String, Integer>> bookingsByDay;

    /*Getters and setters*/
    @DynamoDBTypeConverted(converter = MapConverter.class)
    @DynamoDBAttribute(attributeName = "bookingsByDay")
    public Map<String, Map<String, Integer>> getBookingsByDay() {
        return bookingsByDay;
    }

    public void setBookingsByDay(Map<String, Map<String, Integer>> bookingsByDay) {
        this.bookingsByDay = bookingsByDay;
    }

    public Integer getBookingId() {
        return bookingId;
    }


    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    public LocalDate getEnrollFrom() {
        return enrollFrom;
    }

    public void setEnrollFrom(LocalDate enrollFrom) {
        this.enrollFrom = enrollFrom;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getBookings() {
        return bookings;
    }

    public void setBookings(Integer bookings) {
        this.bookings = bookings;
    }

}

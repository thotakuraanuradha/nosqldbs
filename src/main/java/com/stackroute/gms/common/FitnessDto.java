package com.stackroute.gms.common;

import java.time.LocalDate;

/*This class is used to transfer data*/

public class FitnessDto {

    /*Properties*/

    private int trainerId;
    private int customerId;
    private int programId;
    private LocalDate enrollFrom;
    private String timeSlot;

    /*Parameterized constructor*/
    public FitnessDto(int trainerId, int customerId, int programId, LocalDate enrollFrom, String timeSlot) {
        this.trainerId = trainerId;
        this.customerId = customerId;
        this.programId = programId;
        this.enrollFrom = enrollFrom;
        this.timeSlot = timeSlot;
    }

    /*Getters and setters*/
    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

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
}

package com.stackroute.gms.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDate;

/*
 * This utility class is used to covert Date to string and vise versa.
 * This is used in the entities having the date fields.
 * */
public class DateConverter implements DynamoDBTypeConverter<String, LocalDate> {

    /*
     *This method converts date to String while storing entity data to a DynamoDB Table
     * @param LocalDate
     * @return String
     */
    @Override
    public String convert(LocalDate date) {
        return date.toString();
    }

    /*
     *This method converts String to Date while retrieving entity data from a DynamoDB Table
     * @param String
     * @return LocalDate
     */
    @Override
    public LocalDate unconvert(String s) {
        return LocalDate.parse(s);
    }
}

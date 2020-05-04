package com.stackroute.gms.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * This utility class is used to covert List to string and vise versa.
 * This is used in the entities having the List fields.
 * */
public class ListConverter implements DynamoDBTypeConverter<String, List<?>> {

    /*
     *This method converts List to String while storing entity data to a DynamoDB Table
     * @param List
     * @return String
     */
    @Override
    public String convert(List<?> objectList) {
        return objectList.toString();
    }

    /*
     *This method converts String to List while retrieving entity data from a DynamoDB Table
     * @param String
     * @return List
     */
    @Override
    public List<?> unconvert(String stringList) {
        List<Object> returnList = new ArrayList<>();
        String input = stringList.substring(1, stringList.length() - 1);
        String[] data = input.split(",");
        Arrays.asList(data).stream().forEach(returnList::add);
        return returnList;
    }


}

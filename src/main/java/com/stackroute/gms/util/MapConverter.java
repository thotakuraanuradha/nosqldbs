package com.stackroute.gms.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * This utility class is used to covert Map to string and vise versa.
 * This is used in the entities having the Map fields.
 * */
public class MapConverter implements DynamoDBTypeConverter<String, Map<?, ?>> {


    /*
     *This method converts Map to String while storing entity data to a DynamoDB Table
     * @param Map<?,?>
     * @return String
     */
    @Override
    public String convert(Map<?, ?> paramMap) {

        return paramMap.toString();
    }

    /*
     *This method converts String to Map  while retrieving entity data from a DynamoDB Table
     * @param String
     * @return Map<?,?>
     */
    @Override
    public Map<?, ?> unconvert(String mapStr) {
        Map<String, Object> returnMap = new HashMap();

        if (mapStr != null && mapStr.contains(",")) {
            mapStr = mapStr.substring(1, mapStr.length() - 1);
            String[] data = mapStr.split(",");
            Arrays.asList(data).stream().forEach(e ->
                    {
                        String[] elements = e.split("=");

                        if (elements.length == 2) {
                            String key = elements[0];
                            String value = elements[1];
                            returnMap.put(key.trim(), value.trim());
                        } else if (elements.length == 3) {
                            Map<String, Integer> deepMap = new HashMap<>();
                            String key = elements[0];
                            String key2 = elements[1].substring(1);
                            String value2 = elements[2].substring(0, elements[2].length() - 1);
                            deepMap.put(key2, Integer.parseInt(value2));
                            returnMap.put(key, deepMap);

                        }

                    }
            );

        } else if (mapStr != null && !mapStr.equals("{}")) {
            mapStr = mapStr.substring(1, mapStr.length() - 1);

            String[] elements = mapStr.split("=");
            if (elements.length == 2) {
                String key = elements[0];
                String value = elements[1];
                returnMap.put(key.trim(), value.trim());
            } else if (elements.length == 3) {
                Map<String, Integer> deepMap = new HashMap<>();
                String key = elements[0];
                String key2 = elements[1].substring(1);
                String value2 = elements[2].substring(0, elements[2].length() - 1);
                deepMap.put(key2, Integer.parseInt(value2));
                returnMap.put(key, deepMap);

            }
        }
        return returnMap;
    }


}

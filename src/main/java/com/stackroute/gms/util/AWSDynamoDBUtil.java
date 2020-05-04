package com.stackroute.gms.util;


import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/*
 * This is the utility class for Create an in-memory and in-process instance
 * of DynamoDB Local that runs over HTTP
 * An embedded service is used to create resources
 * */
public class AWSDynamoDBUtil {

    private static final Logger logger = LogManager.getLogger(AWSDynamoDBUtil.class);

    /*
     * This method is used to sart server locally
     * @param String,String
     * @return DynamoDBMapper
     * */
    public static DynamoDBMapper startServer(String tableName, String primaryKey) {
        final String[] localArgs = {"-inMemory", "-port", "9099"};
        DynamoDBProxyServer server = null;
        AmazonDynamoDB ddb = null;
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();

            AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                    /*we can use any region here*/
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:9099", "us-west-2"))
                    .build();
            ddb = DynamoDBEmbedded.create().amazonDynamoDB();
            createTable(ddb, tableName, primaryKey);
            return new DynamoDBMapper(ddb);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
        } finally {
            // Stop the DynamoDB Local endpoint
            if (server != null) {
                try {
                    server.stop();
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }
        }
        return new DynamoDBMapper(ddb);

    }

    /*
     * This method is used to create the required tables
     * @param String,String
     * @return DynamoDBMapper
     * @param AmazonDynamoDB
     * @return CreateTableResult
     */
    private static CreateTableResult createTable(AmazonDynamoDB ddb, String tableName, String hashKeyName) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition(hashKeyName, ScalarAttributeType.N));

        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement(hashKeyName, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request =
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withKeySchema(ks)
                        .withProvisionedThroughput(provisionedthroughput);


        return ddb.createTable(request);
    }


}



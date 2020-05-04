package com.stackroute.gms.commander.test;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.stackroute.gms.model.Program;
import com.stackroute.gms.util.AWSDynamoDBUtil;
import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

public class AWSDynamoDBUtilTests {

   public  AWSDynamoDBUtilTests(){
        System.setProperty("sqlite4java.library.path", "target/dependencies");

    }

    private AmazonDynamoDB amazonDynamoDB;
    private static final String TABLE_NAME = "Program";
    private static final String ITEM = "programId";

    @Test
    public void givenClassWhenSaveObjectThenCheckValuesInDB() {


        DynamoDBMapper mapper = AWSDynamoDBUtil.startServer(TABLE_NAME, ITEM);
        Program yogaProgram = new Program(1, "Yoga", "1 hr");
        mapper.save(yogaProgram);
        Program dbResult = mapper.load(Program.class, 1);
        assertEquals(1, dbResult.getProgramId());
        assertEquals("Yoga", dbResult.getProgramName());
        assertEquals("1 hr", dbResult.getDuration());
        assertNull(dbResult.getCustomerBookingsMap());
        assertNull(dbResult.getTimeSlotBookingsMap());
    }

    @Test
    public void givenClassWhenDeleteObjectThenCheckValuesDeleted() {

        DynamoDBMapper mapper = AWSDynamoDBUtil.startServer(TABLE_NAME, ITEM);
        Program yogaProgram = new Program(1, "Yoga", "1 hr");
        mapper.delete(yogaProgram);
        assertNull(mapper.load(Program.class, 1));
    }

    @Test
    public void givenClassWhenDeleteNonExistentObjectThenThrowException() {
        DynamoDBMapper mapper = AWSDynamoDBUtil.startServer("TABLE_NAME", ITEM);
        Program yogaProgram = new Program(1, "Yoga", "1 hr");
        assertThrows(ResourceNotFoundException.class, () -> mapper.delete(yogaProgram), "TABLE NOT FOUND");
    }
}


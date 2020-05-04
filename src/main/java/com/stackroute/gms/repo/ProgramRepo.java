package com.stackroute.gms.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.util.AWSDynamoDBUtil;

import java.math.BigDecimal;
import java.util.*;

/*
 *This class represents program data manipulation from the application in the DynamoDB.
 *Basically contain all the CRUD operations for the Program entity
 */

public class ProgramRepo {

    private DynamoDBMapper mapper;

    public ProgramRepo() {
        mapper = AWSDynamoDBUtil.startServer("Program", "programId");
        createProgram();

    }

    /*
     * this  method to create the program
     * @param
     * @return
     */
    public List<Program> createProgram() {
        Map<String, BigDecimal> yogaPackageCostMap = new HashMap<>();
        Map<String, BigDecimal> aerobicsPackageCostMap = new HashMap<>();
        List<Program> programs = new ArrayList<>();
        String[] yogaSlots = new String[3];
        String[] aerobicSlots = new String[3];
        yogaSlots[0] = "6:00 am to 7:00 am";
        yogaSlots[1] = "7:30 am to 8:30 am";
        yogaSlots[2] = "9:00 am to 10:00 am";
        aerobicSlots[0] = "4:00 pm to 5:00 pm";
        aerobicSlots[1] = "5:00 pm to 6:00 pm";
        aerobicSlots[2] = "6:00 pm to 7:00 pm";
        yogaPackageCostMap.put("3 months Yoga", BigDecimal.valueOf(10000.000));
        aerobicsPackageCostMap.put("3 months Aerobics", BigDecimal.valueOf(20000.000));
        Program yogaProgram = new Program(1, "Yoga", "1 hr");
        Program aerobicsProgram = new Program(2, "Aerobics", "1 hr");
        yogaProgram.setAvailableTimeSlots(Arrays.asList(yogaSlots));
        yogaProgram.setPackageCostMap(yogaPackageCostMap);
        yogaProgram.setCustomerBookingsMap(new HashMap<>());
        yogaProgram.setTimeSlotBookingsMap(new TimeSlotsBooking());
        aerobicsProgram.setAvailableTimeSlots(Arrays.asList(aerobicSlots));
        aerobicsProgram.setPackageCostMap(aerobicsPackageCostMap);
        aerobicsProgram.setCustomerBookingsMap(new HashMap<>());
        aerobicsProgram.setTimeSlotBookingsMap(new TimeSlotsBooking());
        programs.add(yogaProgram);
        programs.add(aerobicsProgram);
        mapper.batchSave(programs);
        return programs;
    }


    /*
     * this method is used to retrieve the Program data
     * @param
     * @return List<Program>
     */
    public List<Program> retrievePrograms() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(Program.class, scanExpression);
    }

    /*
     * this method is used to save the Program data
     * @param Program
     * @return Program
     */
    public Program saveProgram(Program program) {
        mapper.save(program);
        return mapper.load(Program.class, program.getProgramId());
    }

    /*
     * this method is used to delete the Program data
     * @param Program
     * @return Program
     */
    public void deleteProgram(Program program) {
        mapper.delete(program);
    }

    /*
     * this method is used to get the Program data by Id
     * @param Program,@int
     * @return Program
     */
    public Program getProgramById(Class<Program> program, int programId) {
        return mapper.load(program, programId);
    }


}

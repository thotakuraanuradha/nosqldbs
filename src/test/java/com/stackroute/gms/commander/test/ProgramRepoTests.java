package com.stackroute.gms.commander.test;


import com.stackroute.gms.model.Program;
import com.stackroute.gms.model.TimeSlotsBooking;
import com.stackroute.gms.repo.ProgramRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramRepoTests {

    private ProgramRepo repo;
    private Program program;
    private List<Program> programs;


    @BeforeEach
    public void setUp() {
        repo = new ProgramRepo();
        programs = new ArrayList<>();
        Map<String, BigDecimal> yogaPackageCostMap = new HashMap<>();
        String[] yogaSlots = new String[3];
        yogaSlots[0] = "6:00 am to 7:00 am";
        yogaSlots[1] = "7:30 am to 8:30 am";
        yogaSlots[2] = "9:00 am to 10:00 am";
        yogaPackageCostMap.put("3 months Yoga", BigDecimal.valueOf(10000.000));
        program = new Program(1, "Yoga", "1 hr");
        program.setAvailableTimeSlots(Arrays.asList(yogaSlots));
        program.setPackageCostMap(yogaPackageCostMap);
        program.setTimeSlotBookingsMap(new TimeSlotsBooking());
    }

    @AfterEach
    public void tearDown() {
        repo = null;
        program = null;
    }

    @Test
    public void givenObjectWhenSaveProgramThenReturnObject() {
        Program programSaved = repo.saveProgram(program);
        assertEquals(program.getProgramId(), programSaved.getProgramId());
        assertEquals(program.getProgramName(), programSaved.getProgramName());
        assertEquals(program.getPackageCostMap().size(), programSaved.getPackageCostMap().size());
        assertNull(program.getCustomerBookingsMap());
    }

    @Test
    public void givenObjectWhenRetrieveProgramsThenReturnList() {
        programs = repo.retrievePrograms();
        assertEquals(2, programs.size());
    }

    public void givenObjectWhenRetrieveProgramsThenReturnExactList() {
        programs = repo.retrievePrograms();
        assertNotEquals(20, programs.size());
    }

    @Test
    public void givenObjectWhenCallGetProgramByIdThenReturnObject() {
        assertNotNull(repo.getProgramById(Program.class, 1));
    }

    @Test
    public void givenObjectWhenCallGetProgramByNonIdThenReturnNull() {
        assertNull(repo.getProgramById(Program.class, 100));
    }


    @Test
    public void givenObjectWhenDeleteProgramThenReturnNone() {
        repo.deleteProgram(program);
        assertNull(repo.getProgramById(Program.class, 1));

    }
}

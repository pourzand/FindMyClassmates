package com.example.findmyclassmates;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassModelUnitTest {

    private ClassModel classModel;
    private final String testDepartment = "test department";
    private final String testClassName = "test class name";
    private final String testDescription = "test description";
    private final String testUnits = "test units";
    private final String testTime = "test time";
    private final String testDays = "test days";
    private final String testProfessor = "test professor";
    private final String testLocation = "test location";
    private final String testClassID = "test classID";

    @Before
    public void setUp() {
        classModel = new ClassModel(testDepartment, testClassName, testDescription, testUnits, testTime, testDays, testProfessor, testLocation, testClassID);
    }

    @Test
    public void gettersAndSetters_workCorrectly() {
        // Testing Getters
        assertEquals(testDepartment, classModel.getDepartment());
        assertEquals(testClassName, classModel.getClassName());
        assertEquals(testDescription, classModel.getDescription());
        assertEquals(testUnits, classModel.getUnits());
        assertEquals(testTime, classModel.getTime());
        assertEquals(testDays, classModel.getDays());
        assertEquals(testProfessor, classModel.getProfessor());
        assertEquals(testLocation, classModel.getLocation());
        assertEquals(testClassID, classModel.getClassID());

        // Testing Setters
        String newDepartment = "new department";
        classModel.setDepartment(newDepartment);
        assertEquals(newDepartment, classModel.getDepartment());

        String newClassName = "new class name";
        classModel.setClassName(newClassName);
        assertEquals(newClassName, classModel.getClassName());

        String newDescription = "new description";
        classModel.setDescription(newDescription);
        assertEquals(newDescription, classModel.getDescription());

        String newUnits = "new units";
        classModel.setUnits(newUnits);
        assertEquals(newUnits, classModel.getUnits());

        String newTime = "new time";
        classModel.setTime(newTime);
        assertEquals(newTime, classModel.getTime());

        String newDays = "new days";
        classModel.setDays(newDays);
        assertEquals(newDays, classModel.getDays());

        String newProfessor = "new professor";
        classModel.setProfessor(newProfessor);
        assertEquals(newProfessor, classModel.getProfessor());

        String newLocation = "new location";
        classModel.setLocation(newLocation);
        assertEquals(newLocation, classModel.getLocation());

        String newClassID = "new classID";
        classModel.setClassID(newClassID);
        assertEquals(newClassID, classModel.getClassID());
    }
}

package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {
    DBTableFixtures fixture;
    Connection con;
    ProjectMapper projectMapper;
    SubprojectMapper subprojectMapper;
    TaskMapper taskMapper;


    @BeforeEach
    public void setup() throws SQLException {
        fixture = new DBTableFixtures();
        fixture.setUp();
        projectMapper = new ProjectMapper((fixture.getTestConnection()));
        subprojectMapper = new SubprojectMapper((fixture.getTestConnection()));
        taskMapper = new TaskMapper((fixture.getTestConnection()));
    }

    @Test

           void createTask_newTaskSuccessfullyAddedToDB() throws SQLException, DefaultException {
            con = fixture.getTestConnection();
            projectMapper.createProject(1, "Test Projekt #2", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8);
            subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
            taskMapper.createTask(1, "Test taks 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8, 2000);
            String SQL = "SELECT COUNT(*) FROM task;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int result = rs.getInt("count(*)");

            assertEquals(1, result);
    }


    @Test
        void editTask_TaskSuccessfullyEditedToDB() throws SQLException, DefaultException{

            con = fixture.getTestConnection();
            projectMapper.createProject(1, "Test Projekt #2", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8);
            subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
            taskMapper.createTask(1, "Test taks 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8, 2000);
            taskMapper.editTask(1, "Edited Test tasks 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30),8,2000,3000);

            String SQL = "SELECT COUNT(*) FROM task where title = \"Edited Test tasks 1\";";
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int result = rs.getInt("count(*)");

            assertEquals(1, result);

        }


    @Test
    void getTasks_Successfully() throws SQLException, DefaultException {
        con = fixture.getTestConnection();

        projectMapper.createProject(1, "Test Projekt #2", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8);
        subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
        taskMapper.createTask(1, "Test taks 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8, 2000);
        taskMapper.createTask(1, "Test tasks 2", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30),8,200,2000);
        taskMapper.getTasks(1);

        String SQL = "SELECT COUNT(*) FROM task";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(2, result);

    }
}
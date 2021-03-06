// Wafaa
package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.Subproject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubprojectMapperTest {
    DBTableFixtures fixture;
    Connection con;
    SubprojectMapper subprojectMapper;


    @BeforeEach
    public void setup() throws SQLException {
        fixture = new DBTableFixtures();
        fixture.setUp();
        subprojectMapper = new SubprojectMapper(fixture.getTestConnection());
    }


   @Test
    void createSubproject_newSubprojectSuccessfullyAddedToDB() throws SQLException, DefaultException {
        con = fixture.getTestConnection();
        subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
        String SQL = "SELECT COUNT(*) FROM subproject;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(1, result);
    }

    @Test
    void createSubproject_throwsSuccessfullyWhenUserIDisNotFound() {
        Assertions.assertThrows(DefaultException.class, () -> {
            subprojectMapper.createSubproject(0, "", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
        });
    }

    @Test
    void createSubproject_throwsSuccessfullyWhenDateIsWrong() {
        Assertions.assertThrows(DateTimeException.class, () -> {
            subprojectMapper.createSubproject(1, "", LocalDate.of(0,0,0), LocalDate.of(0,0,0));
        });
    }


    @Test
    void getSubprojects_Successfully() throws SQLException, DefaultException{
        con = fixture.getTestConnection();
        subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
        subprojectMapper.getSubprojects(1);

        String SQL = "SELECT COUNT(*) FROM subproject";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(1, result);

    }

    @Test
    void updateSubprojects() throws SQLException, DefaultException {
        con = fixture.getTestConnection();

        subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));

        ArrayList<Subproject> subprojects = new ArrayList<>();
        Subproject subproject = new Subproject();
        subproject.setId(1);
        subproject.setTotalWorkDays(10);
        subproject.setTotalWorkHours(80);
        subproject.setDeadlineDifference(365);
        subprojects.add(subproject);

        subprojectMapper.updateSubprojects(subprojects);

        String SQL = "SELECT * FROM subproject " +
                "LEFT JOIN project_object_info ON project_object_info.subproject_id = subproject.id " +
                "WHERE subproject_id = 1";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        rs.next();
        int sampleResult1 = rs.getInt("total_work_days");
        int sampleResult2 = rs.getInt("total_work_hours");
        int sampleResult3 = rs.getInt("deadline_difference");

        assertEquals(10, sampleResult1);
        assertEquals(80, sampleResult2);
        assertEquals(365, sampleResult3);
    }



    @Test
    void setSubproject_successfullyMapsDataFromResultSetToObject() throws SQLException, DefaultException {
        con = fixture.getTestConnection();
        subprojectMapper.createSubproject(1, "Test subproject", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));

       String SQL = "SELECT * FROM subproject " +
                "LEFT JOIN project_object_info ON project_object_info.subproject_id = subproject.id " +
                "WHERE subproject_id = 1";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        rs.next();


        Subproject subproject = subprojectMapper.setSubproject(rs);

        assertEquals("Test subproject", subproject.getTitle());
    }

    @Test
    void editSubproject_SubprojectSuccessfullyEditedToDB() throws SQLException, DefaultException{

        con = fixture.getTestConnection();
        subprojectMapper.createSubproject(1, "Test subprojects 1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));
        subprojectMapper.editSubproject(1, "Edited Test subprojects #1", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30));

        String SQL = "SELECT COUNT(*) FROM project_object_info where title = \"Edited Test subprojects #1\";";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(1, result);

    }


}
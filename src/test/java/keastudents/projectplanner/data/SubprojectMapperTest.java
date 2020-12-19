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
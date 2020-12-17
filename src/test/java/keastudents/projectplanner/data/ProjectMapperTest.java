package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {
    DBTableFixtures fixture;
    Connection con;
    ProjectMapper projectMapper;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new DBTableFixtures();
        fixture.setUp();
        projectMapper = new ProjectMapper((fixture.getTestConnection()));
    }

    @Test
    void createProject_newProjectSuccessfullyAddedToDB() throws SQLException, DefaultException {
        con = fixture.getTestConnection();
        projectMapper.createProject(1, "Test Projekt #2", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), 120,8);

        String SQL = "SELECT COUNT(*) FROM project;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(2, result);
    }

    @Test
    void createProject_throwsSuccessfullyWhenUserIDisNotFound() {
        Assertions.assertThrows(DefaultException.class, () -> {
            projectMapper.createProject(0, "", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), -5, -5);
        });
    }

    @Test
    void createProject_throwsSuccessfullyWhenDateIsWrong() {
        Assertions.assertThrows(DateTimeException.class, () -> {
            projectMapper.createProject(1, "", LocalDate.of(0,0,0), LocalDate.of(0,0,0), -5, -5);
        });
    }

    @Test
    void updateProject_updatesDataSuccessfully() throws SQLException, DefaultException {
        con = fixture.getTestConnection();
        Project project = new Project();
        project.setTotalWorkDays(10);
        project.setTotalWorkHours(80);
        project.setEstFinishedByDate(LocalDate.of(2021,12,31));
        project.setDeadlineDifference(365);
        project.setChangeToWorkHoursNeeded(20);
        project.setEstTotalCost(30000);
        project.setId(1);

        projectMapper.updateProject(project);

        String SQL = "SELECT * FROM project " +
                "LEFT JOIN project_object_info ON project_object_info.project_id = project.id " +
                "WHERE project_id = 1";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        rs.next();
        int sampleResult1 = rs.getInt("total_work_hours");
        int sampleResult2 = rs.getInt("est_total_cost");
        int sampleResult3 = rs.getInt("deadline_difference");

        assertEquals(80, sampleResult1);
        assertEquals(30000, sampleResult2);
        assertEquals(365, sampleResult3);
    }

    @Test
    void editProject_SuccessfullyChangedTitle() throws SQLException, DefaultException {
        con = fixture.getTestConnection();
        projectMapper.editProject(1, "New title for test", LocalDate.of(2021,01,01), LocalDate.of(2021,01,30), -5, -5);

        String SQL = "SELECT * FROM project " +
                "LEFT JOIN project_object_info ON project_object_info.project_id = project.id " +
                "WHERE project_id = 1";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        rs.next();
        String result = rs.getString("title");

        assertEquals("New title for test", result);
    }

    @Test
    void deleteProjectObject_successfullyDeletedProject() throws SQLException, DefaultException {
        con = fixture.getTestConnection();
        projectMapper.deleteProjectObject(1, "project");

        String SQL = "SELECT COUNT(*) FROM project;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(0, result);

    }

    @Test
    void getProjects_successfullyRetrievesAllProjects() throws DefaultException, SQLException {
        con = fixture.getTestConnection();
        ArrayList<Project> projects = projectMapper.getProjects(1);

        assertEquals(1, projects.size());
    }

    @Test
    void getProject_successfullyGetsTestProject() throws DefaultException, SQLException {
        con = fixture.getTestConnection();
        Project project = projectMapper.getProject(1);

        assertEquals("Test projekt", project.getTitle());
    }

    @Test
    void setProject_successfullyMapsDataFromResultSetToObject() throws SQLException {
        con = fixture.getTestConnection();

        String SQL = "SELECT * FROM project " +
                "LEFT JOIN project_object_info ON project_object_info.project_id = project.id " +
                "WHERE project_id = 1";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        rs.next();

        Project project = projectMapper.setProject(rs);

        assertEquals("Test projekt", project.getTitle());
    }
}
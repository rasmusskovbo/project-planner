package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.Subproject;
import keastudents.projectplanner.domain.Task;

import java.sql.*;
import java.time.LocalDate;

public class ProjectMapper {
    Connection con = DBManager.getConnection();

    public void createProject(int id, String title, String startDate) throws DefaultException {
        try {

            String createProjectSQL = "INSERT INTO project (user_id, title, start_date) VALUES (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(createProjectSQL);
            psProject.setInt(1, id);
            psProject.setString(2, title);
            psProject.setString(3, startDate);
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException(ex.getMessage());
        }
    }

    public Project getProject(int id) throws DefaultException {
        try {

            String SQL = "SELECT * FROM project " +
                  //  "LEFT JOIN subproject ON subproject.project_id = project.id " +
                  //  "LEFT JOIN task ON task.subproject_id = subproject.id " +
                    "WHERE user_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Project project = new Project(
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("start_date"))
                );


                // TODO While loop der tilføjer subprojects og tasks.


                return project;
            } else {
                return null;
            }

        } catch(SQLException e) {

            throw new DefaultException(e.getMessage());

        }
    }

    public void createSubProject(int project_id, String title, String startDate) throws DefaultException {
        try {
            String SQL = "INSERT INTO subproject (project_id, title, start_date) VALUES (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(SQL);
            psProject.setInt(1, project_id);
            psProject.setString(2, title);
            psProject.setString(3, startDate);
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException("Unable to create subproject (Project ID unknown or invalid arguments)");
        }
    }

    public void createTask(int subproject_id, String title, String startDate) throws DefaultException {
        try {
            String SQL = "INSERT INTO task (subproject_id, title, start_date) VALUES (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(SQL);
            psProject.setInt(1, subproject_id);
            psProject.setString(2, title);
            psProject.setString(3, startDate);
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException("Unable to create task (Subproject ID unknown or invalid arguments)");
        }
    }
}

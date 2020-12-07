package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.Subproject;
import keastudents.projectplanner.domain.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectMapper {
    Connection con = DBManager.getConnection();

    public void createProject(int userId, String projectTitle, LocalDate startDate) throws DefaultException {
        try {

            String createProjectSQL = "INSERT INTO project (user_id, title, start_date) VALUES (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(createProjectSQL);
            psProject.setInt(1, userId);
            psProject.setString(2, projectTitle);
            psProject.setDate(3, Date.valueOf(startDate));
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException(ex.getMessage());
        }
    }

    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDate) throws DefaultException {
        try {
            String SQL = "INSERT INTO subproject (project_id, title, start_date) VALUES (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(SQL);
            psProject.setInt(1, projectId);
            psProject.setString(2, subprojectTitle);
            psProject.setString(3, String.valueOf(startDate));
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException("Unable to create subproject (Project ID unknown or invalid arguments)");
        }
    }

    public void createTask(int subprojectId, String taskTitle, LocalDate startDate) throws DefaultException {
        try {
            String SQL = "INSERT INTO task (subproject_id, title, start_date) VALUES (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(SQL);
            psProject.setInt(1, subprojectId);
            psProject.setString(2, taskTitle);
            psProject.setString(3, String.valueOf(startDate));
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException("Unable to create task (Subproject ID unknown or invalid arguments)");
        }
    }

    public  ArrayList<Project> getProjects(int userId) throws DefaultException {
        try {

            String SQL = "SELECT * FROM project " +
                    //  "LEFT JOIN subproject ON subproject.project_id = project.id " +
                    //  "LEFT JOIN task ON task.subproject_id = subproject.id " +
                    "WHERE user_id= ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            ArrayList<Project> projects = new ArrayList<>();
            while (rs.next()) {
                Project project = new Project(
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("start_date"))
                );
                project.setId(rs.getInt("id"));

                // Appends all subprojects to task
                project.setSubprojects(getSubprojects(project.getId()));

                projects.add(project);
            }
            return projects;
        } catch(SQLException e) {

            throw new DefaultException(e.getMessage());

        }
    }


    public ArrayList<Subproject> getSubprojects(int projectId) throws DefaultException {

        ArrayList<Subproject> subprojects = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM subproject " +
                    "WHERE project_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            // Loops through subprojects if found
            while (rs.next()) {
                Subproject subproject = new Subproject(
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("start_date"))
                );
                subproject.setId(rs.getInt("id"));

                // Appends all tasks to subproject
                subproject.setTasks(getTasks(subproject.getId()));

                subprojects.add(subproject);
            }

            return subprojects;

        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());
        }
    }

    public ArrayList<Task> getTasks(int subprojectId) throws DefaultException {

        ArrayList<Task> tasks = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM task " +
                    "WHERE subproject_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, subprojectId);
            ResultSet rs = ps.executeQuery();

            // Loops through subprojects if found
            while (rs.next()) {
                Task task = new Task(
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("start_date"))
                );
                task.setId(rs.getInt("id"));

                tasks.add(task);
            }

            return tasks;

        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());
        }
    }

}

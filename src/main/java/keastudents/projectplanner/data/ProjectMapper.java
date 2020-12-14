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

    public void createProject(int userId, String projectTitle, LocalDate startDate, LocalDate deadline, int baselineManHourCost, int baselineHoursPrWorkday) throws DefaultException {
        try {

            // create project
            String createProjectSQL = "INSERT INTO project (user_id) VALUES (?)";
            PreparedStatement psProject = con.prepareStatement(createProjectSQL, Statement.RETURN_GENERATED_KEYS);
            psProject.setInt(1, userId);
            psProject.executeUpdate();

            ResultSet id = psProject.getGeneratedKeys();
            id.next();
            int project_id = id.getInt(1);

            // Set object info
            String setProjectInfoSQL = "INSERT INTO project_object_info (title, start_date, deadline, baseline_man_hour_cost, baseline_hours_pr_workday, project_id) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psObjectInfo = con.prepareStatement(setProjectInfoSQL);
            psObjectInfo.setString(1, projectTitle);
            psObjectInfo.setDate(2, Date.valueOf(startDate));
            psObjectInfo.setDate(3, Date.valueOf(deadline));
            psObjectInfo.setInt(4, baselineManHourCost);
            psObjectInfo.setInt(5, baselineHoursPrWorkday);
            psObjectInfo.setInt(6, project_id);
            psObjectInfo.executeUpdate();

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
            psProject.executeQuery();

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

    // For project & subproject
    public void editProject(int projectId, String title, LocalDate start_date, LocalDate deadline, int baseline_man_hour_cost, int baseline_hours_pr_workday) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            SQL = "UPDATE project_object_info " +
                    "SET title = ?, start_date = ?, deadline = ?, baseline_man_hour_cost = ?, baseline_hours_pr_workday = ? " +
                    "WHERE project_id = ?";
            ps = con.prepareStatement(SQL);
            ps.setString(1, title);
            ps.setDate(2, Date.valueOf(start_date));
            ps.setDate(3, Date.valueOf(deadline));
            ps.setInt(4, baseline_man_hour_cost);
            ps.setInt(5, baseline_hours_pr_workday);
            ps.setInt(6, projectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DefaultException("Unable to edit project in database");
        }
    }

    // Choice = project, subproject or task
    public void deleteProjectObject(int id, String choice) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            switch (choice) {
                case "project":
                    SQL = "DELETE from project WHERE id = ?";
                    ps = con.prepareStatement(SQL);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    break;

                case "subproject":
                    SQL = "DELETE from subproject WHERE id = ?";
                    ps = con.prepareStatement(SQL);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    break;

                case "task":
                    SQL = "DELETE from task WHERE id = ?";
                    ps = con.prepareStatement(SQL);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    break;
            }

        } catch (SQLException e) {
            throw new DefaultException("Unable to delete project_object in database: "+e.getMessage());
        }
    }


    // TODO Når objektet laves skal al information fra result set sættes ind
    public ArrayList<Project> getProjects(int userId) throws DefaultException {
        try {

            String SQL = "SELECT * FROM project " +
                    "LEFT JOIN project_object_info ON project_object_info.project_id = project.id " +
                    "WHERE user_id = ?;";
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

    public Project getProject(int projectId) throws DefaultException{
        try {

            String SQL = "SELECT * FROM project " +
                    "LEFT JOIN project_object_info ON project_object_info.project_id = project.id " +
                    "WHERE project_id = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Project project = new Project(
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("start_date"))
                );
                project.setId(rs.getInt("id"));

                // Appends all subprojects to task
                project.setSubprojects(getSubprojects(project.getId()));

                return project;
            } else {
                return null;
            }
        } catch(SQLException e) {

            throw new DefaultException(e.getMessage());

        }
    }


    // TODO Når objektet laves skal al information fra result set sættes ind
    public ArrayList<Subproject> getSubprojects(int projectId) throws DefaultException {

        ArrayList<Subproject> subprojects = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM subproject " +
                    "LEFT JOIN project_object_info ON project_object_info.subproject_id = subproject.id " +
                    "WHERE subproject.project_id = ?";
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
                    "LEFT JOIN project_object_info ON project_object_info.task_id = task.id " +
                    "WHERE task.subproject_id = ?";
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

// Rasmus
package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectMapper {
    Connection con = DBManager.getConnection();
    SubprojectMapper subprojectMapper = new SubprojectMapper();

    public ProjectMapper() {
    }

    public ProjectMapper(Connection connection) {
        con = connection;
    }

    // CREATE
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

// EDIT
    public void updateProject(Project project) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            SQL = "UPDATE project_object_info " +
                    "SET " +
                    "total_work_hours = ?, " +
                    "total_work_days = ?, " +
                    "est_finished_by_date = ?, " +
                    "deadline_difference = ?, " +
                    "change_to_workhours_needed = ?, " +
                    "est_total_cost = ? " +
                    "WHERE project_id = ?;";
            ps = con.prepareStatement(SQL);
            ps.setInt(1, project.getTotalWorkHours());
            ps.setInt(2, project.getTotalWorkDays());
            ps.setDate(3, Date.valueOf(project.getEstFinishedByDate()));
            ps.setInt(4, project.getDeadlineDifference());
            ps.setInt(5, project.getChangeToWorkHoursNeeded());
            ps.setInt(6, project.getEstTotalCost());
            ps.setInt(7, project.getId());
            ps.executeUpdate();

            subprojectMapper.updateSubprojects(project.getSubprojects());

        } catch (SQLException e) {
            throw new DefaultException("Could not update project: " + e.getMessage());
        }

    }

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

// DELETE
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

// GET
    public ArrayList<Project> getProjects(int userId) throws DefaultException {
        //only used to get a list of all user's project titles (no need to append subprojects/task)
        try {

            String SQL = "SELECT * FROM project " +
                    "LEFT JOIN project_object_info ON project_object_info.project_id = project.id " +
                    "WHERE user_id = ?;";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            ArrayList<Project> projects = new ArrayList<>();
            while (rs.next()) {
                Project project = setProject(rs);

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
                Project project = setProject(rs);

                // Appends all subprojects to task
                project.setSubprojects(subprojectMapper.getSubprojects(project.getId()));
                project.sortSubprojects();

                return project;
            } else {
                return null;
            }
        } catch(SQLException e) {

            throw new DefaultException(e.getMessage());

        }
    }

    public Project setProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setTitle(rs.getString("title"));
        project.setStartDate((rs.getString("start_date")));
        project.setDeadline((rs.getString("deadline")));
        project.setBaselineManHourCost(rs.getInt("baseline_man_hour_cost"));
        project.setBaselineHoursPrWorkday(rs.getInt("baseline_hours_pr_workday"));
        project.setTotalWorkHours(rs.getInt("total_work_hours"));
        project.setTotalWorkDays(rs.getInt("total_work_days"));
        project.setEstFinishedByDate((rs.getString("est_finished_by_date")));
        project.setDeadlineDifference(rs.getInt("deadline_difference"));
        project.setChangeToWorkHoursNeeded(rs.getInt("change_to_workhours_needed"));
        project.setEstTotalCost(rs.getInt("est_total_cost"));

        return project;
    }

}

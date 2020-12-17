package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Subproject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SubprojectMapper {
    Connection con = DBManager.getConnection();
    TaskMapper taskMapper = new TaskMapper();

    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDate, LocalDate deadline) throws DefaultException {
        try {
            String SQL = "INSERT INTO subproject (project_id) VALUES (?)";
            PreparedStatement psProject = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            psProject.setInt(1, projectId);
            psProject.executeUpdate();

            ResultSet id = psProject.getGeneratedKeys();
            id.next();
            int subproject_id = id.getInt(1);

            // Set object info
            String setProjectInfoSQL = "INSERT INTO project_object_info (title, start_date, deadline, subproject_id) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement psObjectInfo = con.prepareStatement(setProjectInfoSQL);
            psObjectInfo.setString(1, subprojectTitle);
            psObjectInfo.setDate(2, Date.valueOf(startDate));
            psObjectInfo.setDate(3, Date.valueOf(deadline));
            psObjectInfo.setInt(4, subproject_id);
            psObjectInfo.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException("Unable to create subproject (Project ID unknown or invalid arguments)");
        }
    }

    public void updateSubprojects(ArrayList<Subproject> subprojects) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            for (int i = 0; i<subprojects.size(); i++) {
                Subproject subproject = subprojects.get(i);

                SQL = "UPDATE project_object_info " +
                        "SET " +
                        "total_work_hours = ?, " +
                        "total_work_days = ?, " +
                        "est_finished_by_date = ?, " +
                        "deadline_difference = ?, " +
                        "change_to_workhours_needed = ?, " +
                        "est_total_cost = ? " +
                        "WHERE subproject_id = ?;";
                ps = con.prepareStatement(SQL);
                ps.setInt(1, subproject.getTotalWorkHours());
                ps.setInt(2, subproject.getTotalWorkDays());
                ps.setDate(3, Date.valueOf(subproject.getEstFinishedByDate()));
                ps.setInt(4, subproject.getDeadlineDifference());
                ps.setInt(5, subproject.getChangeToWorkHoursNeeded());
                ps.setInt(6, subproject.getEstTotalCost());
                ps.setInt(7, subproject.getId());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DefaultException("Could not update subproject: " + e.getMessage());
        }

    }

    public void editSubproject(int subprojectId, String title, LocalDate start_date, LocalDate deadline) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            SQL = "UPDATE project_object_info " +
                    "SET title = ?, start_date = ?, deadline = ? " +
                    "WHERE subproject_id = ?";
            ps = con.prepareStatement(SQL);
            ps.setString(1, title);
            ps.setDate(2, Date.valueOf(start_date));
            ps.setDate(3, Date.valueOf(deadline));
            ps.setInt(4, subprojectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DefaultException("Unable to edit subproject in database: "+e.getMessage());
        }
    }

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
                Subproject subproject = setSubproject(rs);

                // Appends all tasks to subproject
                subproject.setTasks(taskMapper.getTasks(subproject.getId()));

                subprojects.add(subproject);
            }

            return subprojects;

        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());
        }
    }

    public Subproject setSubproject(ResultSet rs) throws SQLException {
        Subproject subproject = new Subproject();
        subproject.setId(rs.getInt("id"));
        subproject.setTitle(rs.getString("title"));
        subproject.setStartDate((rs.getString("start_date")));
        subproject.setDeadline((rs.getString("deadline")));
        subproject.setBaselineManHourCost(rs.getInt("baseline_man_hour_cost"));
        subproject.setBaselineHoursPrWorkday(rs.getInt("baseline_hours_pr_workday"));
        subproject.setTotalWorkHours(rs.getInt("total_work_hours"));
        subproject.setTotalWorkDays(rs.getInt("total_work_days"));
        subproject.setEstFinishedByDate((rs.getString("est_finished_by_date")));
        subproject.setDeadlineDifference(rs.getInt("deadline_difference"));
        subproject.setChangeToWorkHoursNeeded(rs.getInt("change_to_workhours_needed"));
        subproject.setEstTotalCost(rs.getInt("est_total_cost"));
        return subproject;
    }
}

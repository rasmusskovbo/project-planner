// Rasmus
package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskMapper {
    Connection con = DBManager.getConnection();

    public TaskMapper(Connection testConnection) { this.con = testConnection; }
    public TaskMapper(){

    }

    public void createTask(int subprojectId, String taskTitle, LocalDate startDate, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost) throws DefaultException {
        try {
            String SQL = "INSERT INTO task (subproject_id, workhours_needed, extra_costs, man_hour_cost) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subprojectId);
            ps.setInt(2, workHoursNeeded);
            ps.setInt(3, extraCosts);
            ps.setInt(4, manHourCost);
            ps.executeUpdate();

            ResultSet id = ps.getGeneratedKeys();
            id.next();
            int task_id = id.getInt(1);

            // Set object info
            String setProjectInfoSQL = "INSERT INTO project_object_info (title, start_date, deadline, task_id) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement psObjectInfo = con.prepareStatement(setProjectInfoSQL);
            psObjectInfo.setString(1, taskTitle);
            psObjectInfo.setDate(2, Date.valueOf(startDate));
            psObjectInfo.setDate(3, Date.valueOf(deadline));
            psObjectInfo.setInt(4, task_id);
            psObjectInfo.executeUpdate();


        } catch (SQLException ex) {
            throw new DefaultException("Unable to create task (Task ID unknown or invalid arguments)" + ex.getMessage());
        }
    }

    public void editTask(int taskId, String title, LocalDate start_date, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            SQL = "UPDATE task "+
                    "SET workhours_needed = ?, extra_costs = ?, man_hour_cost = ? " +
                    "WHERE id = ?";
            ps = con.prepareStatement(SQL);
            ps.setInt(1, workHoursNeeded);
            ps.setInt(2, extraCosts);
            ps.setInt(3, manHourCost);
            ps.setInt(4, taskId);
            ps.executeUpdate();

            String objectSQL = "UPDATE project_object_info "+
                    "SET title = ?, start_date = ?, deadline = ? "+
                    "WHERE task_id = ?";
            PreparedStatement objectPS = con.prepareStatement(objectSQL);
            objectPS.setString(1, title);
            objectPS.setDate(2, Date.valueOf(start_date));
            objectPS.setDate(3, Date.valueOf(deadline));
            objectPS.setInt(4, taskId);
            objectPS.executeUpdate();

        } catch (SQLException e) {
            throw new DefaultException("Unable to edit task: "+e.getMessage());
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
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setStartDate(rs.getString("start_date"));
                task.setDeadline(rs.getString("deadline"));
                task.setWorkHoursNeeded(rs.getInt("workhours_needed"));
                task.setExtraCosts(rs.getInt("extra_costs"));
                task.setManHourCost(rs.getInt("man_hour_cost"));

                tasks.add(task);
            }

            return tasks;

        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());
        }
    }
}

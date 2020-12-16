package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskMapper {
    Connection con = DBManager.getConnection();

    public void createTask(int subprojectId, String taskTitle, LocalDate startDate, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost, int hoursPrWorkday) throws DefaultException {
        try {
            String SQL = "INSERT INTO task (subproject_id, workhours_needed, extra_costs, man_hour_cost, hours_pr_workday) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, subprojectId);
            ps.setInt(2, workHoursNeeded);
            ps.setInt(3, extraCosts);
            ps.setInt(4, manHourCost);
            ps.setInt(5, hoursPrWorkday);
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
            throw new DefaultException("Unable to create task (Task ID unknown or invalid arguments)");
        }
    }

    public void editTask(int taskId, String title, LocalDate start_date, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost, int hoursPrWorkday) throws DefaultException {
        String SQL = "";
        PreparedStatement ps = null;

        try {
            SQL = "UPDATE task "+
                    "SET workhours_needed = ?, extra_costs = ?, man_hour_cost = ?, hours_pr_workday = ? " +
                    "WHERE id = ?";
            ps = con.prepareStatement(SQL);
            ps.setInt(1, workHoursNeeded);
            ps.setInt(2, extraCosts);
            ps.setInt(3, manHourCost);
            ps.setInt(4, hoursPrWorkday);
            ps.setInt(5, taskId);
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

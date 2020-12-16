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
                subproject.setTasks(taskMapper.getTasks(subproject.getId()));

                subprojects.add(subproject);
            }

            return subprojects;

        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());
        }
    }
}

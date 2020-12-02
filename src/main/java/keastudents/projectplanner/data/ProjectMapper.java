package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;

import java.sql.*;
import java.time.LocalDate;

public class ProjectMapper {
    Connection con = DBManager.getConnection();

    public void createProject(int id, String title, String startDate) throws DefaultException {
        try {
            Connection con = DBManager.getConnection();

            String createProjectSQL = "INSERT INTO project (user_id, title, start_date) VALUE (?, ?, ?)";
            PreparedStatement psProject = con.prepareStatement(createProjectSQL);
            psProject.setInt(1, id);
            psProject.setString(2, title);
            psProject.setString(3, startDate);
            psProject.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException(ex.getMessage());
        }
//        try {
//
//            String SQL = "INSERT INTO project (title, start_date) VALUES (?, ?)";
//            PreparedStatement ps = con.prepareStatement(SQL);
//            ps.setString(1, title);
//            ps.setString(2, startDate);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                Project project = new Project(
//                        rs.getString("title"),
//                        LocalDate.parse(rs.getString("start_date"))
//                );
//            } else {
//                throw new DefaultException("Could not create project (Input or database error");
//            }
//
//        } catch(SQLException e) {
//
//            throw new DefaultException(e.getMessage());
//
//        }
    }

    public Project getProject(int id) throws DefaultException {
        try {

            String SQL = "SELECT * FROM project " +
                        //"JOIN subproject USING (id) " TODO Eksempel på implementering
                        //"JOIN task USING (id) "
                        "WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Project project = new Project(
                        rs.getString("title"),
                        LocalDate.parse(rs.getString("start_date"))
                );

                return project;
            } else {
                throw new DefaultException("Could not retrieve project (ID or database error)");
            }

        } catch(SQLException e) {

            throw new DefaultException(e.getMessage());

        }
    }
}

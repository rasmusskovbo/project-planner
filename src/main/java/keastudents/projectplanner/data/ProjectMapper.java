package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;

import java.sql.*;

public class ProjectMapper {

    public void createProject(Project project) throws DefaultException {

        Connection con = DBManager.getConnection();

        try {

            String SQL = "INSERT INTO project (title, start_date) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, project.getTitle());
            ps.setDate(2, (Date) project.getStart_date());
            ps.executeQuery();

            System.out.println("Project title:"+project.getTitle());
            System.out.println("Project start date:"+project.getStart_date());

        } catch(SQLException e) {

            throw new DefaultException(e.getMessage());

        }
    }
}

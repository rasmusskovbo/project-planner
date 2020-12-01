package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;

import java.sql.*;

public class ProjectMapper {

    public void createProject(Project project) throws DefaultException {

        Connection con = DBManager.getConnection();

        // Mangler konkretisering
        try {
            String SQL = "INSERT INTO project (title) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, "");
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());

        }
    }
}

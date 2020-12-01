package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.User;

import java.sql.*;

public class UserMapper {

    public void createUser(User user) throws DefaultException {

        try {
            Connection con = DBManager.getConnection();

            //Insert created user's email into "user" parent table
            String userSQL = "INSERT INTO user (email) VALUES (?)";
            PreparedStatement psUser = con.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, user.getEmail());
            psUser.executeUpdate();
            //Get "user" tables auto-generated id and set it as the user's id
            ResultSet ids = psUser.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            user.setId(id);

            //Insert created user's info into "user_info" child table
            String userinfoSQL = "INSERT INTO user_info (user_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement psUserinfo = con.prepareStatement(userinfoSQL);
            psUserinfo.setInt(1, user.getId());
            psUserinfo.setString(2, user.getFirstName());
            psUserinfo.setString(3, user.getLastName());
            psUserinfo.executeUpdate();

            //Insert created user's password into "user_login" child table
            String userloginSQL = "INSERT INTO login_info (user_id, pword) VALUES (?, ?)";
            PreparedStatement psUserlogin = con.prepareStatement(userloginSQL);
            psUserlogin.setInt(1, user.getId());
            psUserlogin.setString(2, user.getPassword());
            psUserlogin.executeUpdate();

        } catch (SQLException ex) {
            throw new DefaultException(ex.getMessage());
        }
    }

    public User login(String email, String password) throws DefaultException {
        try {
            Connection con = DBManager.getConnection();
            String SQL = "SELECT * from users "
                    + "JOIN login_info using (idusers) "
                    + "WHERE email=? AND pword=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idusers");
                User user = new User(email, password);
                user.setId(id);
                return user;
            } else {
                throw new DefaultException("Could not validate user");
            }
        } catch (SQLException ex) {
            throw new DefaultException(ex.getMessage());
        }
    }
}

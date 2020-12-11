package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.User;
import org.apache.juli.logging.Log;

import java.sql.*;

public class UserMapper {

    private Connection con = DBManager.getConnection();

    public UserMapper() {
    }
    public UserMapper(Connection connection) {
        con = connection;
    }

    public void createUser(User user) throws DefaultException {

        try {

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

    public User login(String email, String password) throws LoginException {

        try {

            String SQL = "SELECT * FROM user " +
                    "LEFT JOIN login_info ON login_info.user_id = user.id " +
                    "WHERE email=? AND pword=?";

            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                User user = new User(email, password);
                user.setId(id);
                return user;
            } else {
                throw new LoginException("Could not validate user.");
            }
        } catch (SQLException ex) {
            throw new LoginException(ex.getMessage());
        }
    }

    public User getUser(int userId) throws DefaultException {

        try {
            String SQL = "SELECT * FROM user " +
                    "LEFT JOIN user_info ON user_info.user_id = user.id " +
                    "WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                );
                return user;

            } else {
                throw new DefaultException("Could not validate user (ID not found in database)");
            }
        } catch (SQLException e) {
            throw new DefaultException(e.getMessage());
        }
    }

}


package keastudents.projectplanner.domain;

import keastudents.projectplanner.data.DBTableFixtures;
import keastudents.projectplanner.data.LoginException;
import keastudents.projectplanner.data.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserMapperTest {
    UserMapper userMapper;
    DBTableFixtures fixture;
    Connection con;

    @BeforeEach
    public void setup() throws SQLException {
        fixture = new DBTableFixtures();
        fixture.setUp();
        userMapper = new UserMapper(fixture.getTestConnection());
    }

    @Test
    public void createUser_newUserSuccessfullyAddedToDB() throws DefaultException, SQLException {
        con = fixture.getTestConnection();
        userMapper.createUser(new User("Hans", "Olesen", "hans@olesen.dk", "1234"));

        String SQL = "SELECT COUNT(*) FROM user;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt("count(*)");

        assertEquals(2, result);
    }

    @Test
    public void login_throwsWhenWrongEmail() {
        Assertions.assertThrows(LoginException.class, () -> {
            userMapper.login("notvalid@email.dk", "1234");
        });
    }

    @Test
    public void login_throwsWhenWrongPW() {
        Assertions.assertThrows(LoginException.class, () -> {
            userMapper.login("test@test.dk", "wrong_password");
        });
    }

    @Test
    public void login_successfullyLogsInWhenMatchingCredentials() throws LoginException {
        User user = userMapper.login("test@test.dk", "1234");
        assertEquals("test@test.dk", user.getEmail());
    }




}

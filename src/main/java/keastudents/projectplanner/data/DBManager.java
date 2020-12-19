package keastudents.projectplanner.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static String user;
    private static String password;
    private static String url;
    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection != null) return connection;
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url,user,password);
            //ændret til test database fordi uanset hvor man ændre database navn så defaulter den altid til det der bliver sat her (mange timer brændt af på fejlfinding pga. dette)
            String setDB = "USE projectplannertestdatabase;";
            PreparedStatement ps = connection.prepareStatement(setDB);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

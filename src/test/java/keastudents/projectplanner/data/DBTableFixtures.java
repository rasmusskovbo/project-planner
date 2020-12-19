package keastudents.projectplanner.data;

import java.sql.*;

public class DBTableFixtures {
    private String url = "jdbc:mysql://localhost:3306/projectplannertestdatabase";
    private String user = "projectplanner";
    private String pw = "1234";

    private Connection con = null;

    public void setUp() throws SQLException {
        try {
            con = getTestConnection();

            Statement st = con.createStatement();

            con.setAutoCommit(false);

            // Temporarily removes foreign key checks to drop tables
            st.addBatch("SET foreign_key_checks = 0;");

            // Sets up DB
            st.addBatch("DROP TABLE IF EXISTS user;");
            st.addBatch("CREATE TABLE user (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  email varchar(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  UNIQUE KEY email_UNIQUE (email)" +
                    ")");
            
            st.addBatch("DROP TABLE IF EXISTS user_info;");
            st.addBatch("CREATE TABLE user_info (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  first_name varchar(45) NOT NULL," +
                    "  last_name varchar(45) NOT NULL," +
                    "  user_id int NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY user_info_to_user (user_id)," +
                    "  CONSTRAINT user_id_info FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");");

            st.addBatch("DROP TABLE IF EXISTS login_info;");
            st.addBatch("CREATE TABLE login_info (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  pword varchar(100) NOT NULL," +
                    "  user_id int NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY login_info_to_user (user_id)," +
                    "  CONSTRAINT user_id_login FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");");

            st.addBatch("DROP TABLE IF EXISTS project;");
            st.addBatch("CREATE TABLE project (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  user_id int NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY user_id_project_idx (user_id)," +
                    "  CONSTRAINT user_id_project FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");");

            st.addBatch("DROP TABLE IF EXISTS subproject;");
            st.addBatch("CREATE TABLE subproject (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  project_id int NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY project_id_idx (project_id)," +
                    "  CONSTRAINT project_id FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");");

            st.addBatch("DROP TABLE IF EXISTS task;");
            st.addBatch("CREATE TABLE task (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  subproject_id int NOT NULL," +
                    "  workhours_needed int DEFAULT NULL," +
                    "  extra_costs int DEFAULT NULL," +
                    "  man_hour_cost int DEFAULT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY subproject_id_idx (subproject_id)," +
                    "  CONSTRAINT subproject_id FOREIGN KEY (subproject_id) REFERENCES subproject (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");");

            st.addBatch("DROP TABLE IF EXISTS project_object_info;");
            st.addBatch("CREATE TABLE project_object_info (" +
                    "  id int NOT NULL AUTO_INCREMENT," +
                    "  title varchar(100) NOT NULL," +
                    "  start_date date NOT NULL," +
                    "  deadline date DEFAULT NULL," +
                    "  baseline_man_hour_cost int DEFAULT NULL," +
                    "  baseline_hours_pr_workday int DEFAULT NULL," +
                    "  total_work_hours int DEFAULT NULL," +
                    "  total_work_days int DEFAULT NULL," +
                    "  est_finished_by_date date DEFAULT NULL," +
                    "  deadline_difference varchar(45) DEFAULT NULL," +
                    "  change_to_workhours_needed int DEFAULT NULL," +
                    "  est_total_cost int DEFAULT NULL," +
                    "  project_id int DEFAULT NULL," +
                    "  subproject_id int DEFAULT NULL," +
                    "  task_id int DEFAULT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY project_id_idx (project_id)," +
                    "  KEY subproject_id_idx (subproject_id)," +
                    "  KEY task_id_idx (task_id)," +
                    "  CONSTRAINT project_id_objectinfo FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  CONSTRAINT subproject_id_objectinfo FOREIGN KEY (subproject_id) REFERENCES subproject (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  CONSTRAINT task_id_objectinfo FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");");
            st.addBatch("SET foreign_key_checks = 1;");

            // Injects data
            // (USER)
            st.addBatch("INSERT INTO user (email) VALUE ('test@test.dk')");
            st.addBatch("INSERT INTO user_info (user_id, first_name, last_name) VALUES (1, 'test', 'testersen')");
            st.addBatch("INSERT INTO login_info (user_id, pword) VALUES (1, '1234')");

            // (PROJECT)
            st.addBatch("INSERT INTO project (user_id) VALUE (1)");
            st.addBatch("INSERT INTO project_object_info (title, start_date, deadline, baseline_man_hour_cost, baseline_hours_pr_workday, project_id) " +
                    "VALUES ('Test projekt', 20210101, 20210131, 120, 8, 1);");

            int[] updateCounts = st.executeBatch();
            
            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDatabase(Connection connection, String user, String password, String url) {
        try {
            connection = DriverManager.getConnection(url,user,password);
            String setDB = "USE projectplannertestdatabase;";
            PreparedStatement ps = connection.prepareStatement(setDB);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getTestConnection() throws SQLException {
        con = DriverManager.getConnection(url, user, pw);
        setDatabase(con, user, pw, url);
        return con;
    }

}



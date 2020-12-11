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
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  email varchar(100) NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  UNIQUE KEY email_UNIQUE (email)" +
                    ")");
            
            st.addBatch("DROP TABLE IF EXISTS user_info;");
            st.addBatch("CREATE TABLE user_info (" +
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  first_name varchar(45) NOT NULL," +
                    "  last_name varchar(45) NOT NULL," +
                    "  user_id smallint NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY user_id_idx (user_id)," +
                    "  CONSTRAINT user_id_info FOREIGN KEY (user_id) REFERENCES user (id)" +
                    ");");

            st.addBatch("DROP TABLE IF EXISTS login_info;");
            st.addBatch("CREATE TABLE login_info (" +
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  pword varchar(100) NOT NULL," +
                    "  user_id smallint NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY user_id_idx (user_id)," +
                    "  CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user (id)" +
                    ")");

            st.addBatch("DROP TABLE IF EXISTS project;");
            st.addBatch("CREATE TABLE project (" +
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  user_id smallint NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY user_id_project_idx (user_id)," +
                    "  CONSTRAINT user_id_project FOREIGN KEY (user_id) REFERENCES user (id)" +
                    ") ");

            st.addBatch("DROP TABLE IF EXISTS subproject;");
            st.addBatch("CREATE TABLE subproject (" +
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  project_id smallint NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY project_id_idx (project_id)," +
                    "  CONSTRAINT project_id FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE" +
                    ") ");

            st.addBatch("DROP TABLE IF EXISTS task;");
            st.addBatch("CREATE TABLE task (" +
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  subproject_id smallint NOT NULL," +
                    "  workhours_needed smallint DEFAULT NULL," +
                    "  extra_costs smallint DEFAULT NULL," +
                    "  man_hour_cost smallint DEFAULT NULL," +
                    "  hours_pr_workday smallint DEFAULT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY subproject_id_idx (subproject_id)," +
                    "  CONSTRAINT subproject_id FOREIGN KEY (subproject_id) REFERENCES subproject (id) ON DELETE CASCADE" +
                    ")");

            st.addBatch("DROP TABLE IF EXISTS project_object_info;");
            st.addBatch("CREATE TABLE project_object_info (" +
                    "  id smallint NOT NULL AUTO_INCREMENT," +
                    "  title varchar(100) NOT NULL," +
                    "  start_date date NOT NULL," +
                    "  deadline date DEFAULT NULL," +
                    "  baseline_man_hour_cost smallint DEFAULT NULL," +
                    "  baseline_hours_pr_workday smallint DEFAULT NULL," +
                    "  total_work_hours smallint DEFAULT NULL," +
                    "  total_work_days smallint DEFAULT NULL," +
                    "  est_finished_by_date date DEFAULT NULL," +
                    "  deadline_difference varchar(45) DEFAULT NULL," +
                    "  change_to_workhours_needed smallint DEFAULT NULL," +
                    "  est_total_cost smallint DEFAULT NULL," +
                    "  project_id smallint DEFAULT NULL," +
                    "  subproject_id smallint DEFAULT NULL," +
                    "  task_id smallint DEFAULT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  KEY project_id_idx (project_id)," +
                    "  KEY subproject_id_idx (subproject_id)," +
                    "  KEY task_id_idx (task_id)," +
                    "  CONSTRAINT project_id_objectinfo FOREIGN KEY (project_id) REFERENCES project (id)," +
                    "  CONSTRAINT subproject_id_objectinfo FOREIGN KEY (subproject_id) REFERENCES subproject (id)," +
                    "  CONSTRAINT task_id_objectinfo FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE" +
                    ")");
            st.addBatch("SET foreign_key_checks = 1;");

            // Injects data (USER)
            st.addBatch("INSERT INTO user (email) VALUE ('test@test.dk')");
            st.addBatch("INSERT INTO user_info (user_id, first_name, last_name) VALUES (1, 'test', 'testersen')");
            st.addBatch("INSERT INTO login_info (user_id, pword) VALUES (1, '1234')");

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



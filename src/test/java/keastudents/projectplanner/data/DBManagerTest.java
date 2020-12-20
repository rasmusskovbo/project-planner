package keastudents.projectplanner.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {

    @Test
    public void getConnection_successfullyConnectsToDB() {
        DBManager dbManager = new DBManager();

        assertTrue(dbManager.getConnection() != null);

    }


}
package com.company;

import java.io.IOException;
import java.sql.SQLException;

public class Admin extends Cashier{
    Admin(int dbId, String dbName, int Age) throws SQLException, IOException, ClassNotFoundException {
        super(dbId, dbName, Age);
    }
}

package com.company;

import java.io.IOException;
import java.sql.SQLException;

public class Cashier extends TransactionWorker {
    private int Id;
    private String Name;

    Cashier(int dbId , String dbName) throws SQLException, IOException, ClassNotFoundException {
        this.Id = dbId;
        this.Name=dbName;

        createAllTransactions();
        createAllMaterials();
        createAllSuppliers();
    }

}

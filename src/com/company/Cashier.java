package com.company;

import java.io.IOException;
import java.sql.SQLException;

public class Cashier extends TransactionWorker {
    private int Id;
    private String Name;
    private int Age;
    Cashier(int dbId , String dbName,int Age) throws SQLException, IOException, ClassNotFoundException {
        this.Id = dbId;
        this.Name=dbName;
        this.Age=Age;
        createAllTransactions();
        createAllMaterials();
        createAllSuppliers();
    }

}

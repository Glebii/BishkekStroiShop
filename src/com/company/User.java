package com.company;

import java.io.IOException;
import java.sql.SQLException;

public abstract class User extends TransactionWorker{
    private final int Id;
    private final String Name;
    public String getName() {
        return this.Name;
    }
    public int getId(){return this.Id;}


    User(int dbId , String dbName) throws SQLException, IOException, ClassNotFoundException {
        this.Id = dbId;
        this.Name=dbName;

        createAllTransactions();
        createAllMaterials();
        createAllSuppliers();
    }
}

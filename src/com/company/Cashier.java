package com.company;

import java.io.IOException;
import java.sql.SQLException;

public class Cashier extends TransactionWorker implements MenuShower {
    private final int Id;
    private final String Name;
    public String getName() {
        return this.Name;
    }



    Cashier(int dbId , String dbName) throws SQLException, IOException, ClassNotFoundException {
        this.Id = dbId;
        this.Name=dbName;

        createAllTransactions();
        createAllMaterials();
        createAllSuppliers();
    }


    @Override
    public void getMenu() {

    }
}

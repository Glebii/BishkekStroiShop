package com.company;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        MaterialWorker mat = new MaterialWorker();
        TransactionWorker tr = new TransactionWorker();
        tr.makeASale();
        mat.getAllMaterials();




    }

}

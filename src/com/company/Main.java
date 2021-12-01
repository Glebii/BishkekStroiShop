package com.company;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        MaterialWorker mat = new MaterialWorker();
        mat.getAllMaterials();
//        mat.materialsRefillUpdate();
//        mat.updateMaterials();
        mat.deleteMaterial();
        mat.getAllMaterials();


//        TransactionWorker tr = new TransactionWorker();
//        tr.makeASale();
//        System.out.println("===============================================");
//        tr.makeASale();



    }

}

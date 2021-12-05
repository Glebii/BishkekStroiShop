package com.company;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
//        MaterialWorker mat = new MaterialWorker();
//        mat.getAllMaterials();
//        TransactionWorker tr = new TransactionWorker();
//        tr.makeASale();
//        System.out.println("===============================================");
        Admin a = new Admin(12,"Bob",13);
        a.getAllInfoAboutSuppliers();
        a.supplierAdd();


        Cashier c = new Cashier(12,"Mob",13);
        c.getAllInfoAboutSuppliers();







    }

}

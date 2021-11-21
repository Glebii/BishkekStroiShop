package com.company;
import java.io.IOException;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        MaterialWorker mat = new MaterialWorker();
                mat.getMaterials();
                mat.updateMaterials();
                mat.getMaterials();
//        SuppliersWorker sup = new SuppliersWorker();
//        sup.getAllInfoAboutSuppliers();
//        sup.updateSupplier();
//        sup.getAllInfoAboutSuppliers();






    }

}

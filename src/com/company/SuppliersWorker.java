package com.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class SuppliersWorker {
    DBConnector dbcon = new DBConnector();
    private List<Supplier> LS = new ArrayList();
    Scanner sc = new Scanner(System.in);

    protected class Supplier{
        private int SupplierId;
        private String SupplierName;
        private String SupplierSurname;
        private String SupplierPhone;
        private String SupplierAdress;
        Supplier(
                int supplierId,
                String supplierName,
                String supplierSurname,
                String supplierPhone,
                String supplierAdress
        )
        {
            this.SupplierId = supplierId;
            this.SupplierName = supplierName;
            this.SupplierSurname = supplierSurname;
            this.SupplierPhone = supplierPhone;
            this.SupplierAdress = supplierAdress;
        }
        //Getters
        public int getSupplierId(){
            return this.SupplierId;
        }
        public String getSupplierName() { return SupplierName; }
        public String getSupplierSurname() { return SupplierSurname; }
        public String getSupplierPhone() { return SupplierPhone; }
        public String getSupplierAdress() { return SupplierAdress; }
        public void getInfoAboutSup()
        {
            System.out.printf("\nLieferanten-ID: %d\n" +
                            "Name des Lieferanten: %s\n" +
                            "Nachname des Lieferanten: %s\n" +
                            "Lieferantentelefon: %s\n" +
                            "Lieferantenadresse: %s \n",
                    this.SupplierId,this.SupplierName,this.SupplierSurname,this.SupplierPhone,this.SupplierAdress);
        }
        //Setters
        public void setSupplierName(String supplierName) { SupplierName = supplierName; }
        public void setSupplierSurname(String supplierSurname) { SupplierSurname = supplierSurname; }
        public void setSupplierPhone(String supplierPhone) { SupplierPhone = supplierPhone; }
        public void setSupplierAdress(String supplierAdress) { SupplierAdress = supplierAdress; }
    }
    protected List<Supplier> getLS(){
        return this.LS;
    }

    protected void createSupplier(int SID, String SN, String SS, String SP , String SA)
    {
        Supplier supplier = new Supplier(SID,SN,SS,SP,SA);
        LS.add(supplier);
    }
    protected void createAllSuppliers() throws SQLException, IOException, ClassNotFoundException {
      //Create all suppliers from DB
        dbcon.getConnectionToDB();
        ResultSet res = dbcon.statement.executeQuery("Select * From suppliers");
        res.beforeFirst();
        while (res.next()) {
            int ids = res.getInt("idsupplier");
            String supname = res.getString("SupplierName");
            String supsurname = res.getString("Surname");
            String phone = res.getString("Phone");
            String adress = res.getString("Adress");
            createSupplier(ids,supname,supsurname,phone,adress);
        }
        dbcon.closeConnections();
    }
    protected void getAllSuppliers(){
        for(Supplier s: LS){
            s.getInfoAboutSup();
            System.out.println("-------------------------------");
        }
    }
    protected Supplier getSupByID(int id){
        Supplier requiredSup = null;
        for (Supplier s:
             LS) {
            if(s.getSupplierId()==id){
                requiredSup=s;
            }

        }
        return requiredSup;
    }
     protected void getSupByID(){
         boolean isSupplierEx = false;

         System.out.println("Geben Sie die ID des gewünschten Anbieters ein:");
         int requiredSupId=sc.nextInt();
         for (Supplier s:
                 LS) {
             if(s.getSupplierId()==requiredSupId){
                s.getInfoAboutSup();
                isSupplierEx = true;
             }
         }
         System.out.println((isSupplierEx)?"":"Leider wurde kein Anbieter mit dieser ID gefunden.");


     }
}

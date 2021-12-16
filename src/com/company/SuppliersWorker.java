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
                            "Name des Lieferanten: %supplier\n" +
                            "Nachname des Lieferanten: %s\n" +
                            "Lieferantentelefon: %s\n" +
                            "Lieferantenadresse: %Lieferant",
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
            int ids = res.getInt("id lieferant");
            String supname = res.getString("Name des Lieferanten");
            String supsurname = res.getString("Familienname");
            String phone = res.getString("Telefon");
            String adress = res.getString("Anschrift");
            createSupplier(ids,supname,supsurname,phone,adress);
        }
        dbcon.closeConnections();
    }
    protected void getAllSuppliers(){
        for(Supplier s: LS){
            s.getInfoAboutSup();
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
         System.out.println("Geben Sie die ID des gewünschten Anbieters ein:");
         int requiredSupId=sc.nextInt();
         for (Supplier s:
                 LS) {
             if(s.getSupplierId()==requiredSupId){
                s.getInfoAboutSup();
             }
             else System.out.println("Leider wurde kein Anbieter mit dieser ID gefunden.");
         }

     }

//Admin's method
//    protected void updateSupplier() throws SQLException, IOException, ClassNotFoundException {
//        Scanner in = new Scanner(System.in);
//        getAllInfoAboutSuppliers();
//        dbcon.getConnectionToDB();
//        System.out.print("\n Enter id of updating material: ");
//        int id_of_updating_suplier = in.nextInt();
//        String space = in.nextLine();
//        System.out.print(
//                "\n Enter the column name to update\n"  +
//                "1)  SupplierName;\n" +
//                "2)  Surname;\n" +
//                "3)  Phone;\n" +
//                "4)  Adress;:  "
//                );
//        String colName_for_updating = in.nextLine();
//        System.out.print("\n Enter new value: ");
//        String nValue_String = in.nextLine();
//
//        if (
//                colName_for_updating.equals("SupplierName") || colName_for_updating.equals("Surname") || colName_for_updating.equals("Phone") || colName_for_updating.equals("Adress")
//        ){
//
//            for (SuppliersWorker.Supplier s : LS){
//                if (s.getSupplierId() == id_of_updating_suplier) {
//                    if(colName_for_updating.equals("SupplierName")){ s.setSupplierName(nValue_String); }
//                    else if(colName_for_updating.equals("Surname")){ s.setSupplierSurname(nValue_String); }
//                    else if(colName_for_updating.equals("Phone")){ s.setSupplierPhone(nValue_String); }
//                    else if(colName_for_updating.equals("Adress")){ s.setSupplierAdress(nValue_String); }
//                }
//            }
//            System.out.println("Updating successful done...");
//        }
//        else
//            {
//                System.out.println("The column not found! Please, try again!");
//                updateSupplier();
//            }
//
//        try
//        {
//            String sql = String.format("UPDATE suppliers  SET %s = \'%s\' WHERE idsupplier = %d", colName_for_updating, nValue_String, id_of_updating_suplier);
//            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("Changes were written successfully!");
//            System.out.printf("%d rows added", rows);
//            preparedStatement.close();
//        }
//        catch (Exception ex)
//        {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();
//        }
//        dbcon.connection.close();
//
//    }
    //Admin's method
//    protected void supplierDelete() throws SQLException, IOException, ClassNotFoundException {
//
//        try
//        { dbcon.getConnectionToDB();
//        Scanner scann = new Scanner(System.in);
//        System.out.println("Enter id to delete supplier: ");
//        int suplirsId_to_delete = scann.nextInt();
//        Iterator<Supplier> supplierIterator= LS.iterator();
//            while(supplierIterator.hasNext()) {
//
//                Supplier nextSup = supplierIterator.next();
//                if (nextSup.getSupplierId() == suplirsId_to_delete) {
//                    supplierIterator.remove();
//                }
//            }
//
//        String sql = String.format("DELETE FROM suppliers WHERE idsupplier = %d;", suplirsId_to_delete);
//        int rows = dbcon.statement.executeUpdate(sql);
//        System.out.println("Getting record...");
//        System.out.printf("%d rows deleted ", rows);
//        dbcon.closeConnections();
//        }
//        catch (Exception ex) {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();}
//        dbcon.closeConnections();
//     }

     //Admin's method
//    protected void supplierAdd() throws  SQLException {
//
//        try{
//            dbcon.getConnectionToDB();
//        Scanner in = new Scanner(System.in);
//        System.out.println("Now you will need to enter the required information for new suplier");
//        System.out.println("\nEnter name of supplier: ");
//        String names = in.nextLine();
//        System.out.println("\nEnter surname of supplier: ");
//        String surname = in.nextLine();
//        System.out.println("\nEnter phone of supplier: ");
//        String phone = in.nextLine();
//        System.out.println("Enter address of supplier: ");
//        String adress = in.nextLine();
//        int ids = LS.size() +1;
//
//        createSupplier(ids,names,surname,phone,adress);
//
//        String SQL = String.format("INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
//                ids, names, surname, phone, adress);
//
//            PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(SQL);
//            supplierCreatorDB.executeUpdate(SQL);
//            System.out.println("Done...");
//            dbcon.closeConnections();
//        }
//        catch (Exception ex) {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();}
//            dbcon.closeConnections();
//    }

}

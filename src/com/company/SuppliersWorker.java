package com.company;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SuppliersWorker{
    DBConnector dbcon = new DBConnector();
    List<Supplier> LS = new ArrayList();
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
        public void getInfoAboutSup(){
            System.out.printf("\nSupplier id: %d\nSupplier name: %s\nSupplier surname: %s\nSupplier phone: %s\nSupplier adress: %s\n",
                    this.SupplierId,this.SupplierName,this.SupplierSurname,this.SupplierPhone,this.SupplierAdress);
        }
        public int getSupplierId(){
            return this.SupplierId;
        }
    }
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";
//
//    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
//    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
//    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
//    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
//    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
//    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
//    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
//    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public void createSupplier(int SID, String SN, String SS, String SP , String SA)
    {
        Supplier supplier = new Supplier(SID,SN,SS,SP,SA);
        LS.add(supplier);
    }
//    public int
    public  void createAllSuppliers() throws SQLException, IOException, ClassNotFoundException {
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
    public void getAllInfoAboutSuppliers(){
        for(Supplier s:LS){
            s.getInfoAboutSup();
        }
    }

//    public static void updateSupplier() throws SQLException, IOException, ClassNotFoundException {
//        DBConnector.getConnectionToDB();
//        Statement statement = DBConnector.statement;
//
//        Scanner in = new Scanner(System.in);
//
//        System.out.println(ANSI_GREEN_BACKGROUND+"                                                                                                                                                            "+ANSI_RESET);
//
//        System.out.print("\n Enter the column name to update:  ");
//        String col = in.nextLine();
//        System.out.print("\n Enter name: ");
//        String name = in.nextLine();
//        System.out.print("\n Enter new value: ");
//        String nValue = in.next();
//
//        System.out.println(ANSI_GREEN_BACKGROUND+"                                                                                                                                                            "+ANSI_RESET);
//
//        try {
//            String sql = String.format("UPDATE suppliers  SET %s = \'%s\' WHERE SupplierName = \'%s\'", col, nValue, name);
//            PreparedStatement preparedStatement = DBConnector.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("Changes were written successfully!");
//            System.out.printf("%d rows added", rows);
//            DBConnector.connection.close();
//            preparedStatement.close();
//        } catch (Exception ex) {
//            System.out.println(ANSI_GREEN_BACKGROUND+"                                                                                                                                                            "+ANSI_RESET);
//
//            System.out.println("Connection failed...");
//
//            ex.printStackTrace();
//        }
//        statement.close();
//        DBConnector.connection.close();
//    }
//    public static void supplierDelete() throws SQLException, IOException, ClassNotFoundException{
//
//        DBConnector.getConnectionToDB();
//        Statement statement = DBConnector.statement;
//
//        Scanner scann = new Scanner(System.in);
//
//        System.out.println(ANSI_GREEN_BACKGROUND + "                                                                                                                                                            " + ANSI_RESET);
//
//        System.out.println("Enter number to delete supplier: ");
//        int id = scann.nextInt();
//
//        System.out.println(ANSI_GREEN_BACKGROUND + "                                                                                                                                                            " + ANSI_RESET);
//
//        String sql = String.format("DELETE FROM shop.suppliers WHERE idsupplier = %d;", id);
////        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//        int rows = statement.executeUpdate(sql);
//
//        System.out.println(ANSI_GREEN_BACKGROUND + "                                                                                                                                                            " + ANSI_RESET);
//
//        System.out.println("Getting record...");
//        System.out.printf("%d rows deleted ", rows);
//
//        statement.close();
//        DBConnector.connection.close();
//
//    }
//    public static void supplierAdd() throws ClassNotFoundException, SQLException, IOException {
//        DBConnector.getConnectionToDB();
//        Statement statement = DBConnector.statement;
//
//        Scanner in = new Scanner(System.in);
//        System.out.println("Now you will need to enter the required information for new material");
//
//        System.out.println(ANSI_GREEN_BACKGROUND+"                                                                                                                                                            "+ANSI_RESET);
//
//        System.out.println("\nEnter name of supplier: ");
//        String names = in.nextLine();
//        System.out.println("\nEnter surname of supplier: ");
//        String surname = in.nextLine();
//        System.out.println("\nEnter phone of supplier: ");
//        String phone = in.nextLine();
//        System.out.println("Enter address of supplier: ");
//        String adress = in.nextLine();
//        System.out.println("\nEnter new ID: ");
//        int ids = in.nextInt();
//
//        System.out.println(ANSI_GREEN_BACKGROUND+"                                                                                                                                                            "+ANSI_RESET);
//
//
//        try {
//            String sql = String.format("INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
//                    ids, names, surname, phone, adress);
//            PreparedStatement preparedStatement = DBConnector.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("New supplier is added successfully!");
//            System.out.printf("%d rows updated", rows);
//            System.out.println("Now, please enter the information for the supplier of added material");
//        }
//        catch (Exception ex) {
//            System.out.println(ANSI_GREEN_BACKGROUND+"                                                                                                                                                            "+ANSI_RESET);
//
//            System.out.println("Connection failed...");
//            ex.printStackTrace();}
//
//        statement.close();
//        DBConnector.connection.close();
//
//
//    }
}

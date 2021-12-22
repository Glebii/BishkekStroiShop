package com.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Admin a = new Admin(1,"John");
        a.addNewUser();

    }


    public static void runApplication() throws SQLException, IOException, ClassNotFoundException {
        userVerification();

    }
    public static void userVerification() throws SQLException, IOException, ClassNotFoundException {
        DBConnector dbcon = new DBConnector();
        dbcon.getConnectionToDB();
        dbcon.statement.executeUpdate("DELETE FROM `materials` WHERE Quantity<=0 AND idmaterial not in (SELECT materials_idmaterial FROM materials_has_suppliers)");
        dbcon.closeConnections();
        for(int count = 1; count <= 3; count++) {
            Scanner scann = new Scanner(System.in);
            System.out.println("Geben Sie Ihr Login ein: ");
            String log = scann.nextLine();
            System.out.println("Geben Sie Ihr Passwort ein:");
            String pass = scann.nextLine();

            try {
                dbcon.getConnectionToDB();
                String query = "SELECT Position,Fname,ID FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                
                ResultSet res = dbcon.statement.executeQuery(query);
                System.out.println("good");

                res.beforeFirst();
                if(!res.next()){
                    System.out.println("Das Passwort wurde falsch eingegeben, bitte versuchen Sie es erneut!");
                    int chance = 3 - count;
                    System.out.println("Sie haben nur noch " + chance + " Versuche.");
                    dbcon.closeConnections();
                }
                else{
                    res.beforeFirst();
                    while (res.next()){
                       String position = res.getString("Position");
                        String name  = res.getString("FName");
                        int Id = res.getInt("ID");
                        System.out.println(position);
                        System.out.println(name);
                        System.out.println(Id);
                        usersPosition(Id,name,position);
                    }
                    return;
                }
            } catch (SQLException | InterruptedException e) {
                System.out.println("catch");

                e.printStackTrace();
            }
        }
        System.out.println("Sie haben alle Chance genutzt!");
        dbcon.closeConnections();


    }


    public static void usersPosition(int id,String name,String position) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        if(position.equals("Administrator")){
            Admin administrator = new Admin(id,name);
            System.out.printf("Herzlich willkommen, %s !",administrator.getName());
            administrator.getMenu();
        }
        else if(position.equals("Cashier")){
            Cashier cashier = new Cashier(id,name);
            System.out.printf("Herzlich willkommen, %s !",cashier.getName());
            cashier.getMenu();

        }
        else{
            System.out.println("null");
        }
    }

}

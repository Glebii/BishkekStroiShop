package com.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        runApplication();
    }


    public static void runApplication() throws SQLException, IOException, ClassNotFoundException {
        userVerification();

    }
    public static void userVerification() throws SQLException, IOException, ClassNotFoundException {
        DBConnector dbcon = new DBConnector();
        for(int count = 1; count <= 3; count++) {
            Scanner scann = new Scanner(System.in);
            System.out.println("Geben Sie Ihr Login ein: ");
            String log = scann.nextLine();
            System.out.println("Geben Sie Ihr Passwort ein:");
            String pass = scann.nextLine();

            try {
                dbcon.getConnectionToDB();
                dbcon.statement.executeUpdate("DELETE FROM `materials` WHERE Quantity<=0 and idmaterial NOT IN (SELECT materials_idmaterial FROM materials_has_suppliers);");
                dbcon.closeConnections();
                dbcon.getConnectionToDB();


                String query = "SELECT Position FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                String queryForName = "SELECT Lname FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                String queryForId = "SELECT ID FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                
                ResultSet res = dbcon.statement.executeQuery(query);
                ResultSet resForName = dbcon.statement.executeQuery(queryForName);
                ResultSet resForId = dbcon.statement.executeQuery(queryForId);

                res.beforeFirst();
                if(!res.next()){
                    System.out.println("Das Passwort wurde falsch eingegeben, bitte versuchen Sie es erneut!");
                    int chance = 3 - count;
                    System.out.println("Sie haben nur noch " + chance + " Versuche.");
                    count++;
                    dbcon.closeConnections();

                }
                else{
                    res.beforeFirst();
                    String position = null;
                    if(res.next()){ position = res.getString(1);}

                    resForName.beforeFirst();
                    String name = null;
                    if(res.next()){ name = res.getString(1);}

                    resForId.beforeFirst();
                    int Id = 0;
                    if(res.next()){ Id = res.getInt(1);}

                    assert position != null : "Sie bekleiden keine Position.";
                    dbcon.closeConnections();
                    usersPosition(Id,name,position);

                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sie haben alle Chance genutzt!");

    }


    public static void usersPosition(int id,  String name,String position) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        if(position.equals("Administrator")){
            Admin administrator = new Admin(id,name);
            administrator.getMenu();
        }
        else if(position.equals("Kassier")){
            Cashier cashier = new Cashier(id,name);
            System.out.printf("Herzlich willkommen, %s !",cashier.getName());
            cashier.getMenu();

        }
    }

}

package com.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

//
//        Admin cc = new Admin(34,"fasd");
//        cc.addNewUser();

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

                String query = "SELECT Position,Fname,ID FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
//                String queryForName = "SELECT Lname FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
//                String queryForId = "SELECT ID FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                
                ResultSet res = dbcon.statement.executeQuery(query);
//                ResultSet resForName = dbcon.statement.executeQuery(queryForName);
//                ResultSet resForId = dbcon.statement.executeQuery(queryForId);
                System.out.println("good");

                res.beforeFirst();
                if(!res.next()){
                    System.out.println("Das Passwort wurde falsch eingegeben, bitte versuchen Sie es erneut!");
                    int chance = 3 - count;
                    System.out.println("Sie haben nur noch " + chance + " Versuche.");
                }
                else{
                    res.beforeFirst();
                    String position ;
                    String name ;
                    int Id ;
                    if(res.next()){
                        position = res.getString("Position");
                        name  = res.getString("FName");
                        Id = res.getInt("ID");
                        System.out.println(position);
                        System.out.println(name);
                        System.out.println(Id);
                        System.out.println("bla b;la");

                        usersPosition(Id,name,position);

                        System.out.println("bitti");

                    }


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
        if(position.equals("Админстратор")){
            System.out.println("funk");
            Admin administrator = new Admin(id,name);
            administrator.getMenu();
        }
        else if(position.equals("Kassier")){
            Cashier cashier = new Cashier(id,name);
            System.out.printf("Herzlich willkommen, %s !",cashier.getName());
            cashier.getMenu();

        }
        else{
            System.out.println("null");
        }
    }

}

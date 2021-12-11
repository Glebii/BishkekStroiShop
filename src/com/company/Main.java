package com.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        run();
    }


    public static void run() throws SQLException, IOException, ClassNotFoundException {
        String x = pass();

    }
    public static String  pass() throws SQLException, IOException, ClassNotFoundException {
        DBConnector dbcon = new DBConnector();
        dbcon.getConnectionToDB();


        for(int count = 1; count <= 3; count++) {
            Scanner scann = new Scanner(System.in);

            System.out.println("login: ");
            String log = scann.nextLine();
            System.out.println("password: ");
            String pass = scann.nextLine();

            try {
                dbcon.getConnectionToDB();

                String query = "SELECT Position FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                String queryForName = "SELECT Lname FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                String queryForId = "SELECT Userid FROM users WHERE Login='" + log + "' AND Password_='" + pass + "';";
                
                ResultSet res = dbcon.statement.executeQuery(query);
                ResultSet resForName = dbcon.statement.executeQuery(queryForName);
                ResultSet resForId = dbcon.statement.executeQuery(queryForId);

                res.beforeFirst();
                if(!res.next()){
                    System.out.println("Wrong password. Please, try again! ");
                    int chance = 3 - count;
                    System.out.println("You have only " + chance + " trials left.");
                    count++;
                    continue;
                }
                else{
                    System.out.println("Alles gut! Ehhh!");
                    res.beforeFirst();
                    String position = null;
                    if(res.next()){ position = res.getString(1);}
//                    System.out.println(position);

                    resForName.beforeFirst();
                    String name = null;
                    if(res.next()){ name = res.getString(1);}

                    resForId.beforeFirst();
                    int Id = 0;
                    if(res.next()){ Id = res.getInt(1);}

                    usersPosition(Id,name,position);
                    return position;
                }
            } catch (SQLException e) {
            }
        }
        System.out.println("Sie haben schon alle Chancen benutzt!");
        return "null";
    }


    public static void usersPosition(int id,  String name,String position) throws SQLException, IOException, ClassNotFoundException {

        menu(position);
        if(position.equals("Админстратор")){
            Admin administrator = new Admin(id,name);
            administrator.getAllInfoAboutSuppliers();
        }
        else if(position.equals("Кассир")){
            Cashier cashier = new Cashier(id,name);

        }
        else{
            System.out.println("");
        }
    }
    public static void  menu(String position) throws SQLException, IOException, ClassNotFoundException {
        Scanner scann = new Scanner(System.in);

        if(position.equals("Админстратор")){
            System.out.println("Enter the number of op");
            System.out.println("Search ");
            System.out.println("Add");
            System.out.println("Delete");
            System.out.println(" ");

            int option = scann.nextInt();

        }
        else{
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");


        }
    }
    public static void performingOperations

    }



}

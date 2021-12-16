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
                    dbcon.closeConnections();

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

                    assert position != null : "Вы не занимаете никакую должность";
                    usersPosition(Id,name,position);

                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sie haben schon alle Chancen benutzt!");

    }


    public static void usersPosition(int id,  String name,String position) throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        if(position.equals("Админстратор")){
            Admin administrator = new Admin(id,name);
//            administrator.getMenu();
        }
        else if(position.equals("Кассир")){
            Cashier cashier = new Cashier(id,name);
            System.out.printf("Добро пожаловать , %s!",cashier.getName());
            cashier.getMenu();

        }
    }

}

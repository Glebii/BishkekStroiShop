//package com.company;
//
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class Statistics {
//    public static void getMaxQuantityOfMaterials() throws SQLException, IOException, ClassNotFoundException {
//        DBConnector.getConnectionToDB();
//        Statement statement = DBConnector.statement;
//        ResultSet res = statement.executeQuery("select max(q),name \n" +
//                "from(SELECT sum(Quantity) as q , Name,sum(Total),Date from transactions GROUP BY Name ORDER BY Quantity desc) as custom");
//        res.first();
//        String name = res.getString("name");
//        String q = res.getString("max(q)");
//        System.out.println(name+" "+q);
//    }
//    public  static void getMinQuantityOfMaterials() throws SQLException, IOException, ClassNotFoundException {
//        DBConnector.getConnectionToDB();
//        Statement statement = DBConnector.statement;
//        ResultSet res = statement.executeQuery("select min(q),name \n" +
//                "from(SELECT sum(Quantity) as q , Name,Total,Date from transactions GROUP BY Name ORDER BY Quantity asc) as custom");
//        res.first();
//        String name = res.getString("name");
//        String q = res.getString("min(q)");
//        System.out.println(name+" "+q);
//    }
//    public static void getMaxTotalOfMaterials() throws SQLException, IOException, ClassNotFoundException {
//        DBConnector.getConnectionToDB();
//        Statement statement = DBConnector.statement;
//        ResultSet res = statement.executeQuery("SELECT sum(Total) as t , Name,sum(Quantity),Date from transactions GROUP BY Name ORDER BY t desc");
//        res.first();
//        String name = res.getString("name");
//        String total = res.getString("t");
//        System.out.println(name+" "+total);
//
//    }
//}

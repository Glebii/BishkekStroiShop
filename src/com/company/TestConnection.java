//package com.company;
//
//
//import java.io.IOException;
//import java.sql.*;
//import java.time.ZonedDateTime;
//import java.util.*;
//import static java.sql.ResultSet.*;
//
//
//public class TestConnection {
//    public static void Transactions() throws SQLException, IOException, ClassNotFoundException {
//        DBConnector.getConnectionToDB();
//        Scanner in = new Scanner(System.in);
//        Statement statement = DBConnector.statement;
//        ResultSet res = statement.executeQuery("SELECT Brand FROM materials");
//        res.beforeFirst();
//        TreeSet<String> BrandsS = new TreeSet<>();
//        while (res.next()) {
//            String brand = res.getString("Brand");
//            BrandsS.add(brand);
//        }
//        HashMap<Integer, String> Brand = new HashMap<>();
//        int i = 1;
//        for (String element :
//                BrandsS) {
//            Brand.put(i, element);
//            i = ++i;
//        }
//        for (Map.Entry entry : Brand.entrySet()) {
//            System.out.println(entry.getKey() + ")" + " "
//                    + entry.getValue());
//        }
//        System.out.print("\nВыберите бренд строительного материала для продажи , введя соответственный номер: ");
//        int choose = in.nextInt();
//        System.out.println();
//        String neededBrand = Brand.get(choose);
//        String sql = String.format("SELECT * FROM materials WHERE Brand = \'%s\'", neededBrand);
//        ResultSet res2 = statement.executeQuery(sql);
//        res2.beforeFirst();
//        HashMap<Integer, List<Object>> Materials = new HashMap<>();
//        int k = 1;
//        while (res2.next()) {
//            String name = res2.getString("Name");
//            String description = res2.getString("Description");
//            String qty = res2.getString("Quantity");
//            String price = res2.getString("Price");
//            String[] arr = {name, description, qty, price};
//            Materials.put(k, Arrays.asList(arr));
//            k = ++k;
//        }
//        for (Map.Entry entry : Materials.entrySet()) {
//            int c = (int) entry.getKey();
//            List<Object> m = Materials.get(c);
//            System.out.println("#" + c);
//            System.out.println("Name : " + m.get(0));
//            System.out.println("Description : " + m.get(1));
//            System.out.println("Quantity : " + m.get(2));
//            System.out.println("Price : " + m.get(3));
//            System.out.println();
//        }
//        System.out.print("Выберите материал для покупки,введя назначенный номер: ");
//        int BM = in.nextInt();
//        String neededMaterial = (String) Materials.get(BM).get(0);
//        System.out.println("Сколько штук вам нужно : ");
//        String searchSql = String.format("SELECT Quantity , Price FROM materials WHERE Brand = \'%s\' and Name = \'%s\'", neededBrand, neededMaterial);
//        ResultSet res3 = statement.executeQuery(searchSql);
//        res3.first();
//        int buyerQuantity = in.nextInt();
//        int q = res3.getInt("Quantity");
//        int p = res3.getInt("Price");
//        int newValForDB;
//        if (buyerQuantity <= q && buyerQuantity>0) {
//            int sum = 0;
//            newValForDB = q - buyerQuantity;
//            System.out.println("Отлично!");
//            sum += p * buyerQuantity;
//            statement.executeUpdate(String.format("UPDATE materials SET Quantity = %d WHERE Brand = \'%s\' and Name = \'%s\' ", newValForDB, neededBrand, neededMaterial));
//            System.out.printf("Ваш заказ :\n%s\nК оплате : %d\n\nВведите сумму которая у вас есть:", neededMaterial, sum);
//            int onHand = in.nextInt();
//            if ((onHand - sum) > 0) {
//                System.out.println("Спасибо за покупку! Ваша сдача: " + (onHand - sum));
//                String date = getTime();
//                String transaction = String.format("INSERT INTO finalbishkekstroi.transactions (Name,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)",neededMaterial,date,buyerQuantity,sum);
//                statement.executeUpdate(transaction);
//            }
//            else if ((onHand - sum) < 0) {
//                while ((onHand - sum) < 0) {
//                    System.out.println("К сожалению вам не хватает " + Math.abs(onHand - sum) + " добавьте немного денюшек");
//                    int add = in.nextInt();
//                    onHand += add;
//                }
//                System.out.println("Cпасибо за покупку! Ваша сдача: " + (onHand - sum));
//                String date = getTime();
//                String transaction = String.format("INSERT INTO finalbishkekstroi.transactions (Name,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)",neededMaterial,date,buyerQuantity,sum);
//                statement.executeUpdate(transaction);
//
//            } else {
//                System.out.println("Спасибо за покупку! Сдачи нет");
//                String date = getTime();
//                String transaction = String.format("INSERT INTO finalbishkekstroi.transactions (Name,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)", neededMaterial, date, buyerQuantity, sum);
//                statement.executeUpdate(transaction);
//            }
//
//        } else if (buyerQuantity>q){
//            int difference = Math.abs(buyerQuantity - q);
//            System.out.printf("К сожалению у нас нет в наличии %d единиц(ы) данного материала\nмы выдадим все что у нас есть в наличии", difference);
//            int sum = 0;
//            sum += p * q;
//            System.out.printf("Ваш заказ :\n%s\nК оплате : %d\n\nВведите сумму которая у вас есть:", neededMaterial, sum);
//            int onHand = in.nextInt();
//            if ((onHand - sum) > 0) {
//                System.out.println("Спасибо за покупку! Ваша сдача: " + (onHand - sum));
//                statement.executeUpdate(String.format("UPDATE materials SET Quantity = 0 WHERE Brand = \'%s\' and Name = \'%s\' ", neededBrand, neededMaterial));
//                String date = getTime();
//                String transaction = String.format("INSERT INTO finalbishkekstroi.transactions (Name,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)",neededMaterial,date,q,sum);
//                statement.executeUpdate(transaction);
//            }
//            else if ((onHand - sum) < 0) {
//                while ((onHand - sum) < 0) {
//                    System.out.println("К сожалению вам не хватает " + Math.abs(onHand - sum) + " добавьте немного денюшек");
//                    int add = in.nextInt();
//                    onHand += add;
//                }
//                System.out.println("Cпасибо за покупку! Ваша сдача: " + (onHand - sum));
//                statement.executeUpdate(String.format("UPDATE materials SET Quantity = 0 WHERE Brand = \'%s\' and Name = \'%s\' ", neededBrand, neededMaterial));
//                String date = getTime();
//                String transaction = String.format("INSERT INTO finalbishkekstroi.transactions (Name,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)",neededMaterial,date,q,sum);
//                statement.executeUpdate(transaction);
//
//            } else {
//                System.out.println("Спасибо за покупку! Сдачи нет");
//                String date = getTime();
//                String transaction = String.format("INSERT INTO finalbishkekstroi.transactions (Name,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)", neededMaterial, date, q, sum);
//                statement.executeUpdate(transaction);
//            }
//
//        }
//        statement.close();
//        DBConnector.connection.close();
//    }
//    public static String getTime(){
//        String YMD = String.join(
//                "-",
//                String.valueOf(ZonedDateTime.now().getYear()),
//                String.valueOf(ZonedDateTime.now().getMonthValue()),
//                String.valueOf(ZonedDateTime.now().getDayOfMonth())
//        );
//        String HMS = String.join(
//                ":",
//                String.valueOf(ZonedDateTime.now().getHour()),
//                String.valueOf(ZonedDateTime.now().getMinute()),
//                String.valueOf(ZonedDateTime.now().getSecond())
//        );
//        return (YMD +" "+HMS);
//    }
//
//
//}
//
//

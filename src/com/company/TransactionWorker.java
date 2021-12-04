package com.company;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.*;

    abstract class TransactionWorker extends MaterialWorker{
    private long allSumsTogether;
    private long fullQuantity;
    List<Transaction> LT = new ArrayList<>();
    Map<Integer,Integer> soldMaterials = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    private class Transaction {
        private int transactionId;
        private String nameOfMaterial;
        private String dateOfTransaction;
        private int quantityOfSoldMaterial;
        private int totalSumOfPurchase;

        Transaction(int tid, String matName, String date, int quantityOfSoldMaterial, int total) {
            this.transactionId = tid;
            this.nameOfMaterial = matName;
            this.dateOfTransaction = date;
            this.quantityOfSoldMaterial = quantityOfSoldMaterial;
            this.totalSumOfPurchase = total;
        }

        //Geters
        public int getTransactionId() {
            return this.transactionId;
        }

        public String getNameOfMaterial() {
            return this.nameOfMaterial;
        }

        public String getDateOfTransaction() {
            return this.dateOfTransaction;
        }

        public int getQuantityOfSoldMaterial() {
            return this.quantityOfSoldMaterial;
        }

        public int getTotalSumOfPurchase() {
            return this.totalSumOfPurchase;
        }
    }

    private void createTransaction(int tid, String matName, String date, int quantityOfSoldMaterial, int total) {
        Transaction transaction = new Transaction(tid, matName, date, quantityOfSoldMaterial, total);
        LT.add(transaction);
    }

    protected void createAllTransactions() throws SQLException, IOException, ClassNotFoundException {
        try {
            dbcon.getConnectionToDB();
            final String sqlForCreatingExistingTransactions = "SELECT * FROM `transactions`";
            ResultSet res = dbcon.statement.executeQuery(sqlForCreatingExistingTransactions);
            res.beforeFirst();
            while (res.next()) {
                int tid = res.getInt("idtransaction");
                String matName = res.getString("Info");
                Date date = res.getDate("Date");
                int quantityOfSoldMaterial = res.getInt("Quantity");
                int total = res.getInt("Total");
                createTransaction(tid, matName, date.toString(), quantityOfSoldMaterial, total);
            }
            dbcon.closeConnections();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllTransactions() {
        for (Transaction tr : LT) {
            System.out.println("Transaction ID: " + tr.getTransactionId());
            System.out.println("Sold material: " + tr.getNameOfMaterial());
            System.out.println("Date of purchase: " + tr.getDateOfTransaction());
            System.out.println("Quantity of sold material: " + tr.getQuantityOfSoldMaterial());
            System.out.println("Total purchase amount: " + tr.getTotalSumOfPurchase());
            System.out.println("====================================================");
        }
    }

    public void makeASale() throws SQLException, IOException, ClassNotFoundException {

        List<MaterialWorker.Material> requiredMaterials = new ArrayList<>(searchMaterialByBrand());
        System.out.print("Выберите материал для покупки,введя его ID номер: ");
        int idOfRequiredMaterial = sc.nextInt();
        MaterialWorker.Material neededMaterial = searchMaterialByIdInList(idOfRequiredMaterial, requiredMaterials);
        System.out.println("Какое количество единиц данного материала вам нужно: ");
        int buyerQuantity = sc.nextInt();
        if (buyerQuantity > neededMaterial.getQuantity()) {
            System.out.println(
                    "К сожалению у нас нет такого количества материалов которое вам нужно\n" +
                            "Если вы хотите купить доступное количество материалов нажмите кнопку: 1\n" +
                            "Если хотите отменить покупку введите: 2 "
            );
            int customerChoice = sc.nextInt();
            switch (customerChoice) {
                case 1:
                    dbcon.getConnectionToDB();
                    int totalSumForOneMaterial = neededMaterial.getPrice() * neededMaterial.getQuantity();
                    soldMaterials.put(neededMaterial.getId(),neededMaterial.getQuantity());
                    final String sqlFUMAbsolute = String.format("UPDATE materials SET Quantity=0 WHERE idmaterial=%d",neededMaterial.getId());
                    PreparedStatement quantityUpdater0 = dbcon.connection.prepareStatement(sqlFUMAbsolute);
                    quantityUpdater0.executeUpdate(sqlFUMAbsolute);
                    fullQuantity+=neededMaterial.getQuantity();
                    allSumsTogether += totalSumForOneMaterial;
                    neededMaterial.setQuantity(0);
                    System.out.println("Вы хотите продолжить покупки (1),провести оплату (2),увидеть вашу корзину(3),отменить покупку(4)?");
                    int customerChoiceToExitOrContinue = sc.nextInt();
                    postoperativeBehavior(customerChoiceToExitOrContinue);
                    dbcon.closeConnections();
                    break;
                case 2:
                    System.out.println("Exit!!");
                    break;
            }
        } else if (buyerQuantity > 0 && buyerQuantity <= neededMaterial.getQuantity()) {
            dbcon.getConnectionToDB();
            int totalSumForOneMaterial = 0;
            int newQuantityForMaterial = neededMaterial.getQuantity() - buyerQuantity;
            totalSumForOneMaterial += neededMaterial.getPrice() * buyerQuantity;
            soldMaterials.put(neededMaterial.getId(),buyerQuantity);
            final String sqlFUM= String.format("UPDATE materials SET Quantity=%d WHERE idmaterial=%d",newQuantityForMaterial,neededMaterial.getId());
            PreparedStatement quantityUpdater0 = dbcon.connection.prepareStatement(sqlFUM);
            quantityUpdater0.executeUpdate(sqlFUM);
            fullQuantity+=buyerQuantity;
            allSumsTogether += totalSumForOneMaterial;
            neededMaterial.setQuantity(newQuantityForMaterial);
            System.out.println("Вы хотите продолжить покупки (1),провести оплату (2),увидеть вашу корзину(3),отменить покупку(4)?");
            int customerChoiceToExitOrContinue = sc.nextInt();
            postoperativeBehavior(customerChoiceToExitOrContinue);
            dbcon.closeConnections();
        } else if (buyerQuantity < 0) {
            System.out.println("Введите значение больше 0");
            makeASale();
        }

    }

    public void postoperativeBehavior(int secondChoice) throws SQLException, IOException, ClassNotFoundException {
        if (secondChoice == 1) {
            makeASale();
        }
        else if (secondChoice == 2) {
            System.out.println("Ваш заказ:");
            for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
                MaterialWorker.Material m = searchMatById(e.getKey());
                System.out.printf("ID:%d\nName:%s\nDescription:%s\nPrice:%d\nQuantity for sale:%d\nThe amount for this material:%d\n\n",m.getId(),m.getName(),m.getDescription(),m.getPrice(),e.getValue(),e.getValue()*m.getPrice());
            }
            System.out.printf("К оплате: %d\nВведите вносимую сумму:", allSumsTogether);
            int money = sc.nextInt();
            payment(money);
        }
        else if (secondChoice == 3) {
            for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
                MaterialWorker.Material m = searchMatById(e.getKey());
                System.out.printf("ID:%d\nName:%s\nDescription:%s\nPrice:%d\nQuantity for sale:%d\nThe amount for this material:%d\n\n",m.getId(),m.getName(),m.getDescription(),m.getPrice(),e.getValue(),e.getValue()*m.getPrice());
            }
            System.out.printf("В вашей корзине %d материала(-ов)\nОбщая сумма:%d", soldMaterials.size(), allSumsTogether);
            System.out.println("\nВы хотите продолжить покупки (1),провести оплату (2),увидеть вашу корзину(3),отменить покупку(4)?");
            int c = sc.nextInt();
            postoperativeBehavior(c);
        } else if (secondChoice == 4) {
            System.out.println("Exit!!!");
            rollingBackChanges();
            allSumsTogether=0;
            fullQuantity=0;
            soldMaterials.clear();
        }
    }
    public void payment(int money) throws SQLException, IOException, ClassNotFoundException {
        if(money<0){
            System.out.println("Введено некорректное значение суммы,проведите повторный ввод!");
            postoperativeBehavior(3);
        }
        else if(money>0 && money<allSumsTogether){
            while (money<allSumsTogether){
                System.out.printf("Внесенных средств недостаточно для оплаты. Недостоющая сумма:%d\n",allSumsTogether-money);
                System.out.println("Введите размер доплаты: ");
                int dop = sc.nextInt();
                money+=dop;
                payment(money);
            }
        }
        else if(money>=allSumsTogether){


            String date = getTime();
            String info = "";
            for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
                MaterialWorker.Material m = searchMatById(e.getKey());
                info = info.concat(String.format("Id:%d Name:%s Quantity of sold:%d Price:%d\n",m.getId(),m.getName(),e.getValue(),m.getPrice()));
            }
            dbcon.getConnectionToDB();
            final String transaction = String.format("INSERT INTO transactions (Info,Date,Quantity,Total) VALUES (\'%s\',\'%s\',%d,%d)",info,date,fullQuantity,allSumsTogether);
            PreparedStatement transactionConducter = dbcon.connection.prepareStatement(transaction);
            transactionConducter.executeUpdate(transaction);
            dbcon.closeConnections();
            System.out.printf("Отлично транзакция проведена! Ваша сдача: %d\n",money-allSumsTogether);
            allSumsTogether=0;
            fullQuantity=0;
            soldMaterials.clear();


        }
    }
    public void rollingBackChanges() throws SQLException, IOException, ClassNotFoundException {
        for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
            MaterialWorker.Material m = searchMatById(e.getKey());
            dbcon.getConnectionToDB();
            final String sqlForRollback =String.format("UPDATE materials SET Quantity=%d WHERE idmaterial=%d",m.getQuantity()+e.getValue(),e.getKey());
            PreparedStatement roller = dbcon.connection.prepareStatement(sqlForRollback);
            roller.executeUpdate(sqlForRollback);
            dbcon.closeConnections();
            m.setQuantity(m.getQuantity()+e.getValue());
        }
    }
        public String getTime(){
        String YMD = String.join(
                "-",
                String.valueOf(ZonedDateTime.now().getYear()),
                String.valueOf(ZonedDateTime.now().getMonthValue()),
                String.valueOf(ZonedDateTime.now().getDayOfMonth())
        );
        String HMS = String.join(
                ":",
                String.valueOf(ZonedDateTime.now().getHour()),
                String.valueOf(ZonedDateTime.now().getMinute()),
                String.valueOf(ZonedDateTime.now().getSecond())
        );
        return (YMD +" "+HMS);
    }
}






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


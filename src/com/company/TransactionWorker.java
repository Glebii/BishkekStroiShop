package com.company;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        private int userId;



        Transaction(int tid, String matName, String date, int quantityOfSoldMaterial, int total, int usid) {
            this.transactionId = tid;
            this.nameOfMaterial = matName;
            this.dateOfTransaction = date;
            this.quantityOfSoldMaterial = quantityOfSoldMaterial;
            this.totalSumOfPurchase = total;
            this.userId=usid;
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

        public int getUserId() {
            return userId;
        }
    }

    private void createTransaction(int tid, String matName, String date, int quantityOfSoldMaterial, int total,int usid) {
        Transaction transaction = new Transaction(tid, matName, date, quantityOfSoldMaterial, total,usid);
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
                int usid = res.getInt("CashierIDTransaction");
                createTransaction(tid, matName, date.toString(), quantityOfSoldMaterial, total,usid);
            }
            dbcon.closeConnections();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllTransactions() {
        for (Transaction tr : LT) {
            System.out.println("Transaction ID: " + tr.getTransactionId());
            System.out.println();
            System.out.println("Sold materials:\n" + tr.getNameOfMaterial());
            System.out.println("Date of purchase: " + tr.getDateOfTransaction());
            System.out.println("Quantity of sold material: " + tr.getQuantityOfSoldMaterial());
            System.out.println("Total purchase amount: " + tr.getTotalSumOfPurchase());
            System.out.println("Employee Id: "+tr.getUserId());
            System.out.println("====================================================");
        }
    }

    protected void makeASale(User user) throws SQLException, IOException, ClassNotFoundException {

        List<MaterialWorker.Material> requiredMaterials = new ArrayList<>(searchMaterialByBrandForMakeASale());
        System.out.print("Выберите материал для покупки,введя его ID номер: ");
        int idOfRequiredMaterial = sc.nextInt();
        if(searchMaterialByIdInListForMakeASale(idOfRequiredMaterial, requiredMaterials)==null){
            System.out.println("В списке материалов для данного бренда нет выбранного вами материала.\nПожалуйста выберите бренд повторно и укажите материал относящийся к нему.");
            requiredMaterials.clear();
            makeASale(user);
            return;
        }
        MaterialWorker.Material neededMaterial = searchMaterialByIdInListForMakeASale(idOfRequiredMaterial, requiredMaterials);
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
                    if(soldMaterials.containsKey(neededMaterial.getId())){
                        System.out.println("Мы выдали все что было в наличии");
                    }
                    else soldMaterials.put(neededMaterial.getId(),neededMaterial.getQuantity());
                    final String sqlFUMAbsolute = String.format("UPDATE materials SET Quantity=0 WHERE idmaterial=%d",neededMaterial.getId());
                    PreparedStatement quantityUpdater0 = dbcon.connection.prepareStatement(sqlFUMAbsolute);
                    quantityUpdater0.executeUpdate(sqlFUMAbsolute);
                    fullQuantity+=neededMaterial.getQuantity();
                    allSumsTogether += totalSumForOneMaterial;
                    neededMaterial.setQuantity(0);
                    System.out.println("Вы хотите продолжить покупки (1),провести оплату (2),увидеть вашу корзину(3),отменить покупку(4)?");
                    int customerChoiceToExitOrContinue = sc.nextInt();
                    postoperativeBehavior(customerChoiceToExitOrContinue,user);
                    dbcon.closeConnections();
                    break;
                case 2:
                    System.out.println("Exit!!");
                    rollingBackChanges();
                    break;
            }
        } else if (buyerQuantity > 0 && buyerQuantity <= neededMaterial.getQuantity()) {
            dbcon.getConnectionToDB();
            int totalSumForOneMaterial = 0;
            int newQuantityForMaterial = neededMaterial.getQuantity() - buyerQuantity;
            totalSumForOneMaterial += neededMaterial.getPrice() * buyerQuantity;
            if(soldMaterials.containsKey(neededMaterial.getId())){
                soldMaterials.put(neededMaterial.getId(),soldMaterials.get(neededMaterial.getId())+buyerQuantity);
            }else soldMaterials.put(neededMaterial.getId(),buyerQuantity);
            final String sqlFUM= String.format("UPDATE materials SET Quantity=%d WHERE idmaterial=%d",newQuantityForMaterial,neededMaterial.getId());
            PreparedStatement quantityUpdater0 = dbcon.connection.prepareStatement(sqlFUM);
            quantityUpdater0.executeUpdate(sqlFUM);
            fullQuantity+=buyerQuantity;
            allSumsTogether += totalSumForOneMaterial;
            neededMaterial.setQuantity(newQuantityForMaterial);
            System.out.println("Вы хотите продолжить покупки (1),провести оплату (2),увидеть вашу корзину(3),отменить покупку(4)?");
            int customerChoiceToExitOrContinue = sc.nextInt();
            postoperativeBehavior(customerChoiceToExitOrContinue,user);
            dbcon.closeConnections();
        } else if (buyerQuantity <= 0) {
            System.out.println("Введите значение больше 0");
            makeASale(user);
        }

    }

    private void postoperativeBehavior(int secondChoice,User user) throws SQLException, IOException, ClassNotFoundException {
        if (secondChoice == 1) {
            makeASale(user);
        }
        else if (secondChoice == 2) {
            System.out.println("Ваш заказ:");
            for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
                MaterialWorker.Material m = searchMatByIdForMakeASale(e.getKey());
                System.out.printf("ID:%d\nName:%s\nDescription:%s\nPrice:%d\nQuantity for sale:%d\nThe amount for this material:%d\n\n",m.getId(),m.getName(),m.getDescription(),m.getPrice(),e.getValue(),e.getValue()*m.getPrice());
            }
            System.out.printf("К оплате: %d\nВведите вносимую сумму:", allSumsTogether);
            int money = sc.nextInt();
            payment(money,user);
        }
        else if (secondChoice == 3) {
            for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
                MaterialWorker.Material m = searchMatByIdForMakeASale(e.getKey());
                System.out.printf("ID:%d\nName:%s\nDescription:%s\nPrice:%d\nQuantity for sale:%d\nThe amount for this material:%d\n\n",m.getId(),m.getName(),m.getDescription(),m.getPrice(),e.getValue(),e.getValue()*m.getPrice());
            }
            System.out.printf("В вашей корзине %d материала(-ов)\nОбщая сумма:%d", soldMaterials.size(), allSumsTogether);
            System.out.println("\nВы хотите продолжить покупки (1),провести оплату (2),увидеть вашу корзину(3),отменить покупку(4)?");
            int c = sc.nextInt();
            postoperativeBehavior(c,user);
        } else if (secondChoice == 4) {
            System.out.println("Exit!!!");
            rollingBackChanges();
            allSumsTogether=0;
            fullQuantity=0;
            soldMaterials.clear();
        }
    }
    private void payment(int money, User user) throws SQLException, IOException, ClassNotFoundException {
        try {
            if (money < 0) {
                System.out.println("Введено некорректное значение суммы,проведите повторный ввод!");
                postoperativeBehavior(3, user);
            } else if (money > 0 && money < allSumsTogether) {
                while (money < allSumsTogether) {
                    System.out.printf("Внесенных средств недостаточно для оплаты. Недостоющая сумма:%d\n", allSumsTogether - money);
                    System.out.println("Введите размер доплаты: ");
                    int dop = sc.nextInt();
                    money += dop;
                    payment(money, user);
                }
            } else if (money >= allSumsTogether) {


                String date = getTime();
                String info = "";
                for (Map.Entry<Integer, Integer> e : soldMaterials.entrySet()) {
                    MaterialWorker.Material m = searchMatByIdForMakeASale(e.getKey());
                    info = info.concat(String.format("Id:%d Quantity of sold:%d Name:%s Price:%d\n", m.getId(), e.getValue() ,m.getName(), m.getPrice()));
                }
                dbcon.getConnectionToDB();
                final String transaction = String.format("INSERT INTO transactions (Info,Date,Quantity,Total,CashierIDTransaction) VALUES (\'%s\',\'%s\',%d,%d,%d)", info, date, fullQuantity, allSumsTogether, user.getId());
                PreparedStatement transactionConducter = dbcon.connection.prepareStatement(transaction);
                transactionConducter.executeUpdate();
                dbcon.closeConnections();
                dbcon.getConnectionToDB();
                dbcon.statement.executeUpdate("DELETE FROM `materials` WHERE Quantity<=0 and idmaterial NOT IN (SELECT materials_idmaterial FROM materials_has_suppliers);");
                dbcon.closeConnections();
                System.out.printf("Отлично транзакция проведена! Ваша сдача: %d\n", money - allSumsTogether);
                allSumsTogether = 0;
                fullQuantity = 0;
                soldMaterials.clear();
                return;


            }
        } catch (SQLException exception) {
            rollingBackChanges();
            exception.printStackTrace();
        }

    }
    private void rollingBackChanges() throws SQLException, IOException, ClassNotFoundException {
        for (Map.Entry<Integer,Integer> e: soldMaterials.entrySet()) {
            MaterialWorker.Material m = searchMatByIdForMakeASale(e.getKey());
            dbcon.getConnectionToDB();
            final String sqlForRollback =String.format("UPDATE materials SET Quantity=%d WHERE idmaterial=%d",m.getQuantity()+e.getValue(),e.getKey());
            PreparedStatement roller = dbcon.connection.prepareStatement(sqlForRollback);
            roller.executeUpdate(sqlForRollback);
            dbcon.closeConnections();
            m.setQuantity(m.getQuantity()+e.getValue());
        }
    }
    protected String getTime(){
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
    private Transaction getTransactionById(int id){
        Transaction requiredTransaction = null;
        for (Transaction tr:
             LT) {
            if(tr.getTransactionId()==id){
                requiredTransaction=tr;
            }
        }
        return requiredTransaction;
    }
    protected void rollbackTransaction() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Введите Id транзакции: ");
        int trId = sc.nextInt();
        Transaction requiredTransaction = getTransactionById(trId);
        String transactionBody = requiredTransaction.getNameOfMaterial();
        String[] words = transactionBody.split("\n");
        HashMap<Integer,Integer>materialsForRollback = new HashMap<>();
        for (String w:
             words) {
            Pattern pattern = Pattern.compile("(\\d{1,4})");
            Matcher m = pattern.matcher(w);
            Integer key = 0;
            Integer value = 0;
            for (int i=0;i<2;i++){
                if(m.find()) {
                    switch (i){
                        case 0:
                            key=Integer.valueOf(m.group());
                            break;
                        case 1:
                            value=Integer.valueOf(m.group());
                            break;
                    }

                }
            }
            materialsForRollback.put(key,value);
        }
        for (Map.Entry<Integer,Integer> entry:
                materialsForRollback.entrySet()
        )
        {
            MaterialWorker.Material m = searchMatByIdForMakeASale(entry.getKey());
            dbcon.getConnectionToDB();
            final String sqlForRollback =String.format("UPDATE materials SET Quantity=%d WHERE idmaterial=%d",m.getQuantity()+entry.getValue(),entry.getKey());
            PreparedStatement roller = dbcon.connection.prepareStatement(sqlForRollback);
            roller.executeUpdate();
            dbcon.closeConnections();
            m.setQuantity(m.getQuantity()+entry.getValue());

        }
        dbcon.getConnectionToDB();
        String sqlForRemovingTransaction = String.format("DELETE FROM `transactions` WHERE idtransaction=%d",trId);
        dbcon.statement.executeUpdate(sqlForRemovingTransaction);
        dbcon.closeConnections();
        LT.remove(getTransactionById(trId));
        getAllTransactions();
    }
}



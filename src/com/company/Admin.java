
package com.company;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Predicate;

public class Admin extends Cashier{
    Admin(int dbId, String dbName) throws SQLException, IOException, ClassNotFoundException {
        super(dbId, dbName);
    }

    @Override
    public void getMenu() throws InterruptedException, SQLException, IOException, ClassNotFoundException {
        System.out.println("Чего желаете?\n" +
                "Материалы"+
                "1)  Увидеть все материалы\n" +
                "2)  Посмотреть материал по Id\n" +
                "3)  Найти материалы соответсвующие по бренду\n" +
                "4)  Удалить материал\n" +
                "5)  Пополнить количество материалов\n" +
                "6)  Изменить параметры материала\n" +
                "7)  Добавить новый материал\n" +
                "Поставщики"+
                "8)  Увидеть всех поставщиков\n" +
                "9)  Посмотреть данные поставщика по Id\n"+
                "10) Добавить нового поставщика\n"+
                "11) Изменить параметры поставщика\n"+
                "12) Удалить поставщика\n"+
                "Продажи"+
                "13) Сделать продажу\n" +
                "14) Увидеть все продажи\n"+
                "15) Завершить работу\n"+
                "Введите номер операции:");

        Scanner sc = new Scanner(System.in);
        int operationNumber = sc.nextInt();
        switch (operationNumber) {
            case 1:
                getAllMaterials();
                Thread.sleep(2000);
                getMenu();
                break;
            case 2:
                searchMatById();
                Thread.sleep(2000);
                getMenu();
                break;
            case 3:
                searchMaterialByBrand();
                Thread.sleep(2000);
                getMenu();
                break;
            case 4:
                deleteMaterial();
                Thread.sleep(2000);
                getMenu();
                break;
            case 5:
                materialsRefillUpdate();
                Thread.sleep(2000);
                getMenu();
                break;
            case 6:
                updateMaterials();
                Thread.sleep(2000);
                getMenu();
                break;
            case 7:
                createTheNewMaterial();
                Thread.sleep(2000);
                getMenu();
            case 8:
                getAllSuppliers();
                Thread.sleep(2000);
                getMenu();
                break;
            case 9:
                getSupByID();
                Thread.sleep(2000);
                getMenu();
                break;
            case 10:
                supplierAdd();
                Thread.sleep(2000);
                getMenu();
                break;
            case 11:
                updateSupplier();
                Thread.sleep(2000);
                getMenu();
                break;
            case 12:
                supplierDelete();
                Thread.sleep(2000);
                getMenu();
                break;
            case 13:
                makeASale();
                Thread.sleep(2000);
                getMenu();
                break;
            case 14:
                getAllTransactions();
                Thread.sleep(2000);
                getMenu();
                break;
            case 15:
                System.out.printf("Отличная работа ,%s", getName());
                break;
            default:
                System.out.println("Номер операции введен не верно, попробуйте снова!");
                getMenu();
                break;


        }
    }

    //Add new user
    protected void addNewUser() throws SQLException, IOException, ClassNotFoundException {
        System.out.println(" fname:");
        String fname = sc.nextLine();
        System.out.println(" lname:");
        String lname = sc.nextLine();
        System.out.println(" login:");
        String login = sc.nextLine();
        System.out.println(" password:");
        String password = sc.nextLine();
        System.out.println(" position: Администратор или Кассир");
        String position = sc.nextLine();

        dbcon.getConnectionToDB();
        String SQL = String.format("Insert INTO users Values(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",fname,lname,password,login,position);

        PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(SQL);
        supplierCreatorDB.executeUpdate();
        System.out.println("Done...");
        dbcon.closeConnections();

    }
    //Transaction's methods


    //Supplier's methods
    protected void supplierAdd() throws  SQLException {

        try{
            dbcon.getConnectionToDB();
            Scanner in = new Scanner(System.in);
            System.out.println("Now you will need to enter the required information for new suplier");
            System.out.println("\nEnter name of supplier: ");
            String names = in.nextLine();
            System.out.println("\nEnter surname of supplier: ");
            String surname = in.nextLine();
            System.out.println("\nEnter phone of supplier: ");
            String phone = in.nextLine();
            System.out.println("Enter address of supplier: ");
            String adress = in.nextLine();
            int ids = getLS().size() +1;
            //        int ids = LS.size() +1;


            createSupplier(ids,names,surname,phone,adress);

            String SQL = String.format("INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
                    ids, names, surname, phone, adress);

            PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(SQL);
            supplierCreatorDB.executeUpdate(SQL);
            System.out.println("Done...");
            dbcon.closeConnections();
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();}
        dbcon.closeConnections();
    }

    protected void supplierDelete() throws SQLException, IOException, ClassNotFoundException {

        try
        { dbcon.getConnectionToDB();
            Scanner scann = new Scanner(System.in);
            System.out.println("Enter id to delete supplier: ");
            int suplirsId_to_delete = scann.nextInt();
            Iterator<Supplier> supplierIterator= getLS().iterator();
            while(supplierIterator.hasNext()) {
                Supplier nextSup = supplierIterator.next();
                if (nextSup.getSupplierId() == suplirsId_to_delete) {
                    supplierIterator.remove();
                }
            }

            String sql = String.format("DELETE FROM suppliers WHERE idsupplier = %d;", suplirsId_to_delete);
            int rows = dbcon.statement.executeUpdate(sql);
            System.out.println("Getting record...");
            System.out.printf("%d rows deleted ", rows);
            dbcon.closeConnections();
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();}
        dbcon.closeConnections();
    }

    protected void updateSupplier() throws SQLException, IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        getAllSuppliers();
        dbcon.getConnectionToDB();
        System.out.print("\n Enter id of updating material: ");
        int id_of_updating_suplier = in.nextInt();
        String space = in.nextLine();
        System.out.print(
                "\n Enter the column name to update\n"  +
                        "1)  SupplierName;\n" +
                        "2)  Surname;\n" +
                        "3)  Phone;\n" +
                        "4)  Adress; :  "
        );
        String colName_for_updating = in.nextLine();
        System.out.print("\n Enter new value: ");
        String nValue_String = in.nextLine();

        if (
                colName_for_updating.equals("SupplierName") || colName_for_updating.equals("Surname") || colName_for_updating.equals("Phone") || colName_for_updating.equals("Adress")
        ){

            for (SuppliersWorker.Supplier s : getLS()){
                if (s.getSupplierId() == id_of_updating_suplier) {
                    if(colName_for_updating.equals("SupplierName")){ s.setSupplierName(nValue_String); }
                    else if(colName_for_updating.equals("Surname")){ s.setSupplierSurname(nValue_String); }
                    else if(colName_for_updating.equals("Phone")){ s.setSupplierPhone(nValue_String); }
                    else if(colName_for_updating.equals("Adress")){ s.setSupplierAdress(nValue_String); }
                }
            }
            System.out.println("Updating successful done...");
        }
        else
        {
            System.out.println("The column not found! Please, try again!");
            updateSupplier();
        }

        try
        {
            String sql = String.format("UPDATE suppliers  SET %s = \'%s\' WHERE idsupplier = %d", colName_for_updating, nValue_String, id_of_updating_suplier);
            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate(sql);
            System.out.println("Changes were written successfully!");
            System.out.printf("%d rows added", rows);
            preparedStatement.close();
        }
        catch (Exception ex)
        {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        dbcon.connection.close();

    }


    //Material's methods
    public void createTheNewMaterial() throws ClassNotFoundException, SQLException, IOException {
        try {
            dbcon.getConnectionToDB();
            System.out.println("Now you will need to enter the required information for new material");
            System.out.println("Enter new ID: ");
            int idm = sc.nextInt();
            System.out.println("\nEnter Name: ");
            String name = sc.next();
            System.out.println();
            System.out.println("Enter Brand: ");
            String brand = sc.next();
            System.out.println("Enter Description: ");
            String space = sc.nextLine();
            String descr = sc.nextLine();
            System.out.println("\nEnter Quantity: ");
            int quantity = sc.nextInt();
            System.out.println("Enter Price: ");
            int price = sc.nextInt();
            System.out.println("Now, please enter the information for the supplier of added material");
            System.out.println("Do you want to add a new supplier or choose the existing one?");
            System.out.println("1-Add new supplier for this material");
            System.out.println("2-Choose the existing supplier");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Now you will need to enter the required information for new supplier");
                    int SIDs = getLS().size() + 1;
                    System.out.println("\nEnter Supplier Name: ");
                    String nameOfNewSupplier = sc.next();
                    System.out.println("\nEnter Surname: ");
                    String surnameOfNewSupplier = sc.next();
                    System.out.println("Enter Phone: ");
                    String phoneOfNewSupplier = sc.next();
                    System.out.println("Enter Address: ");
                    String spaceV2 = sc.nextLine();
                    String addressOfNewSupplier = sc.nextLine();
                    createSupplier(SIDs, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier);
                    createMaterial(idm, name, brand, descr, quantity, price, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier, SIDs);
                    final String sqlForCreatingNewSupplier = String.format(
                            "INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
                            SIDs, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier
                    );
                    PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(sqlForCreatingNewSupplier);
                    supplierCreatorDB.executeUpdate(sqlForCreatingNewSupplier);
                    final String sqlBasedOnCNM = String.format(
                            sqlForCreatingNewMaterial,
                            idm, name, brand, descr, quantity, price
                    );
                    PreparedStatement materialCreatorDB = dbcon.connection.prepareStatement(sqlBasedOnCNM);
                    materialCreatorDB.executeUpdate();
                    final String sqlBasedOnMCBSM = String.format(
                            sqlForMakingConnectionBetweenSupAndMat,
                            idm, SIDs
                    );
                    PreparedStatement materialWithSupplierConnector = dbcon.connection.prepareStatement(sqlBasedOnMCBSM);
                    materialWithSupplierConnector.executeUpdate();
                    dbcon.closeConnections();
                    System.out.println("==================Materials========================");
                    getAllMaterials();
                    System.out.println("===============Sups===============================");
                    getAllSuppliers();
                    break;
                case 2:
                    getAllSuppliers();
                    System.out.println("Choose the existing supplier ID for the new material: ");
                    int choosenID = sc.nextInt();
                    if (choosenID <= getLS().size() && choosenID > 0) {
                        createMaterial(
                                idm,
                                name,
                                brand,
                                descr,
                                quantity,
                                price,
                                getSupByID(choosenID).getSupplierName(),
                                getSupByID(choosenID).getSupplierSurname(),
                                getSupByID(choosenID).getSupplierPhone(),
                                getSupByID(choosenID).getSupplierAdress(),
                                getSupByID(choosenID).getSupplierId()
                        );
                        final String sqlBasedOnCNMES = String.format(
                                sqlForCreatingNewMaterial,
                                idm, name, brand, descr, quantity, price
                        );
                        PreparedStatement materialCDB = dbcon.connection.prepareStatement(sqlBasedOnCNMES);
                        materialCDB.executeUpdate();
                        final String sqlBasedOnMCBESM = String.format(
                                sqlForMakingConnectionBetweenSupAndMat,
                                idm, choosenID
                        );
                        PreparedStatement materialWSC = dbcon.connection.prepareStatement(sqlBasedOnMCBESM);
                        materialWSC.executeUpdate();
                        dbcon.closeConnections();
                        System.out.println("==================Materials========================");
                        getAllMaterials();
                        System.out.println("===============Sups===============================");
                        getAllSuppliers();
                    }
                    break;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void materialsRefillUpdate() throws SQLException, IOException, ClassNotFoundException {
        dbcon.getConnectionToDB();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter material's name which you want to refill: ");
        String name_of_object_for_refill = sc.nextLine();
        System.out.print("\n Enter the quantity to refill: ");
        int quantity_for_refill = sc.nextInt();
        for (Material m : getLM())
        {
            if(m.getName().equals((name_of_object_for_refill)))
            {
                m.setQuantity(m.getQuantity()+quantity_for_refill);
            }
        }
        try {
            String sql = String.format("UPDATE materials SET Quantity=Quantity+%d WHERE Name=\'%s\';", quantity_for_refill, name_of_object_for_refill);
            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate(sql);
            System.out.println("Materials were refilled successfully!");
            System.out.printf("%d rows updated", rows);
            preparedStatement.close();
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        dbcon.closeConnections();
    }

    public void deleteMaterial() throws SQLException {
        try
        {
            dbcon.getConnectionToDB();
            System.out.println("Enter name to delete material: ");
            String name_of_object = sc.nextLine();

            Predicate<Material> deleteMaterial = (Material x) -> x.getName().equals(name_of_object);
            getLM().removeIf(deleteMaterial);
            //проверку нужно сделать

            String sql = String.format("DELETE FROM materials WHERE Name=\'%s\';", name_of_object);
            int rows = dbcon.statement.executeUpdate(sql);
            System.out.println("Getting record...");
            System.out.printf("%d rows deleted ", rows);
            getAllMaterials();
            dbcon.closeConnections();
        }

        catch (Exception ex)
        {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        dbcon.closeConnections();
    }

    public  void updateMaterials() throws SQLException, IOException, ClassNotFoundException {
        dbcon.getConnectionToDB();
        String sql= "";
        Scanner sc = new Scanner(System.in);

        System.out.print("\n Enter name of updating material: ");
        String Name_of_updating_material = sc.nextLine();

        System.out.print("\n " +
                "1)  Name;\n" +
                "2)  Brand;\n" +
                "3)  Description;\n" +
                "4)  Quantity;\n" +
                "5)  Price;\n" +
                "Enter the column name to update:  ");
        String colName_for_updating = sc.nextLine();

        if (colName_for_updating.equals("Name") || colName_for_updating.equals("Brand") || colName_for_updating.equals("Description")) {
            System.out.print("\n Enter new value: ");
            String nValue_String = sc.nextLine();

            for (Material m : getLM())
            {
                if (m.getName().equals(Name_of_updating_material)) {
                    if(colName_for_updating.equals("Name")) {
                        m.setName(nValue_String);
                    }
                    if(colName_for_updating.equals("Brand")){
                        m.setBrand(nValue_String);
                    }
                    else if(colName_for_updating.equals("Description")){
                        m.setDescription(nValue_String);
                    }
                }
                else{ System.out.println("Материал не найден!"); }
            }
            sql = String.format("UPDATE materials  SET %s=\'%s\' WHERE Name =\'%s\' ;", colName_for_updating, nValue_String, Name_of_updating_material);


        }
        else if (colName_for_updating.equals("Quantity") || colName_for_updating.equals("Price")) {
            int nValue_int_quantity = sc.nextInt();
            for (Material m : getLM()) {
                if (m.getName().equals((Name_of_updating_material))) {
                    m.setQuantity(nValue_int_quantity);
                }
                else if(colName_for_updating.equals("Quantity")){
                    m.setQuantity(nValue_int_quantity);
                }
                else { m.setPrice(nValue_int_quantity);}
            }
            sql = String.format("UPDATE materials  SET %s =%d WHERE Name =\'%s\';", colName_for_updating, nValue_int_quantity, Name_of_updating_material);

        }
        else { System.out.println("Column name not found! Please, try again!"); }

        try {
            System.out.println("sql: "+sql);
            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate(sql);
            System.out.println("Changes were written successfully!");
            System.out.printf("%d rows added", rows);
            preparedStatement.close();
            System.out.println("good");

        }
        catch (Exception ex) { System.out.println("Connection failed..."); ex.printStackTrace(); }

        dbcon.closeConnections();

    }

}


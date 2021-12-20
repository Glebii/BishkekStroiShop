
package com.company;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class Admin extends Cashier{
    Admin(int dbId, String dbName) throws SQLException, IOException, ClassNotFoundException {
        super(dbId, dbName);
    }

    @Override
    public void getMenu() throws InterruptedException, SQLException, IOException, ClassNotFoundException {
        System.out.println("Wählen Sie eine Option nach Nummer aus:\n" +
                        "Werkstoffe\n"+
                        "1) Alle Materialien sehen\n" +
                        "2) Material nach Id anzeigen\n" +
                        " 3) Finden Sie relevante Materialien nach Marke\n" +
                        "4) Material entfernen\n" +
                        "5) Materialmenge nachfüllen\n" +
                        "6) Materialeinstellungen ändern\n" +
                        "7) Neues Material hinzufügen\n" +
                        "Lieferanten"+
                        "8) Alle Anbieter anzeigen\n" +
                        "9) Lieferantendaten nach Id anzeigen\n"+
                        "10) Neuen Anbieter hinzufügen\n"+
                        "11) Kreditoreneinstellungen ändern\n"+
                        "12) Anbieter entfernen\n"+
                        "Verkäufe"+
                        "13) Verkauf tätigen\n" +
                        "14) Alle Verkäufe sehen\n"+
                        "15) Fügt einen neuen Benutzer hinzu\n"+
                        "16) Herunterfahren\n"+
                        "Geben Sie die Operationsnummer ein:");

//                "Материалы"+
//                "1)  Увидеть все материалы\n" +
//                "2)  Посмотреть материал по Id\n" +
//                "3)  Найти материалы соответсвующие по бренду\n" +
//                "4)  Удалить материал\n" +
//                "5)  Пополнить количество материалов\n" +
//                "6)  Изменить параметры материала\n" +
//                "7)  Добавить новый материал\n" +
//                "Поставщики"+
//                "8)  Увидеть всех поставщиков\n" +
//                "9)  Посмотреть данные поставщика по Id\n"+
//                "10) Добавить нового поставщика\n"+
//                "11) Изменить параметры поставщика\n"+
//                "12) Удалить поставщика\n"+
//                "Продажи"+
//                "13) Сделать продажу\n" +
//                "14) Увидеть все продажи\n"+
//                "15) Добавит нового пользователя\n"+
//                "16) Завершить работу\n"+

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
                makeASale(this);
                Thread.sleep(2000);
                getMenu();
                break;
            case 14:
                getAllTransactions();
                Thread.sleep(2000);
                getMenu();
                break;
            case 15:
                addNewUser();
                Thread.sleep(2000);
                getMenu();
                break;
            case 16:
                System.out.printf("Gute Arbeit ,%s", getName());
                return;
            default:
                System.out.println("Die eingegebene Optionsnummer ist falsch, versuchen Sie es erneut!");
                getMenu();
                break;
        }
    }

    //Add new user
    protected void addNewUser() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("So erstellen Sie einen neuen Benutzer:");
        System.out.println("Geben Sie Ihren Voramen ein: ");
        String fname = sc.nextLine();
        System.out.println("Geben Sie Ihren Nachnamen ein: ");
        String lname = sc.nextLine();
        System.out.println("Login eingeben: ");
        String login = sc.nextLine();
        System.out.println(" Möchten Sie ein Passwort generieren? \n1-Ja 2-Nein");
        int ansToGenerateNewPass = sc.nextInt();
        String password = null;
        switch (ansToGenerateNewPass){
            case 1:
                password = new Random().ints(10, 33, 122)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
                System.out.println(password);
                sc.nextLine();
                break;
            case 2:
                boolean creatingPassProcess = true;

                sc.nextLine();

                while(creatingPassProcess){
                    System.out.println("Das Passwort muss aus mindestens 8 Zeichen bestehen und Groß- und Kleinbuchstaben und Ziffern enthalten, darf aber aus Sicherheitsgründen keinen Vor- oder Nachnamen enthalten!");
                    System.out.println("Geben Sie Ihr Passwort ein:");
                    password = sc.nextLine();
                    boolean hasNumber = password.matches(".*\\d+.*");
                    boolean hasUppercase = !password.equals(password.toLowerCase());
                    boolean hasLowercase = !password.equals(password.toUpperCase());
                    boolean hasName = !(password.contains(fname) || password.contains(lname));
                    boolean hasEightSymbol = (password.length() >= 8);

                    if( hasUppercase && hasNumber && hasLowercase && hasName && hasEightSymbol){
                        System.out.println("Ihr Passwort ist nicht sicher, versuchen Sie es erneut!");
                        creatingPassProcess = false;
                    }
                }
                break;
            default:
                System.out.println("Die Operation ist falsch, versuchen Sie es erneut!");
                addNewUser();
        }

        System.out.println(" Position eingeben (Administrator/Kassierer): ");
        String position = sc.nextLine();

        dbcon.getConnectionToDB();
        String SQL = String.format("Insert INTO users(FName,LName,Login,Password_,Position) Values(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",fname,lname,password,login,position);

        PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(SQL);
        supplierCreatorDB.executeUpdate();
        System.out.println("Der Benutzer wurde erfolgreich hinzugefügt!");
        dbcon.closeConnections();
    }

    //Transaction's methods


    //Supplier's methods
    protected void supplierAdd() throws  SQLException {

        try{
            dbcon.getConnectionToDB();
            Scanner in = new Scanner(System.in);
            System.out.println("Jetzt müssen Sie die erforderlichen Informationen für den neuen Lieferanten eingeben.");
            System.out.println("\nGeben Sie den Namen des Lieferanten ein: ");
            String names = in.nextLine();
            System.out.println("\nGeben Sie den Nachname des Lieferanten ein: ");
            String surname = in.nextLine();
            System.out.println("\nGeben Sie die Telefonnummer des Lieferanten ein: ");
            String phone = in.nextLine();
            System.out.println("\nGeben Sie die Adresse des Lieferanten ein: ");
            String adress = in.nextLine();
            int ids = getLS().size() +2;



            createSupplier(ids,names,surname,phone,adress);

            String SQL = String.format("INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",ids, names, surname, phone, adress);

            PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(SQL);
            supplierCreatorDB.executeUpdate(SQL);
            System.out.println("Der neue Anbieter wurde erfolgreich hinzugefügt!");
            dbcon.closeConnections();
        }
        catch (Exception ex) {
            System.out.println("Verbindung fehlgeschlagen...");
            ex.printStackTrace();}
        dbcon.closeConnections();
    }

    protected void supplierDelete() throws SQLException, IOException, ClassNotFoundException {

        try
        { dbcon.getConnectionToDB();
            Scanner scann = new Scanner(System.in);
            System.out.println("geben Sie den ID ein, um Lieferant zu löschen:");
            int suplirsId_to_delete = scann.nextInt();
            Iterator<Supplier> supplierIterator= getLS().iterator();
            while(supplierIterator.hasNext()) {
                Supplier nextSup = supplierIterator.next();
                if (nextSup.getSupplierId() == suplirsId_to_delete) {
                    supplierIterator.remove();
                }
            }

            String sql = String.format("DELETE FROM suppliers WHERE idsupplier = %d;", suplirsId_to_delete);
            dbcon.statement.executeUpdate(sql);
            dbcon.closeConnections();
            dbcon.getConnectionToDB();
            dbcon.statement.executeUpdate("DELETE FROM `materials` WHERE Quantity<=0 and idmaterial NOT IN (SELECT materials_idmaterial FROM materials_has_suppliers);");
            dbcon.closeConnections();
            System.out.println("Der Anbieter wurde erfolgreich entfernt!");
        }
        catch (Exception ex) {
            System.out.println("Verbindung fehlgeschlagen...");
            ex.printStackTrace();}
        dbcon.closeConnections();
    }

    protected void updateSupplier() throws SQLException, IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        getAllSuppliers();
        dbcon.getConnectionToDB();
        System.out.print("\n Geben Sie die ID des Aktualisierungsmaterials ein:");
        int id_of_updating_suplier = in.nextInt();
        String space = in.nextLine();
        System.out.print(
                "\n Geben Sie den zu aktualisierenden Spaltennamen ein\n"  +
                        "Der Name des Lieferanten - SupplierName;\n" +
                        "Der Nachname -  Surname;\n" +
                        "Die Telefonnummer - Phone;\n" +
                        "Die Adresse - Adress :  "
        );
        String colName_for_updating = in.nextLine();
        System.out.print("\nGeben Sie einen neuen Wert ein: ");
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
            System.out.println("Aktualisierung erfolgreich abgeschlossen...");
        }
        else
        {
            System.out.println("Seite nicht gefunden! Bitte versuchen Sie es erneut!");
            updateSupplier();
        }

        try
        {
            String sql = String.format("UPDATE suppliers  SET %s = \'%s\' WHERE idsupplier = %d", colName_for_updating, nValue_String, id_of_updating_suplier);
            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate(sql);
            System.out.println("Änderungen wurden erfolgreich geschrieben!");
            preparedStatement.close();
        }
        catch (Exception ex)
        {
            System.out.println("Verbindung fehlgeschlagen...");
            ex.printStackTrace();
        }
        dbcon.connection.close();

    }


    //Material's methods
    public void createTheNewMaterial() throws ClassNotFoundException, SQLException, IOException {
        try {
            dbcon.getConnectionToDB();
            System.out.println("Jetzt müssen Sie die erforderlichen Informationen für neues Material eingeben.");
            System.out.println("ID: ");
            int idm = sc.nextInt();
            System.out.println("\nName: ");
            String name = sc.next();
            System.out.println();
            System.out.println("\nBrand: ");
            String brand = sc.next();
            System.out.println("\nBeschreibung eingeben: ");
            String space = sc.nextLine();
            String descr = sc.nextLine();
            System.out.println("\nMenge eingeben: ");
            int quantity = sc.nextInt();
            System.out.println("\nPreis eingeben: ");
            int price = sc.nextInt();
            System.out.println("Geben Sie nun bitte die Informationen für den Lieferanten des hinzugefügten Materials ein.");
            System.out.println("Möchten Sie einen neuen Lieferanten hinzufügen oder den bestehenden auswählen?");
            System.out.println("1-Neuen Lieferanten für dieses Material hinzufügen");
            System.out.println("2-Wählen Sie den bestehenden Lieferanten");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Jetzt müssen Sie die erforderlichen Informationen für den neuen Lieferanten eingeben.");
                    int SIDs = getLS().size() + 1;
                    System.out.println("\nGeben Sie den Namen des Lieferanten ein:  ");
                    String nameOfNewSupplier = sc.next();
                    System.out.println("\nGeben Sie den Nachname des Lieferanten ein:  ");
                    String surnameOfNewSupplier = sc.next();
                    System.out.println("\nGeben Sie die Telefonnummer des Lieferanten ein: ");
                    String phoneOfNewSupplier = sc.next();
                    System.out.println("\nGeben Sie die Adresse des Lieferanten ein:  ");
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
                    System.out.println("==================Werkstoffe========================");
                    getAllMaterials();
                    System.out.println("===============Mitarbeiter===============================");
                    getAllSuppliers();
                    break;
                case 2:
                    getAllSuppliers();
                    System.out.println("Wählen Sie die vorhandene Lieferanten-ID für das neue Material:");
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
                        System.out.println("==================Werkstoffe========================");
                        getAllMaterials();
                        System.out.println("===============Mitarbieter===============================");
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
        System.out.print("Geben Sie den Namen des Materials ein, das Sie nachfüllen möchten:");
        String name_of_object_for_refill = sc.nextLine();
        System.out.print("\n Geben Sie die Menge zum Nachfüllen ein: ");
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
            preparedStatement.executeUpdate();
            System.out.println("Materialien wurden erfolgreich nachgefüllt!");
            preparedStatement.close();
        } catch (Exception ex) {
            System.out.println("Verbindung fehlgeschlagen...");
            ex.printStackTrace();
        }
        dbcon.closeConnections();
    }

    public void deleteMaterial() throws SQLException {
        try
        {
            dbcon.getConnectionToDB();
            System.out.println("Geben Sie einen Namen ein, um Material zu löschen:");
            String name_of_object = sc.nextLine();

            Predicate<Material> deleteMaterial = (Material x) -> x.getName().equals(name_of_object);
            getLM().removeIf(deleteMaterial);
            //проверку нужно сделать

            String sql = String.format("DELETE FROM materials WHERE Name=\'%s\';", name_of_object);
            dbcon.statement.executeUpdate(sql);
            System.out.println("Das Material wurde entfernt!");
            getAllMaterials();
            dbcon.closeConnections();
        }

        catch (Exception ex)
        {
            System.out.println("Verbindung fehlgeschlagen...");
            ex.printStackTrace();
        }
        dbcon.closeConnections();
    }

    public  void updateMaterials() throws SQLException, IOException, ClassNotFoundException {
        dbcon.getConnectionToDB();
        String sql= "";
        Scanner sc = new Scanner(System.in);

        System.out.print("\n Geben Sie den Namen des Aktualisierungsmaterials ein:");
        String Name_of_updating_material = sc.nextLine();

        System.out.print("\n" +
                        "1) Name\n" +
                        "2) Marke\n" +
                        "3) Beschreibung\n" +
                        "4) Menge\n" +
                        "5) Preis\n" +
                        "Geben Sie den Spaltennamen ein, der aktualisiert werden soll: ");
        String colName_for_updating = sc.nextLine();

        if (colName_for_updating.equals("Name") || colName_for_updating.equals("Marke") || colName_for_updating.equals("Beschreibung")) {
            System.out.print("\n Neuen Wert eingeben: ");
            String nValue_String = sc.nextLine();

            for (Material m : getLM())
            {
                if (m.getName().equals(Name_of_updating_material)) {
                    if(colName_for_updating.equals("Name")) {
                        m.setName(nValue_String);
                    }
                    if(colName_for_updating.equals("Marke")){
                        m.setBrand(nValue_String);
                    }
                    else if(colName_for_updating.equals("Beschreibung")){
                        m.setDescription(nValue_String);
                    }
                }
                else{ System.out.println("Kein Material gefunden!"); }
            }
            sql = String.format("UPDATE materials  SET %s=\'%s\' WHERE Name =\'%s\' ;", colName_for_updating, nValue_String, Name_of_updating_material);


        }
        else if (colName_for_updating.equals("Menge") || colName_for_updating.equals("Preis")) {
            int nValue_int_quantity = sc.nextInt();
            for (Material m : getLM()) {
                if (m.getName().equals((Name_of_updating_material))) {
                    m.setQuantity(nValue_int_quantity);
                }
                else if(colName_for_updating.equals("Menge")){
                    m.setQuantity(nValue_int_quantity);
                }
                else { m.setPrice(nValue_int_quantity);}
            }
            sql = String.format("UPDATE materials  SET %s =%d WHERE Name =\'%s\';", colName_for_updating, nValue_int_quantity, Name_of_updating_material);

        }
        else { System.out.println("Seite nicht gefunden! Bitte versuchen Sie es erneut!"); }

        try {
            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate(sql);
            System.out.println("Änderungen wurden erfolgreich geschrieben!");
            preparedStatement.close();

        }
        catch (Exception ex) { System.out.println("Verbindung fehlgeschlagen..."); ex.printStackTrace(); }

        dbcon.closeConnections();

    }

}


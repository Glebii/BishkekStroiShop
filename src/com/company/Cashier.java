package com.company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Cashier extends User implements MenuShower {

    Cashier(int dbId, String dbName) throws SQLException, IOException, ClassNotFoundException {
        super(dbId, dbName);
    }

    @Override
    public void getMenu() throws InterruptedException, SQLException, IOException, ClassNotFoundException {
        System.out.println("Was wünschen Sie sich?\n" +
                "1) Alle Materialien anzeigen\n" +
                "2) Material nach Id anzeigen\n" +
                "3) Material nach Marke anzeigen\n" +
                "4) Alle Lieferanten anzeigen\n" +
                "5) Lieferantendaten nach ID anzeigen\n" +
                "6) Verkauf tätigen\n" +
                "7) Alle Verkäufe anzeigen\n" +
                "8) Arbeit beenden\n" +
                "Geben Sie die Operationsnummer ein:");
        Scanner sc = new Scanner(System.in);
        int operationNumber = sc.nextInt();
        switch (operationNumber){
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
                getAllSuppliers();
                Thread.sleep(2000);
                getMenu();
                break;
            case 5:
                getSupByID();
                Thread.sleep(2000);
                getMenu();
                break;
            case 6:
                makeASale(this);
                Thread.sleep(2000);
                getMenu();
                break;
            case 7:
                getAllTransactions();
                Thread.sleep(2000);
                getMenu();
            case 8:
                System.out.printf("Gute Arbeit ,%s !",getName());
            default:
                System.out.println("Die Operationsnummer ist falsch eingegeben,versuchen Sie es erneut!");
                getMenu();
                break;

        }
    }
}

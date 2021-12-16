package com.company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Cashier extends TransactionWorker implements MenuShower {
    private final int Id;
    private final String Name;
    public String getName() {
        return this.Name;
    }



    Cashier(int dbId , String dbName) throws SQLException, IOException, ClassNotFoundException {
        this.Id = dbId;
        this.Name=dbName;

        createAllTransactions();
        createAllMaterials();
        createAllSuppliers();
    }


    @Override
    public void getMenu() throws InterruptedException, SQLException, IOException, ClassNotFoundException {
        System.out.println("Чего желаете?\n1) Увидеть все материалы\n2) Посмотреть материал по Id\n3) Посмотреть материал по бренду\n4) Увидеть всех поставщиков\n5) Посмотреть данные поставщика по Id\n6) Сделать продажу\n7) Увидеть все продажи\n8) Завершить работу\nВведите номер операции:");
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
                makeASale();
                Thread.sleep(2000);
                getMenu();
                break;
            case 7:
                getAllTransactions();
                Thread.sleep(2000);
                getMenu();
            case 8:
                System.out.printf("Отличная работа ,%s",getName());
            default:
                System.out.println("Номер операции введен не верно, попробуйте снова!");
                getMenu();
                break;

        }
    }
}

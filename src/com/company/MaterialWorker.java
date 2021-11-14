package com.company;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MaterialWorker {
    List<Material> LM = new ArrayList();
    DBConnector dbcon = new DBConnector();
    SuppliersWorker sup = new SuppliersWorker();

    MaterialWorker() throws SQLException, IOException, ClassNotFoundException {
        sup.createAllSuppliers();
        createAllMaterials();
    }

    protected class Material {
        private int id;
        private String name;
        private String brand;
        private String description;
        private int quantity;
        private int price;
        private String supliers_name;
        private String supliers_surname;
        private String supliers_phone;
        private String supliers_adress;
        private int supIdEqualToMatID;

        Material(
                int id,
                String name,
                String brand,
                String desc,
                int quantity,
                int price,
                String supname,
                String supsur,
                String supphone,
                String supadress,
                int supId
        ) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.description = desc;
            this.quantity = quantity;
            this.price = price;
            this.supliers_name = supname;
            this.supliers_surname = supsur;
            this.supliers_phone = supphone;
            this.supliers_adress = supadress;
            this.supIdEqualToMatID = supId;
        }
        //Geters
        public void getInfo() {
            System.out.println("ID: "+this.id);
            System.out.println("Name of material: "+ this.name);
            System.out.println("Brand of Material: "+ this.brand);
            System.out.println("Description of Material: "+ this.description);
            System.out.println("Quantity of Material: "+ this.quantity);
            System.out.println("Price of Material: "+this.price);
            System.out.println("Supplier :"+this.supliers_name);
            System.out.println("Suppliers surname: "+this.supliers_surname);
            System.out.println("Suppliers phone: "+this.supliers_phone);
            System.out.println("Suppliers address: "+this.supliers_adress);
            System.out.println("==========================================");
        }
        public int getId() {
            return this.id;
        }
        public String getName() {
            return this.name;
        }
        public String getBrand() {
            return this.brand;
        }
        public String getDescription() {
            return this.description;
        }
        public int getQuantity() {
            return this.quantity;
        }
        public int getPrice() {
            return this.price;
        }
        public String getSupliersName() {
            return this.supliers_name;
        }
        public String getSupliersSurname() {
            return this.supliers_surname;
        }
        public String getSupliersPhone() {
            return this.supliers_phone;
        }
        public String getSupliersAdress() {
            return this.supliers_adress;
        }
        public int getSupIdEqualToMatID() {
            return this.supIdEqualToMatID;
        }
        //Seters
        public  void setName(String name){
            this.name=name;
        }
        public void setQuantity(int quantity){
            this.quantity = quantity;
        }
        public void setBrand(String brand) {
            this.brand = brand;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setPrice(int price) {
            this.price = price;
        }
        public void setSupliers_name(String supliers_name) {
            this.supliers_name = supliers_name;
        }
        public void setSupliers_surname(String supliers_surname) {
            this.supliers_surname = supliers_surname;
        }
        public void setSupliers_phone(String supliers_phone) {
            this.supliers_phone = supliers_phone;
        }
        public void setSupliers_adress(String supliers_adress) {
            this.supliers_adress = supliers_adress;
        }

    }
    Scanner sc = new Scanner(System.in);
    public void createMaterial(int Mid, String Mname, String Mbrand, String Mdesc, int Mquan, int Mprice, String Msupn, String Msups, String Msupp, String Msupa, int supId) {
        Material material = new Material(Mid, Mname, Mbrand, Mdesc, Mquan, Mprice, Msupn, Msups, Msupp, Msupa, supId);
        LM.add(material);
    }
    public void createAllMaterials() throws SQLException, IOException, ClassNotFoundException {
        //All materials
        try {

            dbcon.getConnectionToDB();
            final String SQL = "Select materials.idmaterial,materials.Name , materials.Brand, materials.Description, materials.Quantity, materials.Price,\n" +
                    "suppliers.SupplierName, suppliers.Surname, suppliers.Phone, suppliers.Adress," +
                    "materials_has_suppliers.materials_idmaterial ,materials_has_suppliers.suppliers_idsupplier\n" +
                    "from materials left join materials_has_suppliers\n" +
                    "on materials.idmaterial = materials_has_suppliers.materials_idmaterial\n" +
                    "left join suppliers on materials_has_suppliers.suppliers_idsupplier = suppliers.idsupplier;";
            ResultSet res = dbcon.statement.executeQuery(SQL);
            System.out.println("Getting record...");
            res.beforeFirst();
            while (res.next()) {
                int id = res.getInt("idmaterial");
                String name = res.getString("Name");
                String brand = res.getString("Brand");
                String descript = res.getString("Description");
                int quantity = res.getInt("Quantity");
                int price = res.getInt("Price");
                String supname = res.getString("SupplierName");
                String supsurname = res.getString("Surname");
                String phone = res.getString("Phone");
                String adress = res.getString("Adress");
                int supIdInMHs = res.getInt("suppliers_idsupplier");
                createMaterial(id, name, brand, descript, quantity, price, supname, supsurname, phone, adress, supIdInMHs);
            }
            dbcon.closeConnections();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void getAllMaterials() {
        for (Material m : LM) {
            System.out.println("ID: " + m.getId());
            System.out.println("Name: " + m.getName());
            System.out.println("Brand: " + m.getBrand());
            System.out.println("Descript: " + m.getDescription());
            System.out.println("Quantity: " + m.getQuantity());
            System.out.println("Price: " + m.getPrice());
            System.out.println("Supliers name: " + m.getSupliersName());
            System.out.println("Supliers surname: " + m.getSupliersSurname());
            System.out.println("Supliers phone: " + m.getSupliersPhone());
            System.out.println("Supliers adress: " + m.getSupliersAdress());
            System.out.println("\n=================================\n");
        }
    }
    public void deleteMaterial() {
        System.out.println("Enter name to delete material: ");
        String name_of_object = sc.nextLine();
        for (Material m :
                LM) {
            if (m.getName().equals(name_of_object)) {
                LM.remove(m);
            }
        }
        getAllMaterials();
    }
//    public  void materialsDelete() throws SQLException, IOException, ClassNotFoundException {
//        dbcon.getConnectionToDB();
//        Scanner scann = new Scanner(System.in);
//        System.out.println("Enter name to delete material: ");
//        String namme = scann.nextLine();
//        String sql = String.format("DELETE FROM bishkekstroishop.materials WHERE name = \'%s\';", namme);
//        int rows = dbcon.statement.executeUpdate(sql);
//        System.out.println("Getting record...");
//        System.out.printf("%d rows deleted ", rows);
//        dbcon.closeConnections();

    //    }
    public void updateMaterials() throws SQLException, IOException, ClassNotFoundException{
        dbcon.getConnectionToDB();
        Scanner sc = new Scanner(System.in);
        System.out.print("\n Enter name of updating material: ");
        String name_of_updating_material = sc.nextLine();
        System.out.print("\n Enter the column name to update\n" +
                "1)  name;\n" +
                "2)  brand;\n" +
                "3)  description;\n" +
                "4)  quantity;\n" +
                "5)  price;\n" +
                "6)  supliers_name;\n" +
                "7)  supliers_surname;\n" +
                "8)  supliers_phone;\n" +
                "9) supliers_adress;:  ");
        String colName_for_updating = sc.nextLine();
        String space = sc.nextLine();
        if (
                colName_for_updating.equals("name") || colName_for_updating.equals("brand") || colName_for_updating.equals("description")
                || colName_for_updating.equals("supliers_name") || colName_for_updating.equals("supliers_surname") || colName_for_updating.equals("supliers_adress")
        ){
            System.out.print("\n Enter new value: ");
            String nValue_String = sc.nextLine();
            for (Material m : LM){
                if (m.getName().equals((name_of_updating_material))) {
                    if(colName_for_updating.equals("name")){
                        m.setName(nValue_String);
                    }
                    else if(colName_for_updating.equals("brand")){
                        m.setBrand(nValue_String);
                    }
                    else if(colName_for_updating.equals("description")){
                        m.setDescription(nValue_String);
                    }
                }
            }
        }
        else if
        (
                colName_for_updating.equals("quantity") || colName_for_updating.equals("price") || colName_for_updating.equals("supliers_phone")
        ){
            int nValue_int_quantity = sc.nextInt();
            for (Material m : LM){
                if (m.getName().equals((name_of_updating_material))){
                    m.setQuantity(nValue_int_quantity);
                }
                else if(colName_for_updating.equals("quantity")){
                    m.setQuantity(nValue_int_quantity);
                }
                else if(colName_for_updating.equals("price")){
                    m.setPrice(nValue_int_quantity);
                }
            }
        }
    }
    public void materialsRefillUpdate()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter material's name which you want to refill: ");
        String name_of_object_for_refill = sc.nextLine();
        System.out.print("\n Enter the quantity to refill: ");
        int quantity_for_refill = sc.nextInt();
        for (Material m : LM)
        {
            if(m.getName().equals((name_of_object_for_refill)))
            {
                m.setQuantity(m.getQuantity()+quantity_for_refill);

            }
        }
    }



    //    public  void updateMaterials() throws SQLException, IOException, ClassNotFoundException {
//        dbcon.getConnectionToDB();
//        Scanner in = new Scanner(System.in);
//        System.out.print("\n Enter the column name to update:  ");
//        String col = in.nextLine();
//        System.out.print("\n Enter id of updating material: ");
//        int idupdate = in.nextInt();
//        System.out.print("\n Enter new value: ");
//        int nValue = in.nextInt();
//        try {
//            String sql = String.format("UPDATE materials  SET %s = %d WHERE idmaterial = %d;", col, nValue, idupdate);
//            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("Changes were written successfully!");
//            System.out.printf("%d rows added", rows);
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();
//        }
//        dbcon.closeConnections();
//    }
//    public void materialsRefillUpdate() throws SQLException, IOException, ClassNotFoundException {
//        dbcon.getConnectionToDB();
//        Scanner in = new Scanner(System.in);
//        System.out.print("Enter material's ID which you want to refill: ");
//        int id = in.nextInt();
//        System.out.print("\n Enter the quantity to refill: ");
//        int quantity = in.nextInt();
//        try {
//            String sql = String.format("UPDATE materials SET Quantity=Quantity+ %s WHERE idmaterial=%s;", quantity, id);
//            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("Materials were refilled successfully!");
//            System.out.printf("%d rows updated", rows);
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();
//        }
//        dbcon.closeConnections();
//    }
    public void createTheNewMaterial() throws ClassNotFoundException, SQLException, IOException {
        try {
            dbcon.getConnectionToDB();
            System.out.println("Now you will need to enter the required information for new material");
            System.out.println("Enter new ID: ");
            int idm = sc.nextInt();
            System.out.println("\nEnter Name: ");
            ;
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
                    int SIDs = sup.LS.size() + 1;
                    System.out.println("\nEnter Supplier Name: ");
                    String nameOfNewSupplier = sc.next();
                    System.out.println("\nEnter Surname: ");
                    String surnameOfNewSupplier = sc.next();
                    System.out.println("Enter Phone: ");
                    String phoneOfNewSupplier = sc.next();
                    System.out.println("Enter Address: ");
                    String spaceV2 = sc.nextLine();
                    String addressOfNewSupplier = sc.nextLine();
                    sup.createSupplier(SIDs, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier);
                    createMaterial(idm, name, brand, descr, quantity, price, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier, SIDs);
                    final String sqlForCreatingNewSupplier = String.format(
                            "INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
                            SIDs, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier
                    );
                    PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(sqlForCreatingNewSupplier);
                    supplierCreatorDB.executeUpdate(sqlForCreatingNewSupplier);
                    final String sqlForCreatingNewMaterial = String.format(
                            "INSERT materials(idmaterial,Name, Brand, Description, Quantity, Price) VALUES (%d, \'%s\',\'%s\',\'%s\',%d,%d);",
                            idm, name, brand, descr, quantity, price
                    );
                    PreparedStatement materialCreatorDB = dbcon.connection.prepareStatement(sqlForCreatingNewMaterial);
                    materialCreatorDB.executeUpdate(sqlForCreatingNewMaterial);
                    final String sqlForMakingConnectionBetweenSupAndMat = String.format(
                            "INSERT materials_has_suppliers(materials_idmaterial, suppliers_idsupplier) VALUES (%d, %d);",
                            idm, SIDs
                    );
                    PreparedStatement materialWithSupplierConnector = dbcon.connection.prepareStatement(sqlForMakingConnectionBetweenSupAndMat);
                    materialWithSupplierConnector.executeUpdate(sqlForMakingConnectionBetweenSupAndMat);
                    dbcon.closeConnections();
                    System.out.println("==================MAterials========================");
                    getAllMaterials();
                    System.out.println("===============Sups===============================");
                    sup.getAllInfoAboutSuppliers();
                    break;
                case 2:
                    sup.getAllInfoAboutSuppliers();
                    System.out.println("Choose the existing supplier ID for the new material: ");
                    int choosenID = sc.nextInt();
                    if (choosenID <= sup.LS.size() && choosenID > 0) {
                        for (SuppliersWorker.Supplier s : sup.LS) {
                            if (s.getSupplierId() == choosenID) {

                            }
                        }
                    }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//    public void materialsRefillInsert() throws ClassNotFoundException, SQLException, IOException {
//        int idm = in.nextInt();

    //        try {
//            String sql = String.format("INSERT materials(idmaterial,Name, Brand, Description, Quantity, Price) VALUES (%d, \'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",
//                    idm, name, brand, descr, quantity, price);
//            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("New Material is added successfully!");
//            System.out.printf("%d rows updated", rows);
//            System.out.println("Your choice: ");
//            int choice = in.nextInt();
//            switch (choice) {
//                case 1:
//                    Scanner inn = new Scanner(System.in);
//                    System.out.println("Now you will need to enter the required information for new supplier");
//                    String sname = inn.nextLine();
//                    System.out.println("\nEnter Surname: ");
//                    String surn = inn.nextLine();
//                    System.out.println("Enter Phone: ");
//                    String phone = in.nextLine();
//                    System.out.println("Enter Address: ");
//                    String adrs = inn.nextLine();
//                    try {
//                        String sql1 = String.format("INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
//                                ids, sname, surn, phone, adrs);
//                        PreparedStatement preparedStatement1 = dbcon.connection.prepareStatement(sql1);
//                        int rows1 = preparedStatement1.executeUpdate(sql1);
//                        System.out.println("New supplier is added successfully!");
//                        System.out.println("Rows added: " + rows1);
//                        String sql2 = String.format("INSERT materials_has_suppliers(materials_idmaterial, suppliers_idsupplier) VALUES (%d, %d);",
//                                idm, ids);
//                        PreparedStatement preparedStatement2 = dbcon.connection.prepareStatement(sql2);
//                        int rows2 = preparedStatement2.executeUpdate(sql2);
//                        System.out.println("New relation is added successfully!");
//                        System.out.println("Rows added: " + rows2);
//                    } catch (Exception ex) {
//                        System.out.println("Connection failed...");
//                        ex.printStackTrace();
//                    }
//                    break;
//                case 2:
//                    try {
//                        ResultSet res2 = dbcon.statement.executeQuery("Select * from Suppliers");
//                        System.out.println("Getting record...");
//                        res2.beforeFirst();
//                        while (res2.next()) {
//                            int ids2 = res2.getInt("idsupplier");
//                            String supname2 = res2.getString("SupplierName");
//                            String supsurname2 = res2.getString("Surname");
//                            String phone2 = res2.getString("Phone");
//                            String adress2 = res2.getString("Adress");
//                            System.out.println(ids2);
//                            System.out.println("Supliers name: " + supname2);
//                            System.out.println("Supliers surname: " + supsurname2);
//                            System.out.println("Supliers phone: " + phone2);
//                            System.out.println("Supliers adress: " + adress2);
//                            System.out.println("\n=================================\n");
//                        }
//                        System.out.println("Choose the existing supplier ID for the new material: ");
//                        int ids3 = in.nextInt();
//                        String sql3 = String.format("INSERT materials_has_suppliers(materials_idmaterial, suppliers_idsupplier) VALUES (%d, %d);",
//                                idm, ids3);
//                        PreparedStatement preparedStatement2 = dbcon.connection.prepareStatement(sql3);
//                        int rows3 = preparedStatement2.executeUpdate(sql3);
//                        System.out.println("New relation is added successfully!");
//                        System.out.println("Rows added: " + rows3);
//                        break;
//                    } catch (Exception ex) {
//                        System.out.println("Connection failed...");
//                        ex.printStackTrace();
//                    }
//            }
//        }catch (Exception ex) {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();}
//        dbcon.closeConnections();
//    }
    public List<Material>searchMaterialByBrand() {
        Set<String> brandsSet = new HashSet<String>();
        HashMap<Integer, String> brandHash = new HashMap<>();
        for (int i = 0; i < LM.size(); i++) {
            brandsSet.add(LM.get(i).getBrand());
        }
        int counter = 0;
        for (String brand :
                brandsSet) {
            System.out.printf("%d) " + brand + "\n", counter += 1);
            brandHash.put(counter, brand);
        }
        System.out.print("\nВыберите бренд строительного материала соотвеветсвующего нужному вам материалу , введя соответственный номер: ");
        int choice = sc.nextInt();
        String brand = brandHash.get(choice);
        List<Material> materialsWithRequiredBrand = new ArrayList<>();
        for (Material m : LM) {
            if (m.getBrand().equals(brand)) {
                materialsWithRequiredBrand.add(m);
                m.getInfo();
            }
        }
        return materialsWithRequiredBrand;
    }
    public Material searchMaterialByIdInList(int id,List<Material> l){
        Material requiredMat = null;
        for(Material m:l){
            if(m.getId()==id){
                m.getInfo();
                requiredMat=m;
            }
        }
        return requiredMat;
    }
}    
//    public void materialSearch() throws SQLException, IOException, ClassNotFoundException {
//        dbcon.getConnectionToDB();
//        HashMap<Integer, String> Brand = new HashMap<>();
//        int i = 1;
//        Scanner in = new Scanner(System.in);
//        Statement statement = dbcon.statement;
//        ResultSet res = statement.executeQuery("SELECT DISTINCT Brand FROM materials ORDER BY Brand ASC");
//        res.beforeFirst();
//        while (res.next()){
//            Brand.put(i,res.getString("Brand"));
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
//    }


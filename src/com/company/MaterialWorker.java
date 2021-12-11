package com.company;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

abstract class MaterialWorker extends SuppliersWorker{
    private List<Material> LM = new ArrayList<>();
    final  String sqlForCreatingNewMaterial = "INSERT materials(idmaterial,Name, Brand, Description, Quantity, Price) VALUES (%d, \'%s\',\'%s\',\'%s\',%d,%d);";
    final  String sqlForMakingConnectionBetweenSupAndMat = "INSERT materials_has_suppliers(materials_idmaterial, suppliers_idsupplier) VALUES (%d, %d);";

    protected class Material {
        private int  id;
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
        //Setters
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


    }

    protected List<Material> getLM(){
        return this.LM;
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
            final String SQL = "Select materials.idmaterial, materials.Name , materials.Brand, materials.Description, materials.Quantity, materials.Price,\n" +
                    "suppliers.SupplierName, suppliers.Surname, suppliers.Phone, suppliers.Adress," +
                    "materials_has_suppliers.materials_idmaterial ,materials_has_suppliers.suppliers_idsupplier\n" +
                    "from materials left join materials_has_suppliers\n" +
                    "on materials.idmaterial = materials_has_suppliers.materials_idmaterial\n" +
                    "left join suppliers on materials_has_suppliers.suppliers_idsupplier = suppliers.idsupplier;";
            ResultSet res = dbcon.statement.executeQuery(SQL);
            res.beforeFirst();
            while (res.next()){
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
    //Admin's method
//    public void deleteMaterial() throws SQLException {
//        try
//        {
//            dbcon.getConnectionToDB();
//            System.out.println("Enter name to delete material: ");
//            String name_of_object = sc.nextLine();
//
//            Predicate<Material> deleteMaterial = (Material x) -> x.getName().equals(name_of_object);
//            LM.removeIf(deleteMaterial);
//
//            String sql = String.format("DELETE FROM materials WHERE Name=\'%s\';", name_of_object);
//            int rows = dbcon.statement.executeUpdate(sql);
//            System.out.println("Getting record...");
//            System.out.printf("%d rows deleted ", rows);
//            getAllMaterials();
//            dbcon.closeConnections();
//        }
//
//        catch (Exception ex)
//        {
//        System.out.println("Connection failed...");
//        ex.printStackTrace();
//        }
//        dbcon.closeConnections();
//    }
    //Admin's method
//    public  void updateMaterials() throws SQLException, IOException, ClassNotFoundException {
//        dbcon.getConnectionToDB();
//        String sql= "";
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("\n Enter name of updating material: ");
//        String Name_of_updating_material = sc.nextLine();
//
//        System.out.print("\n " +
//                "1)  Name;\n" +
//                "2)  Brand;\n" +
//                "3)  Description;\n" +
//                "4)  Quantity;\n" +
//                "5)  Price;\n" +
//                "Enter the column name to update:  ");
//        String colName_for_updating = sc.nextLine();
//
//        if (colName_for_updating.equals("Name") || colName_for_updating.equals("Brand") || colName_for_updating.equals("Description")) {
//            System.out.print("\n Enter new value: ");
//            String nValue_String = sc.nextLine();
//
//            for (Material m : LM)
//            {
//                if (m.getName().equals(Name_of_updating_material)) {
//                    if(colName_for_updating.equals("Name")) {
//                        m.setName(nValue_String);
//                    }
//                    if(colName_for_updating.equals("Brand")){
//                        m.setBrand(nValue_String);
//                    }
//                    else if(colName_for_updating.equals("Description")){
//                        m.setDescription(nValue_String);
//                    }
//                }
//                else{ System.out.println("Материал не найден!"); }
//            }
//            sql = String.format("UPDATE materials  SET %s=\'%s\' WHERE Name =\'%s\' ;", colName_for_updating, nValue_String, Name_of_updating_material);
//
//
//        }
//        else if (colName_for_updating.equals("Quantity") || colName_for_updating.equals("Price")) {
//            int nValue_int_quantity = sc.nextInt();
//            for (Material m : LM) {
//                if (m.getName().equals((Name_of_updating_material))) {
//                    m.setQuantity(nValue_int_quantity);
//                }
//                else if(colName_for_updating.equals("Quantity")){
//                    m.setQuantity(nValue_int_quantity);
//                }
//                else { m.setPrice(nValue_int_quantity);}
//            }
//            sql = String.format("UPDATE materials  SET %s =%d WHERE Name =\'%s\';", colName_for_updating, nValue_int_quantity, Name_of_updating_material);
//
//        }
//        else { System.out.println("Column name not found! Please, try again!"); }
//
//        try {
//            System.out.println("sql: "+sql);
//            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("Changes were written successfully!");
//            System.out.printf("%d rows added", rows);
//            preparedStatement.close();
//            System.out.println("good");
//
//        }
//        catch (Exception ex) { System.out.println("Connection failed..."); ex.printStackTrace(); }
//
//        dbcon.closeConnections();
//
//    }
    //Admin's method
//    public void materialsRefillUpdate() throws SQLException, IOException, ClassNotFoundException {
//        dbcon.getConnectionToDB();
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter material's name which you want to refill: ");
//        String name_of_object_for_refill = sc.nextLine();
//        System.out.print("\n Enter the quantity to refill: ");
//        int quantity_for_refill = sc.nextInt();
//        for (Material m : LM)
//        {
//            if(m.getName().equals((name_of_object_for_refill)))
//            {
//                m.setQuantity(m.getQuantity()+quantity_for_refill);
//            }
//        }
//        try {
//            String sql = String.format("UPDATE materials SET Quantity=Quantity+%d WHERE Name=\'%s\';", quantity_for_refill, name_of_object_for_refill);
//            PreparedStatement preparedStatement = dbcon.connection.prepareStatement(sql);
//            int rows = preparedStatement.executeUpdate(sql);
//            System.out.println("Materials were refilled successfully!");
//            System.out.printf("%d rows updated", rows);
//            preparedStatement.close();
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            ex.printStackTrace();
//        }
//        dbcon.closeConnections();
//    }
    //Admin's method
//    public void createTheNewMaterial() throws ClassNotFoundException, SQLException, IOException {
//        try {
//            dbcon.getConnectionToDB();
//            System.out.println("Now you will need to enter the required information for new material");
//            System.out.println("Enter new ID: ");
//            int idm = sc.nextInt();
//            System.out.println("\nEnter Name: ");
//            String name = sc.next();
//            System.out.println();
//            System.out.println("Enter Brand: ");
//            String brand = sc.next();
//            System.out.println("Enter Description: ");
//            String space = sc.nextLine();
//            String descr = sc.nextLine();
//            System.out.println("\nEnter Quantity: ");
//            int quantity = sc.nextInt();
//            System.out.println("Enter Price: ");
//            int price = sc.nextInt();
//            System.out.println("Now, please enter the information for the supplier of added material");
//            System.out.println("Do you want to add a new supplier or choose the existing one?");
//            System.out.println("1-Add new supplier for this material");
//            System.out.println("2-Choose the existing supplier");
//            int choice = sc.nextInt();
//            switch (choice) {
//                case 1:
//                    System.out.println("Now you will need to enter the required information for new supplier");
//                    int SIDs = getLS().size() + 1;
//                    System.out.println("\nEnter Supplier Name: ");
//                    String nameOfNewSupplier = sc.next();
//                    System.out.println("\nEnter Surname: ");
//                    String surnameOfNewSupplier = sc.next();
//                    System.out.println("Enter Phone: ");
//                    String phoneOfNewSupplier = sc.next();
//                    System.out.println("Enter Address: ");
//                    String spaceV2 = sc.nextLine();
//                    String addressOfNewSupplier = sc.nextLine();
//                    createSupplier(SIDs, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier);
//                    createMaterial(idm, name, brand, descr, quantity, price, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier, SIDs);
//                    final String sqlForCreatingNewSupplier = String.format(
//                            "INSERT suppliers(idsupplier,SupplierName, Surname, Phone, Adress) VALUES (%d,\'%s\',\'%s\',\'%s\',\'%s\');",
//                            SIDs, nameOfNewSupplier, surnameOfNewSupplier, phoneOfNewSupplier, addressOfNewSupplier
//                    );
//                    PreparedStatement supplierCreatorDB = dbcon.connection.prepareStatement(sqlForCreatingNewSupplier);
//                    supplierCreatorDB.executeUpdate(sqlForCreatingNewSupplier);
//                    final String sqlBasedOnCNM = String.format(
//                            sqlForCreatingNewMaterial,
//                            idm, name, brand, descr, quantity, price
//                    );
//                    PreparedStatement materialCreatorDB = dbcon.connection.prepareStatement(sqlBasedOnCNM);
//                    materialCreatorDB.executeUpdate();
//                    final String sqlBasedOnMCBSM = String.format(
//                            sqlForMakingConnectionBetweenSupAndMat,
//                            idm, SIDs
//                    );
//                    PreparedStatement materialWithSupplierConnector = dbcon.connection.prepareStatement(sqlBasedOnMCBSM);
//                    materialWithSupplierConnector.executeUpdate();
//                    dbcon.closeConnections();
//                    System.out.println("==================MAterials========================");
//                    getAllMaterials();
//                    System.out.println("===============Sups===============================");
//                    getAllInfoAboutSuppliers();
//                    break;
//                case 2:
//                    getAllInfoAboutSuppliers();
//                    System.out.println("Choose the existing supplier ID for the new material: ");
//                    int choosenID = sc.nextInt();
//                    if (choosenID <= getLS().size() && choosenID > 0) {
//                        createMaterial(
//                                idm,
//                                name,
//                                brand,
//                                descr,
//                                quantity,
//                                price,
//                                getSupByID(choosenID).getSupplierName(),
//                                getSupByID(choosenID).getSupplierSurname(),
//                                getSupByID(choosenID).getSupplierPhone(),
//                                getSupByID(choosenID).getSupplierAdress(),
//                                getSupByID(choosenID).getSupplierId()
//                        );
//                        final String sqlBasedOnCNMES = String.format(
//                                sqlForCreatingNewMaterial,
//                                idm, name, brand, descr, quantity, price
//                        );
//                        PreparedStatement materialCDB = dbcon.connection.prepareStatement(sqlBasedOnCNMES);
//                        materialCDB.executeUpdate();
//                        final String sqlBasedOnMCBESM = String.format(
//                                sqlForMakingConnectionBetweenSupAndMat,
//                                idm, choosenID
//                        );
//                        PreparedStatement materialWSC = dbcon.connection.prepareStatement(sqlBasedOnMCBESM);
//                        materialWSC.executeUpdate();
//                        dbcon.closeConnections();
//                        System.out.println("==================MAterials========================");
//                        getAllMaterials();
//                        System.out.println("===============Sups===============================");
//                        getAllInfoAboutSuppliers();
//                    }
//                    break;
//            }
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
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
    public Material searchMatById(int id){
        Material requiredMat = null;
        for (Material m:
                LM) {
            if(m.getId()==id){
                requiredMat=m;
            }
        }
        return requiredMat;
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


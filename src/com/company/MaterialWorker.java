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
            System.out.println("Name des Materials:"+ this.name);
            System.out.println("Marke des Materials: "+ this.brand);
            System.out.println("Beschreibung des Materials: "+ this.description);
            System.out.println("Menge des Materials: "+ this.quantity);
            System.out.println("Preis des Materials: "+this.price);
            System.out.println("Name desLieferants :"+this.supliers_name);
            System.out.println("Nachname des Lieferanten: "+this.supliers_surname);
            System.out.println("Lieferanten Telefon: "+this.supliers_phone);
            System.out.println("Lieferantenadresse: "+this.supliers_adress);
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
            System.out.println("Name des Materials:" + m.getName());
            System.out.println("Marke des Materials: " + m.getBrand());
            System.out.println("Beschreibung des Materials: " + m.getDescription());
            System.out.println("Menge des Materials: " + m.getQuantity());
            System.out.println("Preis des Materials: " + m.getPrice());
            System.out.println("Name desLieferants : " + m.getSupliersName());
            System.out.println("Nachname des Lieferanten: " + m.getSupliersSurname());
            System.out.println("Lieferanten Telefon: " + m.getSupliersPhone());
            System.out.println("Lieferantenadresse: " + m.getSupliersAdress());
            System.out.println("\n=================================\n");

        }
    }
    public void searchMatById(){
        System.out.println("Geben Sie die ID des gewünschten Materials ein:");
        int requiredId = sc.nextInt();
        int exist = 0;

        for (Material m:
                LM) {
            if(m.getId()==requiredId){
                m.getInfo();
                exist++;
            }
            else continue;
        }
        if(exist == 0 ){
            System.out.println("Leider haben wir kein Material mit dieser Id gefunden.");
        }

    }
    public void searchMaterialByBrand() {
        Set<String> brandsSet = new HashSet<>();
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
        System.out.print("\nWählen Sie die Marke des Baumaterials aus, das dem gewünschten Material entspricht, indem Sie die entsprechende Nummer eingeben: ");
        int choice = sc.nextInt();
        String brand = brandHash.get(choice);
        for (Material m : LM) {
            if (m.getBrand().equals(brand)) {
                m.getInfo();
            }
        }
        System.out.println("↑ Alle Materialien mit der entsprechenden Marke sind oben dargestellt ↑");

    }

    public List<Material>searchMaterialByBrandForMakeASale() {
        Set<String> brandsSet = new HashSet<>();
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
        System.out.print("\nWählen Sie die Marke des Baumaterials aus, das dem gewünschten Material entspricht, indem Sie die entsprechende Nummer eingeben:");
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
    public Material searchMatByIdForMakeASale(int id){
        Material requiredMat = null;
        for (Material m:
                LM) {
            if(m.getId()==id){
                requiredMat=m;
            }
        }
        return requiredMat;
    }
    public Material searchMaterialByIdInListForMakeASale(int id,List<Material> l){
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


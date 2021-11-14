import com.company.MaterialWorker;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        MaterialWorker m = new MaterialWorker();
        m.showAllData();
//        m.materialsRefillUpdate();
        m.updateMaterials();

        m.showAllData();

	// write your code here
    }
}

package com.company;

import java.io.IOException;
import java.sql.SQLException;

public interface MenuShower {
    void getMenu() throws InterruptedException, SQLException, IOException, ClassNotFoundException;
}

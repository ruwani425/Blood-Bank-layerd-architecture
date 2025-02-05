package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;

import java.sql.SQLException;

public interface HomeBO extends SuperBO {
    int getTotalBloodIDCount() throws Exception;

    int getTotalIssuedBloodIDCount() throws Exception;

    int getTotalRequestBloodCount() throws Exception;
}

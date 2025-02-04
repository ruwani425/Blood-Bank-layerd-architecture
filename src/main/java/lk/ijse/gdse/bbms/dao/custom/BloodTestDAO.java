package lk.ijse.gdse.bbms.dao.custom;

import lk.ijse.gdse.bbms.dao.CrudDAO;
import lk.ijse.gdse.bbms.dao.SuperDAO;
import lk.ijse.gdse.bbms.entity.BloodTest;

public interface BloodTestDAO extends SuperDAO , CrudDAO<BloodTest> {
    String getNextBloodTesdtID()throws Exception;
}

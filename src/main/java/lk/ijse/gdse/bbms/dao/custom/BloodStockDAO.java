package lk.ijse.gdse.bbms.dao.custom;

import lk.ijse.gdse.bbms.dao.CrudDAO;
import lk.ijse.gdse.bbms.dao.SuperDAO;
import lk.ijse.gdse.bbms.entity.BloodStock;

public interface BloodStockDAO extends SuperDAO, CrudDAO<BloodStock> {
    boolean updateBloodStockStatusAfterIssued(String bloodID)throws Exception;
}

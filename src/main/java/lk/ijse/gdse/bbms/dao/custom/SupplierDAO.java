package lk.ijse.gdse.bbms.dao.custom;

import lk.ijse.gdse.bbms.dao.CrudDAO;
import lk.ijse.gdse.bbms.dao.SuperDAO;
import lk.ijse.gdse.bbms.entity.Supplier;

import java.util.ArrayList;

public interface SupplierDAO extends SuperDAO, CrudDAO<Supplier> {
    ArrayList<String> getAllIDs()throws Exception;
}

package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.SupplierInventoryDAO;
import lk.ijse.gdse.bbms.entity.SupplierInventory;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierInventoryDAOImpl implements SupplierInventoryDAO {
    @Override
    public ArrayList<SupplierInventory> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(SupplierInventory supplierInventory) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Supplier_Inventory VALUES (?,?)",
                supplierInventory.getSupplier_id(),
                supplierInventory.getInventory_id()
        );
    }

    @Override
    public boolean update(SupplierInventory Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(SupplierInventory id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<SupplierInventory> search(SupplierInventory newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SupplierInventory findById(SupplierInventory entity) throws SQLException {
        return null;
    }
}

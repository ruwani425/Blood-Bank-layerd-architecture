package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.InventoryDAO;
import lk.ijse.gdse.bbms.entity.Inventory;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public ArrayList<Inventory> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Inventory Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Inventory inventory) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Inventory SET Item_name=?, Status=?, Expiry_date=?, Qty=? WHERE Inventory_id=?",
                inventory.getItemName(),
                inventory.getStatus(),
                inventory.getExpiryDate(),
                inventory.getQty(),
                inventory.getInventoryId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Inventory inventory) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Inventory WHERE Inventory_id=?", inventory.getInventoryId());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT Inventory_id FROM Inventory ORDER BY Inventory_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last inventory ID
            String substring = lastId.substring(1); // Extract numeric part
            int i = Integer.parseInt(substring); // Convert to integer
            int newIdIndex = i + 1; // Increment by 1
            return String.format("I%03d", newIdIndex); // Format as Innn
        }
        return "I001"; // Default inventory ID
    }

    @Override
    public ArrayList<Inventory> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}

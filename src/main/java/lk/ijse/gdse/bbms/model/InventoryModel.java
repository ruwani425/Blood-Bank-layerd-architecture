package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.InventoryDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {
    public String getNextInventoryId() throws SQLException {
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

    public ArrayList<InventoryDTO> getAllInventoryItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Inventory");

        ArrayList<InventoryDTO> inventoryList = new ArrayList<>();

        while (rst.next()) {
            InventoryDTO inventoryDTO = new InventoryDTO(
                    rst.getString("Inventory_id"),
                    rst.getString("Item_name"),
                    rst.getString("Status"),
                    rst.getDate("Expiry_date"),
                    rst.getInt("Qty")
            );
            inventoryList.add(inventoryDTO);
        }
        return inventoryList;
    }

    public boolean addInventoryItem(InventoryDTO inventoryDTO, String supplierId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (CrudUtil.execute(
                    "INSERT INTO Inventory VALUES (?,?,?,?,?)",
                    inventoryDTO.getInventoryId(),
                    inventoryDTO.getItemName(),
                    inventoryDTO.getStatus(),
                    inventoryDTO.getExpiryDate(),
                    inventoryDTO.getQty()
            )) {
                if (CrudUtil.execute("INSERT INTO Supplier_Inventory VALUES (?,?)", supplierId, inventoryDTO.getInventoryId())) {
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean deleteInventoryItem(String inventoryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Inventory WHERE Inventory_id=?", inventoryId);
    }

    public boolean updateInventoryItem(InventoryDTO inventoryDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Inventory SET Item_name=?, Status=?, Expiry_date=?, Qty=? WHERE Inventory_id=?",
                inventoryDTO.getItemName(),
                inventoryDTO.getStatus(),
                inventoryDTO.getExpiryDate(),
                inventoryDTO.getQty(),
                inventoryDTO.getInventoryId()
        );
    }

    public InventoryDTO getInventoryById(String inventoryId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Inventory WHERE Inventory_id=?", inventoryId);

        if (rst.next()) {
            return new InventoryDTO(
                    rst.getString("Inventory_id"),
                    rst.getString("Item_name"),
                    rst.getString("Status"),
                    rst.getDate("Expiry_date"),
                    rst.getInt("Qty")
            );
        }
        return null;
    }

    public ArrayList<String> getAllInventoryIDs() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Inventory_id FROM Inventory");

        ArrayList<String> inventoryIds = new ArrayList<>();

        while (rst.next()) {
            inventoryIds.add(rst.getString("Inventory_id"));
        }
        return inventoryIds;
    }
}

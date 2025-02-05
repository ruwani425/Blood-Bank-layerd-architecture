package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.InventoryBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.InventoryDAO;
import lk.ijse.gdse.bbms.dao.custom.SupplierDAO;
import lk.ijse.gdse.bbms.dao.custom.SupplierInventoryDAO;
import lk.ijse.gdse.bbms.dao.custom.impl.InventoryDAOImpl;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.InventoryDTO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.entity.Inventory;
import lk.ijse.gdse.bbms.entity.Supplier;
import lk.ijse.gdse.bbms.entity.SupplierInventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {

    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
    SupplierInventoryDAO supplierInventoryDAO = (SupplierInventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIERINVENTORY);
    Inventory inventory = new Inventory();

    @Override
    public String getNextInventoryId() throws Exception {
        return inventoryDAO.getNewId();
    }

    @Override
    public boolean addInventoryItem(InventoryDTO inventoryDTO, String supplierID) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (inventoryDAO.save(new Inventory(
                    inventoryDTO.getInventoryId(),
                    inventoryDTO.getItemName(),
                    inventoryDTO.getStatus(),
                    inventoryDTO.getExpiryDate(),
                    inventoryDTO.getQty()
            ))) {
                if (supplierInventoryDAO.save(new SupplierInventory(
                        supplierID,
                        inventoryDTO.getInventoryId()
                ))) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean deleteInventoryItem(String inventoryId) throws Exception {
        inventory.setInventoryId(inventoryId);
        return inventoryDAO.delete(inventory);
    }

    @Override
    public boolean updateInventoryItem(InventoryDTO inventoryDTO) throws Exception {
        inventory.setInventoryId(inventoryDTO.getInventoryId());
        inventory.setExpiryDate(inventoryDTO.getExpiryDate());
        inventory.setItemName(inventoryDTO.getItemName());
        inventory.setQty(inventoryDTO.getQty());
        inventory.setStatus(inventoryDTO.getStatus());
        return inventoryDAO.update(inventory);
    }

    @Override
    public ArrayList<InventoryDTO> getAllInventoryItems() throws Exception {
        ArrayList<InventoryDTO> inventoryDTOS = new ArrayList<>();
        ArrayList<Inventory> inventories = inventoryDAO.getAllData();
        for (Inventory inventory : inventories) {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setInventoryId(inventory.getInventoryId());
            inventoryDTO.setExpiryDate(inventory.getExpiryDate());
            inventoryDTO.setItemName(inventory.getItemName());
            inventoryDTO.setQty(inventory.getQty());
            inventoryDTO.setStatus(inventory.getStatus());
            inventoryDTOS.add(inventoryDTO);
        }
        return inventoryDTOS;
    }

    @Override
    public ArrayList<String> getAllSupplierIDs() throws Exception {
        return supplierDAO.getAllIDs();
    }

    @Override
    public SupplierDTO getSupplierById(String value) throws Exception {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(value);
        supplier = supplierDAO.findById(supplier);

        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setSupplierId(value);
        supplierDTO.setSupplierName(supplier.getSupplierName());
        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setEmail(supplier.getEmail());
        supplierDTO.setDescription(supplier.getDescription());

        return supplierDTO;
    }
}

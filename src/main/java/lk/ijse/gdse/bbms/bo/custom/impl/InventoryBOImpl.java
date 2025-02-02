package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.InventoryBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.InventoryDAO;
import lk.ijse.gdse.bbms.dao.custom.impl.InventoryDAOImpl;
import lk.ijse.gdse.bbms.dto.InventoryDTO;
import lk.ijse.gdse.bbms.entity.Inventory;

public class InventoryBOImpl implements InventoryBO {

    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);
    Inventory inventory = new Inventory();

    @Override
    public String getNextInventoryId() throws Exception {
        return inventoryDAO.getNewId();
    }

    @Override
    public boolean addInventoryItem(InventoryDTO inventoryDTO, String value) throws Exception {
        inventory.setInventoryId(inventoryDTO.getInventoryId());
        inventory.setExpiryDate(inventoryDTO.getExpiryDate());
        inventory.setItemName(inventoryDTO.getItemName());
        inventory.setQty(inventoryDTO.getQty());
        inventory.setStatus(inventoryDTO.getStatus());
        return inventoryDAO.save(inventory);
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
}

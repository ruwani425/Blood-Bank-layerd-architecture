package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.InventoryDTO;

public interface InventoryBO extends SuperBO {
    String getNextInventoryId()throws Exception;

    boolean addInventoryItem(InventoryDTO inventoryDTO, String value)throws Exception;

    boolean deleteInventoryItem(String inventoryId)throws Exception;

    boolean updateInventoryItem(InventoryDTO inventoryDTO)throws Exception;
}

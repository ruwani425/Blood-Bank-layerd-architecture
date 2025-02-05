package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.InventoryDTO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;

import java.util.ArrayList;

public interface InventoryBO extends SuperBO {
    String getNextInventoryId()throws Exception;

    boolean addInventoryItem(InventoryDTO inventoryDTO, String value)throws Exception;

    boolean deleteInventoryItem(String inventoryId)throws Exception;

    boolean updateInventoryItem(InventoryDTO inventoryDTO)throws Exception;

    ArrayList<InventoryDTO> getAllInventoryItems()throws Exception;

    ArrayList<String> getAllSupplierIDs()throws Exception;

    SupplierDTO getSupplierById(String value)throws Exception;
}

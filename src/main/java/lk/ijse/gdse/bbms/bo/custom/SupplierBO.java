package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;

import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    String getNextSupplierId()throws Exception;

    boolean deleteSupplier(String supplierID)throws Exception;

    boolean addSupplier(SupplierDTO supplierDTO)throws Exception;

    boolean updateSupplier(SupplierDTO supplierDTO)throws Exception;

    ArrayList<SupplierDTO> getAllSuppliers()throws Exception;
}

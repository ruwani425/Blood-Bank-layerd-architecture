package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.SupplierBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.SupplierDAO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    private SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNewId();
    }

    @Override
    public boolean deleteSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierID);
        return supplierDAO.delete(supplier);
    }

    @Override
    public boolean addSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setSupplierId(supplierDTO.getSupplierId());
        supplier.setDescription(supplierDTO.getDescription());
        supplier.setEmail(supplierDTO.getEmail());
        return supplierDAO.save(supplier);
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setSupplierId(supplierDTO.getSupplierId());
        supplier.setDescription(supplierDTO.getDescription());
        supplier.setEmail(supplierDTO.getEmail());
        return supplierDAO.update(supplier);
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws Exception {
        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
        ArrayList<Supplier> suppliers = supplierDAO.getAllData();
        for (Supplier supplier : suppliers) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplierId(supplier.getSupplierId());
            supplierDTO.setSupplierName(supplier.getSupplierName());
            supplierDTO.setAddress(supplier.getAddress());
            supplierDTO.setDescription(supplier.getDescription());
            supplierDTO.setEmail(supplier.getEmail());
            supplierDTOs.add(supplierDTO);
        }
        return supplierDTOs;
    }
}

package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.SupplierBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.SupplierDAO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.entity.Supplier;

import java.sql.SQLException;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
    Supplier supplier = new Supplier();

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNewId();
    }

    @Override
    public boolean deleteSupplier(String supplierID) throws SQLException, ClassNotFoundException {
        supplier.setSupplierId(supplierID);
        return supplierDAO.delete(supplier);
    }

    @Override
    public boolean addSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setSupplierId(supplierDTO.getSupplierId());
        supplier.setDescription(supplierDTO.getDescription());
        supplier.setEmail(supplierDTO.getEmail());
        return supplierDAO.save(supplier);
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setSupplierId(supplierDTO.getSupplierId());
        supplier.setDescription(supplierDTO.getDescription());
        supplier.setEmail(supplierDTO.getEmail());
        return supplierDAO.update(supplier);
    }
}

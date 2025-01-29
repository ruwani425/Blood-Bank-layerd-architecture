package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Supplier_id FROM Supplier ORDER BY Supplier_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last supplier ID
            String substring = lastId.substring(1); // Extract numeric part
            int i = Integer.parseInt(substring); // Convert to integer
            int newIdIndex = i + 1; // Increment by 1
            return String.format("S%03d", newIdIndex); // Format as Snnn
        }
        return "S001";
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public boolean addSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Supplier VALUES (?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getSupplierName(),
                supplierDTO.getAddress(),
                supplierDTO.getEmail(),
                supplierDTO.getDescription()
        );
    }

    public boolean deleteSupplier(String supplierId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE Supplier_id=?", supplierId);
    }

    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Supplier SET Name=?, Address=?, E_mail=?, Description=? WHERE Supplier_id=?",
                supplierDTO.getSupplierName(),
                supplierDTO.getAddress(),
                supplierDTO.getEmail(),
                supplierDTO.getDescription(),
                supplierDTO.getSupplierId()
        );
    }

    public SupplierDTO getSupplierById(String supplierId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier WHERE Supplier_id=?", supplierId);

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public ArrayList<String> getAllSupplierIDs() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Supplier_id FROM Supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString("Supplier_id"));
        }
        return supplierIds;
    }
}

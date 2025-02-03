package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.SupplierDAO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.entity.Supplier;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAllData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");

        ArrayList<Supplier> suppliers = new ArrayList<>();

        while (rst.next()) {
            Supplier supplier = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Supplier VALUES (?,?,?,?,?)",
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getDescription()
        );
    }

    @Override
    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Supplier SET Name=?, Address=?, E_mail=?, Description=? WHERE Supplier_id=?",
                supplier.getSupplierName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getDescription(),
                supplier.getSupplierId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE Supplier_id=?", supplier.getSupplierId());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
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

    @Override
    public ArrayList<Supplier> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}

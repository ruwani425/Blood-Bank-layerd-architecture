package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.BloodStockDAO;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.entity.BloodStock;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodStockDAOImpl implements BloodStockDAO {
    @Override
    public ArrayList<BloodStock> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BloodStock bloodStock) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Blood_stock VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                bloodStock.getBloodID(),
                bloodStock.getTestID(),
                bloodStock.getBloodGroup(),
                bloodStock.getQty(),
                bloodStock.getHaemoglobin(),
                bloodStock.getPlatelets(),
                bloodStock.getRedBloodCells(),
                bloodStock.getWhiteBloodCells(),
                bloodStock.getExpiryDate(),
                bloodStock.getStatus()
        );
    }

    @Override
    public boolean update(BloodStock Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(BloodStock id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<BloodStock> search(BloodStock bloodStock) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_stock WHERE status = ?", bloodStock.getStatus());

        ArrayList<BloodStock> bloodStocks = new ArrayList<>();

        while (rst.next()) {
            BloodStock bloodStock1 = new BloodStock(
                    rst.getString(1),   // bloodID
                    rst.getString(2),   // testID
                    rst.getString(3),   // bloodGroup
                    rst.getInt(4),      // qty
                    rst.getDouble(5),   // haemoglobin
                    rst.getFloat(6),    // platelets
                    rst.getDouble(7),   // redBloodCells
                    rst.getDouble(8),   // whiteBloodCells
                    rst.getDate(9),     // expiryDate
                    rst.getString(10)   // status
            );
            bloodStocks.add(bloodStock1);
        }
        return bloodStocks;
    }

    @Override
    public BloodStock findById(BloodStock entity) throws SQLException {
        return null;
    }

    @Override
    public boolean updateBloodStockStatusAfterIssued(String bloodID) throws Exception {
        boolean rowsUpdated = CrudUtil.execute(
                "UPDATE Blood_stock SET status = 'ISSUED' WHERE Blood_id=?", bloodID
        );
        return rowsUpdated;
    }

    @Override
    public String getNextBloodId() throws Exception {
        ResultSet rst = CrudUtil.execute("select Blood_id from Blood_stock order by Blood_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last blood ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%03d", newIdIndex); // Return the new Blood ID in format Bnnn
        }
        return "B001"; // Return the default Blood ID if no data is found
    }
}

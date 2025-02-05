package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.BloodTestDAO;
import lk.ijse.gdse.bbms.entity.BloodTest;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodTestDAOImpl implements BloodTestDAO {
    @Override
    public ArrayList<BloodTest> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BloodTest bloodTest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Blood_test values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                bloodTest.getTestID(),
                bloodTest.getDonationID(),
                bloodTest.getCollectedDate(),
                bloodTest.getExpiryDate(),
                bloodTest.getTestResult(),
                bloodTest.getHaemoglobin(),
                bloodTest.getTestDate(),
                bloodTest.getReportSerialNum(),
                bloodTest.getPlatelets(),
                bloodTest.getRedBloodCells(),
                bloodTest.getWhiteBloodCells(),
                bloodTest.getReportImageUrl(),
                bloodTest.getBloodType(),
                bloodTest.getBloodQty()
        );
    }

    @Override
    public boolean update(BloodTest Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(BloodTest id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<BloodTest> search(BloodTest newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextBloodTesdtID() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Test_id from Blood_test order by Test_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last blood test ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("T%03d", newIdIndex); // Return the new test ID in format Tnnn
        }
        return "T001"; // Return the default test ID if no data is found
    }
}

package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.BloodTestDAO;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.entity.BloodTest;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodTestDAOImpl implements BloodTestDAO {
    @Override
    public ArrayList<BloodTest> getAllData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_test WHERE Test_result = 'PASS' OR Test_result = 'FAIL'");

        ArrayList<BloodTest> bloodTests = new ArrayList<>();

        while (rst.next()) {
            BloodTest bloodTest = new BloodTest(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getFloat(9),
                    rst.getDouble(10),
                    rst.getDouble(11),
                    rst.getString(12),
                    rst.getString(13),
                    rst.getDouble(14)
            );
            bloodTests.add(bloodTest);
        }
        return bloodTests;
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
    public boolean update(BloodTest bloodTest) throws SQLException, ClassNotFoundException {
        System.out.println("run bloodTestDAOImpl");
        return CrudUtil.execute(
                "UPDATE Blood_test SET Collected_date = ?, Expiry_date = ?, Test_result = ?, Haemoglobin = ?, " +
                        "Test_date = ?, Report_serial_Number = ?, Platelets = ?, Red_blood_cells = ?, White_blood_cells = ?, " +
                        "Report_image_URL = ?,Blood_group=?,blood_qty=? WHERE Test_id = ?",
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
                bloodTest.getBloodQty(),
                bloodTest.getTestID()
        );
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
    public ArrayList<BloodTest> search(BloodTest bloodTest) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Blood_test where Test_result=?", bloodTest.getTestResult());

        ArrayList<BloodTest> bloodTests = new ArrayList<>();

        while (rst.next()) {
            BloodTest bloodTest1 = new BloodTest(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getFloat(9),
                    rst.getDouble(10),
                    rst.getDouble(11),
                    rst.getString(12),
                    rst.getString(13),
                    rst.getDouble(14)
            );
            bloodTests.add(bloodTest1);
        }
        return bloodTests;
    }

    @Override
    public BloodTest findById(BloodTest bloodTest) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Blood_group, Collected_date FROM Blood_test WHERE Test_id = ?", bloodTest.getTestID());

        if (rst.next()) {
            String bloodGroup = rst.getString("Blood_group");
            Date collectedDate = rst.getDate("Collected_date");

            return new BloodTest(
                    null,
                    null,
                    collectedDate,
                    null,
                    null,
                    0.0,
                    null,
                    null,
                    0.0f,
                    0.0,
                    0.0,
                    null,
                    bloodGroup,
                    0.0
            );
        }
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

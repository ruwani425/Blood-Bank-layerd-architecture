package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodTestModel {
    BloodStockModel bloodStockModel=new BloodStockModel();

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

    public ArrayList<BloodTestDTO> getAllBloodTests(String result) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Blood_test where Test_result=?", result);

        ArrayList<BloodTestDTO> bloodTestDTOS = new ArrayList<>();

        while (rst.next()) {
            BloodTestDTO bloodTestDTO = new BloodTestDTO(
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
            bloodTestDTOS.add(bloodTestDTO);
        }
        return bloodTestDTOS;
    }
    public boolean addBloodTest(BloodTestDTO bloodTestDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Blood_test values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                bloodTestDTO.getTestID(),
                bloodTestDTO.getDonationID(),
                bloodTestDTO.getCollectedDate(),
                bloodTestDTO.getExpiryDate(),
                bloodTestDTO.getTestResult(),
                bloodTestDTO.getHaemoglobin(),
                bloodTestDTO.getTestDate(),
                bloodTestDTO.getReportSerialNum(),
                bloodTestDTO.getPlatelets(),
                bloodTestDTO.getRedBloodCells(),
                bloodTestDTO.getWhiteBloodCells(),
                bloodTestDTO.getReportImageUrl(),
                bloodTestDTO.getBloodType(),
                bloodTestDTO.getBloodQty()
        );
    }
    public boolean updateBloodTest(BloodTestDTO bloodTestDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        BloodStockDTO bloodStockDTO=new BloodStockDTO();

        bloodStockDTO.setBloodID(bloodStockModel.getNextBloodId());
        bloodStockDTO.setTestID(bloodTestDTO.getTestID());
        bloodStockDTO.setBloodGroup(bloodTestDTO.getBloodType());
        bloodStockDTO.setRedBloodCells(bloodTestDTO.getRedBloodCells());
        bloodStockDTO.setWhiteBloodCells(bloodTestDTO.getWhiteBloodCells());
        bloodStockDTO.setHaemoglobin(bloodTestDTO.getHaemoglobin());
        bloodStockDTO.setPlatelets(bloodTestDTO.getPlatelets());
        bloodStockDTO.setExpiryDate(bloodTestDTO.getExpiryDate());
        bloodStockDTO.setQty(bloodTestDTO.getBloodQty());

        if (bloodTestDTO.getTestResult().equals("PASS")){
            bloodStockDTO.setStatus("VERIFIED");
        }else {
            bloodStockDTO.setStatus("NOT_VERIFIED");
        }

        try {
            if (CrudUtil.execute(
                    "UPDATE Blood_test SET Collected_date = ?, Expiry_date = ?, Test_result = ?, Haemoglobin = ?, " +
                            "Test_date = ?, Report_serial_Number = ?, Platelets = ?, Red_blood_cells = ?, White_blood_cells = ?, " +
                            "Report_image_URL = ?,Blood_group=?,blood_qty=? WHERE Test_id = ?",
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getExpiryDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells(),
                    bloodTestDTO.getReportImageUrl(),
                    bloodTestDTO.getBloodType(),
                    bloodTestDTO.getBloodQty(),
                    bloodTestDTO.getTestID()
            )){
                if (bloodStockModel.addBloodStock(bloodStockDTO)) {
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }
            }else{
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    public boolean setStatus(String status, String testID) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Blood_test SET Test_result = ? WHERE Test_id = ?",
                status,
                testID
        );
    }

    public BloodTestDTO getBloodTestDetailById(String testID) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Blood_group, Collected_date FROM Blood_test WHERE Test_id = ?", testID);

        if (rst.next()) {
            String bloodGroup = rst.getString("Blood_group");
            Date collectedDate = rst.getDate("Collected_date");

            return new BloodTestDTO(
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


    public ArrayList<BloodTestDTO> getAllBloodTests() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_test WHERE Test_result = 'PASS' OR Test_result = 'FAIL'");

        ArrayList<BloodTestDTO> bloodTestDTOS = new ArrayList<>();

        while (rst.next()) {
            BloodTestDTO bloodTestDTO = new BloodTestDTO(
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
            bloodTestDTOS.add(bloodTestDTO);
        }
        return bloodTestDTOS;
    }
}

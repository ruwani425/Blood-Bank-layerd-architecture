package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BloodStockModel {
    BloodRequestModel bloodRequestModel = new BloodRequestModel();

    public String getNextBloodId() throws SQLException {
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

    public ArrayList<BloodStockDTO> getAllBloodStocks(String status) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_stock WHERE status = ?", status);

        ArrayList<BloodStockDTO> bloodStockDTOS = new ArrayList<>();

        while (rst.next()) {
            BloodStockDTO bloodStockDTO = new BloodStockDTO(
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
            bloodStockDTOS.add(bloodStockDTO);
        }
        return bloodStockDTOS;
    }

    public boolean addBloodStock(BloodStockDTO bloodStockDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Blood_stock VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                bloodStockDTO.getBloodID(),
                bloodStockDTO.getTestID(),
                bloodStockDTO.getBloodGroup(),
                bloodStockDTO.getQty(),
                bloodStockDTO.getHaemoglobin(),
                bloodStockDTO.getPlatelets(),
                bloodStockDTO.getRedBloodCells(),
                bloodStockDTO.getWhiteBloodCells(),
                bloodStockDTO.getExpiryDate(),
                bloodStockDTO.getStatus()
        );
    }

    public boolean updateBloodStockStatus() throws SQLException {
        int rowsUpdated = CrudUtil.execute(
                "UPDATE Blood_stock SET status = 'EXPIRED' WHERE Expiry_date < CURDATE()"
        );
        return rowsUpdated > 0;
    }

    public boolean updateBloodStockStatusAfterIssued(String bloodId) throws SQLException {
        boolean rowsUpdated = CrudUtil.execute(
                "UPDATE Blood_stock SET status = 'ISSUED' WHERE Blood_id=?", bloodId
        );
        return rowsUpdated;
    }


    public ArrayList<BloodStockDTO> getExpiredBloodStocks() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_stock WHERE Expiry_date < CURDATE()");

        ArrayList<BloodStockDTO> bloodStockDTOS = new ArrayList<>();

        while (rst.next()) {
            CrudUtil.execute(
                    "UPDATE Blood_stock SET status = 'EXPIRED' WHERE Blood_id = ?",
                    rst.getString(1) // Blood_id
            );

            bloodStockDTOS.add(new BloodStockDTO(
                    rst.getString(1),   // bloodID
                    rst.getString(2),   // testID
                    rst.getString(3),   // bloodGroup
                    rst.getInt(4),      // qty
                    rst.getDouble(5),   // haemoglobin
                    rst.getFloat(6),    // platelets
                    rst.getDouble(7),   // redBloodCells
                    rst.getDouble(8),   // whiteBloodCells
                    rst.getDate(9),     // expiryDate
                    "EXPIRED"           // status (updated to EXPIRED)
            ));
        }
        return bloodStockDTOS;
    }

    public boolean addBloodIssue(BloodRequestTM bloodRequestTM, ArrayList<BloodIssueTM> issuedBlood) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            for (BloodIssueTM bloodStockDTO : issuedBlood) {
                try {
                    if (CrudUtil.execute("INSERT INTO Blood_request_detail VALUES (?,?)",
                            bloodRequestTM.getRequestId(),
                            bloodStockDTO.getBloodIssueID()
                    )) {
                        if (CrudUtil.execute("INSERT INTO Reserved_blood VALUES (?,?,?,?,?)",
                                getNextReservedBloodID(),
                                bloodStockDTO.getBloodID(),
                                bloodRequestTM.getHospitalId(),
                                LocalDate.now(),
                                bloodStockDTO.getBloodQty())) {
                            if (updateBloodStockStatusAfterIssued(bloodStockDTO.getBloodID())) {
                                if (bloodRequestModel.updateStatus(bloodRequestTM.getRequestId())) {
                                    connection.commit();
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public String getNextReservedBloodID() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Reserved_id from Reserved_blood order by Reserved_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last blood ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("R%03d", newIdIndex); // Return the new Blood ID in format Rnnn
        }
        return "R001";
    }

    public int getTotalBloodIDCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Blood_stock");

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    public int getTotalIssuedBloodIDCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Reserved_blood");

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}

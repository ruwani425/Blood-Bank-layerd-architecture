package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodRequestModel {
    public String getNextRequestId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Request_id from Blood_request order by Request_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Get the last Request ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the numeric part by 1
            return String.format("R%03d", newIdIndex); // Return the new Request ID in format Rnnn
        }
        return "R001";
    }
    public boolean addBloodRequest(BloodRequestDTO bloodRequestDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Blood_request values (?, ?, ?, ?, ?, ?)",
                bloodRequestDTO.getRequestId(),
                bloodRequestDTO.getHospitalId(),
                bloodRequestDTO.getBloodType(),
                bloodRequestDTO.getDateOfRequest(),
                bloodRequestDTO.getQty(),
                bloodRequestDTO.getStatus()
        );
    }

    public ArrayList<BloodRequestDTO> getAllRequests(String status) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_request where Status=?",status);

        ArrayList<BloodRequestDTO> bloodRequestList = new ArrayList<>();

        while (rst.next()) {
            BloodRequestDTO bloodRequest = new BloodRequestDTO(
                    rst.getString("Request_id"),
                    rst.getString("Hospital_id"),
                    rst.getString("Blood_group"),
                    rst.getDate("Date_of_request"),
                    rst.getDouble("Qty"),
                    rst.getString("Status")
            );

            bloodRequestList.add(bloodRequest);
        }

        return bloodRequestList;
    }
    public boolean updateStatus(String requestId) throws SQLException {
        return CrudUtil.execute("UPDATE Blood_request SET Status='COMPLETED' WHERE Request_id=?",requestId);
    }

    public int getTotalRequestBloodCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Blood_request");

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}

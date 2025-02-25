package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.entity.BloodRequest;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodRequestDAOImpl implements BloodRequestDAO {
    @Override
    public ArrayList<BloodRequest> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BloodRequest bloodRequest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Blood_request values (?, ?, ?, ?, ?, ?)",
                bloodRequest.getRequestId(),
                bloodRequest.getHospitalId(),
                bloodRequest.getBloodType(),
                bloodRequest.getDateOfRequest(),
                bloodRequest.getQty(),
                bloodRequest.getStatus()
        );
    }

    @Override
    public boolean update(BloodRequest bloodRequest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Blood_request SET Status='COMPLETED' WHERE Request_id=?",bloodRequest.getRequestId());
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(BloodRequest id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
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


    @Override
    public ArrayList<BloodRequest> search(BloodRequest bloodRequest) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_request where Status=?", bloodRequest.getStatus());

        ArrayList<BloodRequest> bloodRequestList = new ArrayList<>();

        while (rst.next()) {
            BloodRequest bloodRequest1 = new BloodRequest(
                    rst.getString("Request_id"),
                    rst.getString("Hospital_id"),
                    rst.getString("Blood_group"),
                    rst.getDate("Date_of_request"),
                    rst.getDouble("Qty"),
                    rst.getString("Status")
            );

            bloodRequestList.add(bloodRequest1);
        }

        return bloodRequestList;
    }

    @Override
    public BloodRequest findById(BloodRequest entity) throws SQLException {
        return null;
    }

    @Override
    public int getTotalRequestBloodCount() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(Request_id) FROM Blood_request");

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}

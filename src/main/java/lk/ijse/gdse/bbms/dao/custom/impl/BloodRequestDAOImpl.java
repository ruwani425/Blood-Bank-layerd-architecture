package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
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
    public boolean update(BloodRequest Dto) throws SQLException, ClassNotFoundException {
        return false;
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
    public ArrayList<BloodRequest> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}

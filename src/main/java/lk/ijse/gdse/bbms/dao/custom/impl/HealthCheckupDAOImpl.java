package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.HealthCheckUpDAO;
import lk.ijse.gdse.bbms.entity.HealthCheckUp;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HealthCheckupDAOImpl implements HealthCheckUpDAO {
    @Override
    public ArrayList<HealthCheckUp> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(HealthCheckUp healthCheckUp) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Health_checkup values (?,?,?,?,?,?,?)",
                healthCheckUp.getCheckupId(),
                healthCheckUp.getDonorId(),
                healthCheckUp.getHealthStatus(),
                healthCheckUp.getCheckupDate(),
                healthCheckUp.getWeight(),
                healthCheckUp.getSugarLevel(),
                healthCheckUp.getBloodPressure()
        );
    }

    @Override
    public boolean update(HealthCheckUp Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(HealthCheckUp id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select Checkup_id from Health_checkup order by Checkup_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last healthCheck ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("H%03d", newIdIndex); // Return the new Donor ID in format Hnnn
        }
        return "H001";
    }

    @Override
    public ArrayList<HealthCheckUp> search(HealthCheckUp newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}

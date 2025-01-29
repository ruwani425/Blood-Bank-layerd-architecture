package lk.ijse.gdse.bbms.model;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthCheckUpModel {
    public boolean addHealthCheckup(HealthCheckupDTO healthCheckupDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Health_checkup values (?,?,?,?,?,?,?)",
                healthCheckupDTO.getCheckupId(),
                healthCheckupDTO.getDonorId(),
                healthCheckupDTO.getHealthStatus(),
                healthCheckupDTO.getCheckupDate(),
                healthCheckupDTO.getWeight(),
                healthCheckupDTO.getSugarLevel(),
                healthCheckupDTO.getBloodPressure()
        );
    }

    public String getNextHealthCheckUpId() throws SQLException {
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

    public HealthCheckupDTO getHealthCheckupByDonorId(String donorId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Health_checkup where Donor_id = ?", donorId);

        if (rst.next()) {
            return new HealthCheckupDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );
        }
        return null;
    }
}
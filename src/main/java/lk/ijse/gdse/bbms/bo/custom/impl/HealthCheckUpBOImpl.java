package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.HealthCheckUpBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.HealthCheckUpDAO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.entity.HealthCheckUp;

import java.sql.SQLException;

public class HealthCheckUpBOImpl implements HealthCheckUpBO {
    HealthCheckUpDAO healthCheckUpDAO = (HealthCheckUpDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HEALTHCHECKUP);
    HealthCheckUp healthCheckUp = new HealthCheckUp();

    @Override
    public String getNextHealthCheckUpId() throws SQLException, ClassNotFoundException {
        return healthCheckUpDAO.getNewId();
    }

    @Override
    public boolean addHealthCheckup(HealthCheckupDTO healthCheckupDTO) throws Exception {
        healthCheckUp.setCheckupId(healthCheckupDTO.getCheckupId());
        healthCheckUp.setHealthStatus(healthCheckupDTO.getHealthStatus());
        healthCheckUp.setCheckupDate(healthCheckupDTO.getCheckupDate());
        healthCheckUp.setWeight(healthCheckupDTO.getWeight());
        healthCheckUp.setDonorId(healthCheckupDTO.getDonorId());
        healthCheckUp.setBloodPressure(healthCheckupDTO.getBloodPressure());
        healthCheckUp.setSugarLevel(healthCheckupDTO.getSugarLevel());
        return healthCheckUpDAO.save(healthCheckUp);
    }
}

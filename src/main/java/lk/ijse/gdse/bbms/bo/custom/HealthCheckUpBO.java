package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;

public interface HealthCheckUpBO extends SuperBO {
    String getNextHealthCheckUpId()throws Exception;

    boolean addHealthCheckup(HealthCheckupDTO healthCheckupDTO)throws Exception;
}

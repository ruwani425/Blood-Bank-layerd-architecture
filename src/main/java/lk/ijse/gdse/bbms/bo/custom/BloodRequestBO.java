package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BloodRequestBO extends SuperBO {
    String getNextRequestId()throws Exception;

    boolean addBloodRequest(BloodRequestDTO bloodRequestDTO)throws Exception;

    ArrayList<BloodRequestDTO> getAllRequests(String pending)throws Exception;

    ArrayList<String> getAllHospitalIDs() throws SQLException;
}

package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;

public interface BloodRequestBO extends SuperBO {
    String getNextRequestId()throws Exception;

    boolean addBloodRequest(BloodRequestDTO bloodRequestDTO)throws Exception;
}

package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;

import java.util.ArrayList;

public interface HospitalBO extends SuperBO {
    String getNextHospitalId()throws Exception;

    boolean addHospital(HospitalDTO hospitalDTO)throws Exception;

    boolean deleteHospital(String hospitalId)throws Exception;

    boolean updateHospital(HospitalDTO hospitalDTO)throws Exception;

    ArrayList<HospitalDTO> getAllHospitals()throws Exception;
}

package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;

import java.util.ArrayList;

public interface BloodStockBO extends SuperBO {
    ArrayList<BloodStockDTO> getAllBloodStocks(String verified)throws Exception;

    ArrayList<BloodStockDTO> getExpiredBloodStocks()throws Exception;

    boolean addBloodIssue(BloodRequestTM bloodRequestTM, ArrayList<BloodIssueTM> issuedBlood)throws Exception;

    HospitalDTO getHospitalById(String hospitalId)throws Exception;
}

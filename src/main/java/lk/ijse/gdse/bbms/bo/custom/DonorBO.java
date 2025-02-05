package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.DonorDTO;

import java.util.ArrayList;

public interface DonorBO extends SuperBO {
    public boolean addDonor(DonorDTO donorDTO);

    public boolean deleteDonor(String donorId) throws Exception;

    public boolean updateDonor(DonorDTO donorDTO) throws Exception;

    public String getNextDonorId() throws Exception;

    public ArrayList<DonorDTO> getAllDonors()throws Exception;

    DonorDTO getDonorByNic(String donorNic)throws Exception;
}


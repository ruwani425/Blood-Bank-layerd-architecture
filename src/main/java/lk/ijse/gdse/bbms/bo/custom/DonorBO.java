package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.DonorDTO;

public interface DonorBO extends SuperBO {
    public boolean addDonor(DonorDTO donorDTO) throws Exception;

    public boolean deleteDonor(String donorId) throws Exception;

    public boolean updateDonor(DonorDTO donorDTO) throws Exception;
}

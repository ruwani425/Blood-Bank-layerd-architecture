package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.DonationDTO;

public interface DonationBO extends SuperBO {
    boolean addDonation(DonationDTO donationDTO, String donorId)throws Exception;
}

package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.DonationDTO;

import java.util.ArrayList;

public interface DonationBO extends SuperBO {
    boolean addDonation(DonationDTO donationDTO, String donorId)throws Exception;

    String getNextDonationId() throws Exception;

    ArrayList<DonationDTO> getAllDonations()throws Exception;
}

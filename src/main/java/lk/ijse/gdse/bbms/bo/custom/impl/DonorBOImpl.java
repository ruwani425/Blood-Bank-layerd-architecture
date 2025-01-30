package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.DonorBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.DonorDAO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.entity.Donor;

public class DonorBOImpl implements DonorBO {

    DonorDAO donorDAO = (DonorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONOR);

    @Override
    public boolean addDonor(DonorDTO donorDTO) throws Exception {
        Donor donor = new Donor();
        donor.setDonorId(donorDTO.getDonorId());
        donor.setDonorName(donorDTO.getDonorName());
        donor.setDonorEmail(donorDTO.getDonorEmail());
        donor.setDonorAddress(donorDTO.getDonorAddress());
        donor.setDonorNic(donorDTO.getDonorNic());
        donor.setBloodGroup(donorDTO.getBloodGroup());
        donor.setGender(donorDTO.getGender());
        donor.setDob(donorDTO.getDob());
        donor.setLastDonationDate(donorDTO.getLastDonationDate());
        return donorDAO.save(donor);
    }

    @Override
    public boolean deleteDonor(String donorId) throws Exception {
        Donor donor = new Donor();
        donor.setDonorId(donorId);
        System.out.println(donor.getDonorId());
        System.out.println(String.valueOf(donor));
        return donorDAO.delete(donor);
    }

    @Override
    public boolean updateDonor(DonorDTO donorDTO) throws Exception {
        Donor donor = new Donor();
        donor.setDonorId(donorDTO.getDonorId());
        donor.setDonorName(donorDTO.getDonorName());
        donor.setDonorEmail(donorDTO.getDonorEmail());
        donor.setDonorAddress(donorDTO.getDonorAddress());
        donor.setDonorNic(donorDTO.getDonorNic());
        donor.setBloodGroup(donorDTO.getBloodGroup());
        donor.setGender(donorDTO.getGender());
        donor.setDob(donorDTO.getDob());
        donor.setLastDonationDate(donorDTO.getLastDonationDate());
        return donorDAO.update(donor);
    }
}

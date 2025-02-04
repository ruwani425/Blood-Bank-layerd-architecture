package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.DonorBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.DonorDAO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.entity.Donor;

import java.sql.SQLException;
import java.util.ArrayList;

public class DonorBOImpl implements DonorBO {

    DonorDAO donorDAO = (DonorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONOR);
    Donor donor = new Donor();

    @Override
    public boolean addDonor(DonorDTO donorDTO)  {
        donor.setDonorId(donorDTO.getDonorId());
        donor.setDonorName(donorDTO.getDonorName());
        donor.setDonorEmail(donorDTO.getDonorEmail());
        donor.setDonorAddress(donorDTO.getDonorAddress());
        donor.setDonorNic(donorDTO.getDonorNic());
        donor.setBloodGroup(donorDTO.getBloodGroup());
        donor.setGender(donorDTO.getGender());
        donor.setDob(donorDTO.getDob());
        donor.setLastDonationDate(donorDTO.getLastDonationDate());
        try {
            return donorDAO.save(donor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteDonor(String donorId) throws Exception {
        donor.setDonorId(donorId);
        return donorDAO.delete(donor);
    }

    @Override
    public boolean updateDonor(DonorDTO donorDTO) throws Exception {
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

    @Override
    public String getNextDonorId() throws Exception {
        return donorDAO.getNewId();
    }

    @Override
    public ArrayList<DonorDTO> getAllDonors() throws Exception {
        ArrayList<Donor>donors=donorDAO.getAllData();
        ArrayList<DonorDTO> donorDTOS=new ArrayList<>();
        for (Donor donor : donors) {
            DonorDTO donorDTO=new DonorDTO();
            donorDTO.setDonorId(donor.getDonorId());
            donorDTO.setDonorName(donor.getDonorName());
            donorDTO.setDonorEmail(donor.getDonorEmail());
            donorDTO.setDonorAddress(donor.getDonorAddress());
            donorDTO.setDonorNic(donor.getDonorNic());
            donorDTO.setBloodGroup(donor.getBloodGroup());
            donorDTO.setGender(donor.getGender());
            donorDTO.setDob(donor.getDob());
            donorDTO.setLastDonationDate(donor.getLastDonationDate());
            donorDTOS.add(donorDTO);
        }
        return donorDTOS;
    }
}

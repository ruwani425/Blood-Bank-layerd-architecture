package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.DonationBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodTestDAO;
import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;
import lk.ijse.gdse.bbms.dao.custom.DonationDAO;
import lk.ijse.gdse.bbms.dao.custom.DonorDAO;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.entity.BloodTest;
import lk.ijse.gdse.bbms.entity.Donation;

import java.sql.Connection;

public class DonationBOImpl implements DonationBO {

    DonorDAO donorDAO = (DonorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONOR);
    DonationDAO donationDAO = (DonationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONATION);
    BloodTestDAO bloodTestDAO = (BloodTestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONATION);
    CampaignDAO campaignDAO = (CampaignDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAMPAIGN);

    @Override
    public boolean addDonation(DonationDTO donationDTO, String donorId) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            BloodTestDTO bloodTestDTO = getBloodTestDTO(donationDTO);

            if (donationDAO.save(new Donation(donationDTO.getDonationId(),
                    donationDTO.getCampaignId(),
                    donationDTO.getHelthCheckupId(),
                    donationDTO.getBloodGroup(),
                    donationDTO.getQty(),
                    donationDTO.getDateOfDonation()))
            ) {
                if (bloodTestDAO.save(new BloodTest(bloodTestDTO.getTestID(),
                        bloodTestDTO.getDonationID(),
                        bloodTestDTO.getCollectedDate(),
                        bloodTestDTO.getExpiryDate(),
                        bloodTestDTO.getTestResult(),
                        bloodTestDTO.getHaemoglobin(),
                        bloodTestDTO.getTestDate(),
                        bloodTestDTO.getReportSerialNum(),
                        bloodTestDTO.getPlatelets(),
                        bloodTestDTO.getRedBloodCells(),
                        bloodTestDTO.getWhiteBloodCells(),
                        bloodTestDTO.getReportImageUrl(),
                        bloodTestDTO.getBloodType(),
                        bloodTestDTO.getBloodQty()))) {
                    if (donorDAO.updateLastDonationDate(donorId, donationDTO.getDateOfDonation())) {
                        if (campaignDAO.updateCollectedUnit(donationDTO.getCampaignId(), donationDTO.getQty())) {
                            connection.commit();
                            return true;
                        } else {
                            connection.rollback();
                            return false;
                        }
                    } else {
                        connection.rollback();
                        return false;
                    }

                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private BloodTestDTO getBloodTestDTO(DonationDTO donationDTO) throws Exception {
        try {
            BloodTestDTO bloodTestDTO = new BloodTestDTO();
            bloodTestDTO.setDonationID(donationDTO.getDonationId());
            bloodTestDTO.setTestID(bloodTestDAO.getNextBloodTesdtID());
            bloodTestDTO.setCollectedDate(donationDTO.getDateOfDonation());
            bloodTestDTO.setExpiryDate(null);
            bloodTestDTO.setTestResult("PENDING");
            bloodTestDTO.setHaemoglobin(0);
            bloodTestDTO.setTestDate(null);
            bloodTestDTO.setReportSerialNum("PENDING");
            bloodTestDTO.setPlatelets(0);
            bloodTestDTO.setRedBloodCells(0);
            bloodTestDTO.setWhiteBloodCells(0);
            bloodTestDTO.setReportImageUrl("PENDING");
            bloodTestDTO.setBloodType(donationDTO.getBloodGroup());
            return bloodTestDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.DonationBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodTestDAO;
import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;
import lk.ijse.gdse.bbms.dao.custom.DonationDAO;
import lk.ijse.gdse.bbms.dao.custom.DonorDAO;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.entity.BloodTest;
import lk.ijse.gdse.bbms.entity.Campaign;
import lk.ijse.gdse.bbms.entity.Donation;
import lk.ijse.gdse.bbms.entity.Donor;

import java.sql.Connection;
import java.util.ArrayList;

public class DonationBOImpl implements DonationBO {

    private DonorDAO donorDAO = (DonorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONOR);
    private DonationDAO donationDAO = (DonationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DONATION);
    private BloodTestDAO bloodTestDAO = (BloodTestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODTEST);
    private CampaignDAO campaignDAO = (CampaignDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAMPAIGN);

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
                System.out.println("Donation added successfully");
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
                    System.out.println("blood test added successfully");
                    if (donorDAO.updateLastDonationDate(donorId, donationDTO.getDateOfDonation())) {
                        System.out.println("updated last donation successfully");
                        if (campaignDAO.updateCollectedUnit(donationDTO.getCampaignId(), donationDTO.getQty())) {
                            System.out.println("updated collected unit successfully");
                            connection.commit();
                            return true;
                        } else {
                            System.out.println("couldn't update collected unit successfully");
                            connection.rollback();
                            return false;
                        }
                    } else {
                        System.out.println("couldn't update last donation successfully");
                        connection.rollback();
                        return false;
                    }

                } else {
                    System.out.println("couldn't update blood test successfully");
                    connection.rollback();
                    return false;
                }
            } else {
                System.out.println("couldn't save blood donation successfully");
                connection.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String getNextDonationId() throws Exception {
        return donationDAO.getNewId();
    }

    @Override
    public ArrayList<DonationDTO> getAllDonations() throws Exception {
        ArrayList<DonationDTO> donationDTOS = new ArrayList<>();
        ArrayList<Donation> donations = donationDAO.getAllData();
        for (Donation donation : donations) {
            DonationDTO donationDTO = new DonationDTO();
            donationDTO.setDonationId(donation.getDonationId());
            donationDTO.setDateOfDonation(donation.getDateOfDonation());
            donationDTO.setQty(donation.getQty());
            donationDTO.setBloodGroup(donation.getBloodGroup());
            donationDTO.setCampaignId(donation.getCampaignId());
            donationDTO.setHelthCheckupId(donation.getHelthCheckupId());
            donationDTOS.add(donationDTO);
        }
        return donationDTOS;
    }

    @Override
    public DonorDTO getDonorById(String id) throws Exception {
        Donor donor = donorDAO.findById(new Donor(id));
        DonorDTO donorDTO = new DonorDTO();
        donorDTO.setDonorId(donor.getDonorId());
        donorDTO.setDonorNic(donor.getDonorNic());
        donorDTO.setDonorName(donor.getDonorName());
        donorDTO.setDonorEmail(donor.getDonorEmail());
        donorDTO.setDonorAddress(donor.getDonorAddress());
        donorDTO.setBloodGroup(donor.getBloodGroup());
        donorDTO.setGender(donor.getGender());
        donorDTO.setDob(donor.getDob());
        donorDTO.setLastDonationDate(donor.getLastDonationDate());
        return donorDTO;
    }

    @Override
    public ArrayList<String> findCampaignIds() throws Exception {
        return campaignDAO.getCampaignIDs();
    }

    @Override
    public CampaignDTO getCampaignById(String value) throws Exception {
        Campaign campaign = campaignDAO.findById(new Campaign(value));
        CampaignDTO campaignDTO = new CampaignDTO();

        campaignDTO.setCampaign_name(campaign.getCampaign_name());
        campaignDTO.setBlood_campaign_id(value);
        campaignDTO.setAddress(campaign.getAddress());
        campaignDTO.setStatus(campaign.getStatus());
        campaignDTO.setCollectedUnits(campaign.getCollectedUnits());
        campaignDTO.setEndDate(campaign.getEndDate());
        campaignDTO.setStartDate(campaign.getStartDate());

        return campaignDTO;
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

package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.BloodTestBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodStockDAO;
import lk.ijse.gdse.bbms.dao.custom.BloodTestDAO;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.entity.BloodStock;
import lk.ijse.gdse.bbms.entity.BloodTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodTestBOImpl implements BloodTestBO {
    BloodStockDAO bloodStockDAO = (BloodStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODSTOCK);
    BloodTestDAO bloodTestDAO = (BloodTestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODTEST);
    BloodTest bloodTest = new BloodTest();

    @Override
    public BloodTestDTO getBloodTestDetailById(String testID) throws SQLException {
        bloodTest.setTestID(testID);
        bloodTest = bloodTestDAO.findById(bloodTest);
        BloodTestDTO bloodTestDTO = new BloodTestDTO();

        bloodTestDTO.setTestID(bloodTest.getTestID());
        bloodTestDTO.setTestResult(bloodTest.getTestResult());
        bloodTestDTO.setTestDate(bloodTest.getTestDate());
        bloodTestDTO.setBloodType(bloodTest.getBloodType());
        bloodTestDTO.setWhiteBloodCells(bloodTest.getWhiteBloodCells());
        bloodTestDTO.setRedBloodCells(bloodTest.getRedBloodCells());
        bloodTestDTO.setBloodQty(bloodTest.getBloodQty());
        bloodTestDTO.setReportImageUrl(bloodTest.getReportImageUrl());
        bloodTestDTO.setHaemoglobin(bloodTest.getHaemoglobin());
        bloodTestDTO.setExpiryDate(bloodTest.getExpiryDate());
        bloodTestDTO.setCollectedDate(bloodTest.getCollectedDate());
        bloodTestDTO.setDonationID(bloodTest.getDonationID());
        bloodTestDTO.setPlatelets(bloodTest.getPlatelets());
        bloodTestDTO.setReportSerialNum(bloodTest.getReportSerialNum());
        return bloodTestDTO;
    }

    @Override
    public ArrayList<BloodTestDTO> getAllBloodTestsBystatus(String pending) throws SQLException, ClassNotFoundException {
        bloodTest.setTestResult(pending);
        ArrayList<BloodTestDTO> bloodTestDTOS = new ArrayList<>();
        ArrayList<BloodTest> bloodTests = bloodTestDAO.search(bloodTest);
        for (BloodTest bloodTest1 : bloodTests) {
            BloodTestDTO bloodTestDTO = new BloodTestDTO();

            bloodTestDTO.setTestID(bloodTest1.getTestID());
            bloodTestDTO.setTestResult(bloodTest1.getTestResult());
            bloodTestDTO.setTestDate(bloodTest1.getTestDate());
            bloodTestDTO.setBloodType(bloodTest1.getBloodType());
            bloodTestDTO.setWhiteBloodCells(bloodTest1.getWhiteBloodCells());
            bloodTestDTO.setRedBloodCells(bloodTest1.getRedBloodCells());
            bloodTestDTO.setBloodQty(bloodTest1.getBloodQty());
            bloodTestDTO.setBloodType(bloodTest1.getBloodType());
            bloodTestDTO.setWhiteBloodCells(bloodTest1.getWhiteBloodCells());
            bloodTestDTO.setRedBloodCells(bloodTest1.getRedBloodCells());
            bloodTestDTO.setBloodQty(bloodTest1.getBloodQty());
            bloodTestDTO.setReportImageUrl(bloodTest1.getReportImageUrl());
            bloodTestDTO.setDonationID(bloodTest1.getDonationID());
            bloodTestDTO.setPlatelets(bloodTest1.getPlatelets());
            bloodTestDTO.setReportSerialNum(bloodTest1.getReportSerialNum());
            bloodTestDTOS.add(bloodTestDTO);
        }
        return bloodTestDTOS;
    }

    @Override
    public ArrayList<BloodTestDTO> getAllBloodTests() throws SQLException, ClassNotFoundException {
        ArrayList<BloodTestDTO> bloodTestDTOS = new ArrayList<>();
        ArrayList<BloodTest> bloodTests = bloodTestDAO.getAllData();
        for (BloodTest bloodTest1 : bloodTests) {
            BloodTestDTO bloodTestDTO = new BloodTestDTO();
            bloodTestDTO.setTestID(bloodTest1.getTestID());
            bloodTestDTO.setTestResult(bloodTest1.getTestResult());
            bloodTestDTO.setTestDate(bloodTest1.getTestDate());
            bloodTestDTO.setBloodType(bloodTest1.getBloodType());
            bloodTestDTO.setWhiteBloodCells(bloodTest1.getWhiteBloodCells());
            bloodTestDTO.setRedBloodCells(bloodTest1.getRedBloodCells());
            bloodTestDTO.setBloodQty(bloodTest1.getBloodQty());
            bloodTestDTO.setBloodType(bloodTest1.getBloodType());
            bloodTestDTO.setWhiteBloodCells(bloodTest1.getWhiteBloodCells());
            bloodTestDTO.setRedBloodCells(bloodTest1.getRedBloodCells());
            bloodTestDTO.setBloodQty(bloodTest1.getBloodQty());
            bloodTestDTO.setReportImageUrl(bloodTest1.getReportImageUrl());
            bloodTestDTO.setDonationID(bloodTest1.getDonationID());
            bloodTestDTO.setPlatelets(bloodTest1.getPlatelets());
            bloodTestDTO.setReportSerialNum(bloodTest1.getReportSerialNum());
            bloodTestDTOS.add(bloodTestDTO);
        }
        return bloodTestDTOS;
    }

    @Override
    public boolean updateBloodTest(BloodTestDTO bloodTestDTO) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        BloodStock bloodStock = new BloodStock();

        bloodStock.setBloodID(bloodStockDAO.getNextBloodId());
        System.out.println(bloodStockDAO.getNextBloodId());

        bloodStock.setTestID(bloodTestDTO.getTestID());
        bloodStock.setBloodGroup(bloodTestDTO.getBloodType());
        bloodStock.setRedBloodCells(bloodTestDTO.getRedBloodCells());
        bloodStock.setWhiteBloodCells(bloodTestDTO.getWhiteBloodCells());
        bloodStock.setHaemoglobin(bloodTestDTO.getHaemoglobin());
        bloodStock.setPlatelets(bloodTestDTO.getPlatelets());
        bloodStock.setExpiryDate(bloodTestDTO.getExpiryDate());
        bloodStock.setQty(bloodTestDTO.getBloodQty());


        if (bloodTestDTO.getTestResult().equals("PASS")) {
            bloodStock.setStatus("VERIFIED");
        } else {
            bloodStock.setStatus("NOT_VERIFIED");
        }

        try {
            if (bloodTestDAO.update(new BloodTest(
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
                    bloodTestDTO.getBloodQty(),
                    bloodTestDTO.getTestID()
            ))) {
                if (bloodStockDAO.save(bloodStock)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}

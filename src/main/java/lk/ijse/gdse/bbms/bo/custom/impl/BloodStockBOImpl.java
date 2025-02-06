package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.BloodStockBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.*;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.entity.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BloodStockBOImpl implements BloodStockBO {
    private BloodStockDAO bloodStockDAO = (BloodStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODSTOCK);
    private BloodRequestDetailDAO bloodRequestDetailDAO = (BloodRequestDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUESTDETAIL);
    private ReservedBloodDAO reservedBloodDAO = (ReservedBloodDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVEDBLOOD);
    private BloodRequestDAO bloodRequestDAO = (BloodRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUEST);
    private HospitalDAO hospitalDAO = (HospitalDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOSPITAL);

    @Override
    public ArrayList<BloodStockDTO> getAllBloodStocks(String status) throws SQLException, ClassNotFoundException {

        BloodStock bloodStock = new BloodStock();

        bloodStock.setStatus(status);
        ArrayList<BloodStockDTO> bloodStockDTOs = new ArrayList<>();
        ArrayList<BloodStock> bloodStocks = bloodStockDAO.search(bloodStock);
        for (BloodStock bloodStock1 : bloodStocks) {
            BloodStockDTO bloodStockDTO = new BloodStockDTO();
            bloodStockDTO.setRedBloodCells(bloodStock1.getRedBloodCells());
            bloodStockDTO.setWhiteBloodCells(bloodStock1.getWhiteBloodCells());
            bloodStockDTO.setBloodGroup(bloodStock1.getBloodGroup());
            bloodStockDTO.setBloodID(bloodStock1.getBloodID());
            bloodStockDTO.setPlatelets(bloodStock1.getPlatelets());
            bloodStockDTO.setStatus(status);
            bloodStockDTO.setHaemoglobin(bloodStock1.getHaemoglobin());
            bloodStockDTO.setTestID(bloodStock1.getTestID());
            bloodStockDTO.setQty(bloodStock1.getQty());
            bloodStockDTO.setExpiryDate(bloodStock1.getExpiryDate());
            bloodStockDTOs.add(bloodStockDTO);
        }
        return bloodStockDTOs;
    }

    @Override
    public ArrayList<BloodStockDTO> getExpiredBloodStocks() throws Exception {
        BloodStock bloodStock = new BloodStock();
        bloodStock.setStatus("Expired");
        ArrayList<BloodStockDTO> bloodStockDTOs = new ArrayList<>();
        ArrayList<BloodStock> bloodStocks = bloodStockDAO.search(bloodStock);
        for (BloodStock bloodStock1 : bloodStocks) {
            BloodStockDTO bloodStockDTO = new BloodStockDTO();
            bloodStockDTO.setRedBloodCells(bloodStock1.getRedBloodCells());
            bloodStockDTO.setWhiteBloodCells(bloodStock1.getWhiteBloodCells());
            bloodStockDTO.setBloodGroup(bloodStock1.getBloodGroup());
            bloodStockDTO.setBloodID(bloodStock1.getBloodID());
            bloodStockDTO.setPlatelets(bloodStock1.getPlatelets());
            bloodStockDTO.setStatus("Expired");
            bloodStockDTO.setHaemoglobin(bloodStock1.getHaemoglobin());
            bloodStockDTO.setTestID(bloodStock1.getTestID());
            bloodStockDTO.setQty(bloodStock1.getQty());
            bloodStockDTO.setExpiryDate(bloodStock1.getExpiryDate());
            bloodStockDTOs.add(bloodStockDTO);
        }
        return bloodStockDTOs;
    }

    @Override
    public boolean addBloodIssue(BloodRequestTM bloodRequestTM, ArrayList<BloodIssueTM> issuedBlood) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            for (BloodIssueTM issuedBlood1 : issuedBlood) {
                if (bloodRequestDetailDAO.save(new BloodRequestDetail(bloodRequestTM.getRequestId(),
                        issuedBlood1.getBloodIssueID()))
                ) {
                    if (reservedBloodDAO.save(new ReservedBlood(reservedBloodDAO.getNewId(),
                            issuedBlood1.getBloodID(),
                            bloodRequestTM.getHospitalId(),
                            Date.valueOf(LocalDate.now()),
                            issuedBlood1.getBloodQty()))) {
                        if (bloodStockDAO.updateBloodStockStatusAfterIssued(issuedBlood1.getBloodID())) {
                            if (bloodRequestDAO.update(new BloodRequest(bloodRequestTM.getRequestId()))) {
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
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public HospitalDTO getHospitalById(String hospitalId) throws SQLException, ClassNotFoundException {
        Hospital hospital = hospitalDAO.findById(new Hospital(hospitalId));

        if (hospital != null) {
            return new HospitalDTO(
                    hospital.getHospitalId(),
                    hospital.getHospitalName(),
                    hospital.getHospitalAddress(),
                    hospital.getContactNumber(),
                    hospital.getEmail(),
                    hospital.getType()
            );
        }
        return null;
    }
}

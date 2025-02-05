package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.BloodStockBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
import lk.ijse.gdse.bbms.dao.custom.BloodRequestDetailDAO;
import lk.ijse.gdse.bbms.dao.custom.BloodStockDAO;
import lk.ijse.gdse.bbms.dao.custom.ReservedBloodDAO;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.entity.BloodRequest;
import lk.ijse.gdse.bbms.entity.BloodRequestDetail;
import lk.ijse.gdse.bbms.entity.BloodStock;
import lk.ijse.gdse.bbms.entity.ReservedBlood;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BloodStockBOImpl implements BloodStockBO {
    BloodStockDAO bloodStockDAO = (BloodStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODSTOCK);
    BloodRequestDetailDAO bloodRequestDetailDAO = (BloodRequestDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUESTDETAIL);
    ReservedBloodDAO reservedBloodDAO = (ReservedBloodDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVEDBLOOD);
    BloodRequestDAO bloodRequestDAO = (BloodRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUEST);

    BloodStock bloodStock = new BloodStock();

    @Override
    public ArrayList<BloodStockDTO> getAllBloodStocks(String status) throws SQLException, ClassNotFoundException {
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
}

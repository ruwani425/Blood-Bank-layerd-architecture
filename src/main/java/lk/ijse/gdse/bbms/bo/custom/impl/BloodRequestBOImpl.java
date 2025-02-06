package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.BloodRequestBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
import lk.ijse.gdse.bbms.dao.custom.HospitalDAO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.entity.BloodRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public class BloodRequestBOImpl implements BloodRequestBO {

    private final BloodRequestDAO bloodRequestDAO = (BloodRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUEST);
    private final HospitalDAO hospitalDAO = (HospitalDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOSPITAL);

    @Override
    public String getNextRequestId() throws Exception {
        return bloodRequestDAO.getNewId();
    }

    @Override
    public boolean addBloodRequest(BloodRequestDTO bloodRequestDTO) throws Exception {
        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setRequestId(getNextRequestId());
        bloodRequest.setBloodType(bloodRequestDTO.getBloodType());
        bloodRequest.setDateOfRequest(bloodRequestDTO.getDateOfRequest());
        bloodRequest.setQty(bloodRequestDTO.getQty());
        bloodRequest.setHospitalId(bloodRequestDTO.getHospitalId());
        bloodRequest.setStatus(bloodRequestDTO.getStatus());
        return bloodRequestDAO.save(bloodRequest);
    }

    @Override
    public ArrayList<BloodRequestDTO> getAllRequests(String pending) throws Exception {
        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setStatus(pending);
        ArrayList<BloodRequestDTO> bloodRequestDTOS = new ArrayList<>();
        ArrayList<BloodRequest> bloodRequests = bloodRequestDAO.search(bloodRequest);
        for (BloodRequest bloodRequest1 : bloodRequests) {
            BloodRequestDTO bloodRequestDTO = new BloodRequestDTO();
            bloodRequestDTO.setRequestId(bloodRequest1.getRequestId());
            bloodRequestDTO.setBloodType(bloodRequest1.getBloodType());
            bloodRequestDTO.setDateOfRequest(bloodRequest1.getDateOfRequest());
            bloodRequestDTO.setQty(bloodRequest1.getQty());
            bloodRequestDTO.setHospitalId(bloodRequest1.getHospitalId());
            bloodRequestDTO.setStatus(bloodRequest1.getStatus());
            bloodRequestDTOS.add(bloodRequestDTO);
        }
        return bloodRequestDTOS;
    }

    @Override
    public ArrayList<String> getAllHospitalIDs() throws SQLException {
        System.out.println("getAllHospitalIDs");
        return hospitalDAO.getAllHospitalIDs();
    }
}

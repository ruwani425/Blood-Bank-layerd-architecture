package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.BloodRequestBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.entity.BloodRequest;

import java.util.ArrayList;

public class BloodRequestBOImpl implements BloodRequestBO {

    BloodRequestDAO bloodRequestDAO = (BloodRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUEST);
    BloodRequest bloodRequest = new BloodRequest();

    @Override
    public String getNextRequestId() throws Exception {
        return bloodRequestDAO.getNewId();
    }

    @Override
    public boolean addBloodRequest(BloodRequestDTO bloodRequestDTO) throws Exception {
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
        ArrayList<BloodRequestDTO> bloodRequestDTOS = new ArrayList<>();
        ArrayList<BloodRequest>bloodRequests=bloodRequestDAO.search(pending);
        for (BloodRequest bloodRequest : bloodRequests) {
            BloodRequestDTO bloodRequestDTO = new BloodRequestDTO();
            bloodRequestDTO.setRequestId(bloodRequest.getRequestId());
            bloodRequestDTO.setBloodType(bloodRequest.getBloodType());
            bloodRequestDTO.setDateOfRequest(bloodRequest.getDateOfRequest());
            bloodRequestDTO.setQty(bloodRequest.getQty());
            bloodRequestDTO.setHospitalId(bloodRequest.getHospitalId());
            bloodRequestDTO.setStatus(bloodRequest.getStatus());
            bloodRequestDTOS.add(bloodRequestDTO);
        }
        return bloodRequestDTOS;
    }
}

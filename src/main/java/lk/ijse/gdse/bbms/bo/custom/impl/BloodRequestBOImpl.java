package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.BloodRequestBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.entity.BloodRequest;

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
}

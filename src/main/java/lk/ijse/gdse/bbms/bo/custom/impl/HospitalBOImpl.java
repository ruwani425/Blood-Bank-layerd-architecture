package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.HospitalBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.HospitalDAO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.entity.Hospital;

public class HospitalBOImpl implements HospitalBO {
    HospitalDAO hospitalDAO = (HospitalDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOSPITAL);
    Hospital hospital = new Hospital();

    @Override
    public String getNextHospitalId() throws Exception {
        return hospitalDAO.getNewId();
    }

    @Override
    public boolean addHospital(HospitalDTO hospitalDTO) throws Exception {
        hospital.setHospitalId(hospitalDTO.getHospitalId());
        hospital.setHospitalAddress(hospitalDTO.getHospitalAddress());
        hospital.setHospitalName(hospitalDTO.getHospitalName());
        hospital.setType(hospitalDTO.getType());
        hospital.setEmail(hospitalDTO.getEmail());
        hospital.setContactNumber(hospitalDTO.getContactNumber());
        return hospitalDAO.save(hospital);
    }

    @Override
    public boolean deleteHospital(String hospitalId) throws Exception {
        hospital.setHospitalId(hospitalId);
        return hospitalDAO.delete(hospital);
    }

    @Override
    public boolean updateHospital(HospitalDTO hospitalDTO) throws Exception {
        hospital.setHospitalId(hospitalDTO.getHospitalId());
        hospital.setHospitalAddress(hospitalDTO.getHospitalAddress());
        hospital.setHospitalName(hospitalDTO.getHospitalName());
        hospital.setType(hospitalDTO.getType());
        hospital.setEmail(hospitalDTO.getEmail());
        hospital.setContactNumber(hospitalDTO.getContactNumber());
        return hospitalDAO.update(hospital);
    }
}

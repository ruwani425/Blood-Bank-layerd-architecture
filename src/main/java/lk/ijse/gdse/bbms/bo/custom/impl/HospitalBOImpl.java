package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.HospitalBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.HospitalDAO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.entity.Hospital;

import java.util.ArrayList;

public class HospitalBOImpl implements HospitalBO {
    private HospitalDAO hospitalDAO = (HospitalDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.HOSPITAL);

    @Override
    public String getNextHospitalId() throws Exception {
        return hospitalDAO.getNewId();
    }

    @Override
    public boolean addHospital(HospitalDTO hospitalDTO) throws Exception {
        Hospital hospital = new Hospital();
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
        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        return hospitalDAO.delete(hospital);
    }

    @Override
    public boolean updateHospital(HospitalDTO hospitalDTO) throws Exception {
        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalDTO.getHospitalId());
        hospital.setHospitalAddress(hospitalDTO.getHospitalAddress());
        hospital.setHospitalName(hospitalDTO.getHospitalName());
        hospital.setType(hospitalDTO.getType());
        hospital.setEmail(hospitalDTO.getEmail());
        hospital.setContactNumber(hospitalDTO.getContactNumber());
        return hospitalDAO.update(hospital);
    }

    @Override
    public ArrayList<HospitalDTO> getAllHospitals() throws Exception {
        ArrayList<HospitalDTO> hospitalDTOS = new ArrayList<>();
        ArrayList<Hospital> hospitals = hospitalDAO.getAllData();
        for (Hospital hospital : hospitals) {
            HospitalDTO hospitalDTO = new HospitalDTO();
            hospitalDTO.setHospitalId(hospital.getHospitalId());
            hospitalDTO.setHospitalAddress(hospital.getHospitalAddress());
            hospitalDTO.setHospitalName(hospital.getHospitalName());
            hospitalDTO.setType(hospital.getType());
            hospitalDTO.setEmail(hospital.getEmail());
            hospitalDTO.setContactNumber(hospital.getContactNumber());
            hospitalDTOS.add(hospitalDTO);
        }
        return hospitalDTOS;
    }
}

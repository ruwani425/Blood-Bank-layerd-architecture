package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HospitalModel {
    public String getNextHospitalId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Hospital_id from Hospital order by Hospital_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last hospital ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("H%03d", newIdIndex); // Return the new Hospital ID in format Hnnn
        }
        return "H001"; // Return the default hospital ID if no data is found
    }

    public ArrayList<HospitalDTO> getAllHospitals() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Hospital");

        ArrayList<HospitalDTO> hospitalDTOS = new ArrayList<>();

        while (rst.next()) {
            HospitalDTO hospitalDTO = new HospitalDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            hospitalDTOS.add(hospitalDTO);
        }
        return hospitalDTOS;
    }

    public boolean addHospital(HospitalDTO hospitalDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Hospital values (?,?,?,?,?,?)",
                hospitalDTO.getHospitalId(),
                hospitalDTO.getHospitalName(),
                hospitalDTO.getHospitalAddress(),
                hospitalDTO.getContactNumber(),
                hospitalDTO.getEmail(),
                hospitalDTO.getType()
        );
    }

    public boolean deleteHospital(String hospitalId) throws SQLException {
        return CrudUtil.execute("delete from Hospital where Hospital_id=?", hospitalId);
    }

    public boolean updateHospital(HospitalDTO hospitalDTO) throws SQLException {
        return CrudUtil.execute(
                "update Hospital set Name=?, Address=?, Contact_number=?, Email=?, Type=? where Hospital_id=?",
                hospitalDTO.getHospitalName(),
                hospitalDTO.getHospitalAddress(),
                hospitalDTO.getContactNumber(),
                hospitalDTO.getEmail(),
                hospitalDTO.getType(),
                hospitalDTO.getHospitalId()
        );
    }

    public HospitalDTO getHospitalById(String hospitalId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Hospital where Hospital_id=?", hospitalId);

        if (rst.next()) {
            return new HospitalDTO(
                    rst.getString("Hospital_id"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Contact_number"),
                    rst.getString("Email"),
                    rst.getString("Type")
            );
        }
        return null;
    }

    public ArrayList<String> getAllHospitalIDs() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Hospital_id from Hospital");

        ArrayList<String> hospitalIds = new ArrayList<>();

        while (rst.next()) {
            hospitalIds.add(rst.getString("Hospital_id"));
        }
        return hospitalIds;
    }

}

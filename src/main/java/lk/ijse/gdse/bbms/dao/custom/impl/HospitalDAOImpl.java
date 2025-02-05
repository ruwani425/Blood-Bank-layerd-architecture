package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.HospitalDAO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.entity.Hospital;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HospitalDAOImpl implements HospitalDAO {
    @Override
    public ArrayList<Hospital> getAllData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Hospital");

        ArrayList<Hospital> hospitals = new ArrayList<>();

        while (rst.next()) {
            Hospital hospital = new Hospital(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            hospitals.add(hospital);
        }
        return hospitals;
    }

    @Override
    public boolean save(Hospital hospital) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Hospital values (?,?,?,?,?,?)",
                hospital.getHospitalId(),
                hospital.getHospitalName(),
                hospital.getHospitalAddress(),
                hospital.getContactNumber(),
                hospital.getEmail(),
                hospital.getType()
        );
    }

    @Override
    public boolean update(Hospital hospital) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update Hospital set Name=?, Address=?, Contact_number=?, Email=?, Type=? where Hospital_id=?",
                hospital.getHospitalName(),
                hospital.getHospitalAddress(),
                hospital.getContactNumber(),
                hospital.getEmail(),
                hospital.getType(),
                hospital.getHospitalId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Hospital hospital) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Hospital where Hospital_id=?", hospital.getHospitalId());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
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

    @Override
    public ArrayList<Hospital> search(Hospital newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllHospitalIDs() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Hospital_id from Hospital");

        ArrayList<String> hospitalIds = new ArrayList<>();

        while (rst.next()) {
            hospitalIds.add(rst.getString("Hospital_id"));
        }
        return hospitalIds;
    }

    @Override
    public Hospital findById(Hospital hospital) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Hospital where Hospital_id=?", hospital.getHospitalId());

        if (rst.next()) {
            return new Hospital(
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
}

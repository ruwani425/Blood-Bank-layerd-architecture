package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.DonorDAO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.entity.Donor;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonorDAOImpl implements DonorDAO {
    @Override
    public ArrayList<Donor> getAllData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Donor");

        ArrayList<Donor> donors = new ArrayList<>();

        while (rst.next()) {
            Donor donor = new Donor(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDate(8),
                    rst.getDate(9)
            );
            donors.add(donor);
        }
        return donors;
    }

    @Override
    public boolean save(Donor donorEntity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Donor values (?,?,?,?,?,?,?,?,?)",
                donorEntity.getDonorId(),
                donorEntity.getDonorName(),
                donorEntity.getDonorNic(),
                donorEntity.getDonorAddress(),
                donorEntity.getDonorEmail(),
                donorEntity.getBloodGroup(),
                donorEntity.getGender(),
                donorEntity.getDob(),
                donorEntity.getLastDonationDate()
        );
    }

    @Override
    public boolean update(Donor donor) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update Donor set Name=?, Donor_nic=?, Address=?, E_mail=?,Blood_group=?,Gender=?,Dob=?,Last_donation_date=? where Donor_id=?",
                donor.getDonorName(),
                donor.getDonorNic(),
                donor.getDonorAddress(),
                donor.getDonorEmail(),
                donor.getBloodGroup(),
                donor.getGender(),
                donor.getDob(),
                donor.getLastDonationDate(),
                donor.getDonorId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Donor donor) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Donor where Donor_id=?", donor.getDonorId());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select Donor_id from Donor order by Donor_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("D%03d", newIdIndex); // Return the new Donor ID in format Dnnn
        }
        return "D001"; // Return the default customer ID if no data is found
    }

    @Override
    public ArrayList<Donor> search(Donor newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Donor findById(Donor donor) throws SQLException {
        System.out.println(donor.getDonorId());
        ResultSet rst = CrudUtil.execute("select * from Donor where Donor_id=?", donor.getDonorId());

        if (rst.next()) {
            return new Donor(
                    rst.getString("Donor_id"),
                    rst.getString(2), // Name (not required in this method)
                    rst.getString(3),  // NIC
                    rst.getString(4), // Address
                    rst.getString(5), // E_mail
                    rst.getString("Blood_group"), // Blood_group
                    rst.getString("Gender"), // Gender
                    rst.getDate("Dob"), // Date of Birth
                    rst.getDate("Last_donation_date") // Last Donation Date
            );
        }
        return null; // Return null if no donor with the given NIC is found
    }

    @Override
    public boolean updateLastDonationDate(String donorId, Date dateOfDonation) throws Exception {
        return CrudUtil.execute(
                "UPDATE Donor SET Last_donation_date = ? WHERE Donor_id = ?",
                dateOfDonation,
                donorId
        );
    }

    @Override
    public Donor findDonorByNic(Donor donor) throws Exception {
        ResultSet rst = CrudUtil.execute("select Donor_id, Dob, Last_donation_date,Blood_group,gender from Donor where Donor_nic=?", donor.getDonorNic());

        if (rst.next()) {
            return new Donor(
                    rst.getString("Donor_id"),
                    null, // Name (not required in this method)
                    donor.getDonorNic(),  // NIC
                    null, // Address
                    null, // E_mail
                    rst.getString("Blood_group"), // Blood_group
                    rst.getString("Gender"), // Gender
                    rst.getDate("Dob"), // Date of Birth
                    rst.getDate("Last_donation_date") // Last Donation Date
            );
        }
        return null; // Return null if no donor with the given NIC is found
    }
}

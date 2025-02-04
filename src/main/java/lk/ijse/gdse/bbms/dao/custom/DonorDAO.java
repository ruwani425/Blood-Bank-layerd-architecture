package lk.ijse.gdse.bbms.dao.custom;

import lk.ijse.gdse.bbms.dao.CrudDAO;
import lk.ijse.gdse.bbms.dao.SuperDAO;
import lk.ijse.gdse.bbms.entity.Donor;

import java.sql.Date;
import java.sql.SQLException;

public interface DonorDAO extends SuperDAO, CrudDAO <Donor> {
    boolean updateLastDonationDate(String donorId, Date dateOfDonation)throws Exception;
}

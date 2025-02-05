package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.HomeBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.BloodRequestDAO;
import lk.ijse.gdse.bbms.dao.custom.BloodStockDAO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeBOImpl implements HomeBO {

    BloodStockDAO bloodStockDAO = (BloodStockDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODSTOCK);
    BloodRequestDAO bloodRequestDAO = (BloodRequestDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BLOODREQUEST);

    @Override
    public int getTotalBloodIDCount() throws Exception {
        return bloodStockDAO.getTotalBloodIDCount();
    }

    @Override
    public int getTotalIssuedBloodIDCount() throws Exception {
        return bloodStockDAO.getTotalIssuedBloodIDCount();
    }

    @Override
    public int getTotalRequestBloodCount() throws Exception {
        return bloodRequestDAO.getTotalRequestBloodCount();
    }
}

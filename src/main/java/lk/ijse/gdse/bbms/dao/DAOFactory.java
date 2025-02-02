package lk.ijse.gdse.bbms.dao;

import lk.ijse.gdse.bbms.dao.custom.impl.CampaignDAOImpl;
import lk.ijse.gdse.bbms.dao.custom.impl.DonorDAOImpl;
import lk.ijse.gdse.bbms.dao.custom.impl.EmployeeDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        DONOR, CAMPAIGN, EMPLOYEE
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case DONOR:
                return new DonorDAOImpl();
            case CAMPAIGN:
                return new CampaignDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            default:
                return null;
        }
    }
}

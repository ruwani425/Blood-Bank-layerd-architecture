package lk.ijse.gdse.bbms.dao;

import lk.ijse.gdse.bbms.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        DONOR, CAMPAIGN, EMPLOYEE, SUPPLIER, INVENTORY, HOSPITAL, HEALTHCHECKUP, BLOODREQUEST
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case DONOR:
                return new DonorDAOImpl();
            case CAMPAIGN:
                return new CampaignDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case HOSPITAL:
                return new HospitalDAOImpl();
            case HEALTHCHECKUP:
                return new HealthCheckupDAOImpl();
            case BLOODREQUEST:
                return new BloodRequestDAOImpl();
            default:
                return null;
        }
    }
}

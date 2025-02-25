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
        DONOR, CAMPAIGN, EMPLOYEE, SUPPLIER, INVENTORY, HOSPITAL, HEALTHCHECKUP, BLOODREQUEST, DONATION, BLOODTEST, BLOODSTOCK, BLOODREQUESTDETAIL, RESERVEDBLOOD, SUPPLIERINVENTORY
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
            case DONATION:
                return new DonationDAOImpl();
            case BLOODTEST:
                return new BloodTestDAOImpl();
            case BLOODSTOCK:
                return new BloodStockDAOImpl();
            case BLOODREQUESTDETAIL:
                return new BloodRequestDetailDAOImpl();
            case RESERVEDBLOOD:
                return new ReservedBloodDAOImpl();
            case SUPPLIERINVENTORY:
                return new SupplierInventoryDAOImpl();
            default:
                return null;
        }
    }
}

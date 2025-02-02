package lk.ijse.gdse.bbms.bo;

import lk.ijse.gdse.bbms.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        DONOR, CAMPAIGN, EMPLOYEE, SUPPLIER, INVENTORY, HOSPITAL, HEALTHCHECKUP, BLOODREQUEST
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case DONOR:
                return new DonorBOImpl();
            case CAMPAIGN:
                return new CampaignBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case HOSPITAL:
                return new HospitalBOImpl();
            case HEALTHCHECKUP:
                return new HealthCheckUpBOImpl();
            case BLOODREQUEST:
                return new BloodRequestBOImpl();
            default:
                return null;
        }
    }

}

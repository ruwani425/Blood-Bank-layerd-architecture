package lk.ijse.gdse.bbms.bo;

import lk.ijse.gdse.bbms.bo.custom.impl.CampaignBOImpl;
import lk.ijse.gdse.bbms.bo.custom.impl.DonorBOImpl;
import lk.ijse.gdse.bbms.bo.custom.impl.EmployeeBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        DONOR, CAMPAIGN, EMPLOYEE
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case DONOR:
                return new DonorBOImpl();
            case CAMPAIGN:
                return new CampaignBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            default:
                return null;
        }
    }

}

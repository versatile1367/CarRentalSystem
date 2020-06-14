package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class listReturn {
    private SimpleStringProperty rBrand;
    private SimpleStringProperty rId;
    private SimpleStringProperty rRent;
    private SimpleStringProperty rPledge;

    public listReturn(String brand,String id,String rent,String pledge){
        this.rBrand=new SimpleStringProperty(brand);
        this.rId=new SimpleStringProperty(id);
        this.rRent=new SimpleStringProperty(rent);
        this.rPledge=new SimpleStringProperty(pledge);
    }

    public String getrBrand() {
        return rBrand.get();
    }

    public SimpleStringProperty rBrandProperty() {
        return rBrand;
    }

    public void setrBrand(String rBrand) {
        this.rBrand.set(rBrand);
    }

    public String getrId() {
        return rId.get();
    }

    public SimpleStringProperty rIdProperty() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId.set(rId);
    }

    public String getrRent() {
        return rRent.get();
    }

    public SimpleStringProperty rRentProperty() {
        return rRent;
    }

    public void setrRent(String rRent) {
        this.rRent.set(rRent);
    }

    public String getrPledge() {
        return rPledge.get();
    }

    public SimpleStringProperty rPledgeProperty() {
        return rPledge;
    }

    public void setrPledge(String rPledge) {
        this.rPledge.set(rPledge);
    }
}

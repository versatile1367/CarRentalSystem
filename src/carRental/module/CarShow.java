package carRental.module;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CarShow {
    private SimpleStringProperty Cid;
    private SimpleStringProperty Cbrand;
    private SimpleStringProperty Ccid;
    private SimpleStringProperty Cstatus;
    private SimpleStringProperty Crent;
    private SimpleStringProperty Cpledge;
    private SimpleStringProperty Cvalid;
    private SimpleStringProperty Ctime;

    public CarShow(String id,String brand,String cid,String status,String rent,String pledge,String valid,String time){
        this.Cid=new SimpleStringProperty(id);
        this.Cbrand=new SimpleStringProperty(brand);
        this.Ccid=new SimpleStringProperty(cid);
        this.Cstatus=new SimpleStringProperty(status);
        this.Crent=new SimpleStringProperty(rent);
        this.Cpledge=new SimpleStringProperty(pledge);
        this.Cvalid=new SimpleStringProperty(valid);
        this.Ctime=new SimpleStringProperty(time);
    }

    public String getCid() {
        return Cid.get();
    }

    public SimpleStringProperty cidProperty() {
        return Cid;
    }

    public void setCid(String cid) {
        this.Cid.set(cid);
    }

    public String getCbrand() {
        return Cbrand.get();
    }

    public SimpleStringProperty cbrandProperty() {
        return Cbrand;
    }

    public void setCbrand(String cbrand) {
        this.Cbrand.set(cbrand);
    }

    public String getCcid() {
        return Ccid.get();
    }

    public SimpleStringProperty ccidProperty() {
        return Ccid;
    }

    public void setCcid(String ccid) {
        this.Ccid.set(ccid);
    }

    public String getCstatus() {
        return Cstatus.get();
    }

    public SimpleStringProperty cstatusProperty() {
        return Cstatus;
    }

    public void setCstatus(String cstatus) {
        this.Cstatus.set(cstatus);
    }

    public String getCrent() {
        return Crent.get();
    }

    public SimpleStringProperty crentProperty() {
        return Crent;
    }

    public void setCrent(String crent) {
        this.Crent.set(crent);
    }

    public String getCpledge() {
        return Cpledge.get();
    }

    public SimpleStringProperty cpledgeProperty() {
        return Cpledge;
    }

    public void setCpledge(String cpledge) {
        this.Cpledge.set(cpledge);
    }

    public String getCvalid() {
        return Cvalid.get();
    }

    public SimpleStringProperty cvalidProperty() {
        return Cvalid;
    }

    public void setCvalid(String cvalid) {
        this.Cvalid.set(cvalid);
    }

    public String getCtime() {
        return Ctime.get();
    }

    public SimpleStringProperty ctimeProperty() {
        return Ctime;
    }

    public void setCtime(String ctime) {
        this.Ctime.set(ctime);
    }
}

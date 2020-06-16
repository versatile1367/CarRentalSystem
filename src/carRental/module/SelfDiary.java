package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class SelfDiary {
    private SimpleStringProperty Did;
    private SimpleStringProperty Dbrand;
    private SimpleStringProperty Dcid;
    private SimpleStringProperty Dstuff;
    private SimpleStringProperty Dtime;
    private SimpleStringProperty Devent;
    private SimpleStringProperty Dcost;

    public SelfDiary(String id,String brand,String cid,String stuff,String time,String event,String cost){
        this.Did=new SimpleStringProperty(id);
        this.Dbrand=new SimpleStringProperty(brand);
        this.Dcid=new SimpleStringProperty(cid);
        this.Dstuff=new SimpleStringProperty(stuff);
        this.Dtime=new SimpleStringProperty(time);
        this.Devent=new SimpleStringProperty(event);
        this.Dcost=new SimpleStringProperty(cost);
    }

    public String getDid() {
        return Did.get();
    }

    public SimpleStringProperty didProperty() {
        return Did;
    }

    public void setDid(String did) {
        this.Did.set(did);
    }

    public String getDbrand() {
        return Dbrand.get();
    }

    public SimpleStringProperty dbrandProperty() {
        return Dbrand;
    }

    public void setDbrand(String dbrand) {
        this.Dbrand.set(dbrand);
    }

    public String getDcid() {
        return Dcid.get();
    }

    public SimpleStringProperty dcidProperty() {
        return Dcid;
    }

    public void setDcid(String dcid) {
        this.Dcid.set(dcid);
    }

    public String getDstuff() {
        return Dstuff.get();
    }

    public SimpleStringProperty dstuffProperty() {
        return Dstuff;
    }

    public void setDstuff(String dstuff) {
        this.Dstuff.set(dstuff);
    }

    public String getDtime() {
        return Dtime.get();
    }

    public SimpleStringProperty dtimeProperty() {
        return Dtime;
    }

    public void setDtime(String dtime) {
        this.Dtime.set(dtime);
    }

    public String getDevent() {
        return Devent.get();
    }

    public SimpleStringProperty deventProperty() {
        return Devent;
    }

    public void setDevent(String devent) {
        this.Devent.set(devent);
    }

    public String getDcost() {
        return Dcost.get();
    }

    public SimpleStringProperty dcostProperty() {
        return Dcost;
    }

    public void setDcost(String dcost) {
        this.Dcost.set(dcost);
    }
}

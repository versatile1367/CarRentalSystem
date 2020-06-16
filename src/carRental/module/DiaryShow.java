package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class DiaryShow {
    private SimpleStringProperty Did;
    private SimpleStringProperty Dcarid;
    private SimpleStringProperty Dcusid;
    private SimpleStringProperty Dstuffid;
    private SimpleStringProperty Dtime;
    private SimpleStringProperty Devent;
    private SimpleStringProperty Dcost;
    public DiaryShow(String id,String carid,String cusid,String stuffid,String time,String event,String cost){
        this.Did=new SimpleStringProperty(id);
        this.Dcarid=new SimpleStringProperty(carid);
        this.Dcusid=new SimpleStringProperty(cusid);
        this.Dstuffid=new SimpleStringProperty(stuffid);
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

    public String getDcarid() {
        return Dcarid.get();
    }

    public SimpleStringProperty dcaridProperty() {
        return Dcarid;
    }

    public void setDcarid(String dcarid) {
        this.Dcarid.set(dcarid);
    }

    public String getDcusid() {
        return Dcusid.get();
    }

    public SimpleStringProperty dcusidProperty() {
        return Dcusid;
    }

    public void setDcusid(String dcusid) {
        this.Dcusid.set(dcusid);
    }

    public String getDstuffid() {
        return Dstuffid.get();
    }

    public SimpleStringProperty dstuffidProperty() {
        return Dstuffid;
    }

    public void setDstuffid(String dstuffid) {
        this.Dstuffid.set(dstuffid);
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

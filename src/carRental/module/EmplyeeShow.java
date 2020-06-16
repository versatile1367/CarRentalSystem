package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class EmplyeeShow {
    private SimpleStringProperty Eid;
    private SimpleStringProperty Ename;
    private SimpleStringProperty Elevel;
    private SimpleStringProperty Epwd;
    private SimpleStringProperty Etime;

    public EmplyeeShow(String id,String name,String level,String pwd,String time){
        this.Eid=new SimpleStringProperty(id);
        this.Ename=new SimpleStringProperty(name);
        this.Elevel=new SimpleStringProperty(level);
        this.Epwd=new SimpleStringProperty(pwd);
        this.Etime=new SimpleStringProperty(time);
    }

    public String getEid() {
        return Eid.get();
    }

    public SimpleStringProperty eidProperty() {
        return Eid;
    }

    public void setEid(String eid) {
        this.Eid.set(eid);
    }

    public String getEname() {
        return Ename.get();
    }

    public SimpleStringProperty enameProperty() {
        return Ename;
    }

    public void setEname(String ename) {
        this.Ename.set(ename);
    }

    public String getElevel() {
        return Elevel.get();
    }

    public SimpleStringProperty elevelProperty() {
        return Elevel;
    }

    public void setElevel(String elevel) {
        this.Elevel.set(elevel);
    }

    public String getEpwd() {
        return Epwd.get();
    }

    public SimpleStringProperty epwdProperty() {
        return Epwd;
    }

    public void setEpwd(String epwd) {
        this.Epwd.set(epwd);
    }

    public String getEtime() {
        return Etime.get();
    }

    public SimpleStringProperty etimeProperty() {
        return Etime;
    }

    public void setEtime(String etime) {
        this.Etime.set(etime);
    }
}

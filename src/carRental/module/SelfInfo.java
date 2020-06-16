package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class SelfInfo {
    private SimpleStringProperty Sid;
    private SimpleStringProperty Sname;
    private SimpleStringProperty Smember;
    private SimpleStringProperty Slevel;
    private SimpleStringProperty Stime;

    public SelfInfo(String id,String name,String member,String level,String time){
        this.Sid=new SimpleStringProperty(id);
        this.Sname=new SimpleStringProperty(name);
        this.Smember=new SimpleStringProperty(member);
        this.Slevel=new SimpleStringProperty(level);
        this.Stime=new SimpleStringProperty(time);
    }

    public String getSid() {
        return Sid.get();
    }

    public SimpleStringProperty sidProperty() {
        return Sid;
    }

    public void setSid(String sid) {
        this.Sid.set(sid);
    }

    public String getSname() {
        return Sname.get();
    }

    public SimpleStringProperty snameProperty() {
        return Sname;
    }

    public void setSname(String sname) {
        this.Sname.set(sname);
    }

    public String getSmember() {
        return Smember.get();
    }

    public SimpleStringProperty smemberProperty() {
        return Smember;
    }

    public void setSmember(String smember) {
        this.Smember.set(smember);
    }

    public String getSlevel() {
        return Slevel.get();
    }

    public SimpleStringProperty slevelProperty() {
        return Slevel;
    }

    public void setSlevel(String slevel) {
        this.Slevel.set(slevel);
    }

    public String getStime() {
        return Stime.get();
    }

    public SimpleStringProperty stimeProperty() {
        return Stime;
    }

    public void setStime(String stime) {
        this.Stime.set(stime);
    }
}

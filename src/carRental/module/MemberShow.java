package carRental.module;

import javafx.beans.property.SimpleStringProperty;

public class MemberShow  {
    private SimpleStringProperty Cusid;
    private SimpleStringProperty Cusname;
    private SimpleStringProperty Cuspwd;
    private SimpleStringProperty Cuslevel;
    private SimpleStringProperty Custime;

    public MemberShow(String id,String name,String pwd,String level,String time){
        this.Cusid=new SimpleStringProperty(id);
        this.Cusname=new SimpleStringProperty(name);
        this.Cuspwd=new SimpleStringProperty(pwd);
        this.Cuslevel=new SimpleStringProperty(level);
        this.Custime=new SimpleStringProperty(time);
    }

    public String getCusid() {
        return Cusid.get();
    }

    public SimpleStringProperty cusidProperty() {
        return Cusid;
    }

    public void setCusid(String cusid) {
        this.Cusid.set(cusid);
    }

    public String getCusname() {
        return Cusname.get();
    }

    public SimpleStringProperty cusnameProperty() {
        return Cusname;
    }

    public void setCusname(String cusname) {
        this.Cusname.set(cusname);
    }

    public String getCuspwd() {
        return Cuspwd.get();
    }

    public SimpleStringProperty cuspwdProperty() {
        return Cuspwd;
    }

    public void setCuspwd(String cuspwd) {
        this.Cuspwd.set(cuspwd);
    }

    public String getCuslevel() {
        return Cuslevel.get();
    }

    public SimpleStringProperty cuslevelProperty() {
        return Cuslevel;
    }

    public void setCuslevel(String cuslevel) {
        this.Cuslevel.set(cuslevel);
    }

    public String getCustime() {
        return Custime.get();
    }

    public SimpleStringProperty custimeProperty() {
        return Custime;
    }

    public void setCustime(String custime) {
        this.Custime.set(custime);
    }
}

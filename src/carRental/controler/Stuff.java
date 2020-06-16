package carRental.controler;

import carRental.MainApp;
import carRental.module.*;
import com.sun.tools.javac.Main;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Stuff implements Initializable {
    ObservableList<DiaryShow> diary_list= FXCollections.observableArrayList();
    ObservableList<CarShow> car_list=FXCollections.observableArrayList();
    ObservableList<MemberShow> customer_list=FXCollections.observableArrayList();
    ObservableList<MemberShow> customer_list_1=FXCollections.observableArrayList();
    ObservableList<StuffShow> stuff_list=FXCollections.observableArrayList();

    Connection conn;
    Statement stmt;
    ResultSet rs;
    String sql;

    @FXML
    TableView<DiaryShow> table_diary;
    @FXML
    TableView<CarShow> table_car;
    @FXML
    TableView<MemberShow> table_member;
    @FXML
    TableView<MemberShow> table_nmember;
    @FXML
    TableView<StuffShow> table_stuff;
    @FXML
    TableColumn<DiaryShow,String> col_dId,col_dCarId,col_dCusId,col_dStuffId,col_dTime,col_dEvent,col_dCost;
    @FXML
    TableColumn<CarShow,String> col_cId,col_cBrand,col_cCid,col_cRent,col_cPledge,col_cStatus,col_cValid,col_cTime;
    @FXML
    TableColumn<MemberShow,String>col_cusId,col_cusName,col_cusPwd,col_cusLevel,col_cusTime;
    @FXML
    TableColumn<MemberShow,String>col_ncusId,col_ncusName,col_ncusPwd,col_ncusLevel,col_ncusTime;
    @FXML
    TableColumn<StuffShow,String>col_sId,col_sName,col_sLevel,col_sTime;
    @FXML
    TextField add_dId,add_dCarId,add_dCusId,add_dStuffId,add_dTime,add_dEvent,add_dCost;
    @FXML
    TextField add_cId,add_cBrand,add_cCid,add_cStatus,add_cRent,add_cPledge,add_cValid,add_cTime;
    @FXML
    TextField add_cusId,add_cusName,add_cusPwd,add_cusLevel,add_cusTime;
    @FXML
    TextField add_ncusId,add_ncusName,add_ncusPwd,add_ncusLevel,add_ncusTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        return;
    }

    public static Connection getConnection(){
        String DB_URL = "jdbc:mysql://localhost:3306/cr_data?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
        String USER="root";
        String PASS="990113";
        Connection conn=null;
        try {
            conn= DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @FXML
    public void no_btn_logout(ActionEvent actionEvent) {
        MainApp.setInitUi();
    }

    @FXML
    public void show_diary(Event event) {
        add_dId.setPromptText("ID");
        add_dCarId.setPromptText("汽车id");
        add_dCusId.setPromptText("用户id");
        add_dStuffId.setPromptText("员工id");
        add_dEvent.setPromptText("事件");
        add_dTime.setPromptText("时间");
        add_dCost.setPromptText("金额");
        col_dId.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Did"));
        col_dCarId.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Dcarid"));
        col_dCusId.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Dcusid"));
        col_dStuffId.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Dstuffid"));
        col_dEvent.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Devent"));
        col_dTime.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Dtime"));
        col_dCost.setCellValueFactory(new PropertyValueFactory<DiaryShow,String>("Dcost"));
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM DIARY ORDER BY INFOID";
            rs=stmt.executeQuery(sql);
            String id,carid,cusid,stuffid,time,event1,event2,cost;
            diary_list.clear();
            while(rs.next()){
                id=rs.getString("INFOID");
                carid=rs.getString("CARID");
                cusid=rs.getString("CUSID");
                stuffid=rs.getString("STUFFID");
                time=rs.getString("DTIME");
                event1=rs.getString("DEVENT");
                if(event1.equals("1")) {
                    event2 = "租车";
                }
                else if(event1.equals("2"))
                {
                    event2="还车";
                }
                else if(event1.equals("3"))
                {
                    event2="损坏";
                }
                else{
                    event2="交警罚款";
                }
                cost=rs.getString("COST");
                diary_list.add(new DiaryShow(id,carid,cusid,stuffid,time,event2,cost));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_diary.setItems(diary_list);
        }
    }

    @FXML
    public void show_car(Event event) {
        add_cId.setPromptText("ID");
        add_cBrand.setPromptText("汽车品牌");
        add_cCid.setPromptText("车牌号");
        add_cStatus.setPromptText("车辆状态");
        add_cRent.setPromptText("租金");
        add_cPledge.setPromptText("押金");
        add_cValid.setPromptText("无需填写");
        add_cTime.setPromptText("无需填写");
        col_cId.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Cid"));
        col_cBrand.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Cbrand"));
        col_cCid.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Ccid"));
        col_cStatus.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Cstatus"));
        col_cRent.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Crent"));
        col_cPledge.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Cpledge"));
        col_cTime.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Ctime"));
        col_cValid.setCellValueFactory(new PropertyValueFactory<CarShow,String>("Cvalid"));
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM CARINFO ORDER BY ID";
            rs=stmt.executeQuery(sql);
            String id,brand,cid,status,rent,pledge,valid,time;
            car_list.clear();
            while(rs.next()){
                id=rs.getString("ID");
                brand=rs.getString("BRAND");
                cid=rs.getString("CID");
                status=rs.getString("STATU");
                valid=rs.getString("VALID");
                rent=rs.getString("RENT");
                pledge=rs.getString("PLEDGE");
                time=rs.getString("LASTLOGIN");
                car_list.add(new CarShow(id,brand,cid,status,rent,pledge,valid,time));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_car.setItems(car_list);
        }
    }

    @FXML
    public void show_member(Event event) {
        add_cusId.setPromptText("Id");
        add_cusName.setPromptText("姓名");
        add_cusPwd.setPromptText("密码");
        add_cusLevel.setPromptText("无需填写");
        add_cusTime.setPromptText("无需填写");
        col_cusId.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cusid"));
        col_cusName.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cusname"));
        col_cusPwd.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cuspwd"));
        col_cusLevel.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cuslevel"));
        col_cusTime.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Custime"));
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM CUSTOMERINFO WHERE MEMBER=1 ORDER BY ID";
            rs=stmt.executeQuery(sql);
            String sql1;
            String id,name,pwd,level,time;
            Connection conn1=Stuff.getConnection();
            Statement stmt1=conn1.createStatement();
            ResultSet rs1=null;
            int cusmoral;
            customer_list.clear();
            while(rs.next()){
                id=rs.getString("ID");
                name=rs.getString("CNAME");
                pwd=rs.getString("PASSWD");
                time=rs.getString("DLRQ");
                sql1="SELECT COUNT(DEVENT) AS NUM FROM DIARY WHERE CUSID='"+id+"'"+" AND (DEVENT=3 OR DEVENT=4)";
                rs1=stmt1.executeQuery(sql1);
                rs1.next();
                cusmoral=rs1.getInt("NUM");
                if(cusmoral==0){
                    level="优秀";
                }
                else if(cusmoral<3){
                    level="良好";
                }
                else if(cusmoral<5){
                    level="一般";
                }
                else{
                    level="较差";
                }
                customer_list.add(new MemberShow(id,name,pwd,level,time));
            }
            rs.close();
            stmt.close();
            conn.close();
            rs1.close();
            stmt1.close();
            conn1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_member.setItems(customer_list);
        }
    }

    @FXML
    public void show_no_member(Event event) {
        add_ncusId.setPromptText("Id");
        add_ncusName.setPromptText("姓名");
        add_ncusPwd.setPromptText("密码");
        add_ncusLevel.setPromptText("无需填写");
        add_ncusTime.setPromptText("无需填写");
        col_ncusId.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cusid"));
        col_ncusName.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cusname"));
        col_ncusPwd.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cuspwd"));
        col_ncusLevel.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Cuslevel"));
        col_ncusTime.setCellValueFactory(new PropertyValueFactory<MemberShow,String>("Custime"));
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM CUSTOMERINFO WHERE MEMBER=0 ORDER BY ID";
            rs=stmt.executeQuery(sql);
            String sql1;
            String id,name,pwd,level,time;
            Connection conn1=Stuff.getConnection();
            Statement stmt1=conn1.createStatement();
            ResultSet rs1=null;
            int cusmoral;
            customer_list_1.clear();
            while(rs.next()){
                id=rs.getString("ID");
                name=rs.getString("CNAME");
                pwd=rs.getString("PASSWD");
                time=rs.getString("DLRQ");
                sql1="SELECT COUNT(DEVENT) AS NUM FROM DIARY WHERE CUSID='"+id+"'"+" AND (DEVENT=3 OR DEVENT=4)";
                rs1=stmt1.executeQuery(sql1);
                rs1.next();
                cusmoral=rs1.getInt("NUM");
                if(cusmoral==0){
                    level="优秀";
                }
                else if(cusmoral<3){
                    level="良好";
                }
                else if(cusmoral<5){
                    level="一般";
                }
                else{
                    level="较差";
                }
                customer_list_1.add(new MemberShow(id,name,pwd,level,time));
            }
            rs.close();
            stmt.close();
            conn.close();
            rs1.close();
            stmt1.close();
            conn1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_nmember.setItems(customer_list_1);
        }
    }

    @FXML
    public void show_stuff(Event event) {
        col_sId.setCellValueFactory(new PropertyValueFactory<StuffShow,String>("Sid"));
        col_sName.setCellValueFactory(new PropertyValueFactory<StuffShow,String>("Sname"));
        col_sLevel.setCellValueFactory(new PropertyValueFactory<StuffShow,String>("Slevel"));
        col_sTime.setCellValueFactory(new PropertyValueFactory<StuffShow,String>("Stime"));
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM EMPLOYEEINFO ORDER BY ID";
            rs=stmt.executeQuery(sql);
            String id,name,level,time;
            stuff_list.clear();
            while(rs.next()){
                id=rs.getString("ID");
                name=rs.getString("ENAME");
                level=rs.getString("TITLE");
                time=rs.getString("DLRQ");
                stuff_list.add(new StuffShow(id,name,level,time));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_stuff.setItems(stuff_list);
        }
    }


    @FXML
    public void on_btn_dAdd(ActionEvent actionEvent) {
        String id=add_dId.getText();
        String carid=add_dCarId.getText();
        String cusid=add_dCusId.getText();
        String stuffid=add_dStuffId.getText();
        String time=add_dTime.getText();
        String event=add_dEvent.getText();
        String cost=add_dCost.getText();
        if(id.isEmpty()||carid.isEmpty()||cusid.isEmpty()||stuffid.isEmpty()||time.isEmpty()||event.isEmpty()||cost.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先把添加信息补充完整！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM DIARY WHERE INFOID="+id;
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"添加的流水ID重复，请更换流水ID！","警告",JOptionPane.WARNING_MESSAGE);
                add_dId.clear();
                return;
            }
            sql="INSERT INTO diary(INFOID,CARID,CUSID,STUFFID,DTIME,DEVENT,COST) VALUES("+id+","
                    +carid+","+cusid+","+stuffid+","+"'"+time+"',"+event+","+cost+")";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"添加流水信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_diary(null);
            add_dId.clear();
            add_dCarId.clear();
            add_dCusId.clear();
            add_dStuffId.clear();
            add_dTime.clear();
            add_dEvent.clear();
            add_dCost.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_cAdd(ActionEvent actionEvent) {
        String id=add_cId.getText();
        String brand=add_cBrand.getText();
        String cid=add_cCid.getText();
        String status=add_cStatus.getText();
        String rent=add_cRent.getText();
        String pledge=add_cPledge.getText();
        try{
            conn=Stuff.getConnection();
            if(id.isEmpty()||brand.isEmpty()||cid.isEmpty()||status.isEmpty()||rent.isEmpty()||pledge.isEmpty()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"有信息未填，请先完善添加车辆的信息！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            stmt=conn.createStatement();
            sql="SELECT ID FROM CARINFO WHERE ID="+id;
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"车牌的ID冲突，不可重复，请修改！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            sql="SELECT * FROM CARINFO WHERE CID='"+cid+"'";
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"此车牌号的车辆已经在表中,不可重复！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sql="INSERT INTO CARINFO(ID,BRAND,RENT,STATU,PLEDGE,VALID,CID,LASTLOGIN) VALUES("+id+","+"'"+brand+"',"+rent+","+status+","+pledge+","+"0"+",'"+cid+"','"+df.format(new Date())+"')";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"添加新的车辆信息成功!","通知",JOptionPane.INFORMATION_MESSAGE);
            add_cId.clear();
            add_cBrand.clear();
            add_cCid.clear();
            add_cRent.clear();
            add_cPledge.clear();
            add_cStatus.clear();
            this.show_car(null);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_cusAdd(ActionEvent actionEvent) {
        String id=add_cusId.getText();
        String name=add_cusName.getText();
        String pwd=add_cusPwd.getText();
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            if(id.isEmpty()||name.isEmpty()||pwd.isEmpty()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"有信息未填，请先完善添加会员的信息！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            sql="SELECT * FROM CUSTOMERINFO WHERE ID="+id;
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"会员的ID冲突，不可重复，请修改！","警告",JOptionPane.WARNING_MESSAGE);
                add_cusId.clear();
                return;
            }
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sql="INSERT INTO CUSTOMERINFO(ID,CNAME,MEMBER,PASSWD,DLRQ) VALUES("+id+",'"+name+"',"+"1"+",'"+pwd+"','"+df.format(new Date())+"')";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"添加会员信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_member(null);
            add_cusId.clear();
            add_cusName.clear();
            add_cusPwd.clear();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void on_btn_ncusAdd(ActionEvent actionEvent) {
        String id=add_ncusId.getText();
        String name=add_ncusName.getText();
        String pwd=add_ncusPwd.getText();
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            if(id.isEmpty()||name.isEmpty()||pwd.isEmpty()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"有信息未填，请先完善添加会员的信息！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            sql="SELECT * FROM CUSTOMERINFO WHERE ID="+id;
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"会员的ID冲突，不可重复，请修改！","警告",JOptionPane.WARNING_MESSAGE);
                add_cusId.clear();
                return;
            }
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sql="INSERT INTO CUSTOMERINFO(ID,CNAME,MEMBER,PASSWD,DLRQ) VALUES("+id+",'"+name+"',"+"0"+",'"+pwd+"','"+df.format(new Date())+"')";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"添加会员信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_no_member(null);
            add_ncusId.clear();
            add_ncusName.clear();
            add_ncusPwd.clear();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_dDelete(ActionEvent actionEvent) {
        DiaryShow cnn=table_diary.getSelectionModel().getSelectedItem();
        if(cnn==null){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先选中要删除的流水信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            String id=cnn.getDid();
            String event=cnn.getDevent();
            if(event.equals("租车")||event.equals("还车")){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"不可以删除租车和还车流水记录！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            sql="DELETE FROM DIARY WHERE INFOID="+id;
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"删除流水信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_diary(null);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_cDelete(ActionEvent actionEvent) {
        CarShow cnn=table_car.getSelectionModel().getSelectedItem();
        if(cnn==null){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先选中要删除的车辆信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            String id=cnn.getCid();
            String valid=cnn.getCvalid();
            if(valid.equals("1")){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"该车仍在租用中，不可删除！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            sql="DELETE FROM CARINFO WHERE ID="+id;
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"删除车辆信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_car(null);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_cusDelete(ActionEvent actionEvent) {
        MemberShow cnn=table_member.getSelectionModel().getSelectedItem();
        if(cnn==null){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先选中要删除的会员信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            String id=cnn.getCusid();
            sql="DELETE FROM CUSTOMERINFO WHERE ID="+id;
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"删除客户信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_member(null);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_ncusDelete(ActionEvent actionEvent) {
        MemberShow cnn=table_nmember.getSelectionModel().getSelectedItem();
        if(cnn==null){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先选中要删除的非会员用户信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            String id=cnn.getCusid();
            sql="DELETE FROM CUSTOMERINFO WHERE ID="+id;
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"删除客户信息成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            this.show_no_member(null);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_super(ActionEvent actionEvent) {
        try{
            conn=Stuff.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM EMPLOYEEINFO WHERE ID="+ Current.getB()+" AND TITLE>=6";
            rs=stmt.executeQuery(sql);
            if(!rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"抱歉！您的等级无法查看！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MainApp.setSuperUi();
    }



    @FXML
    public void on_btn_cFix(ActionEvent actionEvent) {
        MainApp.setCarFixUi();
    }


}

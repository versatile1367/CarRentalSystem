package carRental.controler;

import carRental.MainApp;
import carRental.module.Current;
import carRental.module.SelfDiary;
import carRental.module.SelfInfo;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SelfCenter implements Initializable {
    Connection conn;
    Statement stmt;
    String sql;
    ResultSet rs;
    ObservableList<SelfInfo> self_list= FXCollections.observableArrayList();
    ObservableList<SelfDiary> diary_list=FXCollections.observableArrayList();

    @FXML
    TableView<SelfInfo> table_info;
    @FXML
    TableView<SelfDiary> table_diary;
    @FXML
    TableColumn<SelfInfo,String> col_iId,col_iName,col_iMember,col_iLevel,col_iTime;
    @FXML
    TableColumn<SelfDiary,String> col_dId,col_dBrand,col_dCid,col_dStuffId,col_dTime,col_dEvent,col_dCost;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        return;
    }


    @FXML
    public void on_btn_show(ActionEvent actionEvent) {
        System.out.println("---------------------------");
        System.out.println(Current.getA());
        System.out.println(Current.getB());
        diary_list.clear();
        self_list.clear();
        col_iId.setCellValueFactory(new PropertyValueFactory<SelfInfo,String>("Sid"));
        col_iName.setCellValueFactory(new PropertyValueFactory<SelfInfo,String>("Sname"));
        col_iMember.setCellValueFactory(new PropertyValueFactory<SelfInfo,String>("Smember"));
        col_iLevel.setCellValueFactory(new PropertyValueFactory<SelfInfo,String>("Slevel"));
        col_iTime.setCellValueFactory(new PropertyValueFactory<SelfInfo,String>("Stime"));
        col_dId.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Did"));
        col_dBrand.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Dbrand"));
        col_dCid.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Dcid"));
        col_dStuffId.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Dstuff"));
        col_dEvent.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Devent"));
        col_dTime.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Dtime"));
        col_dCost.setCellValueFactory(new PropertyValueFactory<SelfDiary,String>("Dcost"));
        try{
            conn=SelfCenter.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM CUSTOMERINFO WHERE ID="+ Current.getB();
            rs=stmt.executeQuery(sql);
            String id,name,member,level,time;
            Connection conn1;
            Statement stmt1;
            ResultSet rs1;
            conn1=SelfCenter.getConnection();
            stmt1=conn1.createStatement();
            rs.next();
            id=rs.getString("ID");
            name=rs.getString("CNAME");
            member=rs.getString("MEMBER");
            time=rs.getString("DLRQ");
            String sql1;
            sql1="SELECT COUNT(DEVENT) AS NUM FROM DIARY WHERE CUSID="+Current.getB()+" AND (DEVENT=3 OR DEVENT=4)";
            rs1=stmt1.executeQuery(sql1);
            rs1.next();
            int cusmoral=rs1.getInt("NUM");
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
            self_list.add(new SelfInfo(id,name,member,level,time));
            rs1.close();
            stmt1.close();
            conn1.close();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_info.setItems(self_list);
        }

        try{
            conn=SelfCenter.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM DIARY WHERE CUSID="+Current.getB();
            rs=stmt.executeQuery(sql);
            String id,brand,cid,stuff,time,event,cost;
            String sql1;
            Connection conn1=SelfCenter.getConnection();
            Statement stmt1= conn1.createStatement();
            ResultSet rs1=null;
            int cusid,stuffid,eventid,carid;
            while(rs.next()){
                id=rs.getString("INFOID");
                carid=rs.getInt("CARID");
                cusid=rs.getInt("CUSID");
                stuffid=rs.getInt("STUFFID");
                time=rs.getString("DTIME");
                eventid=rs.getInt("DEVENT");
                cost=rs.getString("COST");
                if(eventid==1){
                    event="租车";
                }
                else if(eventid==1){
                    event="还车";
                }
                else if(eventid==3){
                    event="损坏维修";
                }
                else{
                    event="交通罚款";
                }
                sql1="SELECT BRAND,CID FROM CARINFO WHERE ID="+carid;
                rs1=stmt1.executeQuery(sql1);
                rs1.next();
                brand=rs1.getString("BRAND");
                cid=rs1.getString("CID");
                sql1="SELECT ENAME FROM EMPLOYEEINFO WHERE ID="+stuffid;
                rs1=stmt1.executeQuery(sql1);
                rs1.next();
                stuff=rs1.getString("ENAME");
                diary_list.add(new SelfDiary(id,brand,cid,stuff,time,event,cost));
            }
            rs1.close();
            stmt1.close();
            conn1.close();
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
    public void on_btn_logout(ActionEvent actionEvent) {
        diary_list.clear();
        self_list.clear();
        table_diary.setItems(diary_list);
        table_info.setItems(self_list);
        MainApp.setInitUi();
    }


    @FXML
    public void on_btn_back(ActionEvent actionEvent) {
        diary_list.clear();
        self_list.clear();
        table_diary.setItems(diary_list);
        table_info.setItems(self_list);
        MainApp.setCustomerUi();
    }


    public void on_btn_pwdFix(ActionEvent actionEvent) {
        MainApp.setPwdFixUi();
    }
}

package carRental.controler;

import carRental.MainApp;
import carRental.module.Current;
import carRental.module.listReturn;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

public class Customer implements Initializable {
    ObservableList<String> ob_brand= FXCollections.observableArrayList();
    ObservableList<String> ob_id=FXCollections.observableArrayList();
    ObservableList<String> ob_sname=FXCollections.observableArrayList();
    ObservableList<listReturn> ob_carReturnList=FXCollections.observableArrayList();
    Vector<String> list_brand,list_id,list_sname;
    //Vector<Integer> list_id_int;

    @FXML
    TableView<listReturn> table_forReturnCar;
    @FXML
    public TableColumn<listReturn, String> col_rbrand,col_rid,col_rrent,col_rpledge;
    @FXML
    public ComboBox<String> combo_brand,combo_id,combo_sname;
    @FXML
    public Label label_cur;
    @FXML
    public Label label_rent,label_pledge,label_status;


    static Statement stmt=null;
    static Statement stmt1=null;

    public static Connection getConnection(){
        String DB_URL="jdbc:mysql://localhost:3306/cr_data?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
        String USER="root";
        String PASS="990113";
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn1=Customer.getConnection();
        list_brand=new Vector<>();
        list_id=new Vector<>();
        list_sname=new Vector<>();
        //这里为了实时显示当前用户
        //ObservableStringValue cur=Current.getA();
        //label_cur.textProperty().bind(cur);
        try{
            //设置车牌得comboBox
            stmt=conn1.createStatement();
            String sql="SELECT DISTINCT BRAND FROM CARINFO";
            ResultSet rs=stmt.executeQuery(sql);
            String brand;
            while(rs.next()){
                brand=rs.getString("BRAND");
                ob_brand.add(brand);
            }
            combo_brand.setItems(ob_brand);
            rs.close();
            //设置经手员工的comboBox
            sql="SELECT ENAME FROM EMPLOYEEINFO";
            rs=stmt.executeQuery(sql);
            String sname;
            while(rs.next()){
                sname=rs.getString("ENAME");
                ob_sname.add(sname);
            }
            combo_sname.setItems(ob_sname);
            rs.close();
            stmt.close();
            conn1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //给combo_brand加监听，以及时改变combo_id
        combo_brand.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Connection conn2=Customer.getConnection();
            String carId;
            int carIdInt;
            try{
                stmt=conn2.createStatement();
                String sql1="SELECT CID FROM CARINFO WHERE BRAND='"+combo_brand.getSelectionModel().getSelectedItem()+"'"+" AND VALID=0";
                ResultSet rs1=stmt.executeQuery(sql1);
                try{
                    ob_id.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while(rs1.next()){
                    //carIdInt=rs1.getInt("ID");
                    carId=rs1.getString("CID");
                    ob_id.add(carId);
                    //list_id_int.add(carIdInt);
                    System.out.println(carId);
                }
                combo_id.setItems(ob_id);
                label_rent.setText(" ");
                label_pledge.setText(" ");
                label_status.setText(" ");
                rs1.close();
                stmt.close();
                conn2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        //给combo_id加监听，以及时显示租金等信息
        combo_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String sel=combo_id.getSelectionModel().getSelectedItem();
            Connection conn3=Customer.getConnection();
            try{
                stmt=conn3.createStatement();
                String sql1="SELECT RENT,PLEDGE,STATU FROM CARINFO WHERE CID='"+
                        combo_id.getSelectionModel().getSelectedItem()+"'";
                ResultSet rs1=stmt.executeQuery(sql1);
                rs1.next();
                label_rent.setText(rs1.getString("RENT"));
                label_pledge.setText(rs1.getString("PLEDGE"));
                label_status.setText(rs1.getString("STATU"));
                rs1.close();
                stmt.close();
                conn3.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }



    @FXML
    public void on_btn_ok(ActionEvent actionEvent) {
        Connection conn4=Customer.getConnection();
        Statement stmt4=null;
        String brandNow=combo_brand.getSelectionModel().getSelectedItem();
        String idNow=combo_id.getSelectionModel().getSelectedItem();
        String snameNow=combo_sname.getSelectionModel().getSelectedItem();
        int idIndex=combo_id.getSelectionModel().getSelectedIndex();
        //int idNowInt=list_id_int.elementAt(idIndex);
        String rentNow=label_rent.getText();
        if(brandNow==null||idNow==null||snameNow==null||brandNow.isEmpty()||snameNow.isEmpty()||idNow.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先选择租车的信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            stmt4= conn4.createStatement();
            String sql4="SELECT ID FROM EMPLOYEEINFO WHERE ENAME='"+snameNow+"'";
            ResultSet rs4=stmt4.executeQuery(sql4);
            rs4.next();
            int getStuffId=rs4.getInt("ID");
            System.out.println(getStuffId);
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sql4="SELECT COUNT(*) AS NUM1 FROM DIARY";
            rs4=stmt4.executeQuery(sql4);
            rs4.next();
            int getInfoId=rs4.getInt("NUM1");
            System.out.println(getInfoId);
            getInfoId--;
            sql4="SELECT INFOID FROM DIARY ORDER BY INFOID LIMIT "+getInfoId+",1";
            rs4=stmt4.executeQuery(sql4);
            rs4.next();
            getInfoId=rs4.getInt("INFOID");
            getInfoId++;
            System.out.println(getInfoId);

            sql4="SELECT ID FROM CARINFO WHERE CID='"+idNow+"'";
            rs4=stmt4.executeQuery(sql4);
            rs4.next();
            int idNowInt=rs4.getInt("ID");
            //租车事件写入日记！
            sql4="INSERT INTO DIARY(INFOID,CARID,CUSID,STUFFID,DTIME,DEVENT,COST) VALUES("+getInfoId+","+idNowInt+","+
                    Current.getB()+","+getStuffId+",'"+df.format(new Date())+"',"+"1"+","+Integer.parseInt(rentNow)+")";
            stmt4.execute(sql4);
            //更新汽车信息
            sql4="UPDATE CARINFO SET VALID=1 WHERE CID='"+idNow+"'";
            stmt4.execute(sql4);
            sql4="UPDATE  CARINFO SET LASTLOGIN='"+df.format(new Date())+"' WHERE CID='"+idNow+"'";
            stmt4.execute(sql4);
            //租车的profit加入profit表
            sql4="SELECT COUNT(*) AS NUM2 FROM PROFIT";
            rs4=stmt4.executeQuery(sql4);
            rs4.next();
            int getProfitId=rs4.getInt("NUM2");
            getProfitId++;
            sql4="INSERT INTO PROFIT VALUES("+getProfitId+","+"1"+",'"+df.format(new Date())+"',"+rentNow+")";
            stmt4.execute(sql4);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"租车成功！请爱惜用车，如有维修费用，从押金扣除","提示", JOptionPane.INFORMATION_MESSAGE);
            combo_brand.getSelectionModel().clearSelection();
            combo_id.getSelectionModel().clearSelection();
            combo_sname.getSelectionModel().clearSelection();
            label_rent.setText(" ");
            label_pledge.setText(" ");
            label_status.setText(" ");
            rs4.close();
            stmt4.close();
            conn4.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void returnCar(Event event) throws NullPointerException{
        Connection conn5=Customer.getConnection();
        Statement stmt5=null;
        try{
            col_rbrand.setCellValueFactory(new PropertyValueFactory<listReturn,String>("rBrand"));
            col_rid.setCellValueFactory(new PropertyValueFactory<listReturn,String>("rId"));
            col_rrent.setCellValueFactory(new PropertyValueFactory<listReturn,String>("rRent"));
            col_rpledge.setCellValueFactory(new PropertyValueFactory<listReturn,String>("rPledge"));

            stmt5=conn5.createStatement();
            ob_carReturnList.clear();
            String sql5="SELECT DISTINCT CID,BRAND,RENT,PLEDGE FROM carinfo,diary WHERE VALID=1 AND diary.CARID=carinfo.ID AND diary.DTIME=carinfo.LASTLOGIN AND CUSID="+
                    Integer.toString(Current.getB());
            ResultSet rs5=stmt5.executeQuery(sql5);
            String brand_tem,id_tem,rent_tem,pledge_tem;
            while(rs5.next()){
                brand_tem=rs5.getString("BRAND");
                id_tem=rs5.getString("CID");
                rent_tem=Integer.toString(rs5.getInt("RENT"));
                pledge_tem=Integer.toString(rs5.getInt("PLEDGE"));
                ob_carReturnList.add(new listReturn(brand_tem,id_tem,rent_tem,pledge_tem));
                System.out.println(pledge_tem);
            }
            rs5.close();
            stmt5.close();
            conn5.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            table_forReturnCar.setItems(ob_carReturnList);
        }
    }


    @FXML
    public void on_btn_return(ActionEvent actionEvent) {
        listReturn cnn=table_forReturnCar.getSelectionModel().getSelectedItem();
        int fix_pay=0;
        int additionnal_pay=0;
        int return_money=0;
        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(cnn==null){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先选中要还的车辆！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Connection conn6=Customer.getConnection();
        try{
            Statement stmt6=conn6.createStatement();
            ResultSet rs6;
            String sql6;
            String brand=cnn.getrBrand();
            String id=cnn.getrId();
            String rent=cnn.getrRent();
            String pledge=cnn.getrPledge();
            String getDate;
            sql6="SELECT LASTLOGIN FROM CARINFO WHERE CID='"+id+"'";
            rs6=stmt6.executeQuery(sql6);
            rs6.next();
            getDate=rs6.getString("LASTLOGIN");
            sql6="SELECT COUNT(*) AS NUM6 FROM DIARY";
            rs6=stmt6.executeQuery(sql6);
            rs6.next();
            int temp=rs6.getInt("NUM6");
            temp--;
            sql6="SELECT INFOID FROM DIARY ORDER BY INFOID LIMIT "+temp+",1";
            rs6=stmt6.executeQuery(sql6);
            rs6.next();
            temp=rs6.getInt("INFOID");
            temp++;
            String currentTime=df.format(new Date());
            sql6="SELECT SUM(COST) AS NUM6 FROM DIARY WHERE CARID='"+id+"'"+" AND DEVENT=3 AND DTIME BETWEEN '"+getDate+"'"+" AND '"+currentTime+"'";
            rs6=stmt6.executeQuery(sql6);
            rs6.next();
            fix_pay=rs6.getInt("NUM6");
            System.out.println(fix_pay);
            int pledgeInt=Integer.parseInt(pledge);
            if(fix_pay>pledgeInt){
                return_money=0;
                additionnal_pay=fix_pay-pledgeInt;
            }
            else{
                additionnal_pay=0;
                return_money=pledgeInt-fix_pay;
            }
            sql6="SELECT ID FROM CARINFO WHERE CID='"+id+"'";
            rs6=stmt6.executeQuery(sql6);
            rs6.next();
            int getId=rs6.getInt("ID");
            sql6="SELECT STUFFID FROM DIARY WHERE DTIME='"+getDate+"'"+" AND CARID='"+getId+"'";
            rs6=stmt6.executeQuery(sql6);
            rs6.next();
            int getStuffId=rs6.getInt("STUFFID");
            //还车事件加入diary,cost金额即为维修费用赔偿的
            sql6="INSERT INTO DIARY(INFOID,CARID,CUSID,STUFFID,DTIME,DEVENT,COST) VALUES("+temp+","+getId+","+
                    Current.getB()+","+getStuffId+","+currentTime+","+"2"+","+fix_pay+")";
            if(return_money!=pledgeInt){
                sql6="SELECT COUNT(*) AS NUM6 FROM PROFIT";
                rs6=stmt6.executeQuery(sql6);
                rs6.next();
                temp=rs6.getInt("NUM6");
                temp--;
                sql6="SELECT PID FROM PROFIT ORDER BY PID LIMIT "+temp+",1";
                rs6=stmt6.executeQuery(sql6);
                rs6.next();
                temp=rs6.getInt("PID");
                temp++;
                int payProfit;
                if(additionnal_pay==0){
                    payProfit=pledgeInt-return_money;
                    sql6="INSERT INTO PROFIT(PID,PEVENT,PDATETIME,PPROFIT) VALUES("+temp+","+"2"+","+currentTime+","+payProfit+")";
                    stmt6.execute(sql6);
                }
                else{
                    payProfit=pledgeInt+additionnal_pay;
                    sql6="INSERT INTO PROFIT(PID,PEVENT,PDATETIME,PPROFIT) VALUES("+temp+","+"2"+","+currentTime+","+payProfit+")";
                    stmt6.execute(sql6);
                }
            }
            //修改车辆信息
            sql6="UPDATE  CARINFO SET VALID=0,LASTlOGIN=current_time WHERE CID='"+id+"'";
            stmt6.execute(sql6);
            if(additionnal_pay==0){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"已完成还车，退还押金："+return_money+"元!","通知",JOptionPane.INFORMATION_MESSAGE);
                this.returnCar(null);
                return;
            }
            else{
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"已完成还车，押金全部扣除，还需缴纳："+additionnal_pay+"元！","通知",JOptionPane.INFORMATION_MESSAGE);
                this.returnCar(null);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void on_btn_myInfo(ActionEvent actionEvent) {

    }

    @FXML
    public void on_btn_logout(ActionEvent actionEvent) {
        MainApp.setInitUi();
    }
}

package carRental.controler;

import carRental.MainApp;
import carRental.module.Current;
import com.sun.tools.javac.Main;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    public ComboBox<String> combo_type;
    @FXML
    private TextField text_account,text_pass;
    @FXML
    public Button on_btn_login,on_btn_logout;

    static Statement stmt=null;
    Current curAccount=new Current();


    public static Connection getConnnection(){
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
        combo_type.getItems().addAll("顾客","员工");
        combo_type.getSelectionModel().select(0);
    }

    @FXML
    public void on_btn_login(ActionEvent actionEvent) {
        String account=text_account.getText();
        String pass=text_pass.getText();
        if(account.isEmpty()||pass.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"账号或者密码为空，请完整您的登录信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int type=combo_type.getSelectionModel().getSelectedIndex();
        try{
            Connection conn1=Login.getConnnection();
            stmt=conn1.createStatement();
            String sql=null;
            ResultSet rs=null;
            if(type==0){
                sql="SELECT ID,PASSWD FROM customerinfo WHERE CNAME='"+account+"'";
            }
            else if(type==1){
                sql="SELECT ID,PASSWD FROM employeeinfo WHERE ENAME='"+account+"'";
            }
            else return;
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                String passCorrect=rs.getString("PASSWD");
                int idCur=rs.getInt("ID");
                if(passCorrect.equals(pass)){
                    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(type==0){
                        curAccount.setA(account);
                        curAccount.setB(idCur);
                        curAccount.setType(0);
                        sql="UPDATE customerinfo SET DLRQ="+"'"+df.format(new Date())+"'"+" WHERE CNAME='"+account+"'";
                    }
                    else if(type==1){
                        curAccount.setA(account);
                        curAccount.setB(idCur);
                        curAccount.setType(1);
                        sql="UPDATE employeeinfo SET DLRQ="+"'"+df.format(new Date())+"'"+" WHERE ENAME='"+account+"'";
                    }
                    else return;
                    stmt.executeUpdate(sql);
                    text_account.clear();
                    text_pass.clear();
                    System.out.println("登陆成功！");
                    System.out.println(Current.getA());

                    if(type==0){
                        MainApp.setCustomerUi();
                    }
                    else if(type==1){
                        MainApp.setStuffUi();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"密码错误，请重新输入！","警告",JOptionPane.WARNING_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"用户名不存在，请重新输入!","警告",JOptionPane.WARNING_MESSAGE);
            }
            rs.close();
            stmt.close();
            conn1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void on_btn_logout(ActionEvent actionEvent) {
        MainApp.setInitUi();
    }


}

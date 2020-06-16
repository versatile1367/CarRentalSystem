package carRental.controler;

import carRental.MainApp;
import carRental.module.Current;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.*;

public class PwdFix {
    Connection conn;
    Statement stmt;
    ResultSet rs;
    String sql;
    @FXML
    TextField text_oldPwd,text_newPwd;

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


    @FXML
    public void on_btn_back(ActionEvent actionEvent) {
        MainApp.setSelfCenterUi();
    }

    @FXML
    public void on_btn_ok(ActionEvent actionEvent) {
        String old=text_oldPwd.getText();
        String neww=text_newPwd.getText();
        if(old.isEmpty()||neww.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先完善信息！密码没有填！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=PwdFix.getConnection();
            stmt=conn.createStatement();
            sql="SELECT PASSWD FROM CUSTOMERINFO WHERE ID="+ Current.getB();
            rs=stmt.executeQuery(sql);
            rs.next();
            if(!old.equals(rs.getString("PASSWD"))){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"旧密码输入错误！","警告",JOptionPane.WARNING_MESSAGE);
                return;
            }
            sql="UPDATE CUSTOMERINFO SET PASSWD='"+neww+"'"+" WHERE ID="+Current.getB();
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"密码更改成功！","通知",JOptionPane.INFORMATION_MESSAGE);
            text_newPwd.clear();
            text_oldPwd.clear();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

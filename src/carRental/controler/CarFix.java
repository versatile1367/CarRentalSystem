package carRental.controler;

import carRental.MainApp;
import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CarFix implements Initializable {
    Connection conn;
    Statement stmt;
    String sql;
    ResultSet rs;
    ObservableList<String> type_list= FXCollections.observableArrayList();

    @FXML
    private TextField text_carId,text_carContent;
    @FXML
    ComboBox combo_type;

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
    public void initialize(URL location, ResourceBundle resources){
        type_list.add("租金");
        type_list.add("押金");
        type_list.add("状态");
        combo_type.setItems(type_list);
        combo_type.getSelectionModel().select(0);
        return;
    }

    @FXML
    public void on_btn_ok(ActionEvent actionEvent) {
        String id,content;
        id=text_carId.getText();
        content=text_carContent.getText();
        if(id.isEmpty()||content.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先完善需要修改的车辆信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=CarFix.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM CARINFO WHERE CID='"+id+"'";
            rs=stmt.executeQuery(sql);
            if(!rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"您想要修改的车辆不存在，请核对后再输入！","警告",JOptionPane.WARNING_MESSAGE);
                text_carId.clear();
                return;
            }
            switch (combo_type.getSelectionModel().getSelectedIndex()){
                case 0:
                    if(!content.matches("^[0-9]*[1-9][0-9]*$")){
                        JOptionPane.showMessageDialog(new JFrame().getContentPane(),"您输入的租金金额非法，请输入正数！","警告",JOptionPane.WARNING_MESSAGE);
                        text_carContent.clear();
                        return;
                    }
                    sql="UPDATE CARINFO SET RENT="+Integer.parseInt(content)+" WHERE CID='"+id+"'";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"修改租金成功！","通知",JOptionPane.INFORMATION_MESSAGE);
                    text_carContent.clear();
                    text_carId.clear();
                    break;
                case 1:
                    if(!content.matches("^[0-9]*[1-9][0-9]*$")){
                        JOptionPane.showMessageDialog(new JFrame().getContentPane(),"您输入的押金金额非法，请输入正数！","警告",JOptionPane.WARNING_MESSAGE);
                        text_carContent.clear();
                        return;
                    }
                    sql="UPDATE CARINFO SET PLEDGE="+Integer.parseInt(content)+" WHERE CID='"+id+"'";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"修改押金成功！","通知",JOptionPane.INFORMATION_MESSAGE);
                    text_carContent.clear();
                    text_carId.clear();
                    break;
                case 2:
                    if(!(content.equals("1")||content.equals("2")||content.equals("3")||content.equals("4")||content.equals("5"))){
                        JOptionPane.showMessageDialog(new JFrame().getContentPane(),"您输入的汽车状态非法，应输入1-5，请重新输入！","警告",JOptionPane.WARNING_MESSAGE);
                        text_carContent.clear();
                        return;
                    }
                    sql="UPDATE CARINFO SET STATU="+Integer.parseInt(content)+" WHERE CID='"+id+"'";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"修改汽车状态成功！","通知",JOptionPane.INFORMATION_MESSAGE);
                    text_carContent.clear();
                    text_carId.clear();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_back(ActionEvent actionEvent) {
        MainApp.setStuffUi();
    }



}

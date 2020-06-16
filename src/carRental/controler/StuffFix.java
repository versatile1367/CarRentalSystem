package carRental.controler;

import carRental.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StuffFix implements Initializable {

    Connection conn;
    Statement stmt;
    String sql;
    ResultSet rs;
    ObservableList<String> type_list= FXCollections.observableArrayList();

    @FXML
    private TextField text_eId,text_eContent;
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
        type_list.add("职称");
        type_list.add("密码");
        combo_type.setItems(type_list);
        combo_type.getSelectionModel().select(0);
        return;
    }

    @FXML
    public void on_btn_ok(ActionEvent actionEvent) {
        String id,content;
        id=text_eId.getText();
        content=text_eContent.getText();
        if(id.isEmpty()||content.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),"请先完善需要修改的员工信息！","警告",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=StuffFix.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM EMPLOYEEINFO WHERE ID="+id;
            rs=stmt.executeQuery(sql);
            if(!rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),"您想要修改的员工ID不存在，请检查后再输入！","警告",JOptionPane.WARNING_MESSAGE);
                text_eId.clear();
                return;
            }
            switch (combo_type.getSelectionModel().getSelectedIndex()){
                case 0:
                    if(!(content.equals("1")||content.equals("2")||content.equals("3")||content.equals("4")||content.equals("5")||content.equals("6")||content.equals("7"))){
                        JOptionPane.showMessageDialog(new JFrame().getContentPane(),"您输入的职称等级非法，请输入1-7！","警告",JOptionPane.WARNING_MESSAGE);
                        text_eContent.clear();
                        return;
                    }
                    sql="UPDATE EMPLOYEEINFO SET TITLE="+Integer.parseInt(content)+" WHERE ID="+id;
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"修改该职员等级成功！","通知",JOptionPane.INFORMATION_MESSAGE);
                    text_eContent.clear();
                    text_eId.clear();
                    break;
                case 1:
                    sql="UPDATE EMPLOYEEINFO SET PASSWD='"+content+"'"+" WHERE ID="+id;
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),"修改该职员的吗，密码成功！","通知",JOptionPane.INFORMATION_MESSAGE);
                    text_eContent.clear();
                    text_eId.clear();
                    break;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void on_btn_back(ActionEvent actionEvent) {
        MainApp.setSuperUi();
    }
}

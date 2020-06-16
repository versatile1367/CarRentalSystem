package carRental.controler;

import carRental.MainApp;
import carRental.module.ChartShow;
import carRental.module.EmplyeeShow;
import com.sun.tools.javac.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class Super implements Initializable {
    Connection conn;
    Statement stmt;
    String sql;
    ResultSet rs;
    ObservableList<EmplyeeShow> employee_list= FXCollections.observableArrayList();
    ObservableList<ChartShow> ychart_list=FXCollections.observableArrayList();
    ObservableList<ChartShow> schart_list=FXCollections.observableArrayList();
    ObservableList<ChartShow> mchart_list=FXCollections.observableArrayList();
    ObservableList<PieChart.Data> yearPieChartData=FXCollections.observableArrayList();
    ObservableList<PieChart.Data> carPieChartData=FXCollections.observableArrayList();
    ObservableList<PieChart.Data> monthPieCharData=FXCollections.observableArrayList();
    ObservableList<PieChart.Data> seasonPieChartData=FXCollections.observableArrayList();

    @FXML
    PieChart carPieChart,yearPieChart,seasonPieChart,monthPieChart;
    @FXML
    BarChart<String,Number> yearBarChart,seasonBarChart;
    @FXML
    LineChart<String,Number> monthLineChart;
    @FXML
    TableView<EmplyeeShow> table_employee;
    @FXML
    TableView<ChartShow> table_year,table_month,table_season;
    @FXML
    TableColumn<EmplyeeShow,String> col_eId,col_eName,col_eLevel,col_ePwd,col_eTime;
    @FXML
    TableColumn<ChartShow,String> col_yYear,col_yProfit;
    @FXML
    TableColumn<ChartShow,String> col_sSeason,col_sProfit;
    @FXML
    TableColumn<ChartShow,String> col_mMonth,col_mProfit;
    @FXML
    TextField add_eId,add_eName,add_eLevel,add_ePwd,add_eTime;


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


    public void show_employee(Event event) {
        add_eId.setPromptText("ID");
        add_eName.setPromptText("姓名");
        add_eLevel.setPromptText("职称等级");
        add_ePwd.setPromptText("密码");
        add_eTime.setPromptText("不可填写");
        col_eId.setCellValueFactory(new PropertyValueFactory<EmplyeeShow, String>("Eid"));
        col_eName.setCellValueFactory(new PropertyValueFactory<EmplyeeShow, String>("Ename"));
        col_eLevel.setCellValueFactory(new PropertyValueFactory<EmplyeeShow, String>("Elevel"));
        col_ePwd.setCellValueFactory(new PropertyValueFactory<EmplyeeShow, String>("Epwd"));
        col_eTime.setCellValueFactory(new PropertyValueFactory<EmplyeeShow, String>("Etime"));
        try{
            conn=Super.getConnection();
            stmt= conn.createStatement();
            sql="SELECT * FROM EMPLOYEEINFO ORDER BY ID";
            rs=stmt.executeQuery(sql);
            employee_list.clear();
            String id,name,level,pwd,time;
            while(rs.next()){
                id=rs.getString("ID");
                name=rs.getString("ENAME");
                level=rs.getString("TITLE");
                pwd=rs.getString("PASSWD");
                time=rs.getString("DLRQ");
                employee_list.add(new EmplyeeShow(id,name,level,pwd,time));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_employee.setItems(employee_list);
        }
    }


    @FXML
    public void on_btn_eAdd(ActionEvent actionEvent) {
        String id=add_eId.getText();
        String name=add_eName.getText();
        String level=add_eLevel.getText();
        String pwd=add_ePwd.getText();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(id.isEmpty()||name.isEmpty()||level.isEmpty()||pwd.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                    "信息不完整,请输入完整的添加的员工的信息！", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT * FROM EMPLOYEEINFO WHERE ID="+id;
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                        "ID重复,不可以添加重复ID的员工信息！", "警告", JOptionPane.WARNING_MESSAGE);
                add_eId.clear();
                return;
            }
            sql="INSERT INTO EMPLOYEEINFO(ID,ENAME,TITLE,PASSWD,DLRQ) VALUES("+id+",'"+name+"',"+level+",'"+pwd+"','"+df.format(new Date())+"')";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                    "添加新员工成功！", "通知", JOptionPane.INFORMATION_MESSAGE);
            this.show_employee(null);
            add_eId.clear();
            add_eName.clear();
            add_eLevel.clear();
            add_ePwd.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @FXML
    public void on_btn_eDelete(ActionEvent actionEvent) {
        EmplyeeShow cnn=table_employee.getSelectionModel().getSelectedItem();
        if(cnn==null){
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                    "未选中任何员工，请先选中要删除的员工！", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            String id=cnn.getEid();
            sql="DELETE FROM EMPLOYEEINFO WHERE ID="+id;
            stmt.execute(sql);
            JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                    "成功删除对应员工信息!", "通知",  JOptionPane.INFORMATION_MESSAGE);
            this.show_employee(null);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void show_year(Event event) {
        col_yYear.setCellValueFactory(new PropertyValueFactory<ChartShow,String>("index"));
        col_yProfit.setCellValueFactory(new PropertyValueFactory<ChartShow,String>("profit"));
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT year(DTIME) AS YEAR,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY YEAR ORDER BY YEAR";
            rs=stmt.executeQuery(sql);
            String year,pro;
            ychart_list.clear();
            while(rs.next()){
                year=rs.getString("YEAR");
                pro=rs.getString("SUM");
                ychart_list.add(new ChartShow(year,pro));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_year.setItems(ychart_list);
        }

        //下来开始做年度报表的饼状图
        yearPieChartData.clear();
        yearPieChart.getData().clear();
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT year(DTIME) AS YEAR,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY YEAR ORDER BY YEAR";
            rs=stmt.executeQuery(sql);
            String year;
            int profit;
            while(rs.next()){
                year=rs.getString("YEAR");
                profit=rs.getInt("SUM");
                yearPieChartData.add(new PieChart.Data(year,profit));
            }
            yearPieChart.setData(yearPieChartData);
            //yearPieChart.setTitle("年度利润分布饼状图");
            yearPieChart.setClockwise(true);
            yearPieChart.setLabelsVisible(true);
            yearPieChart.setLabelLineLength(8);
            yearPieChart.setStartAngle(180);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //接下来做年度报表的条状图
        yearBarChart.getData().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("year");
        yAxis.setLabel("profit");
        XYChart.Series<String,Number> series=new XYChart.Series<>();
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT year(DTIME) AS YEAR,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY YEAR ORDER BY YEAR";
            rs=stmt.executeQuery(sql);
            String year;
            int profit;
            while(rs.next()){
                year=rs.getString("YEAR");
                profit=rs.getInt("SUM");
                series.getData().add(new XYChart.Data<>(year,profit));
            }
            yearBarChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void show_season(Event event) {
        col_sSeason.setCellValueFactory(new PropertyValueFactory<ChartShow,String>("index"));
        col_sProfit.setCellValueFactory(new PropertyValueFactory<ChartShow,String>("profit"));
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT QUARTER(DTIME) AS QUA,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY QUA ORDER BY QUA";
            rs=stmt.executeQuery(sql);
            String season,pro;
            schart_list.clear();
            while(rs.next()){
                season=rs.getString("QUA");
                pro=rs.getString("SUM");
                schart_list.add(new ChartShow(season,pro));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            table_season.setItems(schart_list);
        }

        //下来开始显示季度饼状图
        seasonPieChartData.clear();
        seasonPieChart.getData().clear();
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT QUARTER(DTIME) AS QUA,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY QUA ORDER BY QUA";
            rs=stmt.executeQuery(sql);
            String season;
            int profit;
            while(rs.next()){
                season=rs.getString("QUA");
                profit=rs.getInt("SUM");
                seasonPieChartData.add(new PieChart.Data(season,profit));
            }
            seasonPieChart.setData(seasonPieChartData);
            //yearPieChart.setTitle("年度利润分布饼状图");
            seasonPieChart.setClockwise(true);
            seasonPieChart.setLabelsVisible(true);
            yearPieChart.setLabelLineLength(8);
            seasonPieChart.setStartAngle(180);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //接下来做季度报表的条状图
        seasonBarChart.getData().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("season");
        xAxis.setCategories(FXCollections.<String> observableArrayList(Arrays.asList(
                "1季度",
                "2季度",
                "3季度",
                "4季度")));
        yAxis.setLabel("profit");
        XYChart.Series<String,Number> series=new XYChart.Series<>();
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT QUARTER(DTIME) AS QUA,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY QUA ORDER BY QUA";
            rs=stmt.executeQuery(sql);
            String season;
            int profit;
            while(rs.next()){
                season=rs.getString("QUA")+"季度";
                System.out.println(season);
                profit=rs.getInt("SUM");
                series.getData().add(new XYChart.Data<>(season,profit));
            }
            seasonBarChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @FXML
    public void show_month(Event event) {
        col_mMonth.setCellValueFactory(new PropertyValueFactory<ChartShow, String>("index"));
        col_mProfit.setCellValueFactory(new PropertyValueFactory<ChartShow, String>("profit"));
        try {
            conn = Super.getConnection();
            stmt = conn.createStatement();
            sql = "SELECT MONTH(DTIME) AS MON,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY MON ORDER BY MON";
            rs = stmt.executeQuery(sql);
            String month, pro;
            mchart_list.clear();
            while (rs.next()) {
                month = rs.getString("MON");
                pro = rs.getString("SUM");
                mchart_list.add(new ChartShow(month, pro));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            table_month.setItems(mchart_list);
        }

        //下来开始显示月度饼状图
        monthPieCharData.clear();
        monthPieChart.getData().clear();
        try {
            conn = Super.getConnection();
            stmt = conn.createStatement();
            sql = "SELECT MONTH(DTIME) AS MON,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY MON ORDER BY MON";
            rs = stmt.executeQuery(sql);
            String month;
            int profit;
            while (rs.next()) {
                month = rs.getString("MON");
                profit = rs.getInt("SUM");
                monthPieCharData.add(new PieChart.Data(month, profit));
            }
            monthPieChart.setData(monthPieCharData);
            monthPieChart.setClockwise(true);
            monthPieChart.setLabelsVisible(true);
            monthPieChart.setLabelLineLength(8);
            monthPieChart.setStartAngle(180);
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //接下来做月度折线图
        monthLineChart.getData().clear();
        CategoryAxis xAis = new CategoryAxis();
        NumberAxis yAix = new NumberAxis();
        xAis.setLabel("月");
        yAix.setLabel("利润");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        try {
            conn = Super.getConnection();
            stmt = conn.createStatement();
            sql = "SELECT MONTH(DTIME) AS MON,SUM(COST) AS SUM FROM DIARY WHERE DEVENT=1 GROUP BY MON ORDER BY MON";
            rs = stmt.executeQuery(sql);
            String month;
            int profit;
            while (rs.next()) {
                month = rs.getString("MON");
                profit = rs.getInt("SUM");
                series.getData().add(new XYChart.Data<>(month,profit));
            }
            monthLineChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @FXML
    public void show_cChart(Event event) {
        carPieChart.getData().clear();
        carPieChartData.clear();
        try{
            conn=Super.getConnection();
            stmt=conn.createStatement();
            sql="SELECT BRAND,COUNT(BRAND) AS NUM FROM CARINFO GROUP BY BRAND";
            rs=stmt.executeQuery(sql);
            String brand;
            int num1;
            while(rs.next()){
                brand=rs.getString("BRAND");
                num1=rs.getInt("NUM");
                carPieChartData.add(new PieChart.Data(brand,num1));
            }
            carPieChart.setData(carPieChartData);
            carPieChart.setTitle("关于汽车品牌的饼状统计图");
            carPieChart.setClockwise(true);
            carPieChart.setLabelLineLength(50);
            carPieChart.setLabelsVisible(true);
            carPieChart.setStartAngle(180);
            //设置百分比
            final Label caption=new Label("");
            caption.setTextFill(Color.DARKORANGE);
            caption.setStyle("-fx-font:24 arial;");
            for(final PieChart.Data data:carPieChart.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                caption.setTranslateX(event.getSceneX());
                                caption.setTranslateY(event.getSceneY());
                                caption.setText(String.valueOf(data.getPieValue())+"%");
                            }
                        });
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void on_btn_back(ActionEvent actionEvent) {
        MainApp.setStuffUi();
    }


    @FXML
    public void on_btn_eFix(ActionEvent actionEvent) {
        MainApp.setStuffFixUi();
    }


    @FXML
    public void on_btn_logout(ActionEvent actionEvent) {
        MainApp.setInitUi();
    }




}

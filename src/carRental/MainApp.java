package carRental;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApp extends Application {

    static final String JDBC_DRIER="com.mysql.jdbc.Driver";
    static Stage primary_stage=null;

    private AnchorPane root_init=null;
    private SplitPane root_login=null;
    private SplitPane root_cuscomer=null;
    private SplitPane root_stuff=null;
    private SplitPane root_super=null;
    private SplitPane root_carFix=null;
    private SplitPane root_stuffFix=null;
    private SplitPane root_selfCenter=null;
    private SplitPane root_pwdFix=null;

    private static Scene scene_init=null;
    private static Scene scene_login=null;
    private static Scene scene_customer=null;
    private static Scene scene_stuff=null;
    private static Scene scene_super=null;
    private static Scene scene_carFix=null;
    private static Scene scene_stuffFix=null;
    private static Scene scene_selfCenter=null;
    private static Scene scene_pwdFix=null;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            primaryStage.setTitle("汽车租赁管理系统");
            primary_stage=primaryStage;
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            root_init=FXMLLoader.load(getClass().getResource("view/init.fxml"));
            root_login=FXMLLoader.load(getClass().getResource("view/login.fxml"));
            root_cuscomer=FXMLLoader.load(getClass().getResource("view/customer.fxml"));
            root_stuff=FXMLLoader.load(getClass().getResource("view/stuff.fxml"));
            root_super= FXMLLoader.load(getClass().getResource("view/super.fxml"));
            root_carFix=FXMLLoader.load(getClass().getResource("view/carFix.fxml"));
            root_stuffFix=FXMLLoader.load(getClass().getResource("view/stuffFix.fxml"));
            root_selfCenter=FXMLLoader.load(getClass().getResource("view/selfCenter.fxml"));
            root_pwdFix=FXMLLoader.load(getClass().getResource("view/pwdFix.fxml"));
            scene_init=new Scene(root_init);
            scene_login=new Scene(root_login);
            scene_customer=new Scene(root_cuscomer);
            scene_stuff=new Scene(root_stuff);
            scene_super=new Scene(root_super);
            scene_carFix=new Scene(root_carFix);
            scene_stuffFix=new Scene(root_stuffFix);
            scene_selfCenter=new Scene(root_selfCenter);
            scene_pwdFix=new Scene(root_pwdFix);
            setInitUi();
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setInitUi() {
        primary_stage.setScene(scene_init);
    }

    public static void setLoginUi() {
        primary_stage.setScene(scene_login);
    }

    public static void setCustomerUi(){primary_stage.setScene(scene_customer);}

    public static void setStuffUi(){primary_stage.setScene(scene_stuff);}

    public static Stage getPrimaryStage(){
        return primary_stage;
    }

    public static  void setSuperUi(){ primary_stage.setScene(scene_super);}

    public static  void setCarFixUi(){ primary_stage.setScene(scene_carFix);}

    public static  void setStuffFixUi(){ primary_stage.setScene(scene_stuffFix);}

    public static  void setSelfCenterUi(){ primary_stage.setScene(scene_selfCenter);}

    public static  void setPwdFixUi(){ primary_stage.setScene(scene_pwdFix);}
}


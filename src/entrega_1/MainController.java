package entrega_1;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * @author migonsa1
 */

public class MainController  {
    
    private Stage primaryStage;
    private Scene oldScene; 
    
    public void initStage (Stage stage){
        primaryStage = stage ;
    }
 
// -----------------------
// <editor-fold desc="instanciacion">
    @FXML
    private Button Alumnos;

    @FXML
    private Button Cursos;

    @FXML
    private Button Salir;

    @FXML
    private Hyperlink Matricular;

    @FXML
    private Hyperlink Borrar;
    
    @FXML
    private Hyperlink Listado;

// </editor-fold>
//------------------------

//------------------------    
//<editor-fold desc="eventos">
    
    @FXML
    private void OnMatricular(ActionEvent event) {

    }

    @FXML
    private void OnBorrar(ActionEvent event) {

    }
     @FXML
    private void OnListado(ActionEvent event) {

    }

    @FXML
    private void OnAlumnos(ActionEvent event) {
        try{
            FXMLLoader loader = new 
                FXMLLoader(getClass().getResource("Alumnos.fxml"));
            Parent root = (Parent) loader.load();
            AlumnosController ventana = 
                    loader.<AlumnosController>getController();
            ventana.initStage(primaryStage);
            oldScene = primaryStage.getScene();
            Scene scene = new 
                    Scene(root,oldScene.getWidth(), oldScene.getHeight());
            primaryStage.setScene(scene);
            ventana.initUI();
            primaryStage.show();
        } catch (IOException e) {e.printStackTrace();}

    }
    
    @FXML
    private void OnCursos(ActionEvent event) {
        try{
            FXMLLoader loader = new 
                FXMLLoader(getClass().getResource("Cursos.fxml"));
            Parent root = (Parent) loader.load();
            CursosController ventana = 
                    loader.<CursosController>getController();
            ventana.initStage(primaryStage);
            oldScene = primaryStage.getScene();
            Scene scene = new 
                    Scene(root,oldScene.getWidth(), oldScene.getHeight());
            primaryStage.setScene(scene);
            ventana.initUI();
            primaryStage.show();
        } catch (IOException e) {e.printStackTrace();}
    }
    
    @FXML
    private void OnSalir(ActionEvent event) {
        Button b = (Button) event.getSource();
        b.getScene().getWindow().hide();
    }

//// </editor-fold>
//------------------------
     
}

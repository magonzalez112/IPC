/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega_1;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import modelo.Curso;
import modelo.Dias;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author Miguel
 */
public class CursosController {
     private Stage primaryStage;
     private Scene oldScene;
     
     public void initStage  (Stage stage){
        primaryStage = stage;     
    }
     
     private final LocalDate fecha = LocalDate.now();
     
     public void initUI() {
        Aceptar.setDisable(true);
        Eliminar.disableProperty().bind
            (Tabla.getSelectionModel().selectedItemProperty().isNull());
        
        AccesoaBD acceso = new AccesoaBD();
        ObservableList<Curso> cursosObservable;
        cursosObservable = FXCollections.observableList(acceso.getCursos());
        Tabla.setItems(cursosObservable);
        
        //----------------------------
        // <editor-fold desc="rendering">
        final Callback<DatePicker, DateCell> dayCellFactory
                = (final DatePicker datePicker) -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(fecha)) {
                    setDisable(true);
                }
            }
        };
        FechaInicio.setDayCellFactory(dayCellFactory);
        FechaFin.setDayCellFactory(dayCellFactory);
        
        Tabla.setCellFactory((ListView<Curso> param) -> new ListCell<Curso>() {
            @Override protected void updateItem(Curso item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getTitulodelcurso() + "\t\t" 
                            +"Profesor: " + item.getProfesorAsignado());
                }
            }
        });
        
        // </editor-fold>
        //----------------------------
        
     }
      //----------------------------
      // <editor-fold desc="instanciacion">
    @FXML
    private Button Volver;

    @FXML
    private Button Add;

    @FXML
    private Button Eliminar;

    @FXML
    private VBox AddBox;

    @FXML
    private TextField TituloField;

    @FXML
    private TextField ProfesorField;

    @FXML
    private TextField NumeroField;

    @FXML
    private DatePicker FechaInicio;

    @FXML
    private DatePicker FechaFin;

    @FXML
    private TextField AulaField;

    @FXML
    private Spinner<LocalTime> HoraSpin;

    @FXML
    private CheckComboBox<Dias> DiasBox;

    @FXML
    private Button Aceptar;

    @FXML
    private Button Cancelar;

    @FXML
    private ListView<Curso> Tabla;
    
    // </editor-fold>
      //----------------------------
    
      //---------------------------
        // <editor-fold desc="eventos">
    @FXML
    void OnAceptar(ActionEvent event) {
        String titulo = TituloField.getText();
        String profesor = ProfesorField.getText();
        int numMax = Integer.parseInt(NumeroField.getText());
        String aula = AulaField.getText();
        
    }

    @FXML
    void OnAdd(ActionEvent event) {
        AddBox.setDisable(false);
        AddBox.setVisible(true);
    }

    @FXML
    void OnCancelar(ActionEvent event) {
         AddBox.setDisable(true);
         AddBox.setVisible(false);
    }

    @FXML
    void OnEliminar(ActionEvent event) {

    }
    
    @FXML
    void OnFechaFin(ActionEvent event) {

    }

    @FXML
    void OnFechaInicio(ActionEvent event) {

    }
    
    @FXML
    void OnVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = (Parent) loader.load();
            MainController ventana = loader.<MainController>getController();
            ventana.initStage(primaryStage);
            oldScene = primaryStage.getScene();
            Scene scene = new Scene(root, oldScene.getWidth(), oldScene.getHeight());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        // </editor-fold>
      //---------------------------
     
}

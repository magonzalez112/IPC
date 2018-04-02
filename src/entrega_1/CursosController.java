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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.LocalTimeStringConverter;
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
     AccesoaBD acceso = new AccesoaBD();
     ObservableList<Curso> cursosObservable;
     
     public void initUI() {
        Aceptar.setDisable(true);
        Eliminar.disableProperty().bind
            (Tabla.getSelectionModel().selectedItemProperty().isNull());
        
        
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
        
         SpinnerValueFactory timeFactory = new SpinnerValueFactory() {

             {
                 setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"),
                         DateTimeFormatter.ofPattern("HH:mm")));
             }

             @Override
             public void decrement(int steps) {
                 if (getValue() == null) {
                     setValue(LocalTime.now());
                 } else {
                     LocalTime time = (LocalTime) getValue();
                     setValue(time.minusMinutes(steps));
                 }
             }

             @Override
             public void increment(int steps) {
                 if (this.getValue() == null) {
                     setValue(LocalTime.now());
                 } else {
                     LocalTime time = (LocalTime) getValue();
                     setValue(time.plusMinutes(steps));
                 }
             }
         };
     
         HoraSpin.setValueFactory(timeFactory);
        
     
        
        // </editor-fold>
        //----------------------------
        
         //----------------------------
        // <editor-fold desc="listeners">
        
         Aceptar.disableProperty().bind(
                 Bindings.isEmpty(TituloField.textProperty())
                         .or(Bindings.isEmpty(ProfesorField.textProperty()))
                         .or(Bindings.isEmpty(NumeroField.textProperty()))
                         .or(Bindings.isEmpty(AulaField.textProperty()))
                         .or(Bindings.isNull(FechaInicio.valueProperty()))
                         .or(Bindings.isNull(FechaFin.valueProperty()))
                         .or(Bindings.isNull(HoraSpin.valueProperty()))
                         .or(Bindings.isEmpty(DiasBox.checkModelProperty().get().getCheckedItems())));
        
       
        
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
        LocalDate fechainicio = FechaInicio.getValue();
        LocalDate fechafin = FechaFin.getValue();
        LocalTime hora = HoraSpin.getValue();
        List<Dias> diaslist = DiasBox.getCheckModel().getCheckedItems();
        ArrayList <Dias> diasimparte = (ArrayList <Dias>) diaslist ;
        Curso curso = new Curso(titulo, profesor,numMax,fechainicio,fechafin, hora, diasimparte,aula);
        
        cursosObservable.add(curso);
        acceso.salvar();
        Tabla.setDisable(false);
        AddBox.setDisable(true);
        AddBox.setVisible(false);
        cursosObservable = FXCollections.observableList(acceso.getCursos());
        Tabla.setItems(cursosObservable);
    }

    @FXML
    void OnAdd(ActionEvent event) {
        Add.setDisable(true);
        AddBox.setDisable(false);
        AddBox.setVisible(true);
        Tabla.setDisable(true);
        Tabla.getSelectionModel().clearSelection();
        FechaInicio.setValue(fecha);
        DiasBox.getItems().addAll(Dias.values());
        HoraSpin.setEditable(true);
    }

    @FXML
    void OnCancelar(ActionEvent event) {
         AddBox.setDisable(true);
         AddBox.setVisible(false);
         Tabla.setDisable(false);
         Add.setDisable(false);
    }

    @FXML
    void OnEliminar(ActionEvent event) {
         Curso curso = Tabla.getSelectionModel().getSelectedItem();
         cursosObservable.remove(curso);
         acceso.salvar();
         cursosObservable = FXCollections.observableList(acceso.getCursos());
         Tabla.setItems(cursosObservable);
         Tabla.getSelectionModel().clearSelection();
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

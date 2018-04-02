/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega_1;

import accesoaBD.AccesoaBD;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import modelo.Alumno;
/**
 *
 * @author Miguel
 */
public class AlumnosController {
     private Stage primaryStage;
     private Scene oldScene;
     
     public void initStage  (Stage stage){
        primaryStage = stage;     
    }
     
     private final LocalDate fecha = LocalDate.now();
     ObservableList<Alumno> alumnosObservable;
     AccesoaBD acceso = new AccesoaBD();
     Image foto;
     
     
     public void initUI() {
        Aceptar.setDisable(true);
        
          
        alumnosObservable = FXCollections.observableList(acceso.getAlumnos());
        Tabla.setItems(alumnosObservable);
        foto = null;
        
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
        Fecha.setDayCellFactory(dayCellFactory);
        
        Tabla.setCellFactory((ListView<Alumno> param) -> new ListCell<Alumno>() {
            @Override protected void updateItem(Alumno item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getNombre() + "\t\t" 
                            +"DNI: " + item.getDni() + "\t\t"
                                +"Alta: " + item.getFechadealta());
                }
            }
        });
        // </editor-fold>
        //----------------------------
        
        //----------------------------
        // <editor-fold desc="listeners">
        
        Eliminar.disableProperty().bind
            (Tabla.getSelectionModel().selectedItemProperty().isNull());
        
        Tabla.getSelectionModel().selectedIndexProperty().addListener((listener)->{
            Img.setImage(Tabla.getSelectionModel(
                        ).getSelectedItem().getFoto());
        });
        
         Aceptar.disableProperty().bind(
                 Bindings.isEmpty(DNIField.textProperty())
                         .or(Bindings.isEmpty(NombreField.textProperty()))
                         .or(Bindings.isEmpty(EdadField.textProperty()))
                         .or(Bindings.isNull(Fecha.valueProperty()))
                         .or(Bindings.isNull(Img.imageProperty()))
                         .or(Bindings.isEmpty(DireccionField.textProperty())));
         
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
    private TextField DNIField;

    @FXML
    private TextField NombreField;

    @FXML
    private TextField EdadField;

    @FXML
    private TextField DireccionField;

    @FXML
    private DatePicker Fecha;

    @FXML
    private ImageView Img;

    @FXML
    private Button Foto;

    @FXML
    private Button Aceptar;

    @FXML
    private Button Cancelar;

    @FXML
    private ListView<Alumno> Tabla;
    
    // </editor-fold>
      //----------------------------
    
      //---------------------------
        // <editor-fold desc="eventos">
    @FXML
    void OnAceptar(ActionEvent event) {
        String dni = DNIField.getText();
        String nombre = NombreField.getText();
        int edad = Integer.parseInt(EdadField.getText());
        String direccion = DireccionField.getText();
        LocalDate fechadealta = Fecha.getValue();
        
        
        Alumno alumno = new Alumno(dni, nombre, edad, direccion, fechadealta, foto);
        alumnosObservable.add(alumno);
        acceso.salvar();
        Tabla.setDisable(false);
        AddBox.setDisable(true);
        AddBox.setVisible(false);
        alumnosObservable = FXCollections.observableList(acceso.getAlumnos());
        Tabla.setItems(alumnosObservable);
        foto =null;
        Img.setImage(foto);
        Add.setDisable(false);
    }

    @FXML
    void OnAdd(ActionEvent event) {
        Add.setDisable(true);
        AddBox.setDisable(false);
        Fecha.setValue(fecha);
        AddBox.setVisible(true);
        foto =null;
        Img.setImage(foto);
        Tabla.setDisable(true);
        Tabla.getSelectionModel().clearSelection();
    }

    @FXML
    void OnCancelar(ActionEvent event) {
         AddBox.setDisable(true);
         AddBox.setVisible(false);
         Tabla.setDisable(false);
         foto =null;
         Img.setImage(foto);
         Add.setDisable(false);
    }

    @FXML
    void OnEliminar(ActionEvent event) {
         Alumno alumno = Tabla.getSelectionModel().getSelectedItem();
         alumnosObservable.remove(alumno);
         acceso.salvar();
         alumnosObservable = FXCollections.observableList(acceso.getAlumnos());
         Tabla.setItems(alumnosObservable);
         Tabla.getSelectionModel().clearSelection();
         foto =null;
         Img.setImage(foto);
         
    }

    @FXML
    void OnFecha(ActionEvent event) {

    }

    @FXML
    void OnFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir fichero");
        fileChooser.getExtensionFilters().addAll(           
                new ExtensionFilter("Imágenes", "*.png", "*.jpg"),
                new ExtensionFilter("Todos", "*.*"));
        File imageFile = fileChooser.showOpenDialog(
                ((Node) event.getSource()).getScene().getWindow());
        if (imageFile != null) {
            String filelocation = imageFile.toURI().toString();
            foto = new Image(filelocation);
            Img.setImage(foto);
        }
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

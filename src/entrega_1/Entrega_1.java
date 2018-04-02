/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega_1;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Miguel
 */
public class Entrega_1 extends Application {
    
    @Override
       public void start(Stage stage) {
        try{
           FXMLLoader loader = new 
        FXMLLoader(getClass().getResource("Main.fxml"));
           BorderPane root =(BorderPane) loader.load ();

           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.setTitle("Academia"); 
           stage.setMinWidth(850);
           stage.setMinHeight(650);
           stage.getIcons().add(new 
                Image(getClass().getResourceAsStream("icon.png")));
           MainController ControladorPrincipal = 
                   loader.<MainController>getController();
           ControladorPrincipal.initStage(stage);
           stage.show();
         } catch (IOException e) {e.printStackTrace();}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hupijekku.reitinhaku.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author eemes
 */
public class Kayttoliittyma extends Application {
    
    @Override
    public void start(Stage window) {
        Label l = new Label("Reitinhaku");
        StackPane paneeli = new StackPane(l);
        Scene ikkuna = new Scene(paneeli, 640, 480);
        window.setScene(ikkuna);
        window.show();
    }
    
    
    public static void main(String[] args) {
        launch(Kayttoliittyma.class);
    }
}

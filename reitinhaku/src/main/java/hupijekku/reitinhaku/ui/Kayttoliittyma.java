/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hupijekku.reitinhaku.ui;

import dao.Kartta;
import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.AStar;
import main.Leveyshaku;
import tietorakenteet.Solmu;
import tietorakenteet.Binäärikeko;

/**
 *
 * @author eemes
 */
public class Kayttoliittyma extends Application {
    
    @Override
    public void start(Stage window) {
        
        Label l = new Label("Reitinhaku");
        StackPane paneeli = new StackPane(l);
        BorderPane menu = new BorderPane();
        Scene ikkuna = new Scene(paneeli, 900, 600);
        VBox menuAsettelu = new VBox(1);
        HBox menuKartanValinta = new HBox(2);
        Label lblKartanValinta = new Label("Valitse kartta");
        ComboBox<String> cbKartanValinta = new ComboBox();
        
        menuKartanValinta.getChildren().add(lblKartanValinta);
        menuKartanValinta.getChildren().add(cbKartanValinta);
        menuAsettelu.getChildren().add(menuKartanValinta);
        
        Insets hienosäätö = new Insets(10, 10, 10, 10);
        menuAsettelu.setPadding(hienosäätö);
        menuKartanValinta.setPadding(hienosäätö);
        menuAsettelu.setSpacing(10);
        menuKartanValinta.setSpacing(10);
        
        File kansio = new File("./kartat/");
        String[] tiedostot = kansio.list();
        
        ObservableList<String> kartat = FXCollections.observableArrayList(tiedostot);
        cbKartanValinta.setItems(kartat);
        cbKartanValinta.getSelectionModel().selectFirst();
        
        int piirtoAlueenKoko = 600;
        Canvas piirtoalusta = new Canvas(piirtoAlueenKoko, piirtoAlueenKoko);
        menu.setCenter(piirtoalusta);
        menu.setRight(menuAsettelu);
        paneeli.getChildren().add(menu);
        GraphicsContext grafiikka = piirtoalusta.getGraphicsContext2D();
        
        Kartta kartta = new Kartta(new File("./kartat/arena2.map"));
        char[][] taulukko = kartta.luoTaulukko();
        int pikselinKoko = piirtoAlueenKoko/Math.max(taulukko.length, taulukko[0].length);        
        
        piirräKartta(taulukko, grafiikka, pikselinKoko);
        cbKartanValinta.setOnAction(event -> {
            grafiikka.clearRect(0, 0, piirtoalusta.getWidth(), piirtoalusta.getHeight());
            Kartta kartta2 = new Kartta(new File("./kartat/" + cbKartanValinta.getValue().toString()));
            char[][] taulukko2 = kartta2.luoTaulukko();
            int pikselinKoko2 = piirtoAlueenKoko/Math.max(taulukko2.length, taulukko2[0].length);
            piirräKartta(taulukko2, grafiikka, pikselinKoko2);
            Solmu alku = new Solmu(85, 105);
            Solmu loppu = new Solmu(260, 110);
            
            
            AStar haku = new AStar(taulukko2, true);
            Leveyshaku haku2 = new Leveyshaku(taulukko2);
            
            double löytyi = haku.etsiReitti(alku, loppu);
            System.out.println(löytyi);
            int[][] reitti = haku.haeReitti();
            
            piirräReitti(taulukko, grafiikka, reitti, pikselinKoko2);
        });
        

        window.setScene(ikkuna);
        
        window.show();
    }
    
    
    public void piirräKartta(char[][] merkit, GraphicsContext grafiikka, int pikselinKoko) {
        Color seinä = Color.BLACK;
        Color tyhjä = Color.WHITE;
        Color puu = Color.GREEN;
        Color vesi = Color.BLUE;
        for (int i = 0; i < merkit[0].length; i++) {
            for (int j = 0; j < merkit.length; j++) {
                if (merkit[j][i] == '@') {
                    grafiikka.setFill(seinä);
                } else if (merkit[j][i] == 'W') {
                    grafiikka.setFill(vesi);
                } else if (merkit[j][i] == 'T') {
                    grafiikka.setFill(puu);
                } else {
                    grafiikka.setFill(tyhjä);
                }
                grafiikka.fillRect(i * pikselinKoko, j * pikselinKoko, pikselinKoko, pikselinKoko);
            }
        }
    }
    
    public void piirräReitti(char[][] merkit, GraphicsContext grafiikka, int[][] reitti, int pikselinKoko) {
        for (int i = 0; i < merkit[0].length; i++) {
            for (int j = 0; j < merkit.length; j++) {
                if (reitti[j][i] == 1) {
                    Color polku = Color.BLUEVIOLET;
                    grafiikka.setFill(polku);
                    grafiikka.fillRect(i * pikselinKoko, j * pikselinKoko, pikselinKoko, pikselinKoko);
                } else if (reitti[j][i] == 2) {
                    Color polku = Color.RED;
                    grafiikka.setFill(polku);
                    grafiikka.fillRect(i * pikselinKoko, j * pikselinKoko, pikselinKoko, pikselinKoko);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        launch(Kayttoliittyma.class);
    }
}

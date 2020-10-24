/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
import algoritmit.AStar;
import algoritmit.JPS;
import algoritmit.Leveyshaku;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tietorakenteet.Solmu;
import tietorakenteet.Binaarikeko;
import tyokalut.Laskin;
import tyokalut.Suorituskykytesti;

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
        HBox menuAlgoritminValinta = new HBox(2);
        HBox menuHaeReittiNappi = new HBox(1);
        HBox menuAsetaAlkuSolmu = new HBox(3);
        HBox menuAsetaLoppuSolmu = new HBox(3);
        HBox menuSuoritaTestit = new HBox(1);
        
        Label lblAlgoritminValinta = new Label("Valitse algoritmi");
        Label lblKartanValinta = new Label("Valitse kartta");
        Label lblAlku = new Label("Lähtöpiste");
        Label lblLoppu = new Label("Maali");
        ComboBox<String> cbKartanValinta = new ComboBox();
        ComboBox<String> cbAlgoritminValinta = new ComboBox();
        Button btnHaeReitti = new Button("Hae Reitti");
        Button btnTestit = new Button("Aja suorituskykytestit");
        TextField tfAlkuX = new TextField("0");
        TextField tfAlkuY = new TextField("0");
        TextField tfLoppuX = new TextField("0");
        TextField tfLoppuY = new TextField("0");
        tfAlkuX.setPrefWidth(50);
        tfAlkuY.setPrefWidth(50);
        tfLoppuX.setPrefWidth(50);
        tfLoppuY.setPrefWidth(50);
        
        
        menuAsetaAlkuSolmu.getChildren().add(lblAlku);
        menuAsetaAlkuSolmu.getChildren().add(tfAlkuX);
        menuAsetaAlkuSolmu.getChildren().add(tfAlkuY);
        
        menuAsetaLoppuSolmu.getChildren().add(lblLoppu);
        menuAsetaLoppuSolmu.getChildren().add(tfLoppuX);
        menuAsetaLoppuSolmu.getChildren().add(tfLoppuY);
        
        menuKartanValinta.getChildren().add(lblKartanValinta);
        menuKartanValinta.getChildren().add(cbKartanValinta);
        menuAsettelu.getChildren().add(menuKartanValinta);
        
        menuAlgoritminValinta.getChildren().add(lblAlgoritminValinta);
        menuAlgoritminValinta.getChildren().add(cbAlgoritminValinta);
        menuAsettelu.getChildren().add(menuAlgoritminValinta);
        
        menuHaeReittiNappi.getChildren().add(btnHaeReitti);
        
        menuAsettelu.getChildren().add(menuAsetaAlkuSolmu);
        menuAsettelu.getChildren().add(menuAsetaLoppuSolmu);
        menuAsettelu.getChildren().add(menuHaeReittiNappi);

        
        Insets hienosäätö = new Insets(10, 10, 10, 10);
        menuAsettelu.setPadding(hienosäätö);
        //menuKartanValinta.setPadding(hienosäätö);
        menuAsettelu.setSpacing(10);
        //menuKartanValinta.setSpacing(10);
        
        File kansio = new File("./kartat/");
        String[] tiedostot = kansio.list();
        
        ObservableList<String> kartat = FXCollections.observableArrayList(tiedostot);
        cbKartanValinta.setItems(kartat);
        cbKartanValinta.getSelectionModel().selectFirst();
        String algoritmit[] = {"AStar", "Jump Point Search", "Leveyshaku"};
        ObservableList<String> listaAlgoritmit = FXCollections.observableArrayList(algoritmit);
        cbAlgoritminValinta.setItems(listaAlgoritmit);
        cbAlgoritminValinta.getSelectionModel().selectFirst();
        
        int piirtoAlueenKoko = 600;
        Canvas piirtoalusta = new Canvas(piirtoAlueenKoko, piirtoAlueenKoko);
        menu.setCenter(piirtoalusta);
        menu.setRight(menuAsettelu);
        paneeli.getChildren().add(menu);
        GraphicsContext grafiikka = piirtoalusta.getGraphicsContext2D();
        
        menuSuoritaTestit.getChildren().add(btnTestit);
        menuAsettelu.getChildren().add(menuSuoritaTestit);
        
        btnTestit.setOnAction(event -> {
           Suorituskykytesti.suoritaTestit(true);
        });
        
        btnHaeReitti.setOnAction(event -> {
            grafiikka.clearRect(0, 0, piirtoalusta.getWidth(), piirtoalusta.getHeight());
            Kartta kartta = new Kartta(new File("./kartat/" + cbKartanValinta.getValue().toString()));
            char[][] taulukko = kartta.luoTaulukko();
            int pikselinKoko = piirtoAlueenKoko/Laskin.maksimi(taulukko.length, taulukko[0].length);
            piirräKartta(taulukko, grafiikka, pikselinKoko);
            //try {
                int x1 = Integer.parseInt(tfAlkuX.getText());
                int y1 = Integer.parseInt(tfAlkuY.getText());
                int x2 = Integer.parseInt(tfLoppuX.getText());
                int y2 = Integer.parseInt(tfLoppuY.getText());
                
                if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 ||
                        x1 >= taulukko[0].length || x2 >= taulukko[0].length ||
                        y1 >= taulukko.length || y2 >= taulukko.length) {
                    virheViesti("Virheellinen syöte", "Varmista että koordinaatit ovat kartta-alueen sisällä");
                } else {
                    Solmu alku = new Solmu(x1, y1);
                    Solmu loppu = new Solmu(x2, y2);
                    double löytyi = -1;
                    int[][] reitti = new int[0][0];
                    long aika = 0;
                    switch (cbAlgoritminValinta.getValue().toString()) {
                        case "AStar":
                            AStar hakuAStar = new AStar(taulukko, true, true);
                            löytyi = hakuAStar.etsiReitti(alku, loppu);
                            reitti = hakuAStar.haeReitti();
                            aika = hakuAStar.kulunutAika();
                            break;
                        case "Jump Point Search":
                            JPS hakuJPS = new JPS(taulukko, false);
                            löytyi = hakuJPS.etsiReitti(alku, loppu);
                            reitti = hakuJPS.haeReitti();
                            aika = hakuJPS.kulunutAika();
                            break;
                        case "Leveyshaku":
                            Leveyshaku hakuBFS = new Leveyshaku(taulukko);
                            löytyi = hakuBFS.etsiReitti(alku, loppu);
                            reitti = hakuBFS.haeReitti();
                            aika = hakuBFS.kulunutAika();
                            break;
                    }
                    System.out.println(löytyi);
                    System.out.println("Aika: " + (aika/1e9) + " s");
                    Color polku = Color.RED;
                    piirräReitti(taulukko, grafiikka, reitti, pikselinKoko);
                    grafiikka.setFill(polku);
                    grafiikka.fillRect(x1 * pikselinKoko, y1 * pikselinKoko, pikselinKoko, pikselinKoko);
                    grafiikka.fillRect(x2 * pikselinKoko, y2 * pikselinKoko, pikselinKoko, pikselinKoko);
                }
            //} catch (Exception e) {
            //    virheViesti("Virheellinen syöte", e.toString());
            //}
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
                    Color polku = Color.CYAN;
                    grafiikka.setFill(polku);
                    grafiikka.fillRect(i * pikselinKoko, j * pikselinKoko, pikselinKoko, pikselinKoko);
                } else if (reitti[j][i] == 3) {
                    Color polku = Color.CORAL;
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
    
    
    /**
    * Virheviesti-ikkuna
    * 
    * @param    otsikko   Virheen tyyppi
    * @param    sisältö   Kuvaus virheestä
    */  
    public void virheViesti(String otsikko, String sisältö) {
        Alert errorBox = new Alert(AlertType.ERROR);
        errorBox.setTitle("Virhe!");
        errorBox.setHeaderText(otsikko);
        errorBox.setContentText(sisältö);
        errorBox.showAndWait();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Lukee .map tiedoston taulukkoon
 * @author eemes
 */
public class Kartta {
    private File kartta;
    private Scanner lukija;
    
    public Kartta(File polku) {
        this.kartta = polku;
        try {
            lukija = new Scanner(kartta);
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt: " + e);
            return;
        }
    }
    
    public char[][] luoTaulukko() {
        if (lukija != null) { 
            try {
                // Rivi 1: tyyppi <- turha?
                // Rivi 2: korkeus
                // Rivi 3: leveys
                // Rivi 4: "map"? <- turha?
                lukija.nextLine(); // Skipataan tyyppi
                int korkeus = Integer.parseInt(lukija.nextLine().substring(7));
                int leveys = Integer.parseInt(lukija.nextLine().substring(6));
                char[][] taulukko = new char[korkeus][leveys];
                lukija.nextLine(); // Skipataan "map"
                for (int i = 0; i < taulukko.length; i++) {
                    taulukko[i] = lukija.nextLine().toCharArray();
                }
                return taulukko;
            } catch (Exception e) {
                // Väliaikainen ratkaisu
                System.out.println(e);
                return null;
            }
        }
        return null;
    }
    
    public Scanner haeLukija() {
        return this.lukija;
    }
    
    public File haeKartta() {
        return this.kartta;
    }
}

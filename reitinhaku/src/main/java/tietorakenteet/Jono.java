/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietorakenteet;

/**
 * Jonorakenne käyttäen hyväksi Listaa. 
 * Tarjoaa metodin ensimmäisen jäsenen toistuvalle hakemiselle.
 * @author eemes
 */
public class Jono {
    
    private Lista lista;
    
    public Jono() {
        this.lista = new Lista();
    }
    
    public Solmu otaEnsimmäinen() {
        Solmu solmu = this.lista.haeIndeksi(0);
        this.lista.poistaIndeksi(0);
        return solmu;
    }
    
    public void lisää(Solmu solmu) {
        this.lista.lisää(solmu);
    }
    
    public boolean onTyhjä() {
        return this.lista.haeMäärä() == 0;
    }
}

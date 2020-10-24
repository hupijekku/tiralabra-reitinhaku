/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tyokalut;

/**
 *
 * @author eemes
 */
public class Laskin {
    
    public static final double SQRT2 = 1.41421356;
    
    public static int neliojuuri(int x) {
        
        if (x == 0 || x == 1) {
            return x;
        }
        
        int i = 1;
        int tulos = 1;
        
        while (tulos <= x) {
            i++;
            tulos = i * i;
        }
        return i - 1;
        
    }
    
    public static int nelio(int x) {
        return x * x;
    }
    
    public static int itseisarvo(int x) {
        return x < 0 ? -x : x;
    }
    
    public static double manhattanEtaisyys(int x1, int y1, int x2, int y2) {
        return (double) itseisarvo(y2 - y1) + itseisarvo(x2 - x1);
    }
    
    public static double euklidinenEtaisyys(int x1, int y1, int x2, int y2) {
        return (double) neliojuuri(nelio(x2 - x1) + nelio(y2 - y1));
    }
    
    public static int maksimi(int x, int y) {
        return x < y ? y : x;
    }
    
    public static int minimi(int x, int y) {
        return x < y ? x : y;
    }
    
}

# Määrittelydokumentti

* TKT / Suomi / Java Maven  

Projektin tarkoituksena on vertailla useampien reitinhakualgoritmien toimintaa ja tehokkuutta. Projektin vertailtavia algoritmeja ovat ainakin leveyshaku/BFS (Breadth-First Search) , Dijkstra, A\* (A-star) ja JPS (Jump Point Search).  

Sovellus käyttää algoritmien testaamiseen kaksiulotteisia karttoja, joita se käsittelee taulukkoina. Jokainen taulukon solu toimii _solmuna_. Solmu sisältää tiedon sijainnistaan sekä pystyykö algoritmi liikkumaan kyseisen solmun läpi.  

## Algoritmit ja tietorakenteet

Leveyshakua varten tarvitaan jono ja muita varten keko. Keon toteutuksessa voidaan vielä veirtailla eri tapojen tehokkuuksia (binary/fibonacci).  
Dijkstran algoritmin aikavaatimukseksi saadaan parhaimmillaan O(E + V\*log(V)) käyttämällä Fibonacci kekoa, missä V on solmujen ja E kaarien lukumäärä.  

## Lähteet

* Antti Laaksonen - Tietorakenteet ja Algoritmit, <https://www.cs.helsinki.fi/u/ahslaaks/tirakirja/>, haettu 11.9.2020  
* Wikipedia, _Dijkstra's algorithm_, <https://en.wikipedia.org/wiki/Dijkstra's_algorithm>, haettu  11.9.2020  
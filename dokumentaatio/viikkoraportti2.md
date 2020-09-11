# Viikko 2

Tällä viikolla pääasiassa suunnittelin projektin rakennetta, tutustuin tarkemmin projektissa tutkittaviin algoritmeihin, sekä aloitin koodin ja testauksen kirjoittamisen.  

Päädyin rakennetta suunnitellessani päätökseen, että piirrän reitinhaussa käytetyt kartat käyttäen JavaFX:n Canvas-oliota. Karttatiedostojen merkeistä muodostuvat kartat luetaan taulukkoon ja merkkien perusteella piirretään käyttöliittymään pieniä värillisiä neliöitä.
 Vastaavasti myös löydetty reitti piirretään kartan päälle jollakin erotettavalla räikeällä värillä. Mietin myös mahdollisuuksia reitinhaun animoinnissa, jotta käyttöliittymästä voisi tutkia miten reitin etsintä etenee.  
 
 Perehdyin projektin algoritmeihin syvemmin, ja uskoisin osaavani nyt toteuttaa ainakin BFS ja Dijkstran algoritmit. BFS tosiaan vaatii, että kaikki kaaret ovat painottomia, joten sen tehokkuutta verrattaessa täytyy kulmikkain liikkuminen estää. Selvitin algoritmeja tutkiessani samalla Dijkstran algoritmin aikavaatimuksen, ja lisäsin sen määrittelydokumenttiin.  

Aloitin projektin myös koodin työstämisen. Loin ensin projektipohjan ja kaikki tällä hetkellä tarvittavat vaatimukset Mavenilla. Loin tyhjän JavaFX sovelluksen varmistaakseni sen toiminnan, sekä yksinkertaisen data-olion joka lukee projektissa käytettyjä .map-tiedostoja taulukoiksi. Loin myös yksinkertaisen pohjan Solmu-rakenteelle, jota täydennän seuraavalla viikolla. 
Lisäsin kartanlukijalle ja solmulle myös ensimmäisiä yksikkötestejä, sekä korjasin niissä syntyneet Checkstyle-virheet.  

Seuraavalla viikolla toteutan keko-tietorakenteen ja alan suunnitella algoritmien koodin toteuttamista, sekä lisään yksikkötestejä.

Tällä viikolla aikaa projektiin kului noin kuusi (6) tuntia. 
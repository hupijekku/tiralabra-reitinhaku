# Toteutusdokumentti

## Ohjelman yleisrakenne  
Sovellus koostuu seuraavista pakkauksista:  

- algoritmit: Reitinhakualgoritmit
- dao: Karttojen lukeminen levyltä
- main: Simppeli main-luokka ohjelman suorittamiseksi
- tietorakenteet: toteutetut tietorakenteet
- tyokalut: apuluokka Laskin, sekä suorituskykytestit
- ui: käyttöliittymä

Toiminta:  
Kartta-luokka lukee .map-tiedoston levyltä ja muuntaa sen char-taulukoksi. Taulukko siirtyy reitinhakualgoritmille, joka etsii reitin kahden annetun Solmu-olion välille. Algoritmi palauttaa reitin taulukkona käyttöliittymälle, joka piirtää kartan ja reitin ikkunaan.  

## Aika- ja tilavaativuudet
### Keko
A\*- ja JPS algoritmeissa on käytössä keko-rakenne. Se on toteutettu tässä ohjelmassa binäärikekona. Binäärikeon päällimmäisin Solmu, eli haluttu pienin Solmu, voidaan hakea aina tehokkaasti ajassa _O(1)_. Alkioita lisätessä tai poistettaessa keon rakenne täytyy kuitenkin korjata, missä kuluu aikaa _O(log n)_.  
Kooltaan keko on toteutetty dynaamisesti, eli se kasvaa tarvittaessa, mutta kuitenkin lineaarisesti _O(n)_.  

### Lista
Leveyshaku käyttää jonoa, joka on toteutettu lista-rakenteen avulla. Toteuttamassani listassa alkioiden lisääminen ja hakeminen tapahtuu vakioajassa _O(1)_.  
Alkion poisto-operaatio kuitenkin on tällä hetkellä _O(n)_, sillä se siirtää listan loppua yhden pykälän vasemmalle poiston kohdalta.  
Kooltaan lista on myös dynaaminen, se kasvaa tarvittaessa. Alkioiden poisto ei kuitenkaan tällä hetkellä pienennä taulukkoa vapauttaen muistia.  

### Leveyshaku
Leveyshaun aikavaativuus on _O(|V| + |E|)_.  
Toteutin leveyshaun lähinnä vertailukohteena A\*- ja JPS-algoritmien tehokkuuksia varten.  
Suorituskykytesteistä ilmeni, että leveyshaku oli kuitenkit A\*:ta nopeampi labyrinttimaisella kartalla, jossa mahdollisia ruutuja on vähän.  
Muilla kartoilla leveyshaku kuitenkin selvästi hävisi A\*:lle ja JPS oli kaikissa tapauksissa huomattavasti nopeampi.  

### A\*
A\*-algoritmin _worst-case_ aikavaativuus on _O(|E|)_. Algoritmin nopeus kuitenkin vaihtelee paljon verkon ja heuristiikan perusteella.  
Suorituskykytestien perusteella A\* pärjäsi hyvin avoimella ja satunnaisella kartalla, mutta tehosi huonosti ahtaassa labyrintissa.  Manhattan-etäisyys oli myös kaikissa kartoissa nopeampi vaihtoehto.  


### JPS
JPS:n aikavaativuus on sama kuin A\*:n, mutta se oli selvästi A\*:ia nopeampi useimmissa testeissä. Satunnaisella kartalla JPS kuitenkin lievästi hävisi A\*, kun A\*:lla oli käytössä Manhattan-etäisyys. Tämä johtunee siitä, että satunnaisessa kartassa JPS-algoritmi joutuu luomaan todella monia hyppypisteitä.  

### Työn puutteet
Olen tyytyväinen ominaisuuksiin, jotka sain aikaan. Alunperin tavoitteena oli kuitenkin toteuttaa keko myös fibonacci-kekona, mutta minulla ei siihen jäänyt aikaa.  
Koodi on mielestäni hyvää, joskin kommentointia olisi jotkin kohdat ehkä kaivanneet lisää.  
Muutama metodi on vielä turhan pitkä, ja erityisesti JPS-algoritmin toteutuksessa syntyneet if-lausekeryppäät ovat ikävän näköisiä.  
Algoritmeihin löytyisi varmasti myös tapoja optimoida niiden tehokkuutta lisää, mutta kirjoittamani algoritmit toimivat ja ovat mielestäni erittäin tehokkaita jo nyt.  


### Lähteet

- https://en.wikipedia.org/wiki/A*_search_algorithm

- https://en.wikipedia.org/wiki/Jump_point_search

- https://harablog.wordpress.com/2011/09/07/jump-point-search/

- **D. Harabor, A. Grastien** (2012), The JPS Pathfinding System, https://www.aaai.org/ocs/index.php/SOCS/SOCS12/paper/viewFile/5396/5212, 
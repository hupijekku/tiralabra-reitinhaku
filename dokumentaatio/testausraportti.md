# Testaus

## Yksikkötestit  
Ohjelma sisältää kattavat yksikkötestit algoritmeille, sekä tietorakenteille. Käyttöliittymälle tai suorituskykytesteille ei ole yksikkötestejä ja ne on siten jätetty testauskattavuuden ulkopuolelle.  

Sovelluksen testit voi ajaa komennolla `mvn test`.  

Testikattavuusraportin voi generoida komennolla `mvn test jacoco:report`. Raportti löytyy tämän jälkeen polusta `/target/site/jacoco/index.html`.  

![Testikattavuus](https://raw.githubusercontent.com/hupijekku/tiralabra-reitinhaku/master/dokumentaatio/kuvat/testikattavuus.png)

## Suorituskykytestit  

Suorituskykytestit vertaavat kolmen eri algoritmin tehokkuutta kolmella eri kartalla. Jokainen algoritmi suorittaa 100:n satunnaisen reitin haun kaikilla kolmella kartalla ja mitatut ajat summataan yhteen.  
Suorituskykytestit voi ajaa painamalla käyttöliittymästä nappia "Aja suorituskykytestit". Tulokset tulostetaan sekä komentoriville, että tiedostoon "suorituskykytestit.txt".  

| Kartta      | Leveyshaku | A* (Manhattan) | A* (Euklidinen) | JPS (Manhattan) | JPS (Euklidinen) |
|-------------|------------|----------------|-----------------|-----------------|------------------|
| Labyrintti  | 1.497784 s | 1.519263 s     | 2.816530 s      | 0.360248 s      | 0.499265 s       |
| New York    | 5.459552 s | 0.143247 s     | 0.961192 s      | 0.061692 s      | 0.061949 s       |
| Satunnainen | 7.789201 s | 0.053222 s     | 0.834099 s      | 0.137170 s      | 0.050202 s       |
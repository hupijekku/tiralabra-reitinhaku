# Reitinhakualgoritmien vertailu

Reitinhakualgoritmien toiminnan ja tehokkuuden vertailua Javalla Tietorakenteet ja algoritmit harjoitustyökurssille, HY 2020

## Dokumentaatio

* [Määrittelydokumentti](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/m%C3%A4%C3%A4rittelydokumentti.md)  
* [Testausraportti](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/testausraportti.md)  
* [Käyttöohje](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/käyttöohje.md)
* [Toteutusdokumentti](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/toteutusdokumentti.md)

## Viikkoraportit

* [Viikko 1](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/viikkoraportti1.md)  
* [Viikko 2](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/viikkoraportti2.md)  
* [Viikko 5](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/viikkoraportti5.md)  
* [Viikko 6](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/viikkoraportti6.md)  

## Toiminnot  

### Testaus

Testit voi suorittaa komennolla

```
mvn test
```

Testikattavuuden raportti luodaan komennolla  

```
mvn test jacoco:report
```  

### Checkstyle

Koodin tyylin tiedoston checkstyle.xml määrittelyn mukaan voi tarkistaa komennolla  

```
mvn jxr:jxr checkstyle:checkstyle
```  
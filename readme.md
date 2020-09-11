# Reitinhakualgoritmien vertailu

Reitinhakualgoritmien toiminnan ja tehokkuuden vertailua Javalla Tietorakenteet ja algoritmit harjoitustyökurssille, HY 2020

## Dokumentaatio

* [Määrittelydokumentti](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/m%C3%A4%C3%A4rittelydokumentti.md)  

## Viikkoraportit

* [Viikko 1](https://github.com/hupijekku/tiralabra-reitinhaku/blob/master/dokumentaatio/viikkoraportti1.md)  

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
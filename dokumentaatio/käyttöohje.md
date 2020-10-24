# Käyttöohje

## Komentorivi

Ohjelman voi suorittaa komentoriviltä komennolla  
```
mvn compile exec:java -Dexec.mainClass=main.Main
```  
Testit voi suorittaa komennolla  
```
mvn test
```  
Testikattavuusraportin voi luoda komennolla  
```
mvn test jacoco:report
```  
Jonka jälkeen raportti löytyy polusta
```
/target/site/jacoco/index.html
```  
Checkstyle-raportin voi luoda komennolla  
```
mvn jxr:jxr checkstyle:checkstyle
```  
Jonka jälkeen raportti löytyy polusta  
```
/target/site/checkstyle.html
```  

## Käyttöliittymä
![Testikattavuus](https://raw.githubusercontent.com/hupijekku/tiralabra-reitinhaku/master/dokumentaatio/kuvat/käyttöliittymä.png)  
Käyttöliittymässä oikealla puolella on valikko, josta käyttäjä voi valita käytettävän kartan, algoritmin ja reitin päätepisteet.  
Päätepisteiden arvojen pitää olla kartan rajojen sisällä, tai ohjelma hylkää syötteet.  
Painamalla "Hae Reitti" -nappia, ohjelma piirtää vasemmalle puolelle valitun kartan ja sen päälle valitulla algoritmilla haetun reitin päätepisteiden välille.  
Painamalla "Aja suorituskykytestit" -nappia ohjelma aloittaa suorituskykytestien ajamisen. Tässä voi kestää parikymmentä sekunttia laitteiston tehosta riippuen.  
Suorituskykytestien tulokset tulevat juurikansioon tekstitiedostoon, sekä komentoriville mikäli sellainen on käytössä.  
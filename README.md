# HowTo Update aax4 Projekt

1. Clone dir das Projekt lokal (IntelliJ oder Eclipse) https://git.congstar.net/it-qa/libs/aax4
2. Ziehe dir die neue api-docs.json zB. TuaDev: https://cc4.nicotuadev.de/co-restservice/v3/api-docs
3. Einmal 'schön' formatieren ( zB. Notepad++ )
4. Im lokalen Projekt einpflegen
5. Committen und pushen
6. Warte darauf das deine Pipline durchgelaufen ist https://git.congstar.net/it-qa/libs/aax4/-/pipelines
7. Navigiere zu -> https://git.congstar.net/it-qa/libs/aax4/-/packages
8. Klicke auf 'de/congstar/aax4'
9. Scrolle runter und suche nach der neuesten Jar und kopiere die Versionnummer ala: 1.0-20241120.055753-14
10. Ersetzt im congoTa Projekt in der pom.xml Versionnummer
```xml
    <dependency>
        <groupId>de.congstar</groupId>
        <artifactId>aax4</artifactId>
        <version>1.0-20241120.055753-14</version>
    </dependency>
```
11. Maven Reload, Clean und Compile, und Build des congoTa Projekts
12. Ggf mit Änderungen committen und pushen (!!!)
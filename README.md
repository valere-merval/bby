# HowTo Update aax4 Projekt

1. Sicherstellen dass die erwarteten Änderungen durch das automatische Update der REST-API-Datei (`api-docs.json`) eingecheckt wurden: https://git.congstar.net/it-qa/libs/aax4/-/commits/main
1. Navigiere zu -> https://git.congstar.net/it-qa/libs/aax4/-/packages
1. Klicke auf 'de/congstar/aax4'
1. Scrolle runter und suche nach der neuesten Jar und kopiere die Versionnummer ala: 1.0-20241120.055753-14
1. Ersetzt im congoTa Projekt in der pom.xml Versionnummer
    ```xml
        <dependency>
            <groupId>de.congstar</groupId>
            <artifactId>aax4</artifactId>
            <version>1.0-20241120.055753-14</version>
        </dependency>
    ```
1. Maven Reload, Clean und Compile, und Build des congoTa Projekts
1. Ggf mit Änderungen committen und pushen (!!!)
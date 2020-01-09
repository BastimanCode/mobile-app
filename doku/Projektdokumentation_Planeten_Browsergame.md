<p style="font-weight:600; font-size:36px; text-align:center;">Planeten Browsergame</p>
<p style="font-weight:600; font-size:18px; text-align:center">Tim Felix Tanner (Matrikelnr)</p>
<p style="font-weight:600; font-size:18px; text-align:center">Bastian Schneider (1151420)</p>
<div style="page-break-after: always;"></div>
[TOC]

# Einleitung

Die Folgende Projektbeschreibung schildert den Aufbau und Ablauf des Projekts im Modul Mobile Applikationen der Fachhochschule Bielefeld, welches die Autoren im laufe des Semesters durchgeführt haben.

## Projektbeschreibung

Die Entwicklung eines Spiels, das auf Browserspielen basiert, die früher populär waren. Das Thema des Spiels ist, aufgrund der Tatsache das der Fantasie kaum Grenzen gesetzt sind, Science-Fiction. Das Spiel findet in einem fiktivem Universum statt und der Spieler übernimmt die Rolle des Herrschers eines Planeten. 

## Projektziel

Die entwickelte Applikation soll anschaulich und intuitiv sein. Es wird bewusst auf Animationen und ähnliches verzichtet, um den Stil und Charme der vorher genannten Browserspiele beizubehalten. Die Benutzeroberfläche soll simpel gehalten werden und dennoch alle nötigen Funktionen klar erkennbar darstellen um eine einfache Bedienung zu gewährleisten. Das Design soll einheitlich sein um der Applikation ein natürliches Aussehen zu verleihen.

Das Spielkonzept soll einfach gehalten werden und trotzdem dafür sorgen dem Spieler Spaß und die Lust zum weiterzuspielen bringen. Berechnungen und ähnliches sollen daher auch im Hintergrund geschehen um den Fluss des Spiels so wenig wie möglich zu unterbrechen.

## Projektbegründung

Aufgrund der Tatsache, das kaum Browserspiele, so wie sie sind, als Mobile Applikationen veröffentlicht wurden haben sich die Autoren dazu entschieden dieses Projekt zu bearbeiten.

## Projektschnittstellen

Die Applikation, die vom Benutzer bedient wird, ist nur ein Teil eines Systems. Die Eingaben des Benutzers werden an einen Server gesendet und dort verarbeitet. Der Server wurde mithilfe von [Node.js](https://nodejs.org/en/) programmiert. Des weiteren gibt es eine [MySQL](https://www.mysql.com/de/) Datenbank zur Speicherung von verschiedensten Werten. Es wurde außerdem [MySQL Workbench](https://www.mysql.com/de/products/workbench/) zur Überwachung und zum einpflegen der Daten verwendet. Die Kommunikation zwischen Server und Datenbank ist auch mit [Node.js](https://nodejs.org/en/) geregelt und im Server implementiert. Genauere Informationen befinden sich im Kapitel [Implementierung](#Implementierung). Der Benutzer interagiert also nur mit dem Client, während alles andere an anderer Stelle passiert. Mehr dazu im Kapitel, in dem die [Architektur](#Architekturbeschreibung) beschrieben wird.

## Projektabgrenzung

Da die Arbeitszeit am Projekt beschränkt ist, soll die Applikation mit wenigen Spielern im Sinn implementiert werden.

# Stand der Technik

# Anforderungsdokumentation

## Übersicht

**Must have:**

- [x] Basen können ausgebaut werden (Minen, Truppen)
- [x] Angriffe auf Spieler
- [x] Ressourcenabbau
- [x] Bilder
- [x] Server-Client Architektur
- [x] Datenbank
- [x] Mindestens drei verschiedene Einheiten mit mindestens zwei verschiedenen Werten (Angriff, Leben)
- [x] Funktionierendes Kampfsystem
- [x] Erstellung eines Kontos und Anmeldung

**Should have:**

- [ ] Nicht-Spieler-Charaktere
- [x] Forschung
- [x] Verbesserungen (Truppen/Gebäude)
- [x] Verteidigungsanlagen
- [x] Ansprechenderes Design
- [x] Einfaches Kampfsystem

**Could have:**

- [ ] zweidimensionale Karte
- [ ] neue Server (Verschiedene Spielmodi)
- [ ] Sound bzw. Musik
- [x] Mehr Einheitenauswahl
- [x] Komplexes, ausbalanciertes Kampfsystem

**Won't have:**

- [ ] Animationen
- [ ] Werbung
- [ ] Bildschirmdrehung

## Verlauf

Zu Beginn des Projekts waren wir sehr motiviert und haben geglaubt, das wir auch die could have's teilweise noch erreichen könnten. Im Laufe der Arbeit am Projekt hat sich herausgestellt das diese Einschätzung sehr optimistisch war und wir haben uns darauf geeinigt uns mit den should have's zufrieden zu geben. Am Ende ist es dann doch noch dazu gekommen das wir unsere erste Zielsetzung erreicht haben.

## Erste Konzepte

### Landscape Layout 

<img src="D:\FH\git\mobile-app\doku\mockups\Basti\startpage.png" alt="Startseite" style="zoom:50%;" />

<center>Bild 1: Mockup der Startseite</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Basti\overview.png" alt="Übersicht" style="zoom:50%;" />

<center>Bild 2: Mockup der Übersichtsseite</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Basti\planets.png" alt="Planeten" style="zoom:50%;" />

<center>Bild 3: Mockup der Planetenseite</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Basti\buildings.png" alt="Gebäude" style="zoom:50%;" />

<center>Bild 4: Mockup der Gebäudeseite</center>
### Portrait Layout

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\home.png" alt="Übersicht" style="zoom:60%;" />

<center>Bild 5: Mockup der Übersichtsseite</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Felix\menu.png" alt="Menü" style="zoom:60%;" />

<center>Bild 6: Mockup des Dropdown Menüs</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Felix\resources.png" alt="Ressourcen" style="zoom:60%;" />

<center>Bild 7: Mockup der Ressourcenseite</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Felix\ressourcedetails.png" alt="Ressourcen - Detailansicht" style="zoom:60%;" />

<center>Bild 8: Mockup der Detailansicht</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Felix\buildings.png" alt="Gebäude" style="zoom:60%;" />

<center>Bild 9: Mockup der Gebäudeseite</center>
<img src="D:\FH\git\mobile-app\doku\mockups\Felix\research.png" alt="Forschung" style="zoom:60%;" />

<center>Bild 10: Mockup der Forschungsseite</center>
### Finales Konzept

Hauptsächlich wurden in den tatsächlichen Entwurf der Benutzeroberfläche Ideen aus den Mockups im [Portrait Layout](#Portrait-Layout) übernommen, da sich diese Darstellungsweise besser für Auflistungen eignet als das [Landscape Layout](#Landscape-Layout).

Der Startbildschirm wie er in *Bild 1* zu sehen ist wurde verworfen, da es innerhalb der Applikation kein Einstellungsmenü gibt. Damit verliert der Startbildschirm seine Funktion. Nun ist der Startbildschirm eine Übersichtsseite die der in *Bild 5* ähnlich sieht.

Auch das Dropdown Menü (*Bild 6*) wurde fast genau so übernommen. Die Planetenleiste am rechten Rand der Bilder mit [Portrait Layout](#Portrait-Layout) ist ganz weggefallen, da jeder Spieler nur einen Planet hat und nicht mehrere wie zu Anfang festgelegt. Die Seiten zur Anzeige der Gebäude (*Bild 9*) und Forschung (*Bild 10*) sind fast eins zu eins übernommen worden. Der einzige unterschied hier ist, das sie jetzt die gesamte Breite einnehmen.

Die Wahl der Farbe

# Architekturbeschreibung

# Implementierung



## Spielkonzepte

Jeder Spieler kann auf seinem Planeten drei verschiedene Ressourcen abbauen, die für verschiedenste Gebäude und Verbesserungen benötigt werden. Der Abbau findet passiv statt. Das heißt, das auch wenn man nicht aktiv im Spiel - also "Offline" - ist werden Ressourcen erzeugt. Diese Ressourcen sind Baumaterialien, Computerchips und Treibstoff. Sie können verwendet werden, um Gebäude zu errichten, Raumschiffe und Verteidigungsanlagen zu bauen und Forschung zu betreiben. Des weiteren können Gebäude Verbessert werden, was verschiedene Boni mit sich bringt. Zum einen gibt es Gebäude, die die Produktion bestimmter Ressourcen verbessern, zum anderen gibt es Gebäude die die maximale Kapazität der Ressourcen erhöht. Des weiteren gibt es Gebäude die die Produktivität verschiedener Aspekte, wie zum Beispiel die Geschwindigkeit der Forschung oder das Bauen von Raumschiffen verbessert.

Die meisten Gebäude können dazu noch aufgewertet werden, was dazu führt das die Ressourcenkosten mit dem jeweiligen Bonus steigen. Das dient dem Zweck dem Spieler einen Sinn für das erlangen von Ressourcen zu geben. Durch die Verbesserung von Verteidigungsanlagen kann der Spieler seine Chancen vergrößern Angriffe von anderen Spielern abzuwehren.

Angriffe sind ein weiterer Weg um Ressourcen zu erhalten. Der Spieler kann seine Raumschiffe zu einem anderen Planeten schicken und versuchen einem anderen Spieler Ressourcen zu stehlen. An dieser Stelle kommen die Verteidigungsanlagen, die man auf einem Planeten errichten und verbessern kann nun ins Spiel. Durch diese erhöht man seine Chance den Kampf zu gewinnen, da diese zusätzlich zu den eigenen Raumschiffen am Kampf teilnehmen.

Raumschiffe haben vier verschiedene Werte. Trefferpunkte, Schilde, Angriff und Feuerrate. Anhand dieser Werte wird ein Kampf simuliert. Die Verschiedenen Schiffe haben Stärken und Schwächen wie zum Beispiel eine besonders hoher Schaden aber dafür eine geringere Feuerrate oder ähnliches.

# Tests und Usability

## Usability

### Benutzeroberfläche

Die Benutzeroberfläche wurde so entworfen, dass sich alle Funktionen entweder als Einträge im immer sichtbaren Dropdown Menü wiederfinden oder als Buttons an dem von der ausgeführten Aktion betroffenem Element sind. Des weiteren wurde für alle Seiten mit ähnlichem, Listenartigen Aufbau das gleiche Muster verwendet. Einzelne, zusammengehörende Elemente sind von einem Rahmen umgeben um sie voneinander abzugrenzen. Das Dropdown Menü beinhaltet fast ausschließlich Punkte zum Wechseln der Seite. Das sind Aktionen, die man nicht permanent benötigt und daher werden sie durch die Platzierung im Dropdown Menü versteckt. Dadurch kann Platz für die wichtigeren Aktionen geschaffen werden, die somit mehr ins Auge fallen.

# Zusammenfassung


<p style="font-weight:600; font-size:36px; text-align:center;">Planeten Browsergame</p>
<p style="font-weight:600; font-size:18px; text-align:center">Tim Felix Tanner (Matrikelnr)</p>
<p style="font-weight:600; font-size:18px; text-align:center">Bastian Schneider (1151420)</p>
<div style="page-break-after: always;"></div>
[TOC]

# Einleitung

## Die Idee

Die Entwicklung eines Spiels, das auf Browserspielen basiert, die früher populär waren. Das Thema des Spiels ist, aufgrund der Tatsache das der Fantasie kaum Grenzen gesetzt sind, Science-Fiction. Das Spiel findet in einem fiktivem Universum statt und der Spieler übernimmt die Rolle des Herrschers eines Planeten. 

## Das Ziel

Die entwickelte Applikation soll anschaulich und intuitiv sein. Es wird bewusst auf Animationen und ähnliches verzichtet, um den Stil und Charme der vorher genannten Browserspiele beizubehalten. Die Benutzeroberfläche soll simpel gehalten werden und dennoch alle nötigen Funktionen klar erkennbar darstellen um eine einfache Bedienung zu gewährleisten. Das Design soll einheitlich sein um der Applikation ein natürliches Aussehen zu verleihen.

Das Spielkonzept soll einfach gehalten werden und trotzdem dafür sorgen dem Spieler Spaß und die Lust zum weiterzuspielen bringen. Berechnungen und ähnliches sollen daher auch im Hintergrund geschehen um den Fluss des Spiels so wenig wie möglich zu unterbrechen.

## Die Begründung

## Die Schnittstellen

Die Applikation, die vom Benutzer bedient wird, ist nur ein Teil eines Systems. Die Eingaben des Benutzers werden an einen Server gesendet und dort verarbeitet. Des weiteren gibt es eine Datenbank zur Speicherung von verschiedensten Werten. Der Benutzer interagiert also nur mit dem Client, während alles andere an anderer Stelle passiert. Mehr dazu im Kapitel, in dem die [Architektur](#Architekturbeschreibung) beschrieben wird.

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

- [ ] Nicht-Spieler-Caraktere
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

![Startseite](D:\FH\git\mobile-app\doku\mockups\Basti\startpage.png)

<center>Bild 1: Mockup der Startseite</center>

![Übersicht](D:\FH\git\mobile-app\doku\mockups\Basti\overview.png)

<center>Bild 2: Mockup der Übersichtsseite</center>

![Planeten](D:\FH\git\mobile-app\doku\mockups\Basti\planets.png)

<center>Bild 3: Mockup der Planetenseite</center>

![Gebäude](D:\FH\git\mobile-app\doku\mockups\Basti\buildings.png)

<center>Bild 4: Mockup der Gebäudeseite</center>

### Portrait Layout

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\home.png" alt="Übersicht" style="zoom:80%;" />

<center>Bild : Mockup der Übersichtsseite</center>

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\menu.png" alt="Menü" style="zoom:80%;" />

<center>Bild 6: Mockup des Dropdown Menüs</center>

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\resources.png" alt="Ressourcen" style="zoom:80%;" />

<center>Bild 7: Mockup der Ressourcenseite</center>

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\ressourcedetails.png" alt="Ressourcen - Detailansicht" style="zoom:80%;" />

<center>Bild 8: Mockup der Detailansicht</center>

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\buildings.png" alt="Gebäude" style="zoom:80%;" />

<center>Bild 9: Mockup der Gebäudeseite</center>

<img src="D:\FH\git\mobile-app\doku\mockups\Felix\research.png" alt="Forschung" style="zoom:80%;" />

<center>Bild 10: Mockup der Forschungsseite</center>

# Architekturbeschreibung

# Implementierung



## Spielkonzepte

Jeder Spieler kann auf seinem Planeten drei verschiedene Ressourcen abbauen, die für verschiedenste Gebäude und Verbesserungen benötigt werden. Der Abbau findet passiv statt. Das heißt, das auch wenn man nicht aktiv im Spiel - also "Offline" - ist werden Ressourcen erzeugt. Diese Ressourcen sind Baumaterialien, Computerchips und Treibstoff. Sie können verwendet werden, um Gebäude zu errichten, Raumschiffe und Verteidigungsanlagen zu bauen und Forschung zu betreiben. Des weiteren können Gebäude Verbessert werden, was verschiedene Boni mit sich bringt. Zum einen gibt es Gebäude, die die Produktion bestimmter Ressourcen verbessern, zum anderen gibt es Gebäude die die maximale Kapazität der Ressourcen erhöht. Des weiteren gibt es Gebäude die die Produktivität verschiedener Aspekte, wie zum Beispiel die Geschwindigkeit der Forschung oder das Bauen von Raumschiffen verbessert.

Die meisten Gebäude können dazu noch aufgewertet werden, was dazu führt das die Ressourcenkosten mit dem jeweiligen Bonus steigen. Das dient dem Zweck dem Spieler einen Sinn für das erlangen von Ressourcen zu geben. Durch die Verbesserung von Verteidigungsanlagen kann der Spieler seine Chancen vergrößern Angriffe von anderen Spielern abzuwehren.

Angriffe sind ein weiterer Weg um Ressourcen zu erhalten. Der Spieler kann seine Raumschiffe zu einem anderen Planeten schicken und versuchen einem anderen Spieler Ressourcen zu stehlen. An dieser Stelle kommen die Verteidigungsanlagen, die man auf einem Planeten errichten und verbessern kann nun ins Spiel. Durch diese erhöht man seine Chance den Kampf zu gewinnen, da diese zusätzlich zu den eigenen Raumschiffen am Kampf teilnehmen.

Raumschiffe haben vier verschiedene Werte. Trefferpunkte, Schilde, Angriff und Feuerrate. Anhand dieser Werte wird ein Kampf simuliert. Die Verschiedenen Schiffe haben Stärken und Schwächen wie zum Beispiel eine besonders hoher Schaden aber dafür eine geringere Feuerrate oder ähnliches.

# Tests und Usability

# Zusammenfassung


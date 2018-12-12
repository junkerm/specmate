# Specmate Getting Started Guide (Leitfaden für den Einstieg)
## Inhaltsverzeichnis

- Installation, Konfiguration & Startup](#Installationskonfiguration--Startup)
  * [Installation](#installation)
  * [Konfiguration](#konfiguration)
    + [Neue Projekte hinzufügen](#neue-projekte-hinzufügen)
    + [Hinzufügen von Anforderungsquellen](#Zusatzanforderungen-Quellen)
  * [Startspezifikation](#Startspezifikation)
- [Nutzung](#nutzung)
  * [Login](#login)
  * [Übersicht](#übersicht)
  * [Modellierungsanforderungen](#Modellierungsanforderungen)
    + Modellierung mit Ursachen-Wirkungs-Diagrammen](#Modellierung mit Ursachen-Wirkungs-Diagrammen-ceg)
    + Modellierung mit Prozessdiagrammen](#Modellierung mit Prozessdiagrammen)
    + Grundlegende Funktionalitäten, die auf beiden Editoren verfügbar sind](#grundlegende Funktionalitäten - verfügbar auf Booth-Editoren)
  * Generierung einer Testfall-Spezifikation](#Erzeugung einer Testfall-Spezifikation)
  * Erstellen eines Testverfahrens](#Erstellen eines Testverfahrens)
   

# Installation, Konfiguration und Inbetriebnahme

## Installation

- Stellen Sie sicher, dass Java 1.8 installiert ist. Wenn nicht, erhalten Sie ein Java 1.8 Release, z.B. von [hier](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). Um herauszufinden, welche Java-Version Sie gerade verwenden, geben Sie in eine Konsole java -version ein.

- Besorgen Sie sich die neueste Version von Specmate auf der Download-Seite.

- Entpacken Sie Specmate in einen Ordner Ihrer Wahl.

- Unter Windows führen Sie start.bat aus, unter Linux/Mac führen Sie start.sh aus.

## Konfiguration
Specmate wird über die Datei specmate-config.properties konfiguriert. Eine Beispiel-Konfigurationsdatei erhalten Sie [hier](https://github.com/junkerm/specmate/blob/develop/bundles/specmate-config/config/specmate-config.properties).

### Neue Projekte hinzufügen
Specmate ist in Projekte gegliedert. Ein Projekt stellt einen Raum für Anforderungen, Modelle und Tests dar, auf die eine Gruppe von Benutzern zugreifen kann. 
Für jedes Projekt können Sie eine Anforderungsquelle und ein Exportziel für Prüfabläufe konfigurieren. 
Um ein neues Projekt hinzuzufügen, fügen Sie die Projekt-ID über die Eigenschaft project.projects in die Projektliste ein.

Beispiel:
project.projects = myproject1, myproject2

### Hinzufügen von Anforderungsquellen
Specmate unterstützt verschiedene Arten von Anforderungsquellen:
- Anforderungsdateien
- JIRA
- HP PPM

Derzeit definieren die Anforderungsquellen auch, welche Benutzer auf das Projekt zugreifen können. Wenn Sie beispielsweise eine JIRA-Anforderungsquelle konfigurieren, kann jeder Benutzer, der Zugriff auf die JIRA-Instanz hat, auf das Projekt zugreifen.

##### Anforderung aus Dateien
Um eine Anforderungsdateiquelle für ein Projekt zu konfigurieren, geben Sie den Speicherort eines Ordners auf Ihrem Dateisystem an. Specmate sucht in diesem Ordner nach *.txt-Dateien und importiert die in diesen Dateien enthaltenen Anforderungen.
Das Format der Anforderungsdateien ist wie folgt:

(Anforderungs-ID)
[Anforderungstext (darf keine Leerzeilen enthalten)]

(Anforderungs-ID)
[Anforderungstext (darf keine Leerzeilen enthalten)]
...

Der Speicherort einer Anforderungsdatei für ein Projekt ist wie folgt konfiguriert:

Projekt.[Projekt-ID].connector.pid = com.specmate.FileConnector
projekt.[projekt-ID].connector.fileConnector.folder = [Speicherort im Dateisystem]
project.[projekt-ID].connector.fileConnector.user = [Benutzername]
project.[projekt-ID].connector.fileConnector.password = [password]
projekt.[projekt-ID].connector.connector.connectorID =[projekt-ID]

##### JIRA Import
Um Anforderungen aus Jira zu importieren, können Sie die folgenden Eigenschaften in der Konfigurationsdatei angeben.

project.projects = [PROJ_ID]

project[PROJ_ID].connector.pid = com.specmate.connectors.jira.jira.JiraConnector
project.[PROJ_ID].connector.jira.url = [JIRA URL]

project.[PROJ_ID].connector.jira.project = [JIRA PROJECT]

project.[PROJ_ID].connector.jira.username = [TECHNISCHER BENUTZERNAME]

project.[PROJ_ID].connector.jira.password = [TECHICAL USER PASSWORD]

project[PROJ_ID].connector.connectorID = [PROJ_ID].

## Start von Specmate
Um specmate zu starten, öffnen Sie ein Terminal und geben Sie Folgendes ein
java -jar specmate.jar -configurationFile [Pfad-zu-deiner-config-file]

Jetzt können Sie einen Browser öffnen und zu http://localhost:8080 navigieren, um auf die Startseite von Specmate zuzugreifen.
 
# Verwendung

## Anmelden
Wählen Sie auf der Titelseite von Specmate ein Projekt aus und geben Sie einen gültigen Login für dieses Projekt ein. Beachten Sie, dass die Anmeldeinformationen im Allgemeinen spezifisch für ein bestimmtes Projekt sind und nicht für jedes Projekt funktionieren.

## Übersicht
Nach dem Anmelden bei Specmate sehen Sie folgende Ansichten

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Welcome.png "Startseite")

- Auf der linken Seite sehen Sie den Projekt-Explorer. Der Projekt-Explorer zeigt die importierten Anforderungen in einer Baumstruktur an. Sie können durch den Baum navigieren (d.h. die Ordner öffnen) und eine Anforderung auswählen.
- Im Projekt-Explorer können Sie zwischen der Projektansicht mit den importierten Anforderungen und der Bibliotheksansicht wechseln. In der Bibliothek können Sie Ordner und Modelle frei hinzufügen.
- Über dem Projekt-Explorer befindet sich ein Suchfeld. Nach der Eingabe eines Suchwortes zeigt der Projekt-Explorer Anforderungen und Modelle an, die dem Suchwort entsprechen. Beachten Sie, dass die Bibliothek derzeit nicht in die Suche miteinbezogen ist.
- Im oberen Teil des Bildschirms direkt neben dem Specmate-Logo finden Sie Schaltflächen zum Speichern des aktuell geöffneten Modell, zur Navigation und zum Zurücksetzen der letzten Aktion in einem Modell-Editor.


Wenn ein Ordner ausgewählt ist, wird Ihnen die folgende Ansicht angezeigt

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Folder%20Overview.png "Ordnerübersicht")

- Im ersten Abschnitt können Sie Details über den ausgewählten Ordner abrufen.
- Das Ändern der Struktur der Bibliothek (z.B. Hinzufügen/Entfernen von Ordnern) kann im Abschnitt *Unterordner* vorgenommen werden.
- Das Erstellen/Anzeigen von Ursache-Wirkungsmodellen oder Prozessmodellen kann im jeweiligen Abschnitt erfolgen. 

## Modellieren von Anforderungen
Für das Modellieren von Anforderungen haben Sie die Wahl zwischen Ursache-Wirkungs-Graphen und Prozessdiagrammen. Je nachdem, ob die Art der Anforderung regelbasiert ("Wenn dies und das, dann das Folgende.... mit Ausnahme von ... dann...") oder prozessbasiert ("Zuerst gibt der Benutzer A ein. Aufgrund der Eingabe gibt das System entweder B oder C ein. Danach fragt das System den Benutzer nach D, danach....") können Sie die entsprechende Modellierungstechnik auswählen. Bei der Modellierung regelbasierter Anforderungen werden Ursache-Wirkungs-Diagramme verwendet, während prozessbasierte Anforderungen mit Prozessdiagrammen modelliert werden können.  

### Modellierung mit Ursache-Wirkungs-Diagrammen (CEG)

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/CEG%20Editor.png "CEG Editor")


Nach dem Öffnen des Ursache-Wirkungs-Editors wird Ihnen ein Modellierungsbereich in der Mitte präsentiert, in dem Sie Ihr CEG erstellen können.
Um ein CEG zu modellieren, können Sie ein Werkzeug aus der Toolbox auswählen.
Nachdem Sie *Knoten* aus der Toolbox ausgewählt haben, können Sie im Modellierungsbereich klicken, um einen neuen Knoten anzulegen. 
Standardmäßig ist der Name des Knotens *variable* und die Bedingung ist auf *is present* gesetzt. Sie können die Attribute des ausgewählten Knotens auf der rechten Seite im Abschnitt *Eigenschaften* ändern. 

Eine bewährte Vorgehensweise ist, die Variablen immer als positive Aussagen zu deklarieren (z.B. *Türen zugesperrt: wahr* statt *Türen nicht zugesperrt: nicht wahr*).
 
Um zwei Knoten zu verbinden, wählen Sie das Werkzeug *Verbindung* aus und anschließend den Knoten, der die Ursache darstellen soll und dann den Knoten, der den Effekt darstellen soll. 
Wenn eine Verbindung erstellt und ausgewählt wird, haben Sie die Möglichkeit, die Verbindung zu negieren.

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/CEG-Graph.png "CEG Graph")

Wenn ein Knoten mehrere eingehende Verbindungen hat, können Sie den Typ des Knotens ändern. 
Abhängig vom Typ des Knotens können eingehende Verbindungen als ODER-Verknüpfungen oder UND-Verknüpfungen definiert werden. Wenn der Typ eines Knotens auf UND gesetzt ist, müssen alle Vorgängerknoten mit einer Verbindung zu dem jeweiligen Knoten ausgefüllt werden, damit der Knoten erfüllt wird. 
Es muss nur ein einziger direkter Vorgänger erfüllt werden, wenn der Typ eines Knotens auf OR gesetzt ist.


### Modellierung mit Prozessdiagrammen
Um Prozessdiagramme zu modellieren öffnen Sie den zugehörigen Editor. 
Mit dem *Schritt*-Werkzeug können Sie dem Modell eine Aktion hinzufügen. Jedes Modell muss einen Startknoten und mindestens einen Endknoten haben.
Je nachdem, welchen Knotentyp Sie anlegen möchten, wählen Sie aus der Toolbox entweder das *Start* oder das *Ende* Werkzeug.

Um die Komplexität des Modells zu erhöhen, können Sie einen Entscheidungsknoten hinzufügen, indem Sie das Werkzeug *Entscheidung* auswählen.  
Um zwei Elemente zu verbinden, müssen Sie das Werkzeug *Verbindung* auswählen und die Knoten auswählen, die Sie verbinden möchten. 
Für jede Verbindung können Sie eine Bedingung setzen, die die Variable erfüllen muss. Bei der Verwendung des Entscheidungsknotens können Sie den Zustand der ausgehenden Verbindungen angeben, die erfüllt werden müssen, um der spezifischen Verbindung im Modell zu folgen. 
Wenn ein Knoten ausgewählt ist, zeigt Specmate die Eigenschaften des Knotens auf der rechten Seite an. Außerdem können Sie das erwartete Ergebnis dieses Schrittes im Eigenschaftenbereich angeben.

Die folgende Abbildung zeigt einen Prozess eines Geldautomaten, der mit dem Prozessdiagramm-Editor modelliert wurde.

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Process%20diagram.png "Prozessdiagramm")


### Grundlegende Funktionen, die auf beiden Editoren verfügbar sind
Wenn Sie alle Elemente im Editor löschen möchten, können Sie auf die Schaltfläche *Alle Inhalte entfernen* in der Symbolleiste klicken. 
Wenn Sie das Werkzeug *Löschen* wählen, können Sie bestimmte Elemente aus dem Modell entfernen. Wenn das Werkzeug ausgewählt ist, können Sie auf das Element klicken, das Sie entfernen möchten. 
Sie haben die Möglichkeit, die Elemente im Editor neu zu ordnen, wenn Sie das Werkzeug *Auswählen* aus der Toolbox auswählen. 
  
Auf der rechten Seite des Editors können Sie den Namen des Modells ändern und eine Beschreibung hinzufügen. Sie können auch eine Beschreibung für jeden Knoten im Modell hinzufügen. Unter der Spalte *Links & Actions* können Sie zur Anforderung zurückkehren und die Beschreibung der Anforderung ansehen, für die Sie gerade ein Modell anlegen. 
Links zu bereits generierten Testspezifikationen werden ebenfalls angezeigt. In der letzten Spalte *Änderungsverlauf* können Sie sehen, welcher Benutzer Änderungen am Diagramm vorgenommen hat. Wenn es Fehler im erstellten Modell gibt, zeigt Specmate diese über der Spalte *Änderungsverlauf* an. 

## Generierung einer Testfall-Spezifikation

Sie haben die Möglichkeit, eine Testfall-Spezifikation manuell zu erstellen oder automatisch aus einem Modell zu generieren. Anhand des Symbols der Spezifikation im Projekt-Explorer können Sie sehen, ob sie automatisch oder manuell generiert wird. 

Manuell erstellt: ![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Manually.png "Manuell erstellte Testfall-Spezifikation") Automatische generiert: ![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Automatic.png "Automatisch generierte Testfall-Spezifikation")

Der Name der Testfall-Spezifikation basiert auf dem Datum und der Uhrzeit, zu der die Spezifikation angelegt wurde. Sie haben die Möglichkeit, den Namen der Spezifikation zu ändern und eine Beschreibung hinzuzufügen. 
Die Spezifikation besteht aus mehreren Testfällen, wobei jeder Testfall eine bestimmte Konfiguration hat.
Ein Testfall weist jeder Variablen einen Wert zu. In bestimmten Testfällen lässt Specmate den Wert einer Variablen frei. Ist dies der Fall, ist die Variable nicht auf einen bestimmten Wert beschränkt.
Die Erstellung der Spezifikation erfolgt nach den Regeln von Liggesmeyer. Die Anwendung dieser Regeln führt zu einem optimalen Verhältnis zwischen Testabdeckung und Anzahl der Testfälle. 

Die Knoten, die sich in der Spalte *Eingabe* befinden, sind Variablen, die die Ursachen aus dem Modell darstellen. Unterhalb der Spalte *Ausgabe* finden Sie die Variablen, die die Effekte darstellen. 

Sie können einen Testfall auch löschen, wenn Sie auf das Papierkorbsymbol des jeweiligen Testfalls klicken.
Wenn Sie Testfälle manuell hinzufügen möchten, können Sie die Schaltfläche *Testfall erstellen* im unteren Bereich drücken. 
Die Reihenfolge der Testfälle kann per Drag & Drop geändert werden. 

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Testscases.png "Testfall-Spezifikation")

## Erstellen einer Testprozedur
Aus jedem Testfall können Sie ein Testprozedur anlegen. Hier können Sie alle notwendigen Schritte für den jeweiligen Testfall definieren. 
Sie können einen weiteren Schritt hinzufügen, indem Sie die Schaltfläche *Testschritt anlegen* drücken. In jedem Schritt des Testverfahrens können Sie auf Parameter aus dem erstellten Modell verweisen. Die Parameter aus dem Modell können in der *Parameter Zuordnung* auf einen bestimmten Wert eingestellt werden. 
Wenn die Erstellung eines Prüfverfahrens abgeschlossen ist, können Sie es mit der Schaltfläche auf der rechten Seite exportieren. 
Sie können auch ein bereits erstelltes Prüfverfahren öffnen und bearbeiten, indem Sie es im Projekt-Explorer oder in der Anforderungsübersicht anklicken. Darüber hinaus haben Sie die Möglichkeit, ein Prüfverfahren als Regressionstest zu kennzeichnen. 
Die Reihenfolge der Prüfschritte kann per Drag & Drop geändert werden und Sie können auch einen Prüfschritt löschen, indem Sie auf das Papierkorbsymbol des jeweiligen Schrittes klicken.

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Test%20procedure.png "Testverfahren")

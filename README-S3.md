# **SWE4-Übungen - SS2024 - Übungszettel 7**

## **Ausbaustufe 3: EURO-Bet-DB**

In dieser Ausbaustufe müssen Sie dafür sorgen, dass die Serverkomponente die
Daten nicht mehr „vergisst“. Verwalten Sie dazu die Daten der Anwendungen
(Benutzer, Mannschaften, Fußballspiele, Spielergebnisse etc.) mithilfe von JDBC
in einer zentralen relationalen Datenbank.

Achten Sie darauf, dass die Datenbankzugriffsschicht möglichst weitgehend von
den anderen Komponenten der Anwendung getrennt ist. Stellen Sie dazu die gesamte
Funktionalität der Datenbankzugriffsschicht über Interfaces zur Verfügung und
sorgen Sie dafür, dass alle Klassen der Serverkomponente ausschließlich von den
Datenzugriffs-Interfaces abhängig sind.

Testen Sie die Datenzugriffsschicht möglichst entkoppelt vom Rest der Anwendung.

Initialisieren Sie beim Starten der Anwendung die Datenbank mit Testdaten und
führen Sie einen ausführlichen Integrationstest durch. Demonstrieren Sie die
Funktionstüchtigkeit Ihrer Anwendung für den folgenden durchgängigen
Anwendungsfall:

* _Verwaltungstool:_ Anlegen von zwei Benutzern im Verwaltungstool
* _Verwaltungstool:_ Anpassen der Daten eines bestehenden Benutzers
* _Verwaltungstool:_ Eingabe von drei Spielpaarung, ein Spiel sollte zum
  aktuellen Zeitpunkt im Gange sein.
* _Wettanwendung:_ Anmeldung mit den neuen Benutzern in zwei Instanzen der Wettanwendung
* _Wettanwendung:_ Auflistung der Spiele, Überprüfung, ob die richtige
  Information angezeigt wird (Spielstand, abgegebener Tipp, erreichte Punkte etc.)
* _Wettanwendung:_ Abgabe von Tipps mit den neuen Benutzern, überprüfen ob
  Falscheingaben richtig behandelt werden
* _Verwaltungstool:_ Aktualisierung von Spielergebnisses
* _Wettanwendung:_ Überprüfen ob ins Auflistung der Spiele Daten aktualisiert wurden
* _Wettanwendung:_ Anzeige der Highscore-Tabelle


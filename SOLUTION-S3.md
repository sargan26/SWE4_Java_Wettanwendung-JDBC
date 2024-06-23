# **SWE4-Übungen - SS2024 - Übungszettel 7 - Ausarbeitung**

## **EURO-Bet - Ausbaustufe 3**

### **Lösungsidee**

In der 3. Ausbaustufe wurde die Anwendung umgebaut, sodass die Daten zentral in einer Datenbank peristent gespeichert werden.

Dafür habe ich mir den latest mySQL Docker Container heruntergeladen und gestartet. Dann die entsprechenden CREATE TABLE Statements mit dem Tool 'DBeaver' abgesetzt. 

Eine große Herausforderung oder Änderung, die ich machen musste war das Umstellen von Objekten auf die IDs als Fremdschlüssel.
zB. hat ein Spiel eine Mannschaft 1 und 2 als Referenz gespeichert und nicht nur die ID, das hat natürlich nicht mit der Datenbank zusammengepasst.

Die Verbindung zur Datenbank im Programm wurde mit JDBC gemacht. Eine saubere Trennung der Datenbankzugriffschicht wird über Interfaces (Dao's) erreicht.

### **Testfälle**

Fürs Testen und den Integrationtest habe ich ein Video erstellt:

https://youtu.be/ZbEDyNCfvnA


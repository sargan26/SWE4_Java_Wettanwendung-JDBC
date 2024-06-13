# **SWE4-Übungen - SS2024 - Übungszettel 6 - Ausarbeitung**

## **EURO-Bet - Ausbaustufe 2**

### **Lösungsidee**

In der Ausbaustufe 2 wurde die Anwendung in eine Client-Server Architektur geändert.

Dafür habe ich die Anwendung in mehrere Teile aufgeteilt:

- **Client**: Die JavaFX Anwendung, die die GUI darstellt und die Benutzerinteraktionen entgegennimmt.
- **Server**: Ein Java Programm, das die Datenhaltung und die Geschäftslogik übernimmt.

Für den Server habe ich ein Interface erstellt, welche Methoden ich anbiete. Dieses Interface wird dann von der Server-Klasse implementiert. Die Server-Klasse ist dann die Klasse, die die eigentliche Logik enthält. 
Außerdem enthält sie eine Instanz des DatenManagers, der die Datenhaltung übernimmt.

Der Client verbindet sich über RMI mit dem Server und kann dann die Methoden des Interfaces aufrufen. Die Daten werden dann über das Interface ausgetauscht.
Im Client habe ich die Datenhaltung entfernt und die Datenhaltung komplett auf den Server verlagert. 
Der Client ist außerdem für die grafische Darstellung der Daten zuständig und für die Benutzerinteraktionen.

Bei RMI muss man beachten, dass man den EuroBetServer als Remote-Objekt registriert und bindet.

Der Client muss dann eine Verbindung zur Registry aufbauen und das Remote-Objekt des Servers holen aus eine lookup Table.

Der DatenManager aht weiterhin die Dao Klassen, die es hoffentlich in der 3. Ausbaustufe einfacher machen die Datenbank einzubinden.

Um der Anforderung gerecht zu werden, dass die GUI bedienbar bleibt wenn Hintergrundprozesse ausgeführt werden, habe ich JavaFX Tasks implemenentiert.
Ein JavaFX Task ist ein Hintergrundprozess, der in einem eigenen Thread ausgeführt wird. Mit task.setOnSucceeded() kann man dann den Task beenden und das Ergebnis in der GUI anzeigen.

### **Testfälle**

![img_15.png](img_15.png)

Server gestartet

![img_16.png](img_16.png)

Kleiner Testclient, der einfach nur die Verbindung zum Server aufbaut und eine Methode aufruft, die dann alle Beispiel Benutzer anzeigt.

![img_17.png](img_17.png)

Client 1 gestartet.

![img_18.png](img_18.png)

![img_19.png](img_19.png)

Nach dem Einloggen ruft der VerwaltungsController die Testmethode printHello vom Server auf.

![img_20.png](img_20.png)

Spiel hinzugefügt

![img_21.png](img_21.png)

Spiel gelöscht

![img_22.png](img_22.png)

Mannschaft hinzufügen

![img_23.png](img_23.png)

Mannschaft ändern

...

![img_24.png](img_24.png)

Jetzt in die Wettanwendung gewechselt, Spiel Tipp hinzufügen.

![img_25.png](img_25.png)

Unit Test: DatenManager

![img_26.png](img_26.png)

Unit Test: MannschaftenDao

![img_27.png](img_27.png)

Unit Test: Mannschaft

![img_28.png](img_28.png)

Einstellung damit der Client mehrmals ausgeführt werden kann.

![img_29.png](img_29.png)

Mehrere Clients laufen. Habe bei einem Client eine Mannschaft hinzugefügt, die wird beim anderen Client auch angezeigt.

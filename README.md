# **Crazy Time Multi-Player (Versione Indipendente)**

Benvenuto nel simulatore di **Crazy Time**! Questa applicazione permette a più utenti di giocare contemporaneamente allo stesso server, ma con la libertà per ogni giocatore di gestire il proprio saldo e far girare la ruota quando preferisce.

## **Requisiti**

* **Java Development Kit**
* **Terminale/Console**: Almeno due finestre aperte (una per il Server, una o più per i Client).

## **Come avviare l'applicazione**

Segui questi passaggi nell'ordine esatto per evitare errori di connessione.

### **1. Preparazione dei file**

Assicurati di avere i seguenti **5 file** nella stessa cartella:

1. CrazyServer.java
2. CrazyWorker.java
3. CrazyClient.java
4. ServerListener.java
5. GestoreGiro.java

### **3. Avvio del Server**

Esegui prima il server in una finestra dedicata:

Il server rimarrà in attesa sulla porta **7777**.

### **4. Avvio del Client**

Apri una nuova finestra del terminale e avvia il client:

*Puoi aprire più terminali ed eseguire questo comando più volte per simulare più giocatori contemporaneamente.*

## **Guida al Gioco**

1. **Connessione**: Una volta entrato, riceverai un messaggio con il tuo saldo iniziale di **500 monete**.
2. **Piazzare una Scommessa**: Inserisci il bersaglio e la cifra separati da una virgola.

   * *Esempio:* 10,50 (punta 50 monete sul numero 10).
   * *Esempio:* CRAZY\_TIME,10 (punta 10 monete sul bonus Crazy Time).

3. **Bersagli Validi**: Puoi puntare solo su: 1, 2, 5, 10, COIN\_FLIP, PACHINKO, CASH\_HUNT, CRAZY\_TIME.
4. **Far girare la ruota**: Dopo aver piazzato una o più scommesse, scrivi **GIRA** per vedere l'esito della ruota.
5. **Vincita**: Se la ruota si ferma su uno dei tuoi bersagli, vincerai la scommessa moltiplicata per il valore del segmento.

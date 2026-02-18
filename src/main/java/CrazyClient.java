import java.io.*;
import java.net.*;

public class CrazyClient {
    public static void main(String[] args) {
        try {
            // Connessione al server locale sulla porta 7777
            Socket socket = new Socket("127.0.0.1", 7777);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("=== BENVENUTO AL CRAZY TIME INDIPENDENTE ===");
            
            // Avviamo il thread di ascolto separato per ricevere i messaggi in tempo reale
            new Thread(new ServerListener(in)).start();

            // Ciclo principale di input dell'utente
            String input;
            while ((input = keyboard.readLine()) != null) {
                out.println(input);
                if (input.equalsIgnoreCase("EXIT")) break;
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Errore: Impossibile connettersi al Server.");
        }
    }
}

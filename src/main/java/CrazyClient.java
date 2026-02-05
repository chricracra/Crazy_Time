import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CrazyClient {
    public static void main(String[] args) {
        try {
            // Connessione al server locale sulla porta 7777
            Socket socket = new Socket("127.0.0.1", 7777);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== BENVENUTO AL CRAZY TIME INDIPENDENTE ===");
            
            // Avviamo il thread di ascolto separato per ricevere i messaggi in tempo reale
            new Thread(new ServerListener(in)).start();

            // Ciclo principale di input dell'utente
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                out.println(input);
                if (input.equalsIgnoreCase("EXIT")) break;
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Errore: Impossibile connettersi al Server.");
        }
    }
}
import java.io.*;
import java.net.*;

public class CrazyServer {
    public static void main(String[] args) {
        int port = 7777;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(">>> SERVER CRAZY TIME ATTIVO SULLA PORTA " + port + " <<<");

            while (true) {
                // Accetta un nuovo cliente e lancia un thread dedicato
                Socket client = serverSocket.accept();
                System.out.println("Nuovo giocatore connesso: " + client.getInetAddress());
                new Thread(new CrazyWorker(client)).start();
            }
        } catch (IOException e) {
            System.out.println("Errore critico del Server.");
        }
    }
}
import java.io.*;

public class ServerListener implements Runnable {
    private BufferedReader in;

    public ServerListener(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String msg;
            // Legge continuamente i messaggi inviati dal CrazyWorker
            while ((msg = in.readLine()) != null) {
                if (msg.equals("GAME_OVER")) {
                    System.out.println("\n[SISTEMA] >>> SALDO ESAURITO! IL GIOCO TERMINA QUI. <<<");
                    System.exit(0);
                }
                System.out.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Connessione chiusa dal server.");
        }
    }
}
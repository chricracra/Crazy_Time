import java.io.*;
import java.net.Socket;
import java.util.Random;

public class CrazyWorker implements Runnable {
    private Socket socket;
    private int saldo = 500;
    private Random rand = new Random();
    
    // Opzioni ufficiali della ruota
    private final String[] OPZIONI = {"1", "2", "5", "10", "COIN_FLIP", "PACHINKO", "CASH_HUNT", "CRAZY_TIME"};

    public CrazyWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Messaggio iniziale di onboarding
            out.println("\n--- SEI CONNESSO AL TAVOLO ---");
            out.println("[INFO] Saldo iniziale: " + saldo);
            out.println("[INFO] Bersagli validi: [1, 2, 5, 10, COIN_FLIP, PACHINKO, CASH_HUNT, CRAZY_TIME]");

            while (saldo > 0) {
                String[] bersagliTurno = new String[10];
                int[] sommeTurno = new int[10];
                int contatore = 0;
                boolean fasePuntate = true;

                while (fasePuntate && saldo > 0 && contatore < 10) {
                    out.println("\n[SALDO: " + saldo + "] Inserisci BERSAGLIO,CIFRA o scrivi 'GIRA':");
                    String input = in.readLine();
                    
                    if (input == null || input.equalsIgnoreCase("GIRA")) {
                        fasePuntate = false;
                        continue;
                    }

                    // Validazione input e registrazione scommessa
                    processaScommessa(input, out, bersagliTurno, sommeTurno, contatore);
                    if (bersagliTurno[contatore] != null) contatore++;
                }

                // Se sono state effettuate puntate, facciamo girare la ruota
                if (contatore > 0) {
                    eseguiEstrazione(out, bersagliTurno, sommeTurno, contatore);
                }

                if (saldo <= 0) {
                    out.println("GAME_OVER");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Giocatore disconnesso.");
        }
    }

    // Metodo sincronizzato per gestire la logica della scommessa singola
    private synchronized void processaScommessa(String input, PrintWriter out, String[] bT, int[] sT, int i) {
        try {
            String[] parti = input.split(",");
            if (parti.length < 2) {
                out.println("[ERRORE] Formato errato. Usa: BERSAGLIO,CIFRA");
                return;
            }

            String bersaglio = parti[0].trim().toUpperCase();
            int cifra = Integer.parseInt(parti[1].trim());

            // Verifica validità bersaglio
            boolean esiste = false;
            for (String s : OPZIONI) if (s.equals(bersaglio)) esiste = true;

            if (!esiste) {
                out.println("[ERRORE] Bersaglio '" + bersaglio + "' non valido!");
            } else if (cifra <= 0 || cifra > saldo) {
                out.println("[ERRORE] Cifra non valida o saldo insufficiente.");
            } else {
                bT[i] = bersaglio;
                sT[i] = cifra;
                saldo -= cifra;
                out.println("[OK] Puntati " + cifra + " su " + bersaglio);
            }
        } catch (Exception e) {
            out.println("[ERRORE] Inserisci valori numerici validi.");
        }
    }

    private synchronized void eseguiEstrazione(PrintWriter out, String[] bT, int[] sT, int n) {
        out.println("--- LA RUOTA GIRA... ---");
        String esito = generaCasuale();
        out.println(">>>> RISULTATO: " + esito + " <<<<");

        int vinto = 0;
        for (int i = 0; i < n; i++) {
            if (bT[i].equals(esito)) {
                int molt = getMolt(esito);
                vinto += sT[i] + (sT[i] * molt);
            }
        }

        if (vinto > 0) {
            saldo += vinto;
            out.println("[VITTORIA] Hai vinto " + vinto + " monete!");
        } else {
            out.println("[PERSO] Nessuna vincita in questo giro.");
        }
    }

    private String generaCasuale() {
        String[] ruota = new String[54];
        for (int i = 0; i < 54; i++) {
            if (i < 21) ruota[i] = "1";
            else if (i < 34) ruota[i] = "2";
            else if (i < 41) ruota[i] = "5";
            else if (i < 45) ruota[i] = "10";
            else if (i < 49) ruota[i] = "COIN_FLIP";
            else if (i < 51) ruota[i] = "PACHINKO";
            else if (i < 53) ruota[i] = "CASH_HUNT";
            else ruota[i] = "CRAZY_TIME";
        }
        return ruota[rand.nextInt(54)];
    }

    private int getMolt(String s) {
        if (s.equals("COIN_FLIP")) return 20;
        if (s.equals("PACHINKO") || s.equals("CASH_HUNT")) return 50;
        if (s.equals("CRAZY_TIME")) return 70;
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }
}

package TCPTime;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TCPServer {

    public static void main(String[] args) {
        int port = 2000;                //porta sulla quale il server è in ascolto
        ServerSocket sSocket = null;    //oggetto ServerSocket necessario per accettare richieste dal client
        Socket connection = null;       //oggetto da usare per realizzare la connessione TCP
        Scanner streamIn = null;        //Stream di caratteri in input
        PrintWriter streamOut = null;   //Stream di caratteri in output

        System.out.println("Apertura porta in corso!");

        try {
            //si crea il socket
            sSocket = new ServerSocket(port);

            while (true) {
                try {
                    //si mette in ascolto il server
                    System.out.println("Server in ascolto sulla porta " + port + ".\n");
                    connection = sSocket.accept();

                    //si è stabilita la connessione
                    System.out.println("Connessione stabilita e richiesta ricevuta!");

                    //creo il flusso in output
                    streamOut = new PrintWriter(connection.getOutputStream());

                    //creo il flusso di input
                    streamIn = new Scanner(connection.getInputStream());

                    Date dataOra;                   //variabile per la data e l'ora
                    SimpleDateFormat formattedDate; //variabile per formattare la data

                    String richiesta = streamIn.nextLine(); //leggo la richiesta del client

                    //analizzo la richiesta
                    if (richiesta.contains("ora")) {
                        dataOra = new Date();
                        formattedDate = new SimpleDateFormat("hh:mm:ss");

                        streamOut.println("Ora " + formattedDate.format(dataOra)); //restituisco al client solo l'ora
                    } else if (richiesta.contains("data")) {
                        dataOra = new Date();
                        formattedDate = new SimpleDateFormat("dd/MM/YYYY");

                        streamOut.println("Data " + formattedDate.format(dataOra)); //restituisco al client solo la data
                    } else {
                        dataOra = null;
                        formattedDate = null;

                        streamOut.println("Comando non riconosciuto!");
                    }
                    System.out.println("Risposta inviata!");
                    
                } catch (IOException e) {
                    System.err.println("Impossibile scrivere nel buffer di input/output!");
                    
                } finally {
                    try {
                        //chiusura del flusso di output e input
                        if (streamOut != null)  streamOut.close();
                        if (streamIn != null)   streamIn.close();

                        //chiusira connessione
                        if (connection != null) connection.close();
                        
                        System.out.println("Connessione chiusa");

                    } catch (IOException e) {
                        System.err.println("Problemi di chiusura dei buffer!");
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Impossibile scrivere nel buffer di input/output!");
        } finally {
            try {
                if(sSocket != null) sSocket.close();
                
            } catch(IOException e){
                System.err.println("Problemi di chiusura dei buffer!");
                
            }
        }

    }

}

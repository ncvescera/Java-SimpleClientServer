package TCPTime;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) {

        int port = 2000;                    //porta a cui connettersi
        InetAddress serverAddress = null;   //Indirizzo del server TCP
        Socket connection = null;           //oggetto da usare per realizzare la connessione TCP
        PrintWriter streamOut = null;       //Stream di carattere in output
        Scanner streamIn = null;            //oggetto Scanner per leggere il flusso di input

        try {
            serverAddress = InetAddress.getLocalHost();
            System.out.println("Indirizzo del server trovato!");

            //apertura connessione
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");

            //creazione dello stream in output
            streamOut = new PrintWriter(connection.getOutputStream(), true);
            streamOut.println("Server dammi l'ora"); //scrivo cosa voglio al server

            //creazione stream input
            streamIn = new Scanner(connection.getInputStream());
            String risposta = streamIn.nextLine(); //leggo risposta dal server
            System.out.println(risposta + " ricevuta dal server.");
            
        } catch(ConnectException e) {
            System.err.println("Impossibile connettersi al server!");
            
        } catch (UnknownHostException e) {
            System.err.println("Impossibile riconoscere l'host specificato !");
            
        } catch (IOException e) {
            System.err.println("Errore ! Impossibile scrivere nel buffer di input/output");
            
        } finally {
            try {
                //chiusura del flusso di input e output
                if (streamIn != null)  streamIn.close();
                if (streamOut != null) streamOut.close();

                //chiusura della connnessione
                if (connection != null) connection.close();
                System.out.println("Connessione chiusa!");
                
            } catch (IOException e) {
                System.err.println("Problemi di chiusura dei buffer!");
            }
        }

    }

}

package TCPTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {

	public static void main(String[] args) {
		/* porta del server maggiore di 1024 
		 * oppure la porta 13 standard del protocollo Daytime
		 */
		int port=2000;
		//Indirizzo del server TCP
		InetAddress serverAddress;
		//oggetto da usare per realizzare la connessione TCP
		Socket connection;
		//Stream di byte di input
		InputStream inSocket;
                //Stream di carattere in output
                PrintWriter streamOut;
		//oggetto Scanner per leggere il flusso di input
		Scanner streamIn;
		
		try {
			/*
			 * si usa getLocalHost() se il server è sulla stessa macchina locale
			 * altrimenti si deve conoscere l'IP del server
			 */
			serverAddress = InetAddress.getLocalHost();
			System.out.println("Indirizzo del server trovato!");
			
			//si apre la connessione al server sulla porta specificata
			connection = new Socket(serverAddress, port);
			System.out.println("Connessione aperta");
			
                        //creazione dello stream in output
                        streamOut = new PrintWriter(connection.getOutputStream());
                        
                        streamOut.println("Server dammi la data"); //scrivo cosa voglio al server
                        streamOut.close();
                        
                        
			//si riceve dal server la data e l'ora
			inSocket = connection.getInputStream();
			streamIn = new Scanner(inSocket);
                        
                        
                        
			String risposta = streamIn.nextLine();
			System.out.println(risposta + " ricevuta dal server.");
			
			//chiusura del flusso di input
			streamIn.close();
			
			//chiusura della connnessione
			connection.close();
			System.out.println("Connessione chiusa!");
			
					
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

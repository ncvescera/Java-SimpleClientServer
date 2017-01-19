package TCPTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TCPServer {

	public static void main(String[] args) {
		/* porta del server maggiore di 1024 
		 * oppure la porta 13 standard del protocollo Daytime
		 */
		int port=2000;
		//oggetto ServerSocket necessario per accettare richieste dal client
		ServerSocket sSocket;
		//oggetto da usare per realizzare la connessione TCP
		Socket connection;
		//Stream di byte di output 
		OutputStream outSocket;
                //Stream di byte in input
                Scanner streamIn;
		//Writer che mostra sullo standard output della console i byte del flusso
		PrintWriter streamOut;
		System.out.println("Apertura porta in corso!");
		
		/* si mette il server in ascolto sulla porta voluta e si stabilisce
		 * la connessione affidabile
		 */
		try {
			//si crea il socket
			sSocket = new ServerSocket(port);
			while(true){
				//si mette in ascolto il server
				System.out.println("Server in ascolto sulla porta " + port + ".\n");
				connection = sSocket.accept();
				//si è stabilita la connessione
				System.out.println("Connessione stabilita e richiesta ricevuta!");
				
                                //creo il flusso in input
                                streamIn = new Scanner(connection.getInputStream());
                                
				//si istanzia il flusso in output
				outSocket = connection.getOutputStream();
				streamOut = new PrintWriter(outSocket);
				
                                Date dataOra; //variabile per la data e l'ora
                                SimpleDateFormat formattedDate; //variabile per formattare la data
                                
                                String richiesta = streamIn.nextLine();
                                
                                
                                if(richiesta.contains("ora")){
                                    dataOra = new Date();
                                    formattedDate = new SimpleDateFormat("hh:mm:ss");
                                    System.out.print("acacad");
                                    
                                    streamOut.println("Ora "+formattedDate.format(dataOra)); //restituisco al client solo l'ora
                                }
                                else if(richiesta.contains("data")){
                                    dataOra = new Date();
                                    formattedDate = new SimpleDateFormat("dd/MM/YYYY");
                                    
                                    streamOut.println("Data "+formattedDate.format(dataOra));
                                }
                                else{
                                    dataOra = null;
                                    formattedDate = null;
                                    
                                    streamOut.println("Comando non riconosciuto!");
                                }
				
				//chiusura del flusso di output
                                streamIn.close();
				streamOut.close();
				System.out.println("Risposta inviata!");
                                connection.close();
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println(e);
		}
				
	}

}

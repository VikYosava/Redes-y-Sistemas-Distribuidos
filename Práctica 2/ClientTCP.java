package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTCP {
	public static void main(String[] args) throws IOException {
		String serverName = "127.0.0.1";
		int portNumber = 1111;
		Socket socket = null;
		
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			InetAddress serverAddr = InetAddress.getByName(serverName);
	        socket = new Socket(serverAddr,portNumber);
	        System.out.println("Conexión local "+serverAddr);
	        System.out.println("-----------------------------------");

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

			
		} catch (UnknownHostException e) {
			System.err.println("Unknown: " + serverName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to: " + serverName);
			System.exit(1);
		}
		System.out.println("STATUS: Conectado al servidor ");
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		
		System.out.println("Introduzca un texto a enviar (END para acabar)");
		System.out.println("-----------------------------------");
		
		userInput = stdIn.readLine();
		out.println(userInput);
		while (userInput.compareTo("END") != 0) {
			out.println(userInput);
			System.out.println("STATUS: Enviando " + userInput);
			System.out.println("STATUS: Esperando eco");
			String echo = null;
			echo = in.readLine();
			System.out.println("echo: " + echo);
			System.out.println("Introduzca un texto a enviar (END para acabar)");
			System.out.println("-----------------------------------");
			userInput = stdIn.readLine();
		}

		System.out.println("STATUS: El cliente quiere terminar el servicio");
		out.println(userInput);
		System.out.println("STATUS: Sending " + userInput);
		System.out.println("STATUS: Waiting for the reply");
		String ok = in.readLine();
		System.out.println("STATUS: Cerrando conexion " + ok);
		in.close();
		out.close();
		stdIn.close();
		socket.close();
		System.out.println("STATUS: Conexion cerrada");
		System.out.println("-----------------------------------");
	}
}
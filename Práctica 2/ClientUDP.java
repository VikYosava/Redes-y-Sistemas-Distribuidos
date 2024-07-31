package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;


public class ClientUDP {
	public static void main(String[] args) throws IOException {
		String serverName = "127.0.0.1";
		int serverPort = 1111;
		InetAddress serverAddress = null;
		String input = "";
		DatagramSocket socket = new DatagramSocket();
		byte[] bytes = new byte[1024];
		DatagramPacket pack = null;
		String mensajeRecibido = "";
		Scanner sc = new Scanner(System.in);
		try {
			serverAddress = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			System.err.println("Unknown: " + serverName);
			System.exit(1);
		}
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Introduzca un texto a enviar (END para acabar)");
        System.out.println("-----------------------------------");

		input = stdIn.readLine();
		while (input.compareTo("END") != 0) {
			try {
				pack = new DatagramPacket(input.getBytes(), input.getBytes().length, serverAddress, serverPort);
				socket.send(pack);
			} catch (IOException iOException) {
			}
			System.out.println("STATUS: Waiting for the reply");

			pack = new DatagramPacket(bytes, bytes.length);
			socket.receive(pack);

			mensajeRecibido = new String(Arrays.copyOfRange(pack.getData(), pack.getOffset(),pack.getOffset() + pack.getLength()));
			System.out.println("echo: " + mensajeRecibido);
			System.out.println("Introduzca un texto a enviar (END para acabar)");

			input = stdIn.readLine();
		}
		System.out.println("STATUS: Closing client");

		socket.close();
		sc.close();
		System.out.println("STATUS: CLOSED");
        System.out.println("-----------------------------------");

	}
}
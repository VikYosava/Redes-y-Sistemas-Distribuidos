package server;


/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class ServerUDP {

	public static void main(String[] args) throws IOException {
		DatagramSocket serverSock = null;
		DatagramPacket out = null;
		DatagramPacket in = null;
		String resultado = "";
		byte[] bytes;
		String line = "";
		int port = 1111;

		try {
			serverSock = new DatagramSocket(port);
			System.out.println("Servidor encendido, esperando conexion");
	        System.out.println("-----------------------------------");

		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
			System.exit(-1);
		}

		while (true)
		{
			System.out.println("Waiting for a new UDP client");
	        System.out.println("-----------------------------------");

			bytes = new byte[20];
			in = new DatagramPacket(bytes, bytes.length); 
			serverSock.receive(in);
			line = new String(Arrays.copyOfRange(in.getData(), in.getOffset(),
					in.getOffset() + in.getLength()));
			System.out.println("Direccion socket del cliente:" + in.getSocketAddress());
			if (line.compareTo("END") != 0) {
				resultado = modificarCadena(line);
				System.out.println("Sending to client " + resultado);
		        System.out.println("-----------------------------------");

			}
			out = new DatagramPacket(bytes, bytes.length);

			int puertoCliente = in.getPort();
			InetAddress direccion = in.getAddress();
			bytes = resultado.getBytes();
			out = new DatagramPacket(bytes, bytes.length, direccion, puertoCliente);

			serverSock.send(out);
			System.out.println("STATUS: Echo sent ");
			System.out.println("STATUS: Waiting for new echo");
	        System.out.println("-----------------------------------");

			if (line.equalsIgnoreCase("END")) {
				System.out.println("Conexion con cliente finalizada");
		        System.out.println("-----------------------------------");

			}

		}
	}

	public static String modificarCadena(String cadena) {
		String cadenaMayuscula = cadena.toUpperCase();
		String cadenaRevertida = (new StringBuffer(cadenaMayuscula)).reverse().toString();
		return cadenaRevertida;
	}

}

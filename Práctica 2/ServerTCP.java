package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class ServerTCP {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSock = null;
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		String line;
		int port = 1111;
		try {
			serverSock = new ServerSocket(port);
			System.out.println("Servidor iniciado "+serverSock);
			System.out.println("-----------------------------------");
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
			System.exit(-1);
		}

		while (true)
		{
			try {
				socket = serverSock.accept();
				System.out.println("Nuevo cliente, socke "+socket);
				System.out.println("-----------------------------------");
			} catch (IOException e) {
				System.out.println("Accept failed: " + port);
				System.exit(-1);
			}
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			} catch (IOException e) {
				System.out.println("Exception " + e);
				System.exit(-1);
			}
			boolean end = false;
			while (!end) {
				try {
					line = in.readLine();
					System.out.println("Received from client " + line);
					if (line.compareTo("END") != 0) {
						String cadenaMayuscula = line.toUpperCase();
						String cadenaFinal = (new StringBuffer(cadenaMayuscula)).reverse().toString();
						line=cadenaFinal;
					} else
					{
						line="OK";
						end = true;
					}
					System.out.println("Sending to client "+line);
					System.out.println("-----------------------------------");
					out.println(line);
				} catch (Exception e) {
					System.out.println("Read failed");
					System.exit(-1);
				}
			}
			System.out.println("Closing connection with the client");
			
			in.close();
			out.close();
			socket.close();
			serverSock.close();
			System.out.println("Waiting for a new client");
			System.out.println("-----------------------------------");
		}
	}

}
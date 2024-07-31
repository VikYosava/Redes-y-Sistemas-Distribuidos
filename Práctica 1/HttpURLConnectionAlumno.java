package HttpURLConnectionAlumno;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;

public class HttpURLConnectionAlumno{

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://150.214.108.58:8080/RySD-Web/ReverseServlet?cadena=";
	private static final String POST_URL = "http://150.214.108.58:8080/RySD-Web/ReverseServlet?cadena=";

	private static final String POST_PARAM = "cadena="; /* param=  */

	public static void main(String[] args) throws IOException {

		if (args.length != 0) {
            System.err.println("Usage:  java HttpURLConnectionAlumno ");
            System.out.println("Invalid arguments " + args.length);
            System.exit(1);
        }

         /*
         Obtención del flujo de entrada del teclado. Lee texto en userInput 
         el valor del pa´rametro que enviaremos al recurso
         */
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("Enter text  :");

        userInput = teclado.readLine();
        /*
         Sustituye espacios por +. Necesario para enviar en una petición HTTP
         */
        String cadena = URLEncoder.encode(userInput, "UTF-8");

        /*
        cadena tiene el valor del paramtro que hay que enviar al recurso 
        */
        
		/*Descomentar para tarea 2*/
		sendPOST(cadena);
		System.out.println("POST DONE");

        /*Descomentar para tarea 3 */
		sendGET(cadena);
		System.out.println("GET DONE");

		
	}

	private static void sendGET(String cadena) throws IOException {
		

		/* COMPLETAR con la url reescrita con el parámetro a enviar*/
		String urlRewritten = GET_URL + cadena;

	    /* COMPLETAR Crear objeto URL */
		URL obj = new URL(urlRewritten);

		
		/* COMPLETAR Crear conexión HTTP a la URL creada */
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		/* COMPLETAR Establecer el método de la petición GET*/
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Length", "" + cadena.getBytes().length);
		
		/* COMPLETAR Establecer el encabezado User-Agent */
		con.setRequestProperty("User-Agent", USER_AGENT);


		/* COMPLETAR Obtener el código de estado de la respuesta*/
		int responseCode = con.getResponseCode();

		System.out.println("GET Response Code :: " + responseCode);
		
		System.out.println("GET Connection-Header :: " + con.getHeaderFieldKey(3));

		/* COMPLETAR Obtener la fecha de la petición*/
		String date = new Date(con.getDate()).toString();
		
		System.out.println("GET Date :: " + date);
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			
			/* COMPLETAR Leer cuerpo de la respuesta */
			InputStream cuerpo = con.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					cuerpo));
			String inputLine;

			
			/* Lee el cuerpo de la respuesta */
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String resultado="GET Respuesta :: "+response.toString();
			
			/* Muestra el cuerpo de la respuesta por pantalla*/
			System.out.println(resultado);
		} else {
			System.out.println("GET request not worked");
		}

	}

	private static void sendPOST(String cadena) throws IOException {
		
		/* COMPLETAR Crear objeto URL */
		URL obj = new URL(POST_URL);

		
		/* COMPLETAR Crear conexión HTTP a la URL creada */
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
		/* COMPLETAR Establecer el método de la petición POST*/
		con.setRequestMethod("POST");

		
		/* COMPLETAR Establecer el encabezado User-Agent */
		con.setRequestProperty("User-Agent",USER_AGENT);
		
		// For POST only - START
		
		/*COMPLETAR INDICAR QUE LA PETICIÓN HTTP TIENE CUERPO*/
		con.setDoOutput(true);
		
		/*COMPLETAR CREAR stream para ACCEDER AL CUERPO DE LA PETICIÓN HTTP*/
		OutputStream os = (OutputStream)con.getOutputStream();

		/*COMPLETAR escribir parametro y valor en el cuerpo de la petición*/
		os.write(cadena.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		/* COMPLETAR Obtener el código de estado de la respuesta*/
		
		int responseCode = con.getResponseCode();

		System.out.println("POST Response Code :: " + responseCode);
		
		// El campo que contiene el Connection-Header es el 4º, o [3] en Java
		System.out.println("GET Connection-Header :: " + con.getHeaderFieldKey(3));		
		
		/* COMPLETAR Obtener la fecha de la petición*/
		String date = new Date(con.getDate()).toString();
		
		System.out.println("POST Date :: " + date);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			/* COMPLETAR Leer cuerpo de la respuesta */
			InputStream cuerpo = con.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					cuerpo));
			String inputLine;
			
			/* Lee el cuerpo de la respuesta */
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			String resultado="POST Respuesta :: "+response.toString();
						
			/* Muestra el cuerpo de la respuesta por pantalla*/
			System.out.println(resultado);
		} else {
			System.out.println("POST request not worked");
		}
	}

}
package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	/**
	 * Atributos est�ticos con los 
	 * par�metros de conexi�n.
	 */
	static String bd = "jsp_libreria"; 
	static String port = "3306";
	static String login = "root";
	static String password = "";
	
	/**
	 * Cadena de conexi�n.
	 */
	static String url = "jdbc:mysql://localhost:" + port + "/" + bd;
	
	/**
	 * Almacenar la conexi�n con la bbdd.
	 */
	Connection connection = null;
	
	/**
	 * Constructor.
	 * Conecta f�sicamente con la bbdd.
	 */
	public DBConnection() {
		try {
			// Cargar driver de conexi�n, para conectar
			// f�sicamente con la bbdd.
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Conectar con la bbdd 
			// a partir de la cadena de conexi�n.
			connection = DriverManager.getConnection(url, login, password);
			
			// Comprobar conexi�n (s�lo la 1� vez).
			if (connection == null) {
				System.out.println("La conexi�n a " + bd + " ha fallado");
			} else {
				System.out.println("Conexi�n OK");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Devuelve el valor
	 * de la conexi�n.
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Desconectar.
	 */
	public void desconectar() {
		connection = null;
	}
}

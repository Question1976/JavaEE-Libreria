package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	/**
	 * Atributos estáticos con los 
	 * parámetros de conexión.
	 */
	static String bd = "jsp_libreria"; 
	static String port = "3306";
	static String login = "root";
	static String password = "";
	
	/**
	 * Cadena de conexión.
	 */
	static String url = "jdbc:mysql://localhost:" + port + "/" + bd;
	
	/**
	 * Almacenar la conexión con la bbdd.
	 */
	Connection connection = null;
	
	/**
	 * Constructor.
	 * Conecta físicamente con la bbdd.
	 */
	public DBConnection() {
		try {
			// Cargar driver de conexión, para conectar
			// físicamente con la bbdd.
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Conectar con la bbdd 
			// a partir de la cadena de conexión.
			connection = DriverManager.getConnection(url, login, password);
			
			// Comprobar conexión (sólo la 1º vez).
			if (connection == null) {
				System.out.println("La conexión a " + bd + " ha fallado");
			} else {
				System.out.println("Conexión OK");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Devuelve el valor
	 * de la conexión.
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

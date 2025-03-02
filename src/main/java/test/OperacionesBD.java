package test;

import java.sql.ResultSet;
import java.sql.Statement;

import beans.Libro;
import connection.DBConnection;

public class OperacionesBD {

	public static void main(String[] args) {
		
		// actualizarLibro(1, "Histórica");
		listarLibros();

	}
	
	/**
	 * CRUD => UPDATE | Libro
	 * @param id
	 * @param genero
	 */
	public static void actualizarLibro(int id, String genero) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "UPDATE libros SET genero = '" + genero + "' WHERE id = " + id;
		
		// Con una estructura try/cath/finally:
		try {
			// Crear obj tipo "Statement" para
			// ejecutar consultas sql, a partir
			// de la conexión.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar consulta sql.
			st.execute(sql);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Liberar la conexión.
			con.desconectar();
		}
	}
	
	/**
	 * CRUD => SELECT | Libro
	 * Todos los registros.
	 */
	public static void listarLibros() {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "SELECT * FROM libros";
		
		// Con una estructura try/cath/finally:
		try {
			// Crear obj tipo "Statement" para
			// ejecutar consultas sql, a partir
			// de la conexión.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar consulta sql.
			// Como el SELECT devuelve datos tengo
			// guardarlos en un obj tipo "ResulSet"
			// y almacenar esos registros devueltos.
			ResultSet rs = st.executeQuery(sql);
			
			// Recorrer los registros para 
			// poder tratar los datos devueltos.
			while (rs.next()) {
				// Capturar los valores de los
				// campos de los diferentes registros.
				int id = rs.getInt("id");
				String titulo = rs.getString("titulo");
				String genero = rs.getString("genero");
				String autor = rs.getString("autor");
				int copias = rs.getInt("copias");
				boolean novedad = rs.getBoolean("novedad");
				
				// Con estos datos capturados
				// crear un obj de tipo "Libro",
				// para guardar la info que se capturó antes.
				Libro libro = new Libro(id, titulo, genero, autor, copias, novedad);
				
				// Comprobar
				System.out.println(libro.toString());
				
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Liberar la conexión.
			con.desconectar();
		}
	}

}

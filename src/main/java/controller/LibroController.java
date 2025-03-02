package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import beans.Libro;
import connection.DBConnection;

public class LibroController implements ILibroController {

	@Override
	public String listar(boolean ordenar, String orden) {
		// Instanciar obj tipo "Gson" 
		// para enviar datos "usuario" tipo JSON
		Gson gson = new Gson();
		
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "SELECT * FROM libros";
		
		// Si están ordenados...
		if (ordenar == true) {
			// concatenar a la consulta 
			// parámetros de ordenación SQL
			sql += " ORDER BY genero " + orden;
		}
		
		// Como tengo que devolver muchos libros 
		// tengo que crear un ArrayList donde ir 
		// almacenando esos libros. 
		// Almacenar los libros convertidos a JSON
		List<String> libros = new ArrayList<String>();
		
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
				// Leer/capturar los datos/registros 
				// de la tabla que trae la consulta.
				// Leer los datos de los libros, 
				// los registros que devuelve el SELECT.
				int id = rs.getInt("id");
				String titulo = rs.getString("titulo");
				String genero = rs.getString("genero");
				String autor = rs.getString("autor");
				int copias = rs.getInt("copias");
				boolean novedad = rs.getBoolean("novedad");

				// Con estos datos,  
				// crear un objeto de tipo "Libro" (beans)
				// para guardar la información
				// este "libro" es el que va a devolver 
				// la información al Servlet en JSON.
				Libro libro = new Libro(id, titulo, genero, autor, copias, novedad);

				// Añadir a "libros" el libro convertido a JSON. 
				libros.add(gson.toJson(libro));
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Liberar la conexión.
			con.desconectar();
		}
		
		// Devolver el ArryList convertido a JSON.
		return gson.toJson(libros);
	}

	@Override
	public String alquilar(int id, String username) {
		// Crear fecha en la que
		// se ha alquilado el libro.
		Timestamp fecha = new Timestamp(new Date().getTime());
				
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear sentencia sql
		String sql = "INSERT IGNORE alquiler VALUES ('" + id + "','" + username + "','" + fecha + "')";
		
		try {
			// Crear objeto de tipo Statement 
			// a partir de la conexión de la bbdd
			// para poder ejecutar la consulta
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta sql
			st.executeUpdate(sql);

			// Restar un libro del stock,
			// porque se ha alquilado
			String modificar = modificar(id);
			
			if (modificar.equals("true")) {
				return "true";
			}

		} catch (Exception e) {
			// Si algo falla 
			// mostrar mensaje de la excepción
			System.out.println(e.getMessage());
			
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		return "false";
	}

	@Override
	public String modificar(int id) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();

		// Crear sentencia sql
		String sql = "UPDATE IGNORE libros SET copias = (copias - 1) WHERE id = " + id;
		
		try {
			// Crear objeto de tipo Statement 
			// a partir de la conexión de la bbdd
			// para poder ejecutar la consulta
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta sql
			st.executeUpdate(sql);

			return "true";
			
		} catch (Exception e) {
			// Si algo falla 
			// mostrar mensaje de la excepción
			System.out.println(e.getMessage());
			
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		return "false";
		
	}

	
	@Override
	public String devolver(int id, String username) {				
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear sentencia sql.
		String sql = "DELETE IGNORE FROM alquiler WHERE id = " + id + " AND username = '" + username + "' LIMIT 1";

		try {
			// Crear obj de tipo Statement 
			// a partir de la conexión de la bbdd
			// para poder ejecutar la consulta.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta sql.
			st.executeUpdate(sql);
			
			// LLamar al método...
			this.sumarCantidad(id);

			// Si todo ha ido bien... 
			// devuelvo true.
			return "true";

		} catch (Exception ex) {
			// Si algo falla... 
			// mostrar mensaje de la excepción.
			System.out.println(ex.getMessage());
			
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd.
			con.desconectar();
		}
		
		// Si algo falló... 
		// devuelve false.
		return "false";
		
	}

	
	@Override
	public String sumarCantidad(int id) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear sentencia sql.
		String sql = "UPDATE IGNORE libros SET copias = (SELECT copias FROM libros WHERE id = " + id + ") + 1 WHERE id = " + id;
		
		try {
			// Crear obj de tipo Statement 
			// a partir de la conexión de la bbdd
			// para poder ejecutar la consulta.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta sql.
			st.executeUpdate(sql);
		
			// Si todo ha ido bien... 
			// devuelvo true.
			return "true";

		} catch (Exception ex) {
			// Si algo falla... 
			// mostrar mensaje de la excepción.
			System.out.println(ex.getMessage());
			
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd.
			con.desconectar();
		}
		
		// Si algo falló... 
		// devuelve false.
		return "false";
	}

}

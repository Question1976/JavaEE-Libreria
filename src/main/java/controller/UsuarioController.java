package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import beans.Usuario;
import connection.DBConnection;

public class UsuarioController implements IUsuarioController {

	@Override
	public String login(String username, String contrasena) {
		// Instanciar obj tipo "Gson" 
		// para enviar datos "usuario" tipo JSON
		Gson gson = new Gson();
		
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "SELECT * FROM usuarios WHERE username = '" + username + "' AND contrasena = '" + contrasena + "'";
		
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
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String email = rs.getString("email");
				double saldo = rs.getDouble("saldo");
				boolean premium = rs.getBoolean("premium");
				
				// Con estos datos capturados
				// crear un obj de tipo "Usuario",
				// para guardar la info que se capturó antes.
				// Este "usuario" es el que devuelvo al servlet,
				// y se devuelve como un JSON.
				Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, email, saldo, premium);
				
				// Devolver el obj "usuario" en formato JSON
				return gson.toJson(usuario);
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Liberar la conexión.
			con.desconectar();
		}
		
		// No existe un usuario con esas credenciales,
		// o se haya producido una excepción.
		return "false";
	}

	@Override
	public String register(String username, String contrasena, String nombre, String apellidos, String email,
			double saldo, boolean premium) {
		// Instanciar obj tipo "Gson" 
		// para enviar datos "usuario" tipo JSON
		Gson gson = new Gson();
		
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "INSERT IGNORE INTO usuarios VALUES('" + username + "', '" + contrasena + "', '" + nombre + "', '"
				+ apellidos + "', '" + email + "', '" + saldo + "', '" + premium + "')";
		
		try {
			// Crear obj tipo "Statement" para
			// ejecutar consultas sql, a partir
			// de la conexión.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta
			st.executeUpdate(sql);
			
			// Con estos datos crear un objeto de tipo "Usuario" (beans)
			// para guardar la información
			// este "usuario" es el que va a devolver 
			// la información al Servlet en JSON
			Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, email, saldo, premium);
			
			// Cerrar el Statement
			st.close();
			
			// Llamar al método "toJson" 
			// y pasarle el "usuario" para enviar los datos
			// y convertir el objeto a JSON
			return gson.toJson(usuario);

		} catch (Exception e) {
			// Si se produce una excepción 
			// mostrar el tipo de error
			System.out.println(e.getMessage());

		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		// En caso de que no haya salido del método | no devuelva el JSON, o 
		// no existe un usuario con ese username ni con esa contrasenia
		// o que se haya producido un error al conectar con la bbdd
		return "false";
	}

	@Override
	public String pedir(String username) {
		// Instanciar obj tipo "Gson" 
		// para enviar datos "usuario" tipo JSON
		Gson gson = new Gson();
		
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "SELECT * FROM usuarios WHERE username = '" + username + "'";
		
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
				String contrasena = rs.getString("contrasena");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String email = rs.getString("email");
				double saldo = rs.getDouble("saldo");
				boolean premium = rs.getBoolean("premium");
				
				// Con estos datos capturados
				// crear un obj de tipo "Usuario",
				// para guardar la info que se capturó antes.
				// Este "usuario" es el que devuelvo al servlet,
				// y se devuelve como un JSON.
				Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, email, saldo, premium);
												
				// Llamar al método "toJson" 
				// y pasarle el "usuario" para enviar los datos
				// y convertir el objeto a JSON
				return gson.toJson(usuario);
			}

		} catch (Exception e) {
			// Si se produce una excepción 
			// mostrar el tipo de error
			System.out.println(e.getMessage());

		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		// En caso de que no haya salido del método | no devuelva el JSON, o 
		// no existe un usuario con ese username ni con esa contrasenia
		// o que se haya producido un error al conectar con la bbdd
		return "false";	
	}

	@Override
	public String restarDinero(String username, double nuevoSaldo) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "UPDATE IGNORE usuarios SET saldo = " + nuevoSaldo + " WHERE username = '" + username + "'";
		
		try {
			// Crear obj tipo "Statement" para
			// ejecutar consultas sql, a partir
			// de la conexión.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta
			st.executeUpdate(sql);
			
			return "true";
			

		} catch (Exception e) {
			// Si se produce una excepción 
			// mostrar el tipo de error
			System.out.println(e.getMessage());

		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		// En caso de que no haya salido del método | no devuelva el JSON, o 
		// no existe un usuario con ese username ni con esa contrasenia
		// o que se haya producido un error al conectar con la bbdd
		return "false";	
	}

	
	@Override
	public String modificar(String username, String nuevaContrasena, String nuevoNombre, String nuevosApellidos,
			String nuevoEmail, double nuevoSaldo, boolean nuevoPremium) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "UPDATE IGNORE usuarios SET contrasena = '" + nuevaContrasena + "', nombre = '" + nuevoNombre + "', " 
				+ "apellidos = '" + nuevosApellidos + "', email = '" + nuevoEmail + "', saldo = " + nuevoSaldo + ", premium = ";  
		
		// Si el usuario es premium o no | 1 - 0 
		if (nuevoPremium == true) {
			sql += " 1 ";
		} else {
			sql += " 0 "; 
		}
		
		// Condición de la consulta sql,
		// todo lo de antes se va a producir cuando
		// el "username" sea igual al que estamos
		// pasando.
		sql += " WHRERE username = '" + username + "'";
		
		try {
			// Crear obj tipo "Statement" para
			// ejecutar consultas sql, a partir
			// de la conexión.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta
			st.executeUpdate(sql);
			
			return "true";
			

		} catch (Exception e) {
			// Si se produce una excepción 
			// mostrar el tipo de error
			System.out.println(e.getMessage());

		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		// En caso de que no haya salido del método | no devuelva el JSON, o 
		// no existe un usuario con ese username ni con esa contrasenia
		// o que se haya producido un error al conectar con la bbdd
		return "false";	
	}

	
	@Override
	public String verCopias(String username) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		String sql = "SELECT id,count(*) AS num_copias FROM alquiler WHERE username = '" + username + "' GROUP BY id;";
		
		// Crear diccionario | almacenar pares de ID´s 
		// del libro reservado y número de copias 
		// de ese libro que tengo reservado.
		Map<Integer, Integer> copias = new HashMap<Integer, Integer>();
		
		try {
			// Crear obj tipo "Statement" para
			// ejecutar consultas sql, a partir
			// de la conexión.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consulta
			ResultSet rs = st.executeQuery(sql);
			
			// Recorrer el ResulSet... 
			// mientras haya registros.
			while(rs.next()) {
				// Capturar/leer los datos de la consulta
				int id = rs.getInt("id");
				int num_copias = rs.getInt("num_copias");
				
				// guardar los datos a la colección Map
				copias.put(id,num_copias);
			}
			
			devolverLibros(username, copias);
			
			// Si todo fue bien | devuelve true.
			return "true";
			

		} catch (Exception e) {
			// Si se produce una excepción 
			// mostrar el tipo de error
			System.out.println(e.getMessage());

		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd
			con.desconectar();
		}

		// En caso de que no haya salido del método | no devuelva el JSON, o 
		// no existe un usuario con ese username ni con esa contrasenia
		// o que se haya producido un error al conectar con la bbdd
		return "false";
	}

	
	@Override
	public String devolverLibros(String username, Map<Integer, Integer> copias) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		try {
			// Recorrer el Map | copias
			for (Map.Entry<Integer, Integer> libro: copias.entrySet()) {
				int id = libro.getKey();
				int num_copias = libro.getValue();
				
				// Crear sentencia sql.
				String sql = "Update ignore libros set copias = (Select copias + " + num_copias + " from libros where id = " + id + ") where id = " + id;
				
				// Crear obj de tipo Statement 
				// a partir de la conexión de la bbdd
				// para poder ejecutar la consulta.
				Statement st = con.getConnection().createStatement();
				
				// Ejecutar la consulta
				st.executeUpdate(sql);
			}
			
			// Llamar al método | eliminar
			this.eliminar(username);
									

		} catch (Exception ex) {
			// Si se produce una excepción
			// mostrar el tipo de error.
			System.out.println(ex.getMessage());
			
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd.
			con.desconectar();
		}

		// En caso de que no haya 
		// salido del método.
		return "false";		
	}

	
	@Override
	public String eliminar(String username) {
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consultas sql.
		String sql1 = "DELETE FROM alquiler WHERE username = '" + username + "'";
		String sql2 = "DELETE FROM usuarios WHERE username = '" + username + "'";
		
		try {
			// Crear obj tipo Statement 
			// a partir de la conexión de la bbdd
			// para poder ejecutar la consulta.
			Statement st = con.getConnection().createStatement();
			
			// Ejecutar la consultas.
			st.executeUpdate(sql1);
			st.executeUpdate(sql2);
			
			// Si todo fue bien | devuelve true.
			return "true";

		} catch (Exception e) {
			// Si se produce una excepción 
			// mostrar el tipo de error.
			System.out.println(e.getMessage());
			
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd.
			con.desconectar();
		}

		// En caso de que 
		// no haya salido del método.
		return "false";
	}	

}
	



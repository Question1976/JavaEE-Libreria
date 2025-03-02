package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.google.gson.Gson;

import beans.Alquiler;
import connection.DBConnection;

public class AlquilerController implements IAlquilerController {

	@Override
	public String listarAlquileres(String username) {
		// Instanciar obj tipo "Gson" 
		// para enviar datos "usuario" tipo JSON
		Gson gson = new Gson();
		
		// Crear obj "con" tipo DBConnection
		// para conectar con la bbdd.
		DBConnection con = new DBConnection();
		
		// Crear consulta sql.
		// las reservas se encuentran en la tabla "alquiler"
		// hay que relacionar la tabla "alquiler" con la tabla "libros"
		// para obtener los datos del alquiler de la reserva como de los libros
		// con un INNER JOIN
		// l -> tabla libro | a -> tabla alquiler
		// ON -> relacionar con los ids de ambas tablas
		String sql = "SELECT l.id, l.titulo, l.genero, l.novedad, a.fecha FROM libros l "
				+ "INNER JOIN alquiler a ON l.id = a.id INNER JOIN usuarios u ON a.username = u.username "
				+ "WHERE a.username = '" + username + "'";
		
		// Como tengo que devolver muchos alquileres 
		// tengo que crear un ArrayList donde ir 
		// almacenando esos alquileres.  
		// Almacenar los alquileres convertidos a JSON.
		List<String> alquileres = new ArrayList<String>();
		
		try {
			// Crear objeto de tipo Statement 
			// a partir de la conexión de la bbdd
			// para poder ejecutar la consulta.
			Statement st = con.getConnection().createStatement();
			
			// Al ser un SELECT necesitamos un ResultSet 
			// para guardar el resultado de la consulta.
			ResultSet rs = st.executeQuery(sql);
			
			// Recorrer el ResulSet 
			// Recorrer los registros que trae la consulta.
			while (rs.next()) {
				// Leer/capturar los datos/registros 
				// de la tabla que trae la consulta.
				// Leer los datos de los libros, 
				// los registros que devuelve el SELECT.
				int id = rs.getInt("id");
				String titulo = rs.getString("titulo");
				String genero = rs.getString("genero");				
				boolean novedad = rs.getBoolean("novedad");
				Date fechaAlquiler = rs.getDate("fecha"); 

				// Con estos datos crear un objeto 
				// de tipo "Alquiler" (benas) para 
				// guardar la información este "alquiler" 
				// que es el que va a devolver la 
				// información al Servlet en JSON.				
				Alquiler alquiler = new Alquiler(id, titulo, fechaAlquiler, novedad, genero); 
						
				// Añadir a "alquileres" 
				// el alquiler convertido a JSON.
				alquileres.add(gson.toJson(alquiler));

			}
		} catch (Exception ex) {
			// Si algo falla...  
			// mostrar mensaje de la excepción
			System.out.println(ex.getMessage());
		} finally {
			// Siempre hay que cerrar 
			// la conexión con la bbdd.
			con.desconectar();
		}
		
		// Devolver el ArryList 
		// convertido a JSON.
		return gson.toJson(alquileres);
	
		
		
	}

}

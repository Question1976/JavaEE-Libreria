package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LibroController;

@WebServlet("/ServletLibroAlquilar")
public class ServletLibroAlquilar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletLibroAlquilar() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Llamar a un método del controlador 
		// que va a crear un registro en la tabla
		// de "alquiler".
		
		// Para poder llamar al método "alquilar" 
		// tengo que crear un objeto de tipo "LibroController"
		LibroController libro = new LibroController();
		
		// Recibir/capturar todos los parámetros 
		// que vamos a recibir de la petición AJAX
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
				
		//  Invocar método alquilar del 
		// LibroController.java | alquilar
		String libroStr = libro.alquilar(id, username);
		
		// Establecer la codificación de la respuesta
		response.setContentType("text/html;charset=UTF-8");
		
		// Crear obj tipo PrintWriter para
		// devolver al frontend.
		PrintWriter out = response.getWriter();
		
		// Lo que quiero enviar.
		out.println(libroStr);
				
		// Limpiar buffer.
		out.flush();
		
		// Cerrar.
		out.close();
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

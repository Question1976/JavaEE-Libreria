package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LibroController;

@WebServlet("/ServletLibroDevolver")
public class ServletLibroDevolver extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletLibroDevolver() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// LLamar al método "devolver()" del
		// controlador "LibroController".
		
		// Para poder llamar al método "devolver" 
		// tengo que crear un obj de tipo 
		// "LibroController.java"
		LibroController libro = new LibroController();
		
		// Recibir/capturar todos los parámetros 
		// que vamos a recibir de la petición AJAX.
		String username = request.getParameter("username");
		int id = Integer.parseInt(request.getParameter("id"));
		
		// Invocar método pedir del LibroController.java | devolver()
		String libroStr = libro.devolver(id, username);
		
		// Establecer la codificación de la respuesta.
		response.setContentType("text/html;charset=UTF-8");
		
		// Crear objeto de tipo PrintWriter | para poder enviar.
		PrintWriter out = response.getWriter();
		
		// Le digo que quiero enviar.
		out.println(libroStr);
		
		// Limpiar buffer.
		out.flush();
		
		// === cerrar ===
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

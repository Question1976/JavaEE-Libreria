package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LibroController;

@WebServlet("/ServletLibroListar")
public class ServletLibroListar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletLibroListar() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Llamar a un método del controlador 
		// que va a obtener de la bbdd, listar().
		
		// Para poder llamar al método "listar" 
		// tengo que crear un objeto de tipo "LibroController"
		LibroController libro = new LibroController();
		
		// Recibir/capturar todos los parámetros 
		// que vamos a recibir de la petición AJAX
		boolean ordernar = Boolean.parseBoolean(request.getParameter("ordenar"));
		String orden = request.getParameter("orden"); 
		
		// Invocar método pedir() del LibroController.java | listar
		String libroStr = libro.listar(ordernar, orden);
		
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

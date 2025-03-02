package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AlquilerController;

@WebServlet("/ServletAlquilerListar")
public class ServletAlquilerListar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletAlquilerListar() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// LLamar a un método del controlador "listarAlquileres()"
		// que va a traer al partir del "username"
		// el listado de todos los alquileres/reservas.
		
		// Para poder llamar al método "listarAlquileres()" 
		// tengo que crear un objeto de tipo "AlquilerController".
		AlquilerController alquiler = new AlquilerController();
				
		// Recibir/capturar todos los parámetros 
		// que vamos a recibir de la petición AJAX.
		String username = request.getParameter("username"); 
		
		// Invocar método pedir del 
		// AlquilerController.java | listarAlquileres().
		String alquilerStr = alquiler.listarAlquileres(username);
		
		// Establecer la codificación de la respuesta
		response.setContentType("text/html;charset=UTF-8");
		
		// Crear obj de tipo PrintWriter 
		// para poder enviar.
		PrintWriter out = response.getWriter();
		
		// Le digo que quiero enviar.
		out.println(alquilerStr);
		
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

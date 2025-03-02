package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UsuarioController;

@WebServlet("/ServletUsuarioRestarDinero")
public class ServletUsuarioRestarDinero extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletUsuarioRestarDinero() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Llamar a un método del controlador 
		// que actualice el saldo del usuario
		// quitandole el precio de haber realizado
		// la reserva.
		
		// Para poder llamar al método "restarDinero" 
		// tengo que crear un objeto 
		// de tipo "UsuarioController"
		UsuarioController usuario = new UsuarioController();
				
		// Recibir/capturar todos los parámetros 
		// que vamos a recibir de la petición AJAX
		String username = request.getParameter("username");
		double saldo = Double.parseDouble(request.getParameter("saldo"));
		
		// Invocar método restarDinero() 
		// del UusarioController.java | restarDinero
		String usuarioStr = usuario.restarDinero(username, saldo);
		
		// Establecer la codificación 
		// de la respuesta
		response.setContentType("text/html;charset=UTF-8");
				
		// Crear objeto de tipo PrintWriter 
		// para poder enviar
		PrintWriter out = response.getWriter();
		
		// Le digo que quiero enviar
		out.println(usuarioStr);
		
		// Limpiar buffer
		out.flush();
		
		// Cerrar
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

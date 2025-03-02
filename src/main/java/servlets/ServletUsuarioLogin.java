package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UsuarioController;

@WebServlet("/ServletUsuarioLogin")
public class ServletUsuarioLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public ServletUsuarioLogin() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Crear obj tipo "UsuarioController",
		// para invocar el método "login" del 
		// "UsuarioController".
		UsuarioController usuario = new UsuarioController();
		
		// Capturar los parámetros que se 
		// reciben del cliente.
		String username = request.getParameter("username");
		String contrasena = request.getParameter("contrasena");
		
		// Invocar al método "login"
		// del "UsuarioController".
		String result = usuario.login(username, contrasena);
		
		// Devolver el resultado de la llamada
		// del método "login" al cliente.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
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

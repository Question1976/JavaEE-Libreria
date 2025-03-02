package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UsuarioController;

@WebServlet("/ServletUsuarioRegister")
public class ServletUsuarioRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletUsuarioRegister() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Para poder invocar al controlador
		// crear un usuario de tipo "UsuarioController".
		UsuarioController usuario = new UsuarioController();
		
		// Si las contraseñas del formulario REGISTRAR
		// son correctas, leer los campos capturados.
		String username = request.getParameter("username");
		String contrasena = request.getParameter("contrasena");
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String email = request.getParameter("email");
		double saldo = Double.parseDouble(request.getParameter("saldo"));
		boolean premium = Boolean.parseBoolean(request.getParameter("premium"));
		
		// Guardar lo que retorna la invocación
		// del método "register()" del controlador
		// "UsuarioController".
		// Invocar al método "register()".
		String result = usuario.register(username, contrasena, nombre, apellidos, email, saldo, premium);
				
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
		doGet(request, response);
	}

}

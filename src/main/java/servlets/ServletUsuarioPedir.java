package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UsuarioController;

@WebServlet("/ServletUsuarioPedir")
public class ServletUsuarioPedir extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletUsuarioPedir() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Llamar a un m�todo del controlador que va 
		// a obtener de la bbdd todos los datos de ese usuario.
		
		// Para poder llamar al m�todo "register" 
		// crear un objeto de tipo "UsuarioController"
		UsuarioController usuario = new UsuarioController();
		
		// Recibir/capturar todos los par�metros 
		// que vamos a recibir de la petici�n AJAX,
		// y los guardo en este obj.
		String username = request.getParameter("username");
		
		// Invocar m�todo pedir del "UsuarioController" pedir().
		// Guardar el resultado de lo que me devuelve 
		// la invocaci�n al m�todo "pedir()".
		String usuarioStr = usuario.pedir(username);
		
		// Crear obj tipo PrintWriter para
		// devolver al frontend.
		PrintWriter out = response.getWriter();
		
		// Lo que quiero enviar.
		out.println(usuarioStr);
		
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

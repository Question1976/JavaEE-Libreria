package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UsuarioController;

@WebServlet("/ServletUsuarioEliminar")
public class ServletUsuarioEliminar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletUsuarioEliminar() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Invocar al método "elimiar()" 
		// del "UsuarioController.java".
		
		// Para poder llamar al método "eliminar()" 
		// tengo que crear un obj de tipo "UsuarioController.java"
		UsuarioController usuario = new UsuarioController();
		
		// Recibir/capturar todos los parámetros 
		// que vamos a recibir de la petición AJAX.
		String username = request.getParameter("username");
		
		// Invocar método pedir del UsuarioController.java
		String usuarioStr = usuario.verCopias(username);
		
		// Establecer la codificación de la respuesta.
		response.setContentType("text/html;charset=UTF-8");
		
		// Crear objeto de tipo PrintWriter 
		// para poder enviar.
		PrintWriter out = response.getWriter();
		
		// Le digo que quiero enviar.
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

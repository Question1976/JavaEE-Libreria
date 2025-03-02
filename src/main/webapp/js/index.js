/**
 * LLamada AJAX
 */

// Seleccionar el documento y cuando esté listo
// invocar una función anónima que va a hacer
// lo siguiente...
$(document).ready(function() {
	
	// FORMULARIO pantalla LOGIN
	// Detectar cuando se hace click
	// en el botón de "submit" del formulario.
	// Seleccionar el formulario por us ID, y
	// detectar el evento "submit", cuando se
	// produzca llamar a función anónima que 
	// va a hacer lo siguiente...
	$("#form-login").submit(function(event) {
		
		// Con el "event" (evento) suprimir
		// comportamiento normal del botón
		// de submit.
		event.preventDefault();
		
		// LLamar al método
		autenticarUsuario();
	});
	
	
	// FORMULARIO pantalla REGISTRO
	$("#form-register").submit(function(event){
		
		event.preventDefault();
		registrarUsuario();	
	})
});


function autenticarUsuario() {	
	// Crear variables para capturar
	// los datos de los campos del 
	// formulario de login mediante 
	// el ID de los campos.
	let username = $("#usuario").val();
	let contrasena = $("#contrasena").val();
	
	// Hacer la llamada AJAX
	$.ajax({
		type: "GET",
		dataType: "html",
		url: "./ServletUsuarioLogin",
		data: $.param({
			// parámetros a enviar desde cliente al servidor (Servlet)
			username: username,
			contrasena: contrasena
		}),
		success: function(result){
			// Si obtengo resultado...
			// Parsear el JSON
			let parsedResult = JSON.parse(result);
			// El login fue correcto...
			if(parsedResult != false) {
				$("#login-error").addClass("d-none");
				let username = parsedResult['username']; 
				document.location.href = "home.html?username=" + username;
			} else {
				// Cuando las credenciales del formulario
				// de login son incorrectas... Mostrar mensaje
				// oculto de bootstrap, hacer visible el contenedor.
				$("#login-error").removeClass("d-none");
			}
		}
	});
}

function registrarUsuario() {
	
	// CAPTURAR los valores que se ingresan
	// en los campos del formulario de
	// REGISTRO.
	let username = $("#input-username").val();
	let contrasena = $("#input-contrasena").val();
	let contrasenaConfirmacion = $("#input-contrasena-repeat").val();
	let nombre = $("#input-nombre").val();
	let apellidos = $("#input-apellidos").val();
	let email = $("#input-email").val();
	let saldo = $("#input-saldo").val();
	let premium = $("#input-premium").prop("checked");
	
	// VALIDACIÓN: 
	// Comprobar que la contraseña 
	// y confirmación de contraseña 
	// sean iguales.
	if(contrasena == contrasenaConfirmacion) {
		// Validación correcta.
		// Haremos petición AJAX al servidor, le pasaremos todos los campos
		// del usuario que se va a registrar y el back (servidor) hará un 
		// insert en la bbdd.				
		// Volver a ocultar el div de los errores.
		// Una vez capturados los valores hacer la llamada AJAX.
		$.ajax({
			type: "GET",
			dataType: "html",
			url: "./ServletUsuarioRegister",
			data: $.param({
				// Parámetros a enviar desde cliente al servidor (Servlet)
				username: username,
				contrasena: contrasena,
				nombre: nombre,
				apellidos: apellidos,
				email: email,
				saldo: saldo,
				premium: premium
			}),
			success: function(result) {
				// Parsear el JSON que nos va a llegar 
				// transformar el JSON 
				// para poder recorrerlo
				let parsedResult = JSON.parse(result);
				
				// Si se ha conseguido la inserción en la bbdd
				if (parsedResult != false) {
					// quitar mensaje de error
					$("#register-error").addClass("d-none");
					// acceder/capturar al username que nos está llegando
					let username = parsedResult['username'];
					// redireccionar a la pantalla de "home.html"
					document.location.href = "home.html?username=" + username;
				} else {
					// en caso contrario | ha habido algún error
					$("#register-error").removeClass("d-none");
					// añadir mensaje de error
					$("#register-error").html("Error en el registro del usuario");
				}
			}
		});
		
	} else {
		// En caso de validación incorrecta mostrar mensaje de error.
		// eliminar la clase d-none del div oculto para mostrar el error
		// en el formulario de registro
		$("#register-error").removeClass("d-none");
		// añadir mensaje de error
		$("#register-error").html("Las contraseñas no coinciden");
	}
	
}

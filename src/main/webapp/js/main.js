/**
 * 
 */

// Variable global.
// Con el usuario que esta logado en la aplicación.
var username = new URL(location.href).searchParams.get("username");

// Variable global.
// Para guardar todos los datos del usuario.
var user;

// Cuando el documento esté listo,
// ejecutar una función...
$(document).ready(function() {

	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
	});

	getUsuario().then(function() {		
		// Capturar el valor del "username" 
		// que pasamos a través de la url del navegador.
		// Cambiar atributo por url y pasar valor username.
		$("#mi-perfil-btn").attr("href", "profile.html?username=" + username);
		
		$("#user-saldo").html(user.saldo.toFixed(2) + "€");
		
		// Función | listar los libros disponibles.
		// @param false -> si están ordenados o no
		// @param "ASC" -> si es en orden ascendente o descendente
		// por defecto van a aparecer ordenados y de forma ascendente
		getLibros(false, "ASC");
		
		// Ordenar libros | en Género flecha arriba y flecha abajo.
		$("#ordenar-genero").click(ordenarLibros);
	});

});


// Traer todos los datos
// del usuario de la bbdd.
async function getUsuario(){	
	await $.ajax({
			type:"GET",
			dataType:"html",
			url:"./ServletUsuarioPedir",
			data: $.param({
				username: username,
			}),
			success: function(result){
				let parsedResult = JSON.parse(result);
				
				if (parsedResult != false){
					user = parsedResult;
				}
				else{
					console.log("Error recuperando los datos del usuario");
				}
			}
		});	
}

// Traer todos
// los libros de la bbdd.
function getLibros(ordenar, orden) {
	$.ajax({
		type: "GET",
		dataType: "html",
		url: "./ServletLibroListar",
		data: $.param({
			ordenar: ordenar,
			orden: orden
		}),
		success: function(result) {
			let parsedResult = JSON.parse(result);

			if (parsedResult != false) {
				// método para mostrar la tabla html
				mostrarLibros(parsedResult);
			} else {
				console.log("Error al recuperar los datos de los libros");
			}
		}

	});
}


function mostrarLibros(libros) {

	// Recorrer los libros. 
	// Para cada uno de los objetos transformarlos de JSON a JS,
	// con los que podremos recorrer sus propiedades y para cada
	//  uno de esos libros añadir una fila en la tabla que tenemos 
	// preparada en html para visualizar todos los libros 
	// disponibles en la bbdd.

	// Variable.  
	// Para generar el código html 
	// que voy a mostrar en el cuerpo de la tabla 
	let contenido = "";

	// Recorrer array | jquery - each
	$.each(libros, function(index, libro) {
		
		// decodificar/parsear los json para poder leerlos
		libro = JSON.parse(libro);
		
		// variable | para calcular el precio al que voy a cobrar la reserva del libro
		// el precio depende de si el usuario es premium o no
		let precio;

		// si las copias del libro son mayor que 0
		if (libro.copias > 0) {
			// incluir la fila | existen copia/as
			// si es usuario premium
			if (user.premium) {

				// si el libro es novedad
				if (libro.novedad) {
					// precio 2€ - 10%
					precio = (2 - (2 * 0.1));
					
				} else {
					// ese libro no es novedad | usuario premium | precio 1€ - 10%
					precio = (1 - (1 * 0.1));
				}

			} else {
				// si el usuario no es premium
				if (libro.novedad) {
					// precio 2€, sin descuento
					precio = 2;
				} else {
					// precio 1€, sin descuento
					precio = 1;
				}
			}

			// Crear el contenido dinámicamente 
			// en la tabla tbody del HTML
			contenido += '<tr><th scope="row">' + libro.id + '</th>' + 
				'<td>' + libro.titulo + '</td>' +
				'<td>' + libro.genero + '</td>' +
				'<td>' + libro.autor + '</td>' +
				'<td>' + libro.copias + '</td>' +
				'<td><input type="checkbox" name="novedad" id="novedad' + libro.id + '" disabled '; 
			if (libro.novedad){
				contenido += 'checked';
			}
			contenido += '></td>' +
				'<td>' + precio + '</td>' +
				'<td><button onclick="alquilarLibro(' + libro.id + ',' + precio + ');" class="btn btn-success" ';
			if (user.saldo < precio){
				contenido += ' disabled ';
			}
				
			contenido += '>Reservar</button></td></tr>'
		}

	});
	
	$("#libros-tbody").html(contenido);
}


// Ordenar los libros
// por género con los puntitos
// de fontawesome
function ordenarLibros(){
	
	// Si el icono es fa-sort...
	if ($("#icono-ordenar").hasClass("fa-sort")) {
		// obtener los libros ordenados de forma ascendente
		getLibros(true, "ASC");
		$("#icono-ordenar").removeClass("fa-sort");
		$("#icono-ordenar").addClass("fa-sort-down");
		
		// ordenar de forma descendente
	} else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
		getLibros(true, "DESC");
		$("#icono-ordenar").removeClass("fa-sort-down");
		$("#icono-ordenar").addClass("fa-sort-up");
		
	} else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
		getLibros(false, "ASC");
		$("#icono-ordenar").removeClass("fa-sort-up");
		$("#icono-ordenar").addClass("fa-sort");
	}
	
}


function alquilarLibro(id, precio) {	
	// Llamada AJAX a un Servlet
	$.ajax({
		type: "GET",
		dataType: "html",
		url: "./ServletLibroAlquilar",
		data: $.param({
			id: id,
			username: username
		}),
		success: function(result) {
			let parsedResult = JSON.parse(result);

			if (parsedResult != false) {
				// restar el dinero al usuario
				restarDinero(precio).then(function() {
					// recargar la pagina actual
					location.reload();
				}) 
			} else {
				console.log("Error en la reserva del libro");
			}
		}

	});
	
}


async function restarDinero(precio) {	
	// Llamada AJAX a un Servlet
	await $.ajax({
		type: "GET",
		dataType: "html",
		url: "./ServletUsuarioRestarDinero",
		data: $.param({
			username: username,
			saldo: parseFloat(user.saldo = precio)
		}),
		success: function(result) {
			let parsedResult = JSON.parse(result);

			if (parsedResult != false) {
				console.log("Saldo actualizado");
			} else {
				console.log("Error en el proceso de pago");
			}
		}

	});
}


-- Volcando estructura de base de datos para libreria_udemy
CREATE DATABASE IF NOT EXISTS `jsp_libreria`;
USE `jsp_libreria`;



-- Volcando estructura para tabla libreria_udemy.libros
CREATE TABLE IF NOT EXISTS `libros` (
  `id` int(11) NOT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `genero` varchar(100) DEFAULT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `copias` int(11) DEFAULT NULL,
  `novedad` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


INSERT INTO `libros` (`id`, `titulo`, `genero`, `autor`, `copias`, `novedad`) VALUES
	(1, 'Robinson Crusoe', 'Aventuras', 'Daniel Defoe', 4, 0),
	(2, 'Los miserables', 'Histórica', 'Victor Hugo', 4, 1),
	(3, 'Viaje al centro de la tierra', 'Aventuras', 'Julio Verne', 3, 0),
	(4, '1984', 'Ciencia Ficción', 'George Orwell', 2, 0),
	(5, 'Los pilares de la tierra', 'Histórica', 'Ken Follet', 10, 1),
	(6, 'Un mundo feliz', 'Ciencia Ficción', 'Aldous Huxley', 6, 0),
	(7, 'Cien años de soledad', 'Aventuras', 'Gabriel García Márquez', 10, 0),
	(8, 'Orgullo y prejuicio', 'Romántica', 'Jane Austen', 4, 0),
	(9, 'El Señor de los Anillos', 'Ciencia Ficción', 'J.R. Tolkien', 8, 1),
	(10, 'El sueño eterno', 'Policiaca', 'Raymond Chandler', 4, 1),
	(11, 'Pensar rápido, pensar despacio', 'Científica', 'Daniel Kahneman', 4, 1),
	(12, 'Asesinato en el Orient Express', 'Policiaca', 'Agatha Christie', 5, 0),
	(13, 'El resplandor', 'Terror', 'Stephen King', 8, 0),
	(14, 'Sapiens: de animales a dioses', 'Científica', 'Yuval Noah Harari', 6, 0),
	(15, 'El exorcista', 'Terror', 'William Peter Blatty', 3, 0),
	(16, 'Steve Jobs', 'Biografía', 'Walter Isaacson', 6, 0),
	(17, 'Elon Musk', 'Biografía', 'Ashlee Vance', 2, 1),
	(18, 'El silencio de los corderos', 'Terror', 'Thomas Harris', 3, 0),
	(19, 'El guardián entre el centeno', 'Romántica', 'J.D. Salinger', 4, 0),
	(20, 'El señor de las moscas', 'Aventuras', 'William Golding', 6, 0);


-- Volcando estructura para tabla libreria_udemy.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `username` varchar(100) NOT NULL,
  `contrasena` varchar(100) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `saldo` double(22,2) DEFAULT NULL,
  `premium` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB;


-- Volcando estructura para tabla libreria_udemy.alquiler
CREATE TABLE IF NOT EXISTS `alquiler` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `fecha` datetime NOT NULL,
  PRIMARY KEY (`id`,`username`,`fecha`) USING BTREE,
  KEY `FK__usuarios` (`username`),
  CONSTRAINT `FK__libros` FOREIGN KEY (`id`) REFERENCES `libros` (`id`),
  CONSTRAINT `FK__usuarios` FOREIGN KEY (`username`) REFERENCES `usuarios` (`username`)
) ENGINE=InnoDB;


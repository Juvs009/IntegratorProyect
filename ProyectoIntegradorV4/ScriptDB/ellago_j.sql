-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-01-2023 a las 19:08:31
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 7.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ellago_j`
--
CREATE DATABASE IF NOT EXISTS `ellago_j` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ellago_j`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumnos`
--

CREATE TABLE `alumnos` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `Apellido` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `Nota` double(5,1) NOT NULL,
  `Curso` varchar(3) COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Volcado de datos para la tabla `alumnos`
--

INSERT INTO `alumnos` (`ID`, `Nombre`, `Apellido`, `Nota`, `Curso`) VALUES
(1, 'Javier', 'Portela', 9.0, 'DAM'),
(2, 'Javier', 'Portela', 8.2, 'dam'),
(3, 'Nicholas', 'Apellido', 9.3, 'DAW'),
(4, 'pepe', 'carlos', 2.0, 'dam'),
(6, 'Pepe', 'Pepa', 7.0, 'DAW'),
(7, 'Pepa', 'Loreta', 6.0, 'DAW'),
(8, 'Jose', 'Martinez', 7.0, 'DAW'),
(9, 'Pepe', 'Popo', 4.0, 'DAW'),
(10, 'pepe', 'pepa', 6.0, 'DAM'),
(11, 'dd', 'dd', 2.0, 'dd'),
(12, 'Javier', 'Portela', 10.0, 'DAM'),
(13, 'Javier', 'Portela', 10.0, 'DAM'),
(14, 'Roberto', 'Portela', 7.0, 'DAM'),
(15, 'Javi', 'PP', 6.0, 'DAM'),
(16, 'Javier', 'Portela', 9.0, 'DAM'),
(17, 'Jaime', 'Portela', 5.0, 'DAW'),
(18, 'Pepa', 'Pepe', 2.0, 'DAM');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumnos`
--
ALTER TABLE `alumnos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=231;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

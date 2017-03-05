-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-03-2017 a las 20:51:09
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `consulta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consulta`
--

CREATE TABLE `consulta` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `horario` varchar(50) NOT NULL,
  `nombre_profesional` varchar(50) NOT NULL,
  `num_consulta` varchar(50) NOT NULL,
  `visible` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `consulta`
--

INSERT INTO `consulta` (`id`, `nombre`, `horario`, `nombre_profesional`, `num_consulta`, `visible`) VALUES
(1, 'Consulta de traumatologia', '08:00h a 14:00h', 'Maria Hernandez', 'Consulta numero 7', 'si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `dni` varchar(9) NOT NULL,
  `num_seg_social` varchar(20) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `edad` int(2) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `visible` varchar(5) NOT NULL,
  `consulta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`id`, `nombre`, `apellidos`, `dni`, `num_seg_social`, `direccion`, `telefono`, `edad`, `sexo`, `visible`, `consulta`) VALUES
(2, 'Antonio', 'Duran Moraga', '78586623Q', '45789632147', 'C/Gran via numero 5', '645781024', 24, 'Hombre', 'si', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientesintoma`
--

CREATE TABLE `pacientesintoma` (
  `id` int(11) NOT NULL,
  `id_paciente` int(11) NOT NULL,
  `id_sintoma` int(11) NOT NULL,
  `visible` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pacientesintoma`
--

INSERT INTO `pacientesintoma` (`id`, `id_paciente`, `id_sintoma`, `visible`) VALUES
(1, 2, 1, 'si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sintoma`
--

CREATE TABLE `sintoma` (
  `id` int(11) NOT NULL,
  `breve_descripcion` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `nivel_gravedad` varchar(50) NOT NULL,
  `visible` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sintoma`
--

INSERT INTO `sintoma` (`id`, `breve_descripcion`, `descripcion`, `nivel_gravedad`, `visible`) VALUES
(1, 'Fiebre', 'Fiebre 38-40', 'Alto', 'si'),
(2, 'Dolor muscular', 'Dolor muscular', 'Medio', 'si');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pc_paciente` (`consulta`);

--
-- Indices de la tabla `pacientesintoma`
--
ALTER TABLE `pacientesintoma`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ps_paciente` (`id_paciente`),
  ADD KEY `fk_ps_sintoma` (`id_sintoma`);

--
-- Indices de la tabla `sintoma`
--
ALTER TABLE `sintoma`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consulta`
--
ALTER TABLE `consulta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `paciente`
--
ALTER TABLE `paciente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `pacientesintoma`
--
ALTER TABLE `pacientesintoma`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `sintoma`
--
ALTER TABLE `sintoma`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `fk_pc_paciente` FOREIGN KEY (`consulta`) REFERENCES `consulta` (`id`);

--
-- Filtros para la tabla `pacientesintoma`
--
ALTER TABLE `pacientesintoma`
  ADD CONSTRAINT `fk_ps_paciente` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id`),
  ADD CONSTRAINT `fk_ps_sintoma` FOREIGN KEY (`id_sintoma`) REFERENCES `sintoma` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

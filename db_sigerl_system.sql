-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 16-11-2025 a las 00:35:00
-- Versión del servidor: 8.4.3
-- Versión de PHP: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_sigerl_system`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbladmin`
--

CREATE TABLE `tbladmin` (
  `IDAdmin` int NOT NULL,
  `NameAdmin` varchar(50) NOT NULL,
  `LastNameAdmin` varchar(50) NOT NULL,
  `PhoneAdmin` varchar(20) DEFAULT NULL,
  `EmailAdmin` varchar(100) NOT NULL,
  `PasswordAdmin` varchar(255) NOT NULL,
  `ProjectAdmin` varchar(100) NOT NULL,
  `Permissions` tinyint(1) NOT NULL DEFAULT '1',
  `CantRequestAdmin` int NOT NULL DEFAULT '0',
  `AreaAdmin` varchar(100) DEFAULT NULL,
  `RangeAdmin` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `tbladmin`
--

INSERT INTO `tbladmin` (`IDAdmin`, `NameAdmin`, `LastNameAdmin`, `PhoneAdmin`, `EmailAdmin`, `PasswordAdmin`, `ProjectAdmin`, `Permissions`, `CantRequestAdmin`, `AreaAdmin`, `RangeAdmin`) VALUES
(1, 'Julian Felipe', 'Forero Villa', '', 'Julix4212@SIGERL.io', 'Olakeace333*', 'SIGERL System', 1, 0, 'Java Developer', '1'),
(2, '12', '12', '', '12@12.com', '1234', 'Viral Load Monitoring', 1, 0, 'Molecular Biology', '1'),
(3, 'pedro', 'Mcgregor', '', 'PedroMc@gmail.com', 'Pedro90', 'Cellular Imaging Workflow', 1, 0, 'Molecular Biology', '1'),
(4, 'Maira', 'Perez', '', 'MairaPerez@gmail.com', '1234', 'SIGERL System', 1, 0, 'Java Developer', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbluser`
--

CREATE TABLE `tbluser` (
  `IDUser` int NOT NULL,
  `NameUser` varchar(50) NOT NULL,
  `LastNameUser` varchar(50) NOT NULL,
  `PhoneUser` varchar(20) NOT NULL,
  `EmailUser` varchar(100) NOT NULL,
  `PasswordUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ProjectUser` varchar(100) NOT NULL,
  `Permissions` tinyint(1) NOT NULL DEFAULT '0',
  `CantRequestUser` int NOT NULL DEFAULT '0',
  `AreaUser` varchar(100) NOT NULL,
  `RolUser` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `tbluser`
--

INSERT INTO `tbluser` (`IDUser`, `NameUser`, `LastNameUser`, `PhoneUser`, `EmailUser`, `PasswordUser`, `ProjectUser`, `Permissions`, `CantRequestUser`, `AreaUser`, `RolUser`) VALUES
(1, 'Julian Felipe', 'Forero Villa', '', 'Julix4212@gmail.com', 'Olakeace333*', 'SIGERL System', 0, 0, 'Java Developer', 'BackEnd'),
(2, 'Pepe', 'Lepu', '', 'Pepe@gmail.com', '1234', 'Microbial Contamination Control', 0, 0, 'Microbiology', 'Research Assistant'),
(3, 'Luna', 'Pascal', '', 'Lunera@hotmail.com', 'Lunita69', 'Cellular Imaging Workflow', 0, 0, 'Genetics', 'Biochemist'),
(4, 'Penelope', 'Paralepipedo', '', 'Pelele@gmail.com', '12345', 'Genetic Mutation Tracking', 0, 0, 'Molecular Biology', 'Biochemist'),
(5, 'Carlos', 'Villagran', '', 'Cavi@gmail.com', '123456', 'Field Sampling Operations', 0, 0, 'Quality Control (QC)', 'Project Coordinator');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tbladmin`
--
ALTER TABLE `tbladmin`
  ADD PRIMARY KEY (`IDAdmin`);

--
-- Indices de la tabla `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`IDUser`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tbladmin`
--
ALTER TABLE `tbladmin`
  MODIFY `IDAdmin` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `IDUser` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
